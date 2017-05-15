package smartcity.accessibility.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

import smartcity.accessibility.categories.UnitTests;
import smartcity.accessibility.exceptions.UserNotFoundException;
import smartcity.accessibility.exceptions.UsernameAlreadyTakenException;
import smartcity.accessibility.exceptions.illigalString;
import smartcity.accessibility.search.SearchQuery;
import smartcity.accessibility.socialnetwork.User;
import smartcity.accessibility.socialnetwork.UserProfile;

/**
 * @author Kolikant
 *
 */

public class UserManagerTest {
	private static final String userName2 = "ttuuuuuuuuuuuuuuuuasdsadsadasdasdasdasdsadsadkljsadkljsakldjssssssssserrr123123123555123";
	private static final String userName1 = "uuuuuuuuuuuuuuuuasdsadsadasdasdasdasdsadsadkljsadkljsakldjssssssssserrr123123123555123";
	private static AbstractUserProfileManager upm;
	private static UserProfile up;
	
	@BeforeClass
	public static void init() throws UserNotFoundException{
		initAllMock();
		ParseDatabase.initialize();
		User u = UserManager.LoginUser(userName1, "password");
		UserManager.DeleteUser(u);
		String UserName2 = userName2;
		u = UserManager.LoginUser(UserName2, "admin");
		UserManager.DeleteUser(u);
		u = UserManager.LoginUser("b"+UserName2, "admin");
		UserManager.DeleteUser(u);
		u = UserManager.LoginUser(userName1, "pass");
		UserManager.DeleteUser(u);
		u = UserManager.LoginUser(userName2, "pass");
		UserManager.DeleteUser(u);
		
		
		
	}
	
	@Before
	public void initMock() throws UserNotFoundException{
		initAllMock();
	}
	
	
	@Category(UnitTests.class)
	@Test
	public void test() {
		String UserName = userName1;
		try {
			UserManager.SignUpUser(UserName, "password", User.Privilege.RegularUser);
		} catch (UsernameAlreadyTakenException e) {
			fail();
		}
		User u = UserManager.LoginUser(UserName, "password");
		assertEquals(UserName, u.getUsername());
		assertEquals("password", u.getPassword());
		assertEquals(new ArrayList<SearchQuery>(), u.getFavouriteSearchQueries());	
		assertEquals(User.Privilege.RegularUser, u.getPrivilege());
		UserManager.DeleteUser(u);
	}
	

	@Category(UnitTests.class) 
	@Test
	public void test2() throws illigalString{
		String UserName = userName2;
		try {
			UserManager.SignUpUser(UserName, "admin", User.Privilege.Admin);
		} catch (UsernameAlreadyTakenException e1) {
			fail();
		}
		User a = UserManager.LoginUser(UserName, "admin");
		assert a != null;
		assertEquals(UserName, a.getUsername());
		assertEquals("admin", a.getPassword());
		assertEquals(new ArrayList<SearchQuery>(), a.getFavouriteSearchQueries());
		
		try {
			UserManager.updateUserName(a, "b"+UserName);
		} catch (UserNotFoundException e) {
			fail();
		}
		
		User b = UserManager.LoginUser("b"+UserName, "admin");
		assert b != null;
		
		List<SearchQuery> l = new ArrayList<SearchQuery>();
		l.add(SearchQuery.TypeSearch("cafe"));
		try {
			UserManager.updatefavouriteQueries(b, l);
		} catch (UserNotFoundException e) {
			fail();
		}
		
		assertEquals(SearchQuery.QueriesList2String(l), SearchQuery.QueriesList2String(UserManager.LoginUser("b" + UserName, "admin").getFavouriteSearchQueries()));
		
		
		UserManager.DeleteUser(b);
	}
	
	@Category(UnitTests.class) 
	@Test
	public void test3(){
		String UserName = userName2;
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
	
	@Category(UnitTests.class) 
	@Test
	public void test4() throws illigalString{
		String UserName = userName2;
		User b=null;
		try {
			 b = UserManager.SignUpUser(UserName, "pass", User.Privilege.RegularUser);
		} catch (UsernameAlreadyTakenException e) {
			fail();
		}
		b.addSearchQuery(SearchQuery.TypeSearch("cafe"), "in case I feel thirsty!");
		
		try {
			UserManager.updatefavouriteQueries(b);
		} catch (UserNotFoundException e) {
			fail();
		}
		assert(UserManager.LoginUser(UserName, "pass").getFavouriteSearchQueries().get(0).getName().equals("in case I feel thirsty!"));
		assert(UserManager.LoginUser(UserName, "pass").getFavouriteSearchQueries().get(0).getQuery().equals("cafe"));
		
		b.getSearchQuery("in case I feel thirsty!").RenameSearchQuery("vugolo!!!!");
		try {
			UserManager.updatefavouriteQueries(b);
		} catch (UserNotFoundException e) {
			fail();
		}
		User u2 = UserManager.LoginUser(UserName, "pass");
		SearchQuery sq = u2.getFavouriteSearchQueries().get(0);
		assert(sq.getName().equals("vugolo!!!!"));
		assert(sq.getQuery().equals("cafe"));
		
		UserManager.DeleteUser(b);
	}
	
	public static void initAllMock() throws UserNotFoundException{
		up = new UserProfile("Chimichanga");
		upm = Mockito.mock(AbstractUserProfileManager.class);
		Mockito.when(upm.get(Mockito.anyString(), Mockito.any())).thenReturn(up);
		Mockito.when(upm.put(Mockito.any(UserProfile.class), Mockito.any())).thenReturn(true);
		Mockito.when(upm.update(Mockito.any(UserProfile.class), Mockito.any())).thenReturn(true);
		Mockito.when(upm.delete(Mockito.any(UserProfile.class), Mockito.any())).thenReturn(true);
		AbstractUserProfileManager.initialize(upm);
	}
}
