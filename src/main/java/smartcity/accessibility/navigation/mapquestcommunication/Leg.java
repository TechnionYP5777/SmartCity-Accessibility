package smartcity.accessibility.navigation.mapquestcommunication;

import java.util.List;

/**
 * 
 * @author yael This class represents a Leg in the route. this class is needed
 *         for communication with mapQuest servers.
 *         Leg in a route is part of the route.(really, look it up)
 */
public class Leg {
	private List<Maneuver> maneuvers;

	public Leg() {

	}

	public List<Maneuver> getManeuvers() {
		return maneuvers;
	}

	public void setManeuvers(List<Maneuver> ms) {
		this.maneuvers = ms;
	}
	
}
