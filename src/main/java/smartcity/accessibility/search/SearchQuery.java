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
	
	public String ParseToString(){
		return locationType;
	}

	public static List<SearchQuery> String2QueriesList(String favouriteQueries) {
		return new ArrayList<SearchQuery>();
	}
	
}
