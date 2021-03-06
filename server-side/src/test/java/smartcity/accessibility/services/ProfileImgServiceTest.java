package smartcity.accessibility.services;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.annotation.ResponseStatus;

import smartcity.accessibility.categories.UnitTests;
import smartcity.accessibility.database.AbstractUserProfileManager;
import smartcity.accessibility.database.ParseDatabase;
import smartcity.accessibility.services.exceptions.UploadImageFailed;
import smartcity.accessibility.socialnetwork.UserBuilder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@Category(UnitTests.class)
/**
 * 
 * @author yael
 *
 */
public class ProfileImgServiceTest extends ServiceTest {

	private Token t;

	@Before
	public void setup() throws Exception {
		ParseDatabase.initialize();
		AbstractUserProfileManager mock_UserManagerProfile = Mockito.mock(AbstractUserProfileManager.class);
		AbstractUserProfileManager.initialize(mock_UserManagerProfile);

		this.mockMvc = webAppContextSetup(webApplicationContext).build();
		mockMvc.perform(post("/signup?name=im&password=1234"));
		mockMvc.perform(post("/login?name=im&password=1234"));
		this.t = Token.calcToken(new UserBuilder().setUsername("im").setPassword("1234").build());
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
		BufferedImage image = ImageIO.read(new File("res/profileImgDef.jpg"));
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", outputStream);
		byte[] defaultContent = outputStream.toByteArray();
		Assert.assertArrayEquals(content, defaultContent);
	}

	@Test
	public void UpdateProfileImageSuccess() throws IOException, Exception {
		InputStream s = new FileInputStream("res/profileImgDef.jpg");
		MockMultipartFile file = new MockMultipartFile("file", s);
		mockMvc.perform(fileUpload("/uploadProfileImg").file(file).header("authToken", this.t.getToken())
				.contentType(contentType)).andExpect(status().is2xxSuccessful()).andReturn();
	}

	@Test
	public void UpdateProfileImageChanged() throws IOException, Exception {
		UserInfo uf = LogInService.getUserInfo(this.t.getToken());
		BufferedImage beforeImg = uf.getUser().getProfile().getProfileImg();

		InputStream s = new FileInputStream("res/profileImgTest.jpg");
		MockMultipartFile file = new MockMultipartFile("file", s);

		mockMvc.perform(fileUpload("/uploadProfileImg").file(file).header("authToken", this.t.getToken())
				.contentType(contentType)).andExpect(status().is2xxSuccessful()).andReturn();

		BufferedImage afterImg = uf.getUser().getProfile().getProfileImg();
		ByteArrayOutputStream streamBefore = new ByteArrayOutputStream();
		ImageIO.write(beforeImg, "jpg", streamBefore);
		ByteArrayOutputStream streamAfter = new ByteArrayOutputStream();
		ImageIO.write(afterImg, "jpg", streamAfter);

		Assert.assertFalse(Arrays.equals(streamBefore.toByteArray(), streamAfter.toByteArray()));
	}

	@Test
	public void UpdateProfileImageWrongFormat() throws IOException, Exception {
		InputStream s = new FileInputStream("res/profileImgDef.png");
		MockMultipartFile file = new MockMultipartFile("file", s);
		String reason = UploadImageFailed.class.getAnnotation(ResponseStatus.class).reason();
		mockMvc.perform(fileUpload("/uploadProfileImg").file(file).header("authToken", this.t.getToken())
				.contentType(contentType)).andExpect(status().isBadRequest()).andExpect(status().reason(reason));
	}
}
