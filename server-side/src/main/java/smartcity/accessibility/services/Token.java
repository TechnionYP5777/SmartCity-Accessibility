package smartcity.accessibility.services;

import smartcity.accessibility.socialnetwork.User;
import smartcity.accessibility.socialnetwork.User.Privilege;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yael
 */
public class Token {

	private static Logger logger = LoggerFactory.getLogger(Token.class);
	private String tokenString;
	private Date expirationDate;
	private Boolean isAdmin;

	public static Token calcToken(User u) {
		String str = u.getUsername() + u.getPassword();
		Token t = new Token();
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(str.getBytes());
			String hashStr = String.format("%064x", new java.math.BigInteger(1, messageDigest.digest()));
			t.setToken(hashStr);
			t.setExpirationDate(DateUtils.addMinutes(new Date(), 3));
			t.setIsAdmin(u.getPrivilegeLevel().equals(Privilege.Admin));
		} catch (NoSuchAlgorithmException e) {
			logger.info("token hash function doesn't exists", e);
		}

		return t;
	}

	public String getToken() {
		return tokenString;
	}

	public void setToken(String token) {
		this.tokenString = token;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
}
