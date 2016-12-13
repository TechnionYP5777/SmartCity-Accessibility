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

	final String mapquestKey = "oa35ASKtNrnBGwF1cW0xV6fXujJ81j2Y";

	public Navigation() {
	}

	/**
	 * [[SuppressWarningsSpartan]]
	 */
	public Route showRoute(Location source, Location destination, Integer accessabilityThreshold) {
		Double radiusOfRoute = calcRadius(source, destination);
		List<MapSegment> segmentsToAvoid = getSegmentsToAvoid(source, destination, accessabilityThreshold,
				radiusOfRoute);
		@SuppressWarnings("unused")
		Route route = getRouteFromMapQuest(segmentsToAvoid);
		// TODO display route for now
		// TODO convert route to a format that can be displayed in JXMap
		return null;
	}

	private Route getRouteFromMapQuest(List<MapSegment> segmentsToAvoid) {
		// (1) TODO request from server to avoid the segments of segmentsToAvoid
		// (2) TODO request route form servers
		return null;
	}

	private Double calcRadius(Location source, Location destination) {
		// TODO calculate the radius of the circle which the route can pass
		// through.
		return null;
	}

	private List<MapSegment> getSegmentsToAvoid(Location source, Location destination, Integer accessabilityThreshold,
			Double radiusOfRoute) {
		// TODO return list of segment to avoid within the given radius
		// this method will use LocationManager to get the locations and then
		// identify their MapSegment
		return null;
	}

}
