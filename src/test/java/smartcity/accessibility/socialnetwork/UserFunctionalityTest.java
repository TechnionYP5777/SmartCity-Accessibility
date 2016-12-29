package smartcity.accessibility.socialnetwork;

import static org.junit.Assert.*;

import org.junit.Test;

import smartcity.accessibility.exceptions.UnauthorizedAccessException;
import smartcity.accessibility.socialnetwork.User.Privilege;

public class UserFunctionalityTest {

	@Test
	public void userNameFunctionality() throws UnauthorizedAccessException { 
		User u = UserImpl.RegularUser("RegularUser", "", "");
		assertEquals("RegularUser", u.getName());
		
		u.setName("Name has chnged");
		assertEquals("Name has chnged", u.getName());
	}
	
	@Test
	public void passwordFunctionality() throws UnauthorizedAccessException { 
		User u = UserImpl.RegularUser("User", "123", "");
		assertEquals("123", u.getPassword());
		
		u.setPassword("123456");
		assertEquals("123456", u.getPassword());
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
	
		User u = UserImpl.DefaultUser("DefaultUser", "", "");
		assertEquals(Privilege.DefaultUser, u.getPrivilege());
		
		u.setPrivilege(Privilege.RegularUser);
		assertEquals(Privilege.RegularUser, u.getPrivilege());
		
		u.setPrivilege(Privilege.Admin);
		assertEquals(Privilege.Admin, u.getPrivilege());
	}

}
