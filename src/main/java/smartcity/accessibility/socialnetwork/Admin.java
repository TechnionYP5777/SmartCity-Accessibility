package smartcity.accessibility.socialnetwork;

/**
 * @author ArthurSap
 *
 */

public class Admin extends AuthenticatedUser {

	String userName;
	String password;
	
	public Admin(String un, String pass){
		super(un,pass);
		userName = un;
		password = pass;
	}
	
	@Override
	public String getUserName() {
		return userName;
	}

	@Override
	public void setUserName(String un) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setPassword(String un) {
		// TODO Auto-generated method stub
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

}
