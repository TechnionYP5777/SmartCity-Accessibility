package smartcity.accessibility.services;

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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

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
public class ComplexSearchServiceTest extends ServiceTest {

	private Token t; 
	
	@Before
	public void setup() throws Exception {
		ParseDatabase.initialize();
		AbstractUserProfileManager mock_UserManagerProfile = Mockito.mock(AbstractUserProfileManager.class);
		AbstractUserProfileManager.initialize(mock_UserManagerProfile);
		AbstractLocationManager mock_LocationManagerProfile = Mockito.mock(AbstractLocationManager.class);
		AbstractLocationManager.initialize(mock_LocationManagerProfile);
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
		mockMvc.perform(post("/signup?name=me&password=1234"));
		mockMvc.perform(post("/login?name=me&password=1234"));
		this.t = Token.calcToken(new UserBuilder().setUsername("me").setPassword("1234").build());
	}
	
	@Test
	@Category(UnitTests.class)
	public void checkComplexSearch() throws Exception {
		MockHttpServletRequestBuilder r = post("/complexSearchAddress?type=restaurant&radius=1000&startLocation=haifa&threshold=1");
		r.header("authToken", this.t.getToken()).contentType(contentType);
		mockMvc.perform(r).andExpect(status().is2xxSuccessful());	
	}
	
	
}
