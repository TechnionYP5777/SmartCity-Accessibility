package smartcity.accessibility.socialnetwork;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author KaplanAlexander
 *
 */
public class UserProfile {

	private final String username;
	private Helpfulness hlp;
	
	@JsonIgnore
	private BufferedImage profileImg;

	private static Logger logger = LoggerFactory.getLogger(UserProfile.class);

	public BufferedImage getProfileImg() {
		return profileImg;
	}

	public void setProfileImg(BufferedImage profileImg) {
		this.profileImg = profileImg;
	}

	public UserProfile(String username) {
		this.username = username;
		hlp = new Helpfulness();
		try {
			this.profileImg = ImageIO.read(new File("res/profileImgDef.png"));
		} catch (IOException e) {
			logger.info("failed to read default profile image", e);
		}
	}

	public String getUsername() {
		return username;
	}

	public int getRating() {
		return hlp.getRating();
	}

	public void setHelpfulness(Helpfulness h) {
		this.hlp = h;
	}

	public int getNumOfReviews() {
		return hlp.getNumOfReviews();
	}

	public void upvote() {
		hlp.upvote();
	}

	public void downvote() {
		hlp.downvote();
	}

	public void addReview() {
		hlp.addReview();
	}

	public void removeReview() {
		hlp.removeReview();
	}

	public double getAvgRating() {
		return hlp.helpfulness();
	}

	@Override
	public String toString() {
		return "UserProfile [username=" + username + "]";
	}

	public Helpfulness getHelpfulness() {
		return hlp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserProfile other = (UserProfile) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
}
