package smartcity.accessibility.database;

import java.util.ArrayList;
import java.util.List;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.parse4j.ParseUser;

import smartcity.accessibility.exceptions.UserNotFoundException;
import smartcity.accessibility.exceptions.UsernameAlreadyTakenException;
import smartcity.accessibility.search.SearchQuery;
import smartcity.accessibility.socialnetwork.User;
import smartcity.accessibility.socialnetwork.User.Privilege;
import smartcity.accessibility.socialnetwork.UserImpl;

/**
 * @author Kolikant
 *
 */
public abstract class UserManager {
	private static final String FavouriteQueriesField = "FavouritQueries";
	private static final String PrivilidgeLevel = "PriviligeLevel";

	public static void logoutCurrUser() {
		if (ParseUser.currentUser != null)
			try {
				ParseUser.currentUser.logout();
			} catch (ParseException e) {
				e.printStackTrace();
			}
	}

	private static boolean UserNameExists(String name) throws ParseException {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
		query.whereEqualTo("username", name);
		List<ParseObject> pol = query.find();
		return pol != null && !pol.isEmpty();
	}

	/*
	 * Users should be created only through here, as this shows if they can be
	 * added to the database
	 */
	public static User SignUpUser(String name, String password, User.Privilege p) throws UsernameAlreadyTakenException {
		try {
			if (UserNameExists(name))
				throw new UsernameAlreadyTakenException();
		} catch (ParseException e1) {
		}
		ParseUser user = new ParseUser();
		user.setUsername(name);
		user.setPassword(password);

		List<String> fqDummy = new ArrayList<String>();

		user.put(PrivilidgeLevel, Integer.toString(p.ordinal()));
		String dummy = fqDummy + "";
		user.put(FavouriteQueriesField, (dummy));

		try {
			user.signUp();
		} catch (ParseException e) {
			return null;
		}

		try {
			user.logout();
			logoutCurrUser();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return (new UserImpl(name, password, p, SearchQuery.EmptyList));
	}

	public static User LoginUser(String name, String password) {
		ParseUser pu = null;
		UserImpl $ = null;
		try {
			pu = ParseUser.login(name, password);
		} catch (ParseException e) {
			return null;
		}
		int level = Integer.parseInt(pu.getString(PrivilidgeLevel));
		Privilege pr = Privilege.fromOrdinal(level);
		String favouriteQueries = pu.getString(FavouriteQueriesField);

		$ = new UserImpl(name, password, pr, favouriteQueries);
		try {
			pu.logout();
			logoutCurrUser();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return $;
	}

	public static void DeleteUser(User u) {
		if (u == null)
			return;
		ParseUser pu;
		try {
			pu = ParseUser.login(u.getName(), u.getPassword());
			pu.delete();
			logoutCurrUser();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public static void updateUserName(User a, String newName) throws UserNotFoundException {
		ParseUser pu;
		try {
			pu = ParseUser.login(a.getName(), a.getPassword());
			pu.setUsername(newName);
			pu.save();
			pu.logout();
			logoutCurrUser();
		} catch (ParseException e1) {
			throw new UserNotFoundException();
		}
	}

	public static void updatefavouriteQueries(User b, List<SearchQuery> qs) throws UserNotFoundException {
		ParseUser pu;
		try {
			pu = ParseUser.login(b.getName(), b.getPassword());
			pu.put(FavouriteQueriesField, SearchQuery.QueriesList2String(qs));
			pu.save();
			pu.logout();
			logoutCurrUser();
		} catch (ParseException e1) {
			throw new UserNotFoundException();
		}
	}

	public static void updateAllUserInformation(User u) throws UserNotFoundException {
		if (u.getPrivilege() == User.Privilege.DefaultUser)
			return;
		if (!u.getName().equals(u.getLocalName()))
			updateUserName(u, u.getLocalName());

		updatefavouriteQueries(u, u.getFavouriteSearchQueries());
	}

}