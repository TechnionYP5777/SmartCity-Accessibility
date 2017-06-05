package smartcity.accessibility.search;

/**
 * @author Kolikant
 *
 */
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.teamdev.jxmaps.Geocoder;
import com.teamdev.jxmaps.GeocoderCallback;
import com.teamdev.jxmaps.GeocoderRequest;
import com.teamdev.jxmaps.GeocoderResult;
import com.teamdev.jxmaps.GeocoderStatus;
import smartcity.accessibility.mapmanagement.LatLng;
import com.teamdev.jxmaps.MapViewOptions;
import com.teamdev.jxmaps.swing.MapView;

import smartcity.accessibility.exceptions.illigalString;
import smartcity.accessibility.mapmanagement.JxMapsFunctionality;
import smartcity.accessibility.mapmanagement.JxMapsFunctionality.ExtendedMapView;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.mapmanagement.Location.LocationSubTypes;
import smartcity.accessibility.mapmanagement.LocationBuilder;

/**
 * Author Kolikant
 */
public class SearchQuery {
	private static final String DefaultQueryName = null;
	private MapView mapview;
	public List<Location> places; // the nearby places result
	public enum SearchStage {
		NotRunning, Running, Done, Failed;

		private static SearchStage[] allValues = values();

		public static SearchStage fromOrdinal(int ¢) {
			return allValues[¢];
		}
	}
	

	public static final String EmptyList = "[]";
	protected static final String thisIsTheStringSplitter = "--This is a String Splitter--";

	String QueryName;
	String queryString;
	boolean isAdress;
	final AtomicInteger searchStatus;

	protected SearchQuery(String parsedQuery, MapView mapview) {
		setQueryParts(parsedQuery);
		searchStatus = new AtomicInteger();
		SetSearchStatus(SearchStage.NotRunning);
		this.mapview = mapview;
	}

	private void setQueryParts(String parsedQuery) {
		String[] a = parsedQuery.split(thisIsTheStringSplitter);
		isAdress = Boolean.parseBoolean(a[0]);
		this.queryString = a[1];
		this.QueryName = a[2] == "null"? null : a[2];
	}

	public SearchQuery RenameSearchQuery(String QueryName){
		this.QueryName = QueryName;
		return this;
	}
	
	public String getName(){
		return this.QueryName;
	}
	
	public void ChangeQuery(String NewQuery){
		setQueryParts(Boolean.toString(isAdress) + thisIsTheStringSplitter + NewQuery);
		SetSearchStatus(SearchStage.NotRunning);
	}
	
	public String getQuery(){
		return this.queryString;
	}
	
	public static SearchQuery adressSearch(String adress, MapView mapview) throws illigalString {
		if(adress.contains(thisIsTheStringSplitter))
			throw new illigalString();
		return new SearchQuery(Boolean.toString(true) + thisIsTheStringSplitter + adress + thisIsTheStringSplitter + DefaultQueryName, mapview);
	}

	public static SearchQuery TypeSearch(String Type, MapView mapview) throws illigalString {
		if(Type.contains(thisIsTheStringSplitter))
			throw new illigalString();
		return new SearchQuery(Boolean.toString(false) + thisIsTheStringSplitter + Type + thisIsTheStringSplitter + DefaultQueryName, mapview);
	}

	protected void SetSearchStatus(SearchStage ¢) {
		searchStatus.set(¢.ordinal());
	}

	public synchronized void waitOnSearch() throws InterruptedException {
		while (searchStatus.get() == SearchStage.Running.ordinal())
			wait();
	}

	protected synchronized void wakeTheWaiters() {
		notifyAll();
	}

	protected SearchQueryResult Search(GeocoderRequest r, MapView v) {
		return !isAdress ? null : adressSearch(r, v);
	}

	protected SearchQueryResult Search(Location initLocation, double radius) {
		return isAdress ? null : typeSearch(initLocation, radius);
	}

