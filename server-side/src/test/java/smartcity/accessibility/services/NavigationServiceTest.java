package smartcity.accessibility.services;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import smartcity.accessibility.socialnetwork.UserBuilder;

/**
 * @author yael
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class NavigationServiceTest extends ServiceTest {
	Token t;

	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
		mockMvc.perform(post("/signup?name=me&password=1234"));
		mockMvc.perform(post("/login?name=me&password=1234"));
		this.t = Token.calcToken(new UserBuilder().setUsername("me").setPassword("1234").build());//new UserImpl("me", "1234", null));
	}

	@Test
	public void navigationUnauthorized() throws IOException, Exception {
		mockMvc.perform(post("/navigation" + "?srcLat=0.0&srcLng=0.0&dstLat=0.0&dstLng=0.0&accessibilityThreshold=0")
				.header("authToken", "lalal").contentType(contentType)).andExpect(status().isUnauthorized());
	}

	@Test
	public void navigationAuthorizedBadRequest() throws IOException, Exception {
		mockMvc.perform(post("/navigation" + "?srcLat=0.0&srcLng=0.0&dstLat=0.0&dstLng=0.0&accessibilityThreshold=0")
				.header("authToken", this.t.getToken()).contentType(contentType)).andExpect(status().isBadRequest());
	}
	
	@Test
	@Ignore
	public void navigationAuthorizedSeccuss() throws IOException, Exception {
		mockMvc.perform(post("/navigation" + "?srcLat=31.768909&srcLng=34.627724&dstLat=31.771334&dstLng=34.632500&accessibilityThreshold=1")
				.header("authToken", this.t.getToken()).contentType(contentType)).andExpect(status().is2xxSuccessful());
	}

}
