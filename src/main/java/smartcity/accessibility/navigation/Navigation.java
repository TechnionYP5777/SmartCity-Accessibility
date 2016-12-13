package smartcity.accessibility.navigation;

import java.util.List;

import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.navigation.mapquestcommunication.Route;

/**
 * 
 * @author yael This class help finds routs in the city. The class contains
 *         segments of the map that should be avoided in the routes it returns.
 */
public class Navigation {

	public Navigation() {
	}

	/**
	 * [[SuppressWarningsSpartan]]
	 */
	public Route showRoute(Location source, Location destination, Integer accessabilityThreshold) {
		// TODO request rout from S to D from mapquest and return it.
		@SuppressWarnings("unused")
		Double radiusOfRoute = calcRadius(source, destination);
		@SuppressWarnings("unused")
		List<MapSegment> segmentsToAvoid = getSegmentsToAvoid(source, destination, accessabilityThreshold);
		return null;
	}

	private Double calcRadius(Location source, Location destination) {
		// calculate the radius of the circle which the route can pass through.
		return null;
	}

	private List<MapSegment> getSegmentsToAvoid(Location source, Location destination, Integer accessabilityThreshold) {
		// return list of segment ot avoid
		return null;
	}

}
