package smartcity.accessibility.database;

import smartcity.accessibility.database.callbacks.ICallback;
import smartcity.accessibility.socialnetwork.UserProfile;

/**
 * All functions can either run in the background and return with a callback method
 * Or block the operation until complete (not advised) and return the value
 * Blocking is done when callback method given is null
 * @author KaplanAlexander
 */
public abstract class AbstractUserProfileManager {
	protected static AbstractUserProfileManager instance;
	public static void initialize(AbstractUserProfileManager m) {
		instance = m;
	}

	public static AbstractUserProfileManager instance() {
		return instance;
	}
	
	public abstract UserProfile get(String username, ICallback<UserProfile> c);
	
	public abstract Boolean put(UserProfile up, ICallback<Boolean> callback);
	
	public abstract Boolean update(UserProfile up, ICallback<Boolean> callback);
	
	public abstract Boolean delete(UserProfile up, ICallback<Boolean> callback);
}
