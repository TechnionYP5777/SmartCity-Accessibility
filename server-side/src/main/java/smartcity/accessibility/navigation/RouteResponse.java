package smartcity.accessibility.navigation;

import smartcity.accessibility.navigation.mapquestcommunication.Latlng;
import smartcity.accessibility.navigation.mapquestcommunication.Route;

/**
 * This class is the response for our NavigationService This is class extends
 * Rout.java to avoid duplication due to our move to ionic.
 * 
 * @author yael
 *
 */
public class RouteResponse extends Route {
	private Latlng[] latlng;

	public RouteResponse(Route r) {
		this.setDistance(r.getDistance());
		this.setTime(r.getTime());
		this.setLegs(r.getLegs());
	}

	public Latlng[] getLatlng() {
		return latlng;
	}

	public void setLatlng(Latlng[] latlng) {
		this.latlng = latlng;
	}
}
