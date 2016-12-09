package smartcity.accessibility.socialnetwork;

import smartcity.accessibility.mapmanagement.Location;

/**
 * @author ArthurSap
 *
 */

public class Admin extends AuthenticatedUser {

	private String userName;
	private String password;	

	public Admin(String userName, String password) {
		super(userName, password);
	}
	
	@Override
	String getUserName() {
		return userName;
	}
	
	@Override
	void setUserName(String un) {
		userName = un;
	}
	
	@Override
	String getPassword() {
		return password;
	}
	
	@Override
	void setPassword(String pass) {
		password = pass;
	}
	
	@Override
	void addReview(Location __, int r, String Review) {
		// TODO Auto-generated method stub
		super.addReview(__, r, Review);
	}

}
