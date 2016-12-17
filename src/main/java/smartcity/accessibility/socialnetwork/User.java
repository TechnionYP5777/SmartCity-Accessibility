package smartcity.accessibility.socialnetwork;

import smartcity.accessibility.search.SearchQuery;

/**
 * 
 * @author Kolikant
 *
 */

public interface User {
	/**
	 * make a search using an existing query and show the user the results
	 * @param q is the query to be used for the search
	 */
	static void search(SearchQuery q){
		//q.Search();
	}
	
	public String getName();
}
