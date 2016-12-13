package smartcity.accessibility.navigation.mapquestcommunication;

import java.util.List;

/**
 * 
 * @author yael This class represents a Leg in the route. this class is needed
 *         for communication with mapQuest servers.
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
