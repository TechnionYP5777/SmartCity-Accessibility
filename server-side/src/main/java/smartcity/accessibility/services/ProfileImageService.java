package smartcity.accessibility.services;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import smartcity.accessibility.database.AbstractUserProfileManager;

/**
 * 
 * @author yael
 *
 */
@Controller
public class ProfileImageService {
	private static Logger logger = LoggerFactory.getLogger(ProfileImageService.class);

	@RequestMapping(value = "/profileImg", method = RequestMethod.GET)
	public void getProfileImage(HttpServletResponse response, @RequestParam("token") String token)
			throws IOException {
		ByteArrayOutputStream OutputStream = new ByteArrayOutputStream();
		try {
			UserInfo userInfo = LogInService.getUserInfo(token);
			BufferedImage image = userInfo.getUser().getProfile().getProfileImg();
			if(image == null){
				image = ImageIO.read(new File("res/profileImgDef.png")); 
			}
			ImageIO.write(image, "png", OutputStream);
		} catch (IllegalArgumentException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			logger.info("problem reading image", e);
		}

		byte[] imgByte = OutputStream.toByteArray();

		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/png");
		ServletOutputStream responseOutputStream = response.getOutputStream();
		responseOutputStream.write(imgByte);
		responseOutputStream.flush();
		responseOutputStream.close();
	}

	@CrossOrigin
	@RequestMapping(value = "/uploadProfileImg", method = RequestMethod.POST)
	public  @ResponseBody void uploadProfileImg(@RequestParam("file") MultipartFile img, @RequestHeader("authToken") String token) {
		if (!img.isEmpty()) {
			byte[] bytes = null;
			try {
				bytes = img.getBytes();
			} catch (IOException e) {
				logger.info("problem in image to byte array", e);
			}
			try (ByteArrayInputStream stream = new ByteArrayInputStream(bytes)) {
				UserInfo userInfo = LogInService.getUserInfo(token);
				BufferedImage profileImg = ImageIO.read(stream);
				userInfo.getUser().getProfile().setProfileImg(profileImg);
				 AbstractUserProfileManager.instance().update(userInfo.getUser().getProfile(), null);
			} catch (IOException e) {
				logger.info("problem in writing byte array to stream", e);
			}
		}
	}
}
