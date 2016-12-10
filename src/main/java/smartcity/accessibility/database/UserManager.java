package smartcity.accessibility.database;

import java.util.List;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.parse4j.callback.FindCallback;

import smartcity.accessibility.search.SearchQuery;
import smartcity.accessibility.socialnetwork.AuthenticatedUser;

/**
 * @author Kolikant
 *
 */
public class UserManager {
	
	private final char seperator = '%';
	private static AuthenticatedUser currentUser;
	
	private String makeKey(String name, String password){
		return name+seperator+password;
	}
	
	/**
	 * @author assaflu
	 *
	 */
	private boolean validString(String str){
		return false;
	}
	
	public AuthenticatedUser Authenticate(String name, String password){
		String key = makeKey(name, password);
		currentUser = null;
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("AuthenticatedUser");
		query.whereEqualTo("key", key);
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
		    public void done(List<ParseObject> users, ParseException e) {
		        if (e == null) {
		        	if(users.size()==0){
		        		return;
		        	}
		        	currentUser = new AuthenticatedUser(name, password);
		        	currentUser.setfavouriteSearchQueries((List<SearchQuery>)users.get(0).get("favouriteSearchQueries"));
		        } else {
		        	return;
		        }
		    }
		});
		if(currentUser == null)
			return null;
		AuthenticatedUser $ = new AuthenticatedUser(name, password);
		$.setfavouriteSearchQueries(currentUser.getfavouriteSearchQueries());
		currentUser = null;
		return $;
	}
	
	/**
	 * @author assaflu
	 *
	 */
	public void SaveUser(AuthenticatedUser user) throws InvalidStringException, UsernameAlreadyTakenException{
		String key = makeKey(user.getUserName(),user.getPassword());
		
	}
}
