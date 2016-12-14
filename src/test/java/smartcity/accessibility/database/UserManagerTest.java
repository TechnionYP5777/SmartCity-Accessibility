package smartcity.accessibility.database;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

import smartcity.accessibility.search.SearchQuery;
import smartcity.accessibility.socialnetwork.AuthenticatedUser;

/**
 * @author Kolikant
 *
 */
public class UserManagerTest {
	@BeforeClass
	public static void init(){
		DatabaseManager.initialize();
	}
	
	@Test
	public void test() {
		UserManager.SignUpUser("user", "password", false);
		AuthenticatedUser u = UserManager.LoginUser("user", "password");
		assertEquals("user", u.getUserName());
		assertEquals("password", u.getPassword());
		assertEquals(new ArrayList<SearchQuery>(), u.getfavouriteSearchQueries());	
		UserManager.DeleteUser(u);
	}
}
