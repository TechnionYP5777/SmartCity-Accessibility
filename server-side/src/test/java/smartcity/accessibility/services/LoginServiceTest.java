package smartcity.accessibility.services;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import smartcity.accessibility.categories.UnitTests;
import smartcity.accessibility.database.AbstractUserProfileManager;
import smartcity.accessibility.database.ParseDatabase;
import smartcity.accessibility.database.UserManager;
import smartcity.accessibility.socialnetwork.User;
import smartcity.accessibility.socialnetwork.UserBuilder;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * @author yael
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@Category(UnitTests.class)
public class LoginServiceTest extends ServiceTest {
	@Before
	public void setup() throws Exception {
		ParseDatabase.initialize();
		AbstractUserProfileManager mock_UserManagerProfile = Mockito.mock(AbstractUserProfileManager.class);
		AbstractUserProfileManager.initialize(mock_UserManagerProfile);
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	@Before
	public void resetUsers() {
		String name = "Dr.Awesome";
		String password = "T4RRR76ppp";
		User u = UserManager.LoginUser(name, password);
		if (u == null)
			return;
		UserManager.DeleteUser(u);
	}

	@Test
	@Ignore
	public void signupSuccess() throws Exception {
		String name = "Dr.Awesome";
		String password = "T4RRR76ppp";
		Token t = Token.calcToken(new UserBuilder().setUsername(name).setPassword(password).build());
		mockMvc.perform(post("/signup" + "?name=" + name + "&password=" + password).contentType(contentType))
				.andExpect(status().is2xxSuccessful()).andExpect(jsonPath("$.token").value(t.getToken()));
	}
}
