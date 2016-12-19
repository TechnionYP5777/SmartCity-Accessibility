package smartcity.accessibility.database;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Ignore;
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
		String UserName = "uuuuuuuuuuuuuuuuasdsadsadasdasdasdasdsadsadkljsadkljsakldjssssssssserrr123123123555123";
		AuthenticatedUser u = UserManager.LoginUser(UserName, "password");
		UserManager.DeleteUser(u);
		String UserName2 = "ttuuuuuuuuuuuuuuuuasdsadsadasdasdasdasdsadsadkljsadkljsakldjssssssssserrr123123123555123";
		u = UserManager.LoginUser(UserName2, "admin");
		UserManager.DeleteUser(u);
		u = UserManager.LoginUser("b"+UserName2, "admin");
		UserManager.DeleteUser(u);
	}
	
	@Test
	public void test() {
		String UserName = "uuuuuuuuuuuuuuuuasdsadsadasdasdasdasdsadsadkljsadkljsakldjssssssssserrr123123123555123";
		UserManager.SignUpUser(UserName, "password", false);
		AuthenticatedUser u = UserManager.LoginUser(UserName, "password");
		assertEquals(UserName, u.getUserName());
		assertEquals("password", u.getPassword());
		assertEquals(new ArrayList<SearchQuery>(), u.getfavouriteSearchQueries());	
		UserManager.DeleteUser(u);
	}
	
	@Ignore
	@Test 
	public void test2(){
		String UserName = "ttuuuuuuuuuuuuuuuuasdsadsadasdasdasdasdsadsadkljsadkljsakldjssssssssserrr123123123555123";
		UserManager.SignUpUser(UserName, "admin", true);
		Admin a = (Admin) UserManager.LoginUser(UserName, "admin");
		assertEquals(UserName, a.getUserName());
		assertEquals("admin", a.getPassword());
		assertEquals(new ArrayList<SearchQuery>(), a.getfavouriteSearchQueries());
		
		try {
			UserManager.updateUserName(a, "b"+UserName);
		} catch (UserNotFoundException e) {
			fail();
		}
		
		Admin b = (Admin) UserManager.LoginUser("b"+UserName, "admin");
		assertNotNull(b);
		
		List<SearchQuery> l = new ArrayList<SearchQuery>();
		l.add(new SearchQuery("cafe"));
		try {
			UserManager.updatefavouriteQueries(b, l);
		} catch (UserNotFoundException e) {
			fail();
		}
		
		Admin c = ((Admin) UserManager.LoginUser("b"+UserName, "admin"));
		assertEquals(SearchQuery.QueriesList2String(l), SearchQuery.QueriesList2String(c.getfavouriteSearchQueries()));
		
		
		UserManager.DeleteUser(b);
	}
}
