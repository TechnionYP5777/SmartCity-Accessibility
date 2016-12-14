package smartcity.accessibility.database;

import java.util.ArrayList;
import java.util.List;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.parse4j.ParseUser;
import org.parse4j.callback.SignUpCallback;

import smartcity.accessibility.exceptions.InvalidStringException;
import smartcity.accessibility.exceptions.UserNotFoundException;
import smartcity.accessibility.exceptions.UsernameAlreadyTakenException;
import smartcity.accessibility.search.SearchQuery;
import smartcity.accessibility.socialnetwork.Admin;
import smartcity.accessibility.socialnetwork.AuthenticatedUser;

/**
 * @author Kolikant
 *
 */
public abstract class UserManager {
	private static final String FavouriteQueriesField = "FavouritQueries";
	private static final String isAdminField = "isAdmin";
	
	/*
	 * Users should be created only through here, as this shows if they can be added to the database
	 */
	public static AuthenticatedUser SignUpUser(String name, String password, boolean isAdmin){
		ParseUser user = new ParseUser();
		user.setUsername(name);
		user.setPassword(password);
		
		List<String> fqDummy = new ArrayList<String>();
		
		user.put(isAdminField, String.valueOf(isAdmin));
		String dummy = fqDummy + "";
		user.put(FavouriteQueriesField, (dummy));
		
		try {
			user.signUp();
		} catch (ParseException e) {
			return null;
		}
		
		try {
			user.logout();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (new AuthenticatedUser(name, password, SearchQuery.EmptyList));
	}
	
	
	@SuppressWarnings("unchecked")
	public static AuthenticatedUser LoginUser(String name, String password){
		ParseUser pu=null;
		AuthenticatedUser $=null;
		try {
			pu = ParseUser.login(name, password);
		} catch (ParseException e) {
			return null;
		}
		boolean isAdmin = java.lang.Boolean.parseBoolean(pu.getString(isAdminField));
		String favouriteQueries = pu.getString(FavouriteQueriesField);
		
		$ = isAdmin ? new Admin(name, password,favouriteQueries) : new AuthenticatedUser(name, password,favouriteQueries);
		try {
			pu.logout();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return $;
	}
	
	public static void DeleteUser(AuthenticatedUser u){
		ParseUser pu;
		try {
			pu = ParseUser.login(u.getUserName(), u.getPassword());
			pu.delete();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	
	public static void updateUserInformation(AuthenticatedUser u) throws InvalidStringException, UsernameAlreadyTakenException, ParseException, UserNotFoundException{
			ParseUser pu;
			try {
				pu = ParseUser.logIn(u.getUserName(), u.getPassword());
			} catch (ParseException e) {
				throw new UserNotFoundException();
			}
			
			pu.put(FavouriteQueriesField, u.getfavouriteSearchQueries());
			pu.saveInBackground();
		}
}