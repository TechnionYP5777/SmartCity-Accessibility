package smartcity.accessibility.navigation;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author yael This class is wrapper of the class Route. This class is needed
 *         for communication with MapQuest servers.
 */
@XmlRootElement
public class RouteWraper {
	private Route route;

	public RouteWraper() {

	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route r) {
		this.route = r;
	}
}
