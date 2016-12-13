package smartcity.accessibility.navigation.mapquestcommunication;

import java.util.List;

/**
 * 
 * @author yael This class represent a step in the route. This class is needed
 *         for communication with the servers.
 */
public class Maneuver {
	private List<Integer> linkIds;
	private List<String> street_names;
	private Direction direction;

	public Maneuver() {

	}

	public List<Integer> getLinkIds() {
		return linkIds;
	}

	public void setLinkIds(List<Integer> linkIds) {
		this.linkIds = linkIds;
	}

	public List<String> getStreet_names() {
		return street_names;
	}

	public void setStreet_names(List<String> street_names) {
		this.street_names = street_names;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction d) {
		this.direction = d;
	}
}
