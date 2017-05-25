package smartcity.accessibility.navigation.mapquestcommunication;

/**
 * Leg is part of the route result
 * 
 * @author yael
 *
 */
public class Leg {
	private Manuever[] maneuvers;

	public Manuever[] getManeuvers() {
		return maneuvers;
	}

	public void setManeuvers(Manuever[] maneuvers) {
		this.maneuvers = maneuvers;
	}
}
