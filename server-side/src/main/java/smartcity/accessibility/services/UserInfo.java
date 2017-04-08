package smartcity.accessibility.services;

import smartcity.accessibility.socialnetwork.User;

public class UserInfo {
	private User u;

	public UserInfo(User u) {
		this.setUser(u);
	}

	public UserInfo() {
		this.setUser(null);
	}

	public User getUser() {
		return u;
	}

	public void setUser(User u) {
		this.u = u;
	}

}