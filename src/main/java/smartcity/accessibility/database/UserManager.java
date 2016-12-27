package smartcity.accessibility.database;

import java.util.ArrayList;
import java.util.List;

import org.parse4j.ParseException;
import org.parse4j.ParseUser;

import smartcity.accessibility.exceptions.UserNotFoundException;
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
	
	public static void logoutCurrUser(){
		if(ParseUser.currentUser != null)
			try {
				ParseUser.currentUser.logout();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	/*
	 * Users should be created only through here, as this shows if they can be added to the database
	 */
	public static User SignUpUser(String name, String password, User.Privilege p){
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (new UserImpl(name, password, p, SearchQuery.EmptyList));
	}
	
	
	public static User LoginUser(String name, String password){
		ParseUser pu=null;
		UserImpl $=null;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return $;
	}
	
	public static void DeleteUser(User u){
		if(u == null)
			return;
		ParseUser pu;
		try {
			pu = ParseUser.login(u.getName(), u.getPassword());
			pu.delete();
			logoutCurrUser();
			//TODO: check if logout is needed here
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public static void updateUserName(User a, String newName) throws UserNotFoundException{
		ParseUser pu;
		try {
			pu = ParseUser.login(a.getName(), a.getPassword());
			pu.setUsername(newName);
			pu.save();
			pu.logout();
			logoutCurrUser();
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
	
	public static void updatefavouriteQueries(User b, List<SearchQuery> l) throws UserNotFoundException{
		ParseUser pu;
		try {
			pu = ParseUser.login(b.getName(), b.getPassword());
			pu.put(FavouriteQueriesField, SearchQuery.QueriesList2String(l));
			pu.save();
			pu.logout();
			logoutCurrUser();
		}catch (ParseException e1) {
			throw new UserNotFoundException();
		}
	}
	
	
}