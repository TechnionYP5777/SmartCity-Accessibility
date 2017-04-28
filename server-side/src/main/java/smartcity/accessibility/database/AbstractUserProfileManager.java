package smartcity.accessibility.database;

import smartcity.accessibility.database.callbacks.ICallback;
import smartcity.accessibility.exceptions.UserNotFoundException;
import smartcity.accessibility.socialnetwork.UserProfile;

public abstract class AbstractUserProfileManager {
	protected static AbstractUserProfileManager instance;
	public static void initialize(AbstractUserProfileManager m) {
		instance = m;
	}

	public static AbstractUserProfileManager instance() {
		return instance;
	}
	
	public abstract UserProfile get(String username, ICallback<UserProfile> c) throws UserNotFoundException;
	
	public abstract Boolean put(UserProfile up, ICallback<Boolean> callback);
	
	public abstract Boolean update(UserProfile up, ICallback<Boolean> callback);
	
	public abstract Boolean delete(UserProfile up, ICallback<Boolean> callback);
}
