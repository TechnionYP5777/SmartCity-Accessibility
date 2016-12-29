package smartcity.accessibility.socialnetwork;

import java.util.List;

import smartcity.accessibility.search.SearchQuery;
/**
 * @author Kolikant
 *
 */
@Deprecated 
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
		name = "Default User";
		password = "";
		favouriteSearchQueries = SearchQuery.String2QueriesList("");
	}

	
	public String getUserName() {
		return name;
	}

	public List<SearchQuery> getfavouriteSearchQueries() {
		return favouriteSearchQueries;
	}

	@Override
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

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}

/*	@Override
	public void setPassword(String pass) {
		// TODO Auto-generated method stub
		
	}*/

	@Override
	public Privilege getPrivilege() {
		// TODO Auto-generated method stub
		return null;
	}

/*	@Override
	public void setPrivilege(Privilege p) {
		// TODO Auto-generated method stub
		
	}*/

	@Override
	public List<SearchQuery> getFavouriteSearchQueries() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFavouriteSearchQueries(String favouriteQueries) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFavouriteSearchQueries(List<SearchQuery> favouriteQueries) {
		// TODO Auto-generated method stub
		
	}

}