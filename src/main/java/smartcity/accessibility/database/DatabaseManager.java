package smartcity.accessibility.database;

import org.parse4j.Parse;

/**
 * @author KaplanAlexander
 *
 */
public abstract class DatabaseManager {
	public static final String serverUrl = "https://smartcityaccessibility.herokuapp.com/parse";
	public static final String restKey = "2139d-231cb2-738fe";
	public static final String appId = "smartcityaccessibility";

	public static void initialize() {
		Parse.initialize(appId, restKey, serverUrl);
		
	}

	public Object getValue(final String clas, final String key) {
		return null;
	}

	public void putValue(final String objectClass, final String key, final Object value) {

	}
}
