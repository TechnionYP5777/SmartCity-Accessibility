package smartcity.accessibility.search;

/**
 * @author Kolikant
 *
 */
import java.util.ArrayList;
import java.util.List;

import com.teamdev.jxmaps.Geocoder;
import com.teamdev.jxmaps.GeocoderCallback;
import com.teamdev.jxmaps.GeocoderRequest;
import com.teamdev.jxmaps.GeocoderResult;
import com.teamdev.jxmaps.GeocoderStatus;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.Marker;
import com.teamdev.jxmaps.swing.MapView;


public class SearchQuery extends MapView{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public static final String EmptyList = "[]";
	
	
	String adress;
	
	public SearchQuery(String parsedQuery) {
		this.adress = parsedQuery;
	}
	
	public SearchQueryResult Search(Map map) {
		GeocoderRequest request = new GeocoderRequest(map);
        request.setAddress(adress);
        
        List<GeocoderResult> results = new ArrayList<GeocoderResult>();
        
        Geocoder g = getServices().getGeocoder();
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
	
	public String toString(){
		return adress;
	}
	
	public static SearchQuery toQuery(String s){
		return new SearchQuery(s);
	}
	
	public static String QueriesList2String(List<SearchQuery> qs){
		List<String> sl = new ArrayList<String>();
		for(SearchQuery q : qs)
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
