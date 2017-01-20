package smartcity.accessibility.navigation.mapquestcommunication;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class is wrapper of the class Route. This class is needed for
 * communication with MapQuest servers.
 * 
 * @author yael
 */
@XmlRootElement
public class RouteWraper {
	private Route route;
	private Info info;

	public RouteWraper() {

	}

	public Route getRoute() {
		return route;
	}

	public Info getInfo() {
		return info;
	}

	public void setInfo(Info i) {
		this.info = i;
	}

	public void setRoute(Route r) {
		this.route = r;
	}
}
