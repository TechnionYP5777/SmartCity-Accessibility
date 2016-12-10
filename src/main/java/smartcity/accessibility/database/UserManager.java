package smartcity.accessibility.database;

import smartcity.accessibility.socialnetwork.AuthenticatedUser;

/**
 * @author Kolikant
 *
 */
public class UserManager {
	
	private final char seperator = '%';
	
	private String makeKey(String name, String password){
		return name+seperator+password;
	}
	
	/**
	 * @author assaflu
	 *
	 */
	private boolean validString(String str){
		return false;
	}
	
	public boolean Authenticate(String name, String password){
		String key = makeKey(name, password);
		
		return false;
	}
	
	/**
	 * @author assaflu
	 *
	 */
	public void SaveUser(AuthenticatedUser user) throws InvalidString, UsernameAlreadyTaken{
		String key = makeKey(user.getUserName(),user.getPassword());
		
	}
}
