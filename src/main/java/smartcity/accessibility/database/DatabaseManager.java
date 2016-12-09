package smartcity.accessibility.database;

import org.parse4j.Parse;

/**
 * @author KaplanAlexander
 *
 */
public abstract class DatabaseManager {
	public static final String serverUrl = "https://github.com/TechnionYP5777/SmartCity-Accessibility";
	public static final String masterKey = "0ef32e07-dec4-4ab0-cfdf4-a2628bd39";
	public static final String appId = "smartcityaccessibility";
	
	public static void initialize(){
		Parse.initialize(appId, masterKey, serverUrl);
	}
	
	public Object getValue(final String clas, final String key) {
		return null;
	}

	public void putValue(final String objectClass, final String key, final Object value) {

	}
}
