package smartcity.accessibility.socialnetwork;

import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.search.SearchQuery;
/**
 * @author Kolikant
 *
 */
public class AuthenticatedUser implements User {
	String name;
	String password;
	
	String getUserName() {
		return null;
	}

	String setUserName() {
		return null;
	}

	void setPassword() {

	}

	void getPassword() {

	}

	/**
	 * @param __ is the location on which we wish to add a review
	 * @param Review is the review that we wish to add as a free text
	 */
	void addReview(Location __, String Review) {
		//Review r = new Review(Location, rating, Review);
	}
	
}