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
	
	public AuthenticatedUser Authenticate(String name, String password){
		String key = makeKey(name, password);
		currentUser = null;
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("AuthenticatedUser");
		query.whereEqualTo("key", key);
		query.findInBackground(new FindCallback<ParseObject>() {
			@SuppressWarnings("unchecked")
			@Override
		    public void done(List<ParseObject> users, ParseException x) {
				if (x != null || users.isEmpty())
					return;
				currentUser = new AuthenticatedUser(name, password);
				currentUser.setfavouriteSearchQueries((List<SearchQuery>) users.get(0).get("favouriteSearchQueries"));
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
	 * @throws ParseException 
	 *
	 */
	public void SaveUser(AuthenticatedUser user) throws InvalidStringException, UsernameAlreadyTakenException, ParseException{
		try{
			ParseQuery<ParseObject> q = ParseQuery.getQuery("AuthenticatedUser").whereEqualTo("UserName",user.getUserName());
			if(q==null){
				final ParseObject obj = new ParseObject("AuthenticatedUser");
				obj.put("UserName",user.getUserName());
				obj.put("Password",user.getPassword());
				obj.put("FavoritQueries",user.getfavouriteSearchQueries());
				obj.save();
			}
			else{
				throw new UsernameAlreadyTakenException();
			}
		}
		catch(ParseException e){
			throw e;
		}

	}
}
