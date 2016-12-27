package smartcity.accessibility.search;

/**
 * @author Kolikant
 *
 */
import java.util.ArrayList;
import java.util.List;
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

import com.teamdev.jxmaps.LatLng;

public class SearchQuery {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String EmptyList = "[]";
	public static MapView mapView;
	String adress;

	public SearchQuery(String parsedQuery) {
		this.adress = parsedQuery;
	}

<<<<<<< HEAD
	public SearchQueryResult Search() {
		GeocoderRequest request = new GeocoderRequest(mapView.getMap());
		request.setAddress(adress);
		return doSearch(request, mapView.getMap());
=======
	public SearchQueryResult SearchByAddress(MapView mapView) {
		GeocoderRequest request = new GeocoderRequest(mapView.getMap());
		request.setAddress(adress);
		return Search(request, mapView);
>>>>>>> f9a6350635507e352650e92475ffcd43c0a2ed6f

	}

	/**
	 * Koral Chapnik
	 */
	public SearchQueryResult searchByCoordinates(Map map, LatLng c) {
		GeocoderRequest request = new GeocoderRequest(map);
		request.setLocation(c);
<<<<<<< HEAD
		return doSearch(request, map);
	}

	/**
	 * Koral Chapnik
	 */
	private SearchQueryResult doSearch(GeocoderRequest request, Map map) {
=======
		return Search(request, mapView);
	}


	private SearchQueryResult Search(GeocoderRequest request, MapView mapView) {
>>>>>>> f9a6350635507e352650e92475ffcd43c0a2ed6f
		List<GeocoderResult> results = new ArrayList<GeocoderResult>();

		Geocoder g = mapView.getServices().getGeocoder();
		g.geocode(request, new GeocoderCallback(map) {
			@Override
			public void onComplete(GeocoderResult[] result, GeocoderStatus status) {
				if (status == GeocoderStatus.OK) {
					results.add(result[0]);
					
				}
			}
		});
		return new SearchQueryResult(results, map);
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
