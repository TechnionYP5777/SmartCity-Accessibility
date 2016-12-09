package smartcity.accessibility.socialnetwork;

import smartcity.accessibility.search.SearchQuery;

/**
 * 
 * @author Kolikant
 *
 */

public interface User {
	/*
	 * make a search using an existing query and show the user the results
	 */
	/**
	 * @param � is the query to be used for the search
	 */
	static void search(SearchQuery q){
		q.Search().showResults();
	}
}
