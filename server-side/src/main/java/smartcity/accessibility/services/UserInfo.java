package smartcity.accessibility.services;

import smartcity.accessibility.mapmanagement.JxMapsFunctionality;
import smartcity.accessibility.mapmanagement.JxMapsFunctionality.ExtendedMapView;
import smartcity.accessibility.socialnetwork.User;
/**
 * @author yael
 */
public class UserInfo {
	private User u;
	private ExtendedMapView mapView;
	
	public UserInfo(User u) {
		this.setUser(u);
		this.mapView = JxMapsFunctionality.getNewMapView();
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

	public ExtendedMapView getMapView() {
		return mapView;
	}

	public void setMapView(ExtendedMapView mapView) {
		this.mapView = mapView;
	}

}