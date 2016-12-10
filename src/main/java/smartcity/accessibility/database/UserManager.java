package smartcity.accessibility.database;

/**
 * @author Kolikant
 *
 */
public class UserManager {
	
	private final char seperator = '%';
	
	private String makeKey(String name, String password){
		return name+seperator+password;
	}
	
	public boolean Authenticate(String name, String password){
		String key = makeKey(name, password);
		
		return false;
	}
	
	
}
