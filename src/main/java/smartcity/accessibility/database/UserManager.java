package smartcity.accessibility.database;

import java.util.ArrayList;
import java.util.List;

import org.parse4j.ParseException;
import org.parse4j.ParseUser;

import smartcity.accessibility.exceptions.UserNotFoundException;
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
		if(u == null)
			return;
		ParseUser pu;
		try {
			pu = ParseUser.login(u.getUserName(), u.getPassword());
			pu.delete();
			//TODO: check if logout is needed here
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public static void updateUserName(AuthenticatedUser u, String newName) throws UserNotFoundException{
		ParseUser pu;
		try {
			pu = ParseUser.login(u.getUserName(), u.getPassword());
			pu.setUsername(newName);
			pu.save();
			pu.logout();
		}catch (ParseException e1) {
			throw new UserNotFoundException();
		}
	}
	
	/*
	 * safety of the server prevents from setting passworda after they have been set
	 * in order to change passwords, email etc are required, if needed, would be implemented
	 */
	
/*	public static void updatepassword(AuthenticatedUser u, String newPassword) throws UserNotFoundException{
		ParseUser pu;
		try {
			pu = ParseUser.login(u.getUserName(), u.getPassword());
			pu.setPassword(newPassword);
			pu.save();
			pu.logout();
		}catch (ParseException e1) {
			throw new UserNotFoundException();
		}
	}*/
	
	public static void updatefavouriteQueries(AuthenticatedUser u, List<SearchQuery> l) throws UserNotFoundException{
		ParseUser pu;
		try {
			pu = ParseUser.login(u.getUserName(), u.getPassword());
			pu.put(FavouriteQueriesField, SearchQuery.QueriesList2String(l));
			pu.save();
			pu.logout();
		}catch (ParseException e1) {
			throw new UserNotFoundException();
		}
	}
	
	
}