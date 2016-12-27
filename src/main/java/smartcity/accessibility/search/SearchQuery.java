package smartcity.accessibility.search;

/**
 * @author Kolikant
 *
 */
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.teamdev.jxmaps.Geocoder;
import com.teamdev.jxmaps.GeocoderCallback;
import com.teamdev.jxmaps.GeocoderRequest;
import com.teamdev.jxmaps.GeocoderResult;
import com.teamdev.jxmaps.GeocoderStatus;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.swing.MapView;

import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.socialnetwork.BestReviews;
import smartcity.accessibility.socialnetwork.User.Privilege;
import smartcity.acessibility.jxMapsFunctionality.JxMapsFunctionality;
import smartcity.acessibility.jxMapsFunctionality.JxMapsFunctionality.helper2;

import com.teamdev.jxmaps.LatLng;

public class SearchQuery {

	public enum SearchStage{
		NotRunning,
		Running,
		Done;
		/*
		 * Kolikant
		 */
		private static SearchStage[] allValues = values();
	    public static SearchStage fromOrdinal(int n) {return allValues[n];}
	}
	
	/**
	 * Author Kolikant
	 */
	private static final long serialVersionUID = 1L;

	public static final String EmptyList = "[]";
	//public static MapView mapView;
	String adress;
	final AtomicInteger searchStatus;

	public SearchQuery(String parsedQuery) {
		this.adress = parsedQuery;
		searchStatus = new AtomicInteger();
		SetSearchStatus(SearchStage.NotRunning);
	}


	private void SetSearchStatus(SearchStage ss) {
		searchStatus.set(ss.ordinal());
	}
	
	public void waitOnSearch() {
		while (searchStatus.get() == SearchStage.Running.ordinal())
			;
	}

	private SearchQueryResult Search(GeocoderRequest request, MapView mapView) {
		SetSearchStatus(SearchStage.Running);
		List<GeocoderResult> results = new ArrayList<GeocoderResult>();
		Geocoder g = mapView.getServices().getGeocoder();
		g.geocode(request, new GeocoderCallback(mapView.getMap()) {
			@Override
			public void onComplete(GeocoderResult[] result, GeocoderStatus status) {
				if (status == GeocoderStatus.OK) {
					results.add(result[0]);
					SetSearchStatus(SearchStage.Done);
				}
			}

		});
		return new SearchQueryResult(results, mapView.getMap());
	}
	
	public SearchQueryResult SearchByAddress(MapView mapView) {
		GeocoderRequest request = new GeocoderRequest(mapView.getMap());
		request.setAddress(adress);
		return Search(request, mapView);

	}

	/**
	 * Koral Chapnik
	 */
	public SearchQueryResult searchByCoordinates(MapView mapView, LatLng c) {
		GeocoderRequest request = new GeocoderRequest(mapView.getMap());
		request.setLocation(c);
		return Search(request, mapView);
	}



	@Override
	public String toString() {
		return adress;
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
