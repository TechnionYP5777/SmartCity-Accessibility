package smartcity.accessibility.navigation.mapquestcommunication;


/**
 * 
 * @author yael This class represents a Leg in the route. this class is needed
 *         for communication with mapQuest servers.
 *         Leg in a route is part of the route.(really, look it up)
 */
public class Leg {
	private Maneuver[] maneuvers;

	public Leg() {

	}

	public Maneuver[] getManeuvers() {
		return maneuvers;
	}

	public void setManeuvers(Maneuver[] ms) {
		this.maneuvers = ms;
	}
	
}
