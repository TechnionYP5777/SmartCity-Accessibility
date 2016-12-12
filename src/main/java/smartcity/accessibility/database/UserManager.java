package smartcity.accessibility.database;

import java.util.List;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.parse4j.ParseUser;
import org.parse4j.callback.SignUpCallback;

import smartcity.accessibility.exceptions.InvalidStringException;
import smartcity.accessibility.exceptions.UsernameAlreadyTakenException;
import smartcity.accessibility.search.SearchQuery;
import smartcity.accessibility.socialnetwork.AuthenticatedUser;

/**
 * @author Kolikant
 *
 */
public class UserManager {
	
	public void SignUpUser(AuthenticatedUser u){
		ParseUser user = new ParseUser();
		user.setUsername(u.getUserName());
		user.setPassword(u.getPassword());
		user.put("List<SearchQuery>", u.getfavouriteSearchQueries());
		
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
		try {
			pu = ParseUser.login(name, password);
		} catch (ParseException e) {
			return null;
		}
		AuthenticatedUser $ = new AuthenticatedUser();
		$.setUserName(name);
		$.setPassword(password);
		$.setfavouriteSearchQueries((List<SearchQuery>)pu.get("FavoritQueries"));
		return $;
	}
	
	/**
	 * @author assaflu
	 * @throws ParseException 
	 *
	 */
	public void SaveNewUser(AuthenticatedUser u) throws InvalidStringException, UsernameAlreadyTakenException, ParseException{
		try{
			
			if (ParseQuery.getQuery("AuthenticatedUser").whereEqualTo("UserName", u.getUserName()).find().isEmpty()) {
			final ParseObject obj = new ParseObject("AuthenticatedUser");
				obj.put("UserName", u.getUserName());
				obj.put("Password", u.getPassword());
				obj.put("FavoritQueries", u.getfavouriteSearchQueries());
			obj.save();
			} else
				throw new UsernameAlreadyTakenException();
		}
		catch(ParseException e){
			throw e;
		}

	}
}
