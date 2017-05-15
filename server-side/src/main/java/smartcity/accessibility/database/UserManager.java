package smartcity.accessibility.database;

import java.util.ArrayList;
import java.util.List;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.parse4j.ParseUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import smartcity.accessibility.exceptions.UserNotFoundException;
import smartcity.accessibility.exceptions.UsernameAlreadyTakenException;
import smartcity.accessibility.search.SearchQuery;
import smartcity.accessibility.socialnetwork.User;
import smartcity.accessibility.socialnetwork.User.Privilege;
import smartcity.accessibility.socialnetwork.UserBuilder;
import smartcity.accessibility.socialnetwork.UserProfile;

/**
 * @author Kolikant
 *
 */
public abstract class UserManager {
	private static final String FAVOURITE_SEARCH_QUERIES = "FavouritQueries";
	private static final String PRIVILEGE_LEVEL = "PriviligeLevel";
	private static Logger logger = LoggerFactory.getLogger(UserManager.class);

	private UserManager() {

	}

	public static void logoutCurrUser() {
		if (ParseUser.currentUser != null)
			try {
				ParseUser.currentUser.logout();
			} catch (ParseException p) {
				logger.error("{}", p);
			}
	}

	private static boolean userNameExists(String name) throws ParseException {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
		query.whereEqualTo("username", name);
		List<ParseObject> lpo = query.find();
		return lpo != null && !lpo.isEmpty();
	}

	/*
	 * Users should be created only through here, as this shows if they can be
	 * added to the database
	 */
	public static User signUpUser(String username, String password, User.Privilege p) throws UsernameAlreadyTakenException {
		try {
			if (userNameExists(username))
				throw new UsernameAlreadyTakenException();
		} catch (ParseException e1) {
			logger.error("{}", e1);
		}
		ParseUser user = new ParseUser();
		user.setUsername(username);
		user.setPassword(password);

		List<String> fqDummy = new ArrayList<>();

		user.put(PRIVILEGE_LEVEL, p.toString());
		String dummy = fqDummy + "";
		user.put(FAVOURITE_SEARCH_QUERIES, dummy);

		try {
			user.signUp();
		} catch (ParseException e) {
			return null;
		}

		try {
			user.logout();
			logoutCurrUser();
		} catch (ParseException e) {
			logger.error("{}", e);
		}
		User u = new UserBuilder().setUsername(username).setPassword(password).setPrivilege(p).build();
		AbstractUserProfileManager.instance().put(u.getProfile(), b -> {
			if (!b)
				logger.error("failed to upload UserProfile {}", username);
		});
		return u;
	}

	public static User loginUser(String name, String password) {
		ParseUser pu = null;
		try {
			pu = ParseUser.login(name, password);
		} catch (ParseException e) {
			logger.error("Failed log in {} ", e);
			return null;
		}
		Privilege pr = Privilege.valueOf(pu.getString(PRIVILEGE_LEVEL));
		String favouriteQueries = pu.getString(FAVOURITE_SEARCH_QUERIES);

		UserProfile up = null;
		try {
			up = AbstractUserProfileManager.instance().get(name, null);
		} catch (UserNotFoundException e) {
			logger.error("Public profile not found {}", e);
		}

		User u = new UserBuilder().setUsername(name).setPassword(password).setPrivilege(pr)
				.setSearchQueries(favouriteQueries).setProfile(up).build();
		try {
			pu.logout();
			logoutCurrUser();
		} catch (ParseException e) {
			logger.error("{}", e);
		}
		return u;
	}

	public static void deleteUser(User u) {
		if (u == null)
			return;
		ParseUser pu;
		try {
			pu = ParseUser.login(u.getUsername(), u.getPassword());
			pu.delete();
			AbstractUserProfileManager.instance().delete(u.getProfile(), b -> {
				if (!b)
					logger.error("Failed to delete userProfile {}", u.getProfile());
			});
			logoutCurrUser();
		} catch (ParseException e) {
			logger.error("{}", e);
		}
	}

	public static void updateUserName(User a, String newName) throws UserNotFoundException {
		ParseUser pu;
		try {
			pu = ParseUser.login(a.getUsername(), a.getPassword());
			pu.setUsername(newName);
			pu.save();
			pu.logout();
			AbstractUserProfileManager.instance().update(a.getProfile(), b -> {
				if (!b)
					logger.error("Failed to update userProfile {}", a.getProfile());
			});
			logoutCurrUser();
		} catch (ParseException e1) {
			throw new UserNotFoundException();
		}
	}

	public static void updatefavouriteQueries(User b, List<SearchQuery> qs) throws UserNotFoundException {
		ParseUser pu = new ParseUser();
		try {
			pu = ParseUser.login(b.getUsername(), b.getPassword());
			pu.put(FAVOURITE_SEARCH_QUERIES, SearchQuery.QueriesList2String(qs));
			pu.save();
			pu.logout();
			logoutCurrUser();
		} catch (ParseException e1) {
			throw new UserNotFoundException();
		}
	}

	public static void updatefavouriteQueries(User b) throws UserNotFoundException {
		ParseUser pu;
		try {
			pu = ParseUser.login(b.getUsername(), b.getPassword());
			pu.put(FAVOURITE_SEARCH_QUERIES, SearchQuery.QueriesList2String(b.getFavouriteSearchQueries()));
			pu.save();
			pu.logout();
			logoutCurrUser();
		} catch (ParseException e1) {
			throw new UserNotFoundException();
		}
	}

}