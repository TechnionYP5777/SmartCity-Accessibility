package smartcity.accessibility.socialnetwork;

import java.util.List;

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
	    public static Privilege fromOrdinal(int n) {return allValues[n];}
	    public static Privilege pinPrivilegeLevel() {return Admin;}
	}
	
	/**
	 * make a search using an existing query and show the user the results
	 * @param q is the query to be used for the search
	 */
	static void search(SearchQuery q){
		//q.Search();
	}
	
	public String getName();
	public void setName(String name);
	
	public String getPassword();
	public void setPassword(String pass);
	
	public Privilege getPrivilege();
	public void setPrivilege(Privilege p);
	
	public List<SearchQuery> getFavouriteSearchQueries();
	public void setFavouriteSearchQueries(String favouriteQueries);
	public void setFavouriteSearchQueries(List<SearchQuery> favouriteQueries);
	
}