	protected SearchQueryResult Search(String initLocation, double radius) throws illigalString, InterruptedException {
        SearchQuery s1 = adressSearch(initLocation, this.mapview);        
        JxMapsFunctionality.waitForMapReady((ExtendedMapView) this.mapview);        
        SearchQueryResult sqr1= s1.SearchByAddress(this.mapview);        
		s1.waitOnSearch();     
		return isAdress ? null : typeSearch(sqr1.getLocations().get(0), radius);
	}
	
	
	private SearchQueryResult typeSearch(Location initLocation, double radius) {
		SetSearchStatus(SearchStage.Running);
		List<String> kindsOfLocations = new ArrayList<String>();
		if(queryString.equals("")){
			for (LocationSubTypes lst : LocationSubTypes.values()) {
				kindsOfLocations.add(lst.getSearchType());
				}
		}else{
			kindsOfLocations.add(queryString);
		}
		MapViewOptions options = new MapViewOptions();
		options.importPlaces();
		//MapView mapView = JxMapsFunctionality.getMapView();
		JxMapsFunctionality.waitForMapReady((ExtendedMapView) this.mapview);
		NearbyPlacesSearch.findNearbyPlaces(this.mapview, initLocation, radius, kindsOfLocations,
				 ¢-> {
						places = ¢;
						SetSearchStatus(SearchStage.Done);
						wakeTheWaiters();
					
				});
		try {
			waitOnSearch();
		} catch (InterruptedException ¢) {
			¢.printStackTrace();
		}
		return new SearchQueryResult(places);
	}

	private SearchQueryResult adressSearch(GeocoderRequest r, MapView v) {
		SetSearchStatus(SearchStage.Running);
		List<Location> $ = new ArrayList<Location>();
		Geocoder g = v.getServices().getGeocoder();
		g.geocode(r, new GeocoderCallback(v.getMap()) {
			@Override
			public void onComplete(GeocoderResult[] rs, GeocoderStatus s) {
				System.out.println("arrived to search on complete");
				if (s != GeocoderStatus.OK) {
					SetSearchStatus(SearchStage.Failed);
					wakeTheWaiters();
					return;
				}
				LatLng l = rs[0].getGeometry().getLocation();
				Location f = new LocationBuilder().setCoordinates(l).build();
				f.setName(rs[0].getFormattedAddress());
				$.add(f);
				SetSearchStatus(SearchStage.Done);
				wakeTheWaiters();
			}

		});
		return new SearchQueryResult($);
	}

	@SuppressWarnings("deprecation")
	public SearchQueryResult SearchByAddress(MapView ¢) {
		GeocoderRequest $ = new GeocoderRequest(¢.getMap());
		$.setAddress(queryString);
		return Search($, ¢);

	}

	/**
	 * Koral Chapnik
	 */
	@SuppressWarnings("deprecation")
	public SearchQueryResult searchByCoordinates(MapView v, LatLng c) {
		GeocoderRequest $ = new GeocoderRequest(v.getMap());
		$.setLocation(c);
		return Search($, v);
	}

	public SearchQueryResult searchByType(Location initLocation, double radius) {
		return Search(initLocation, radius);
	}

	public SearchQueryResult searchByType(String initLocation, double radius) throws illigalString, InterruptedException {
		return Search(initLocation, radius);
	}
	
	@Override
	public String toString() {
		return Boolean.toString(isAdress) + thisIsTheStringSplitter + queryString + thisIsTheStringSplitter + this.QueryName;
	}

	public static SearchQuery toQuery(String ¢) {
		MapView mapView1 = null;
		return new SearchQuery(¢, mapView1);
	}

	public static String QueriesList2String(List<SearchQuery> qs) {
		List<String> $ = new ArrayList<String>();
		for (SearchQuery ¢ : qs)
			$.add(¢ + "");
		return $ + "";
	}

	public static List<SearchQuery> String2QueriesList(String favouriteQueries) {
		String p1 = favouriteQueries.replace("[", "").replace("]", "");
		List<SearchQuery> $ = new ArrayList<SearchQuery>();
		String[] split;
		if (p1.isEmpty())
			return $;
		split = p1.split(", ");
		for (String ¢ : split)
			$.add(SearchQuery.toQuery(¢));
		return $;
	}
	
	public static SearchQuery makeDummy(String dummyName){
		String dummyString = Boolean.toString(false) + thisIsTheStringSplitter + "dummdumm" + thisIsTheStringSplitter + "dummdumm";
		MapView mapView1 = null;
		return (new SearchQuery(dummyString, mapView1)).RenameSearchQuery(dummyName);
	}
	
	@Override
	public boolean equals(final Object o) {
		return o == this || (o instanceof SearchQuery && this.QueryName.equals(((SearchQuery) o).QueryName));
	}

}