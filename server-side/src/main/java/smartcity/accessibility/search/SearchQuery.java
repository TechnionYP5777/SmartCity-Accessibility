package smartcity.accessibility.search;

/**
 * @author Kolikant
 *
 */
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.PendingResult.Callback;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

import smartcity.accessibility.exceptions.illigalString;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.mapmanagement.Location.LocationSubTypes;
import smartcity.accessibility.mapmanagement.LocationBuilder;

/**
 * Author Kolikant
 */
public class SearchQuery {
	private static final String DefaultQueryName = null;
	private static final String API_KEY = "AIzaSyAxFHT3dheK_oTyQu6lERytdi83uaqg5m8";
	private static Logger logger = LoggerFactory.getLogger(SearchQuery.class);
	private static final GeoApiContext context;
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
	
	static {
		context = new GeoApiContext().setApiKey(API_KEY);
	}

	protected SearchQuery(String parsedQuery) {
		setQueryParts(parsedQuery);
		searchStatus = new AtomicInteger();
		SetSearchStatus(SearchStage.NotRunning);
	}

	private void setQueryParts(String parsedQuery) {
		String[] a = parsedQuery.split(thisIsTheStringSplitter);
		isAdress = Boolean.parseBoolean(a[0]);
		this.queryString = a[1];
		this.QueryName = a[2] == "null" ? null : a[2];
	}
	
	public static GeoApiContext getContext(){
		return context;
	}

	public static SearchQuery adressSearch(String adress) throws illigalString {
		if (adress.contains(thisIsTheStringSplitter))
			throw new illigalString();
		return new SearchQuery(
				Boolean.toString(true) + thisIsTheStringSplitter + adress + thisIsTheStringSplitter + DefaultQueryName);
	}

	public static SearchQuery TypeSearch(String Type) throws illigalString {
		if (Type.contains(thisIsTheStringSplitter))
			throw new illigalString();
		return new SearchQuery(
				Boolean.toString(false) + thisIsTheStringSplitter + Type + thisIsTheStringSplitter + DefaultQueryName);
	}

	public static SearchQuery makeDummy(String dummyName) {
		String dummyString = Boolean.toString(false) + thisIsTheStringSplitter + "dummdumm" + thisIsTheStringSplitter
				+ "dummdumm";
		return (new SearchQuery(dummyString)).RenameSearchQuery(dummyName);
	}

	public static SearchQuery toQuery(String ¢) {
		return new SearchQuery(¢);
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

	public SearchQuery RenameSearchQuery(String QueryName) {
		this.QueryName = QueryName;
		return this;
	}

	public String getName() {
		return this.QueryName;
	}

	public void ChangeQuery(String NewQuery) {
		setQueryParts(Boolean.toString(isAdress) + thisIsTheStringSplitter + NewQuery);
		SetSearchStatus(SearchStage.NotRunning);
	}

	public String getQuery() {
		return this.queryString;
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
	
	protected SearchQueryResult Search(String address) {
		return !isAdress ? null : adressSearch(address, null);
	}
	
	protected SearchQueryResult Search(LatLng latlng) {
		return !isAdress ? null : adressSearch(null, latlng);
	}

	protected SearchQueryResult Search(Location initLocation, int radius) {
		return isAdress ? null : typeSearch(initLocation, radius);
	}

	protected SearchQueryResult Search(String initLocation, int radius) throws illigalString, InterruptedException {
		SearchQuery s1 = adressSearch(initLocation);
		SearchQueryResult sqr1 = s1.SearchByAddress();
		s1.waitOnSearch();
		return isAdress ? null : typeSearch(sqr1.getLocations().get(0), radius);
	}

	private SearchQueryResult typeSearch(Location initLocation, int radius) {
		SetSearchStatus(SearchStage.Running);
		List<String> kindsOfLocations = new ArrayList<>();
		if (queryString.equals("")) {
			for (LocationSubTypes lst : LocationSubTypes.values()) {
				kindsOfLocations.add(lst.getSearchType());
			}
		} else {
			kindsOfLocations.add(queryString);
		}
		logger.debug("finding nearby places with {}, radius {}, kinds {}", initLocation, radius, kindsOfLocations);
		NearbyPlacesSearch.findNearbyPlaces(initLocation, radius, kindsOfLocations, res -> {
			places = res;
			SetSearchStatus(SearchStage.Done);
			wakeTheWaiters();

		});
		try {
			waitOnSearch();
		} catch (InterruptedException e) {
			logger.error("type search wait interrupted {}", e);
		}
		return new SearchQueryResult(places);
	}

	private SearchQueryResult adressSearch(String address, LatLng coords) {
		SetSearchStatus(SearchStage.Running);
		List<Location> $ = new ArrayList<>();
		GeocodingApiRequest req = GeocodingApi.newRequest(context);
		if (address != null){
			req = req.address(address);
		}
		if (coords != null){
			req = req.latlng(coords);
		}
		req.setCallback(new Callback<GeocodingResult[]>() {
			
			@Override
			public void onResult(GeocodingResult[] arg0) {
				logger.debug("arrived to search on complete");
				LatLng l = arg0[0].geometry.location;
				Location f = new LocationBuilder().setCoordinates(l).setName(arg0[0].formattedAddress).build();
				$.add(f);
				SetSearchStatus(SearchStage.Done);
				wakeTheWaiters();
			}
			
			@Override
			public void onFailure(Throwable arg0) {
				SetSearchStatus(SearchStage.Failed);
				wakeTheWaiters();
				logger.error("Address Search failed with error {}", arg0);				
			}
		});
		try {
			waitOnSearch();
		} catch (InterruptedException e) {
			logger.error("address search wait interrupted {}", e);
		}
		return new SearchQueryResult($);
	}
	
	public SearchQueryResult SearchByAddress() {
		return Search(queryString);
	}

	/**
	 * Koral Chapnik
	 */
	public SearchQueryResult searchByCoordinates(LatLng c) {
		return Search(c);
	}

	public SearchQueryResult searchByType(Location initLocation, int radius) {
		return Search(initLocation, radius);
	}

	public SearchQueryResult searchByType(String initLocation, int radius)
			throws illigalString, InterruptedException {
		return Search(initLocation, radius);
	}

	@Override
	public String toString() {
		return Boolean.toString(isAdress) + thisIsTheStringSplitter + queryString + thisIsTheStringSplitter
				+ this.QueryName;
	}

	@Override
	public boolean equals(final Object o) {
		return o == this || (o instanceof SearchQuery && this.QueryName.equals(((SearchQuery) o).QueryName));
	}

}