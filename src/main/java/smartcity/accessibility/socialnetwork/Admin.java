package smartcity.accessibility.socialnetwork;

/**
 * @author ArthurSap
 *
 */

public class Admin implements User {

	String userName;
	String password;
	
	public Admin(String un, String pass){
		userName = un;
		password = pass;
	}
	
	@Override
	public String getUserName() {
		return userName;
	}

	@Override
	public String setUserName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPassword() {
		// TODO Auto-generated method stub
	}

	@Override
	public void getPassword() {
		// TODO Auto-generated method stub
	}

}
