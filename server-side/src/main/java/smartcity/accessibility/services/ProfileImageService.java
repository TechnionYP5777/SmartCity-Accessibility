package smartcity.accessibility.services;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * @author yael
 *
 */
@Controller
public class ProfileImageService {
	private static Logger logger = LoggerFactory.getLogger(ProfileImageService.class);

	@RequestMapping(value = "/profileImg", method = RequestMethod.GET)
	public void getImageNerrative(HttpServletResponse response, @RequestParam("userName") String UserName) throws IOException {
		ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
		try {
			BufferedImage image = ImageIO.read(new File("res/profileImgDef.png")); // TODO get image
																// instead of default image
			ImageIO.write(image, "png", pngOutputStream);
		} catch (IllegalArgumentException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			logger.info("problem reading image",e);
		}

		byte[] imgByte = pngOutputStream.toByteArray();

		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/png");
		ServletOutputStream responseOutputStream = response.getOutputStream();
		responseOutputStream.write(imgByte);
		responseOutputStream.flush();
		responseOutputStream.close();
	}
}
