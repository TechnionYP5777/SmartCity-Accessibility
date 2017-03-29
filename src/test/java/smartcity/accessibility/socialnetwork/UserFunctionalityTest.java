package smartcity.accessibility.socialnetwork;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import smartcity.accessibility.categories.UnitTests;
import smartcity.accessibility.exceptions.UnauthorizedAccessException;
import smartcity.accessibility.exceptions.UserNotFoundException;
import smartcity.accessibility.socialnetwork.User.Privilege;

public class UserFunctionalityTest {

	@Category(UnitTests.class)
	@Test
	public void userNameFunctionality() throws UnauthorizedAccessException, UserNotFoundException { 
		User u = UserImpl.RegularUser("RegularUser", "", "");
		assertEquals("RegularUser", u.getName());
		
		u.setLocalName("Name has chnged");
		assertEquals("Name has chnged", u.getLocalName());
	}
	
	@Category(UnitTests.class)
	@Test
	public void passwordFunctionality() throws UnauthorizedAccessException { 
		assertEquals("123", UserImpl.RegularUser("User", "123", "").getPassword());
	}
	
	@Category(UnitTests.class)
	@Test
	public void staticConstructorsCorrectness() {  
		
		assertEquals(Privilege.DefaultUser,
				UserImpl.DefaultUser().getPrivilege());
		
		assertEquals(Privilege.RegularUser,
				UserImpl.RegularUser("RegularUser", "", "").getPrivilege());
		
		assertEquals(Privilege.Admin,
				UserImpl.Admin("Admin", "", "").getPrivilege());
	}
	
	@Category(UnitTests.class)
	@Test
	public void privilegeCorrectness() { 
		assertEquals(Privilege.DefaultUser,
				UserImpl.DefaultUser().getPrivilege());
	}

}
