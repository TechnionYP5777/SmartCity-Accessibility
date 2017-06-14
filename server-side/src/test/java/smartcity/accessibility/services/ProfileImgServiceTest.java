package smartcity.accessibility.services;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import smartcity.accessibility.categories.UnitTests;
import smartcity.accessibility.database.AbstractUserProfileManager;
import smartcity.accessibility.database.ParseDatabase;
import smartcity.accessibility.socialnetwork.UserBuilder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@Category(UnitTests.class)
public class ProfileImgServiceTest extends ServiceTest {

	private Token t;

	@Before
	public void setup() throws Exception {
		ParseDatabase.initialize();
		AbstractUserProfileManager mock_UserManagerProfile = Mockito.mock(AbstractUserProfileManager.class);
		AbstractUserProfileManager.initialize(mock_UserManagerProfile);

		this.mockMvc = webAppContextSetup(webApplicationContext).build();
		mockMvc.perform(post("/signup?name=me&password=1234"));
		mockMvc.perform(post("/login?name=me&password=1234"));
		this.t = Token.calcToken(new UserBuilder().setUsername("me").setPassword("1234").build());
	}

	@Test
	public void getProfileImageSuccess() throws IOException, Exception {
		mockMvc.perform(get("/profileImg" + "?token=" + t.getToken()).contentType(contentType))
				.andExpect(status().is2xxSuccessful());
	}
	
	@Test
	public void getProfileImageDefault() throws IOException, Exception {
		MvcResult result = mockMvc.perform(get("/profileImg" + "?token=" + t.getToken()).contentType(contentType))
				.andExpect(status().is2xxSuccessful()).andReturn();
		byte[] content = result.getResponse().getContentAsByteArray();
		BufferedImage image = ImageIO.read(new File("res/profileImgDef.png"));
		ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
		ImageIO.write(image, "png", pngOutputStream );
		byte[] defaultContent = pngOutputStream.toByteArray();
		Assert.assertArrayEquals(content,defaultContent);
	}
}
