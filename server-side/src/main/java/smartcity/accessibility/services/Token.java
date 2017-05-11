package smartcity.accessibility.services;

import smartcity.accessibility.socialnetwork.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author yael
 */
public class Token {
	private String token;
	private Token(String token) {
		this.setToken(token);
	}
	static public Token calcToken(User u){
		String str = u.getName()+u.getPassword();
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
		}
		messageDigest.update(str.getBytes());
		String hash_str = String.format("%064x", new java.math.BigInteger(1, messageDigest.digest()));
		return new Token(hash_str);
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}
