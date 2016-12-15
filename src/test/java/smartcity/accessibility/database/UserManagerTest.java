package smartcity.accessibility.database;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import smartcity.accessibility.exceptions.UserNotFoundException;
import smartcity.accessibility.search.SearchQuery;
import smartcity.accessibility.socialnetwork.Admin;
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
	
	@Test 
	public void test2(){
		UserManager.SignUpUser("admin", "admin", true);
		Admin a = (Admin) UserManager.LoginUser("admin", "admin");
		assertEquals("admin", a.getUserName());
		assertEquals("admin", a.getPassword());
		assertEquals(new ArrayList<SearchQuery>(), a.getfavouriteSearchQueries());
		
		try {
			UserManager.updateUserName(a, "badmin");
		} catch (UserNotFoundException e) {
			fail();
		}
		
		Admin b = (Admin) UserManager.LoginUser("badmin", "admin");
		assertNotNull(b);
		
		List<SearchQuery> l = new ArrayList<SearchQuery>();
		l.add(new SearchQuery("cafe"));
		try {
			UserManager.updatefavouriteQueries(b, l);
		} catch (UserNotFoundException e) {
			fail();
		}
		
		Admin c = ((Admin) UserManager.LoginUser("badmin", "admin"));
		assertEquals(SearchQuery.QueriesList2String(l), SearchQuery.QueriesList2String(c.getfavouriteSearchQueries()));
		
		
		UserManager.DeleteUser(b);
	}
}
