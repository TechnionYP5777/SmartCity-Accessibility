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
	private Integer realTime;
	private Latlng[] latlng;

	public Integer getRealTime() {
		return realTime;
	}

	public void setRealTime(Integer realTime) {
		this.realTime = realTime;
	}

	public Latlng[] getLatlng() {
		return latlng;
	}

	public void setLatlng(Latlng[] latlng) {
		this.latlng = latlng;
	}

}
