package smartcity.accessibility.search;

/**
 * @author Kolikant
 *
 */
import java.util.ArrayList;
import java.util.List;

import com.teamdev.jxmaps.Geocoder;
import com.teamdev.jxmaps.GeocoderRequest;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.swing.MapView;


public class SearchQuery extends MapView{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public static final String EmptyList = "[]";
	
	
	String adress;
	Map map;
	
	public SearchQuery(String parsedQuery) {
		this.map = getMap();
		this.adress = parsedQuery;
	}
	
	public SearchQueryResult Search() {
		GeocoderRequest request = new GeocoderRequest(getMap());
        request.setAddress(adress);
        List<GeocoderRequest> retvalList = new ArrayList<GeocoderRequest>();
        retvalList.add(request);
        return new SearchQueryResult(retvalList, map);
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
