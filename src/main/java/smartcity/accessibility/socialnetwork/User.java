package smartcity.accessibility.socialnetwork;

/**
 * @author Kolikant
 *
 */
public class User extends Client{
	String name;
	String getUserName();
	
	String setUserName();
	
	void setPassword();
	
	void getPassword();
	
	void addReview(Location l, String Review);
}