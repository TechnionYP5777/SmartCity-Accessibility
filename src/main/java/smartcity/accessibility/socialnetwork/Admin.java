package smartcity.accessibility.socialnetwork;


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
