package smartcity.accessibility.services;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import smartcity.accessibility.categories.NetworkTests;
import smartcity.accessibility.database.AbstractLocationManager;
import smartcity.accessibility.database.AbstractUserProfileManager;
import smartcity.accessibility.database.ParseDatabase;
import smartcity.accessibility.socialnetwork.UserBuilder;

/**
 * @author yael
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@Category(NetworkTests.class)
public class NavigationServiceTest extends ServiceTest {
	Token t;

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
	public void navigationAuthorizedSeccuss() throws IOException, Exception {
		mockMvc.perform(post("/navigation"
				+ "?srcLat=31.768909&srcLng=34.627724&dstLat=31.771334&dstLng=34.632500&accessibilityThreshold=1")
						.header("authToken", this.t.getToken()).contentType(contentType))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	public void responseContainsTime() throws IOException, Exception {
		mockMvc.perform(post("/navigation"
				+ "?srcLat=31.768909&srcLng=34.627724&dstLat=31.771334&dstLng=34.632500&accessibilityThreshold=1")
						.header("authToken", this.t.getToken()).contentType(contentType))
				.andExpect(status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.jsonPath("$.time").isNumber());
	}

	@Test
	public void responseContainsDistance() throws IOException, Exception {
		mockMvc.perform(post("/navigation"
				+ "?srcLat=31.768909&srcLng=34.627724&dstLat=31.771334&dstLng=34.632500&accessibilityThreshold=1")
						.header("authToken", this.t.getToken()).contentType(contentType))
				.andExpect(status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.jsonPath("$.distance").isNumber());
	}
	
	@Test
	public void responseContainsLatLngArray() throws IOException, Exception {
		mockMvc.perform(post("/navigation"
				+ "?srcLat=31.768909&srcLng=34.627724&dstLat=31.771334&dstLng=34.632500&accessibilityThreshold=1")
						.header("authToken", this.t.getToken()).contentType(contentType))
				.andExpect(status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.jsonPath("$.latlng").isArray());
	}
	
	@Test
	public void responseLatLngArrayIsNotEmpty() throws IOException, Exception {
		mockMvc.perform(post("/navigation"
				+ "?srcLat=31.768909&srcLng=34.627724&dstLat=31.771334&dstLng=34.632500&accessibilityThreshold=1")
						.header("authToken", this.t.getToken()).contentType(contentType))
				.andExpect(status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.jsonPath("$.latlng").isNotEmpty());
	}

}
