package smartcity.accessibility.socialnetwork;

import smartcity.accessibility.database.ReviewManager;
import smartcity.accessibility.mapmanagement.Location;
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
	void addReview(Location __, int r, String Review) {
		//TODO: when Review accepts Location, add it to the constructor
		ReviewManager.uploadReview(new Review(/*__*/null, r ,Review));
	}
	
}