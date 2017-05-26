package smartcity.accessibility.services;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import smartcity.accessibility.categories.UnitTests;

/**
 * @author Ariel
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class LocationsInRadiusServiceTest extends ServiceTest{
	
	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	@Category(UnitTests.class)
	public void locationtest1() throws Exception {
		String res = mockMvc.perform(get("locationsInRadius?srcLat=31.906953&srcLng=34.992489")).andReturn().getResponse().getContentAsString();
		System.out.println(res);
	}
}
