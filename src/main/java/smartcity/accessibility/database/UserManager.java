package smartcity.accessibility.database;

import org.parse4j.ParseObject;

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
	
	public AuthenticatedUser Authenticate(String name, String password){
		String key = makeKey(name, password);
		ParseObject po = DatabaseManager.getValue("AuthenticatedUser", key);
		if(po == null){
			return null;
		}
		return null;
		//TODO: figure out how to get the user information
	}
	
	/**
	 * @author assaflu
	 *
	 */
	public void SaveUser(AuthenticatedUser user) throws InvalidStringException, UsernameAlreadyTakenException{
		String key = makeKey(user.getUserName(),user.getPassword());
		
	}
}
