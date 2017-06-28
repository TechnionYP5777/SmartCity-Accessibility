package smartcity.accessibility.services;

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import smartcity.accessibility.categories.UnitTests;
import smartcity.accessibility.database.AbstractLocationManager;
import smartcity.accessibility.database.AbstractUserProfileManager;
import smartcity.accessibility.database.ParseDatabase;
import smartcity.accessibility.socialnetwork.UserBuilder;


/**
 * 
 * @author Koral Chapnik
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class AdminServiceTest extends ServiceTest {

	private Token t; 
	
	@Before
	public void setup() throws Exception {
		ParseDatabase.initialize();
		AbstractUserProfileManager mock_UserManagerProfile = Mockito.mock(AbstractUserProfileManager.class);
		AbstractUserProfileManager.initialize(mock_UserManagerProfile);
		AbstractLocationManager mock_LocationManagerProfile = Mockito.mock(AbstractLocationManager.class);
		AbstractLocationManager.initialize(mock_LocationManagerProfile);
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
		mockMvc.perform(post("/login?name=yaeli&password=1234"));
		this.t = Token.calcToken(new UserBuilder().setUsername("yaeli").setPassword("1234").build());
	}
	
	@Test
	@Category(UnitTests.class)
	public void getAdminInfoTest() throws Exception {
		mockMvc.perform(post("/adminInfo")
				.header("authToken", this.t.getToken()).contentType(contentType))
		.andExpect(status().is2xxSuccessful());	
	}
	

}
