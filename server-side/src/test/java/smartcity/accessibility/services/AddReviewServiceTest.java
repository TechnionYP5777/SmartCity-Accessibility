package smartcity.accessibility.services;

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
import smartcity.accessibility.database.AbstractReviewManager;
import smartcity.accessibility.database.AbstractUserProfileManager;
import smartcity.accessibility.database.ParseDatabase;
import smartcity.accessibility.socialnetwork.UserBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class AddReviewServiceTest extends ServiceTest {
	
	private Token t;

	@Before
	public void setup() throws Exception {
		ParseDatabase.initialize();
		AbstractUserProfileManager mock_UserManagerProfile = Mockito.mock(AbstractUserProfileManager.class);
		AbstractUserProfileManager.initialize(mock_UserManagerProfile);
		AbstractLocationManager mock_LocationManagerProfile = Mockito.mock(AbstractLocationManager.class);
		AbstractLocationManager.initialize(mock_LocationManagerProfile);
		AbstractReviewManager mock_ReviewManagerProfile = Mockito.mock(AbstractReviewManager.class);
		AbstractReviewManager.initialize(mock_ReviewManagerProfile);
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
		mockMvc.perform(post("/signup?name=me&password=1234"));
		mockMvc.perform(post("/login?name=me&password=1234"));
		this.t = Token.calcToken(new UserBuilder().setUsername("me").setPassword("1234").build());
	}
	
	@Test
	@Category(UnitTests.class)
	public void addReview() throws Exception{
		mockMvc.perform(post("/addreview?review=wow this is an amazing place&score=4&lat=1&lng=1&type=FACILITY&subtype=RESTAURANT&name=haifa")
				.header("authToken", this.t.getToken()).contentType(contentType))
		.andExpect(status().is2xxSuccessful());
	}
	
	@Test
	@Category(UnitTests.class)
	public void addStreetReview() throws Exception{
		mockMvc.perform(post("/addreview?review=can't access this street&score=1&lat=1&lng=1&type=STREET&subtype=DEFAULT&name=haifa")
				.header("authToken", this.t.getToken()).contentType(contentType))
		.andExpect(status().is2xxSuccessful());
	}
	
	@Test
	@Category(UnitTests.class)
	public void adminAddReview() throws Exception{
		mockMvc.perform(post("/login?name=yaeli&password=1234"));
		this.t = Token.calcToken(new UserBuilder().setUsername("yaeli").setPassword("1234").build());
		mockMvc.perform(post("/addreview?review=wow this is an amazing place&score=4&lat=1&lng=1&type=FACILITY&subtype=RESTAURANT&name=haifa")
				.header("authToken", this.t.getToken()).contentType(contentType))
		.andExpect(status().is2xxSuccessful());
	}
	
	@Test
	@Category(UnitTests.class)
	public void adminAddStreetReview() throws Exception{
		mockMvc.perform(post("/login?name=yaeli&password=1234"));
		this.t = Token.calcToken(new UserBuilder().setUsername("yaeli").setPassword("1234").build());
		mockMvc.perform(post("/addreview?review=can't access this street&score=1&lat=1&lng=1&type=STREET&subtype=DEFAULT&name=haifa")
				.header("authToken", this.t.getToken()).contentType(contentType))
		.andExpect(status().is2xxSuccessful());
	}
	
	@Test
	@Category(UnitTests.class)
	public void notLoggedInAddReview() throws Exception{
		this.t = Token.calcToken(new UserBuilder().setUsername("loggedout").setPassword("1234").build());
		mockMvc.perform(post("/addreview?review=rev&score=1&lat=1&lng=1&type=STREET&subtype=DEFAULT&name=haifa")
				.header("authToken", this.t.getToken()).contentType(contentType))
		.andExpect(status().isUnauthorized());
	}
	

}
