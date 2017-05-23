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

import smartcity.accessibility.categories.UnitTests;

/**
 * @author ariel
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class AdressSearchServiceTest extends ServiceTest{

	
	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	@Category(UnitTests.class)
	public void searchSuccess() throws Exception {
		String res = mockMvc.perform(get("/simpleSearch/Yehalom 70")).andReturn().getResponse().getContentAsString();
		Assert.assertTrue(res.contains(":34.992489"));
		Assert.assertTrue(res.contains(":31.906953"));
		Assert.assertTrue(res.contains("Yahalom St 70, Modi'in-Maccabim-Re'ut, Israel"));
	}
}
