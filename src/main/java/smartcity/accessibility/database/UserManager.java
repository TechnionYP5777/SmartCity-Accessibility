package smartcity.accessibility.database;

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
public class UserManager {
	private static final String FavouriteQueriesField = "FavoritQueries";
	private static final String isAdminField = "isAdmin";
	public void SignUpUser(AuthenticatedUser u){
		ParseUser user = new ParseUser();
		user.setUsername(u.getUserName());
		user.setPassword(u.getPassword());
		user.put(FavouriteQueriesField, u.getfavouriteSearchQueries());
		user.put(isAdminField, u.getClass() == Admin.class);
		
		user.signUpInBackground(new SignUpCallback() {
			
			@Override
			public void done(ParseException arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public AuthenticatedUser Authenticate(String name, String password){
		ParseUser pu;
		AuthenticatedUser $;
		try {
			pu = ParseUser.login(name, password);
		} catch (ParseException e) {
			return null;
		}
		$ = pu.getBoolean(isAdminField) ? new Admin(name, password) : new AuthenticatedUser(name, password);
		$.setfavouriteSearchQueries((List<SearchQuery>)pu.get(FavouriteQueriesField));
		return $;
	}
	
	
	public void updateUserInformation(AuthenticatedUser u) throws InvalidStringException, UsernameAlreadyTakenException, ParseException, UserNotFoundException{
			ParseUser pu;
			try {
				pu = ParseUser.login(u.getUserName(), u.getPassword());
			} catch (ParseException e) {
				throw new UserNotFoundException();
			}
			
			pu.put(FavouriteQueriesField, u.getfavouriteSearchQueries());
			pu.saveInBackground();
		}
}