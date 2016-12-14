package smartcity.accessibility.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

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
	
	public String QueriesList2String(List<SearchQuery> l){
		List<String> sl = new ArrayList<String>();
		for(SearchQuery q : l){
			sl.add(q.toString());
		}
		return sl.toString();
	}

	public static List<SearchQuery> String2QueriesList(String favouriteQueries) {
		return new ArrayList<SearchQuery>();
	}
	
}
