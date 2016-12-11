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
	
	public void addToFavourites(SearchQuery ¢){
		if (!favouriteSearchQueries.contains(¢))
			this.favouriteSearchQueries.add(¢);
	}
	
	public void RemoveFromFavourites(SearchQuery ¢){
		if (favouriteSearchQueries.contains(¢))
			this.favouriteSearchQueries.remove(¢);
	}
	
	

	/**
	 * @param __ is the location on which we wish to add a review
	 * @param Review is the review that we wish to add as a free text
	 */
	void addReview(Location __, int r, String Review) {
		//TODO: when Review accepts Location, add it to the constructor
		ReviewManager.uploadReview(new Review(/*__*/null, r ,Review));
	}
	
}