package smartcity.accessibility.services;

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
import smartcity.accessibility.database.AbstractUserProfileManager;
import smartcity.accessibility.database.ParseDatabase;

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
	
	@Test
	public void test(){
		
	}
}
