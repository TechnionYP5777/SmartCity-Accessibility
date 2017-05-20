package smartcity.accessibility.services;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import smartcity.accessibility.categories.UnitTests;
import smartcity.accessibility.database.ParseDatabase;

/**
 * @author ariel
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class AdressSearchServiceTest extends ServiceTest{

	
	@Before
	public void setup() throws Exception {
		ParseDatabase.initialize();
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	@Category(UnitTests.class)
	public void searchSuccess() throws Exception {
		ResultMatcher mb = MockMvcResultMatchers.content().json("Yehalom 70");
		
		String res = mockMvc.perform(get("/simpleSearch/Yehalom 70")).andReturn().getResponse().getContentAsString();
		//Thread.sleep(20);
		Assert.assertTrue(res.contains(":34.992489,"));
		Assert.assertTrue(res.contains(":31.906953}"));
		Assert.assertTrue(res.contains("Yahalom St 70, Modi'in-Maccabim-Re'ut, Israel"));
	}
}
