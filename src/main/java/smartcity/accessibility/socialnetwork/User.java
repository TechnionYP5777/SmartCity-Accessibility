package smartcity.accessibility.socialnetwork;

import java.util.List;

import smartcity.accessibility.exceptions.UnauthorizedAccessException;
import smartcity.accessibility.search.SearchQuery;

/**
 * 
 * @author Kolikant
 *
 */

public interface User {	
	
	enum Privilege{
		GodUser, //TODO might not need this, still put it here just in case
		Admin,
		RegularUser,
		DefaultUser;
		
		/*
		 * Kolikant
		 */
		private static Privilege[] allValues = values();
	    public static Privilege fromOrdinal(int i) {return allValues[i];}
	    public static Privilege pinPrivilegeLevel() {return Admin;}
	    public static Privilege addReviewPrivilegeLevel() {return RegularUser;}
	}
	
	/**
	 * make a search using an existing query and show the user the results
	 * @param q is the query to be used for the search
	 */
	static void search(SearchQuery __){
		//q.Search();
	}
	
	String getName();
	void setName(String name) throws UnauthorizedAccessException;
	
	String getPassword();
	void setPassword(String pass) throws UnauthorizedAccessException;
	
	Privilege getPrivilege();
	void setPrivilege(Privilege p);
	
	List<SearchQuery> getFavouriteSearchQueries();
	void setFavouriteSearchQueries(String favouriteQueries);
	void setFavouriteSearchQueries(List<SearchQuery> favouriteQueries);
	
}
