package smartcity.accessibility.search;

import java.util.ArrayList;
import java.util.List;


public class SearchQuery {
	
	public static final String EmptyList = "[]";
	
	
	String locationType;
	
	public SearchQuery(String parsedQuery) {
		this.locationType = parsedQuery;
	}
	
	public SearchQueryResult Search() {
		return null;
	}
	
	public String toString(){
		return locationType;
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
