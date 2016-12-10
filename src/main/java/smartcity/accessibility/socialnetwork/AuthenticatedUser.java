package smartcity.accessibility.socialnetwork;

import smartcity.accessibility.database.ReviewManager;
import smartcity.accessibility.mapmanagement.Location;
/**
 * @author Kolikant
 *
 */
public class AuthenticatedUser implements User {
	private String name;
	private String password;
	
	public AuthenticatedUser(String name, String password) {
		this.name = name;
		this.password = password;
	}
	
	public String getUserName() {
		return name;
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

	/**
	 * @param __ is the location on which we wish to add a review
	 * @param Review is the review that we wish to add as a free text
	 */
	void addReview(Location __, int r, String Review) {
		//TODO: when Review accepts Location, add it to the constructor
		ReviewManager.uploadReview(new Review(/*__*/null, r ,Review));
	}
	
}