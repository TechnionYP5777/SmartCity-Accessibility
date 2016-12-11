package smartcity.accessibility.socialnetwork;

import smartcity.accessibility.mapmanagement.Location;

/**
 * @author ArthurSap
 *
 */

public class Admin extends AuthenticatedUser {


	public Admin(String un, String pass){
		super(un,pass);
		//TODO more stuff on Admin
	}
	
	public void treatReview(Review r){
		//TODO once decided on github, implement the way
		//reviews should get special treatment by the admin.
		//issue #48
	}
	
	public void deleteReview(Review r){
		//TODO implement once the DB has enough info
		//issue #32
	}
	
	@Override
	void addReview(Location __, int r, String Review) {
		super.addReview(__, r, Review);
		//TODO once decided on github, add special treatment
		//issue #49
	}
	

	@Override
	public String getUserName() {
		String userName = super.getUserName();
		//TODO special stuff on userName
		return userName;
	}

	@Override
	public String getPassword() {
		String password = super.getPassword();
		//TODO special stuff on password
		return password;
	}

}
