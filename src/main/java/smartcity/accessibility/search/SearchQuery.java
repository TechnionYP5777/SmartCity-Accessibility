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
import com.teamdev.jxmaps.swing.MapView;


import com.teamdev.jxmaps.LatLng;


/**
 * Author Kolikant
 */
public class SearchQuery {

	public enum SearchStage{
		NotRunning,
		Running,
		Done,
		Failed;
		
		private static SearchStage[] allValues = values();
		public static SearchStage fromOrdinal(int i) {return allValues[i];}
	}

	public static final String EmptyList = "[]";
	public static final String isThisAdressSpliter ="<-This is Adress: ";
	
	String queryString;
	boolean isAdress;
	final AtomicInteger searchStatus;

	private SearchQuery(String parsedQuery) {
		String[] a = parsedQuery.split(isThisAdressSpliter);
		isAdress = Boolean.parseBoolean(a[0]);
		this.queryString = a[1];
		searchStatus = new AtomicInteger();
		SetSearchStatus(SearchStage.NotRunning);
	}
	
	public static SearchQuery adressSearch(String adress){
		return new SearchQuery(Boolean.toString(true)+isThisAdressSpliter+adress);
	}

	public static SearchQuery freeTextSearch(String freeText){
		return new SearchQuery(Boolean.toString(false)+isThisAdressSpliter+freeText);
	}

	private void SetSearchStatus(SearchStage s) {
		searchStatus.set(s.ordinal());
	}

	public void waitOnSearch() {
		while (searchStatus.get() == SearchStage.Running.ordinal())
			//System.out.println("waiting...");
			;
	}

	private SearchQueryResult Search(GeocoderRequest r, MapView v) {
		SetSearchStatus(SearchStage.Running);
		List<GeocoderResult> results = new ArrayList<GeocoderResult>();
		Geocoder g = v.getServices().getGeocoder();
		g.geocode(r, new GeocoderCallback(v.getMap()) {
			@Override
			public void onComplete(GeocoderResult[] rs, GeocoderStatus s) {
				System.out.println("arrived to search on complete");
				if (s != GeocoderStatus.OK){
					SetSearchStatus(SearchStage.Failed);
					return;	
				}
				results.add(rs[0]);
				SetSearchStatus(SearchStage.Done);
			}

		});
		return new SearchQueryResult(results, v.getMap());
	}

	@SuppressWarnings("deprecation")
	public SearchQueryResult SearchByAddress(MapView v) {
		GeocoderRequest request = new GeocoderRequest(v.getMap());
		request.setAddress(queryString);
		return Search(request, v);

	}
	

/*	public SearchQueryResult SearchByFreeText(MapView mapView) {
		GeocoderRequest request = new GeocoderRequest(v.getMap());
		request.(adress);
		return Search(request, v);
	}*/

	/**
	 * Koral Chapnik
	 */
	@SuppressWarnings("deprecation")
	public SearchQueryResult searchByCoordinates(MapView v, LatLng c) {
		GeocoderRequest request = new GeocoderRequest(v.getMap());
		request.setLocation(c);
		return Search(request, v);
	}



	@Override
	public String toString() {
		return Boolean.toString(isAdress)+isThisAdressSpliter+queryString;
	}

	public static SearchQuery toQuery(String s) {
		return new SearchQuery(s);
	}

	public static String QueriesList2String(List<SearchQuery> qs) {
		List<String> sl = new ArrayList<String>();
		for (SearchQuery q : qs)
			sl.add((q + ""));
		return sl + "";
	}

	public static List<SearchQuery> String2QueriesList(String favouriteQueries) {
		String p1 = favouriteQueries.replace("[", "").replace("]", "");
		List<SearchQuery> $ = new ArrayList<SearchQuery>();
		String[] split;
		if (p1.isEmpty())
			return $;
		split = p1.split(", ");
		for (String s : split)
			$.add(SearchQuery.toQuery(s));
		return $;
	}




}