package smartcity.accessibility.socialnetwork;

import java.util.List;

import smartcity.accessibility.search.SearchQuery;
/**
 * @author Kolikant
 *
 */
public class AuthenticatedUser implements User {
	private String name;
	private String password;
	private List<SearchQuery> favouriteSearchQueries;
	
	
	public AuthenticatedUser(String name, String password,String FavouriteQueries) {
		this.name = name;
		this.password = password;
		this.favouriteSearchQueries = SearchQuery.String2QueriesList(FavouriteQueries);
	}
	
	public AuthenticatedUser() {
	}

	
	public String getUserName() {
		return name;
	}

	public List<SearchQuery> getfavouriteSearchQueries() {
		return favouriteSearchQueries;
	}

	public String getPassword() {
		return password;
	}
	
	public void addToFavourites(SearchQuery q){
		if (!favouriteSearchQueries.contains(q))
			this.favouriteSearchQueries.add(q);
	}
	
	public void RemoveFromFavourites(SearchQuery q){
		if (favouriteSearchQueries.contains(q))
			this.favouriteSearchQueries.remove(q);
	}

	//koral
	@Override
	public String getName() {
		return name;
	}

}