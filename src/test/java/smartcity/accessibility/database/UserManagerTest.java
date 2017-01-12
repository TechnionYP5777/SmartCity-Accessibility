package smartcity.accessibility.database;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import smartcity.accessibility.exceptions.UserNotFoundException;
import smartcity.accessibility.exceptions.UsernameAlreadyTakenException;
import smartcity.accessibility.search.SearchQuery;
import smartcity.accessibility.socialnetwork.User;

/**
 * @author Kolikant
 *
 */
public class UserManagerTest {
	@BeforeClass
	public static void init(){
		DatabaseManager.initialize();
		String UserName = "uuuuuuuuuuuuuuuuasdsadsadasdasdasdasdsadsadkljsadkljsakldjssssssssserrr123123123555123";
		User u = UserManager.LoginUser(UserName, "password");
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
		try {
			UserManager.SignUpUser(UserName, "password", User.Privilege.RegularUser);
		} catch (UsernameAlreadyTakenException e) {
			fail();
		}
		User u = UserManager.LoginUser(UserName, "password");
		assertEquals(UserName, u.getName());
		assertEquals("password", u.getPassword());
		assertEquals(new ArrayList<SearchQuery>(), u.getFavouriteSearchQueries());	
		assertEquals(User.Privilege.RegularUser, u.getPrivilege());
		UserManager.DeleteUser(u);
	}
	

	@Test 
	public void test2(){
		String UserName = "ttuuuuuuuuuuuuuuuuasdsadsadasdasdasdasdsadsadkljsadkljsakldjssssssssserrr123123123555123";
		try {
			UserManager.SignUpUser(UserName, "admin", User.Privilege.Admin);
		} catch (UsernameAlreadyTakenException e1) {
			fail();
		}
		User a = UserManager.LoginUser(UserName, "admin");
		assertNotNull(a);
		assertEquals(UserName, a.getName());
		assertEquals("admin", a.getPassword());
		assertEquals(new ArrayList<SearchQuery>(), a.getFavouriteSearchQueries());
		
		try {
			UserManager.updateUserName(a, "b"+UserName);
		} catch (UserNotFoundException e) {
			fail();
		}
		
		User b = UserManager.LoginUser("b"+UserName, "admin");
		assertNotNull(b);
		
		List<SearchQuery> l = new ArrayList<SearchQuery>();
		l.add(SearchQuery.TypeSearch("cafe"));
		try {
			UserManager.updatefavouriteQueries(b, l);
		} catch (UserNotFoundException e) {
			fail();
		}
		
		User c = (UserManager.LoginUser("b"+UserName, "admin"));
		assertEquals(SearchQuery.QueriesList2String(l), SearchQuery.QueriesList2String(c.getFavouriteSearchQueries()));
		
		
		UserManager.DeleteUser(b);
	}
	
	@Test 
	public void test3(){
		String UserName = "ttuuuuuuuuuuuuuuuuasdsadsadasdasdasdasdsadsadkljsadkljsakldjssssssssserrr123123123555123";
		User b=null;
		try {
			 b = UserManager.SignUpUser(UserName, "pass", User.Privilege.RegularUser);
		} catch (UsernameAlreadyTakenException e) {
			fail();
		}
		try {
			UserManager.SignUpUser(UserName, "password", User.Privilege.RegularUser);
			UserManager.DeleteUser(b);
			fail();
		} catch (UsernameAlreadyTakenException e) {
			UserManager.DeleteUser(b);
		}
		
	}
}
