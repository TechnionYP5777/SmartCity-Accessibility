package smartcity.accessibility.database;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import smartcity.accessibility.socialnetwork.User;

public class UserDataManager {
	private Database db;
	private static final String DATABASE_CLASS = "UserData";
	private static UserDataManager instance;
	private static Logger logger = LoggerFactory.getLogger(UserDataManager.class);
	
	@Inject
	public UserDataManager(Database db) {
		this.db = db;
	}
	
	public static void initialize(UserDataManager m) {
		instance = m;
	}
	
	public static UserDataManager instance() {
		return instance;
	}
	
	private Map<String, Object> toMap(User u) {
		
		return new HashMap<>();
	}

}
