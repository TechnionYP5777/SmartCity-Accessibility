/**
 * 
 */
package smartcity.accessibility.database;

/**
 * @author KaplanAlexander
 *
 */
public interface DatabaseManager {
	final String serverUrl = "https://github.com/TechnionYP5777/SmartCity-Accessibility";
	final String masterKey = "key";
	
	Object getValue(String clas, String key);
	void putValue(String objectClass, String key, Object value);
}
