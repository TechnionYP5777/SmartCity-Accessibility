package smartcity.accessibility.services;

import smartcity.accessibility.socialnetwork.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yael
 */
public class Token {

	private static Logger logger = LoggerFactory.getLogger(Token.class);
	private String tokenString;

	public static Token calcToken(User u) {
		String str = u.getUsername() + u.getPassword();
		Token t = new Token();
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(str.getBytes());
			String hashStr = String.format("%064x", new java.math.BigInteger(1, messageDigest.digest()));
			t.setToken(hashStr);
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
}
