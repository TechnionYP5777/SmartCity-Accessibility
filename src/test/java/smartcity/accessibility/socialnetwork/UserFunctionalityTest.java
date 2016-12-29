package smartcity.accessibility.socialnetwork;

import static org.junit.Assert.*;

import org.junit.Test;

import smartcity.accessibility.exceptions.UnauthorizedAccessException;
import smartcity.accessibility.exceptions.UserNotFoundException;
import smartcity.accessibility.socialnetwork.User.Privilege;

public class UserFunctionalityTest {

	@Test
	public void userNameFunctionality() throws UnauthorizedAccessException, UserNotFoundException { 
		User u = UserImpl.RegularUser("RegularUser", "", "");
		assertEquals("RegularUser", u.getName());
		
		u.setName("Name has chnged");
		assertEquals("Name has chnged", u.getName());
	}
	
	@Test
	public void passwordFunctionality() throws UnauthorizedAccessException { 
		assertEquals("123", UserImpl.RegularUser("User", "123", "").getPassword());
	}
	
	@Test
	public void staticConstructorsCorrectness() {  
		
		assertEquals(Privilege.DefaultUser,
				UserImpl.DefaultUser("DefaultUser", "", "").getPrivilege());
		
		assertEquals(Privilege.RegularUser,
				UserImpl.RegularUser("RegularUser", "", "").getPrivilege());
		
		assertEquals(Privilege.Admin,
				UserImpl.Admin("Admin", "", "").getPrivilege());
	}
	
	@Test
	public void privilegeCorrectness() { 
		assertEquals(Privilege.DefaultUser,
				UserImpl.DefaultUser("DefaultUser", "", "").getPrivilege());
	}

}
