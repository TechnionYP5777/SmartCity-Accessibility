package smartcity.accessibility.navigation;

import smartcity.accessibility.navigation.mapquestcommunication.Latlng;

/**
 * This class is the response for our NavigationService There is some
 * duplication with Route.java but due to our move to ionic it has to be.
 * 
 * @author yael
 *
 */
public class RouteResponse {
	private Latlng[] latlng;
	private Integer time;

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public Latlng[] getLatlng() {
		return latlng;
	}

	public void setLatlng(Latlng[] latlng) {
		this.latlng = latlng;
	}
}
