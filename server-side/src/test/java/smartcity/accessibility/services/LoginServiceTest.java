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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.ResponseStatus;

import smartcity.accessibility.categories.UnitTests;
import smartcity.accessibility.database.AbstractUserProfileManager;
import smartcity.accessibility.database.ParseDatabase;
import smartcity.accessibility.database.UserManager;
import smartcity.accessibility.services.exceptions.SignUpFailed;
import smartcity.accessibility.services.exceptions.UserDoesNotExistException;
import smartcity.accessibility.socialnetwork.User;
import smartcity.accessibility.socialnetwork.UserBuilder;

/**
 * @author yael
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@Category(UnitTests.class)
public class LoginServiceTest extends ServiceTest {
	
	private String name = "Dr.Awesome";
	private String password = "T4RRR76ppp";
	
	@Before
	public void setup() throws Exception {
		ParseDatabase.initialize();
		AbstractUserProfileManager mock_UserManagerProfile = Mockito.mock(AbstractUserProfileManager.class);
		AbstractUserProfileManager.initialize(mock_UserManagerProfile);
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	@Before
	public void resetUsers() {
		User u = UserManager.LoginUser(name, password);
		if (u == null)
			return;
		UserManager.DeleteUser(u);
	}

	@Test
	public void signupSuccess() throws Exception {
		Token t = Token.calcToken(new UserBuilder().setUsername(name).setPassword(password).build());
		mockMvc.perform(post("/signup" + "?name=" + name + "&password=" + password))
				.andExpect(status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.jsonPath("$.token").value(t.getToken()));
	}
	
	@Test
	public void signupUserExist() throws Exception {
		String reason = SignUpFailed.class.getAnnotation(ResponseStatus.class).reason();
		mockMvc.perform(post("/signup" + "?name=" + name + "&password=" + password));
		mockMvc.perform(post("/signup" + "?name=" + name + "&password=" + password))
				.andExpect(status().isBadRequest())
				.andExpect(status().reason(reason));
	}
	
	@Test
	public void loginSuccess() throws Exception {
		mockMvc.perform(post("/signup" + "?name=" + name + "&password=" + password));
		Token t = Token.calcToken(new UserBuilder().setUsername(name).setPassword(password).build());
		mockMvc.perform(post("/login" + "?name=" + name + "&password=" + password))
				.andExpect(status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.jsonPath("$.token").value(t.getToken()));
	}
	
	@Test
	public void loginUserDoesNotExist() throws Exception {
		String reason = UserDoesNotExistException.class.getAnnotation(ResponseStatus.class).reason();
		mockMvc.perform(post("/login" + "?name=" + name + "&password=" + password))
				.andExpect(status().isNotFound())
				.andExpect(status().reason(reason));
	}
}
