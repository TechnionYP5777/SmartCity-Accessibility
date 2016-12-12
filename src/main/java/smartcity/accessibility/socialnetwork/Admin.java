package smartcity.accessibility.socialnetwork;

import smartcity.accessibility.database.ReviewManager;

/**
 * @author ArthurSap
 *
 */

public class Admin extends AuthenticatedUser {

	public Admin(String un, String pass){
		super(un,pass);
		//TODO more stuff on Admin
	}
	
	
	/**
	 * Marks a review as important - whilst calculating the location's 
	 * accessibility level always takes this review in the calculation.
	 * Also, always show this review in the top reviews.
	 **/
	public void pinReview(Review r){
		//TODO issue #48
	}
	
	/**
	 * Reverts the effects of pinReview.
	 */
	public void unpinReview(Review r){
		//TODO issue #48
	}
	
	public void deleteReview(Review r){
		//TODO implement once the DB has enough info
		//issue #32
	}
	
	/**
	 * Uploads Admins' reviews. 
	 * Pins them automatically.
	 */
	public void uploadReview(Review r){
		ReviewManager.uploadReview(r, this);
		pinReview(r);
	}
	

	@Override
	public String getUserName() {
		String $ = super.getUserName();
		//TODO special stuff on userName
		return $;
	}

	@Override
	public String getPassword() {
		String password = super.getPassword();
		//TODO special stuff on password
		return password;
	}

}
