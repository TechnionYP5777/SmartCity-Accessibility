package smartcity.accessibility.socialnetwork;

import smartcity.accessibility.search.SearchQuery;

public interface User {
	/*
	 * make a search using an existing query and show the user the results
	 */
	static void search(SearchQuery ¢){
		¢.Search().showResults();
	}
}
