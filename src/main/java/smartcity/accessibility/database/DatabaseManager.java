package smartcity.accessibility.database;

/**
 * @author KaplanAlexander
 *
 */
public interface DatabaseManager {
	String serverUrl = "https://github.com/TechnionYP5777/SmartCity-Accessibility";
	String masterKey = "key";

	Object getValue(String clas, String key);

	void putValue(String objectClass, String key, Object value);

	static void initialize() {
		// TODO Auto-generated method stub
		
	}
}
