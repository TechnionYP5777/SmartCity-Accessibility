package smartcity.accessibility.socialnetwork;

import java.util.ArrayList;
import java.util.List;

import smartcity.accessibility.database.ReviewManager;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.search.SearchQuery;
/**
 * @author Kolikant
 *
 */
public class AuthenticatedUser implements User {
	private String name;
	private String password;
	private List<SearchQuery> favouriteSearchQueries;
	
	public AuthenticatedUser() {

	}
	
	public AuthenticatedUser(String name, String password) {
		this.name = name;
		this.password = password;
		this.favouriteSearchQueries = new ArrayList<>();
	}
	
	public String getUserName() {
		return name;
	}

	public List<SearchQuery> getfavouriteSearchQueries() {
		return favouriteSearchQueries;
	}
	
	public void setfavouriteSearchQueries(List<SearchQuery> lq) {
		this.favouriteSearchQueries = lq;
	}
	
	public void setUserName(String un) {
		this.name = un;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String pass) {
		this.password = pass;
	}
	
	public void addToFavourites(SearchQuery q){
		if (!favouriteSearchQueries.contains(q))
			this.favouriteSearchQueries.add(q);
	}
	
	public void RemoveFromFavourites(SearchQuery q){
		if (favouriteSearchQueries.contains(q))
			this.favouriteSearchQueries.remove(q);
	}

}