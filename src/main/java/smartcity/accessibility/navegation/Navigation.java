package smartcity.accessibility.navegation;

import java.util.ArrayList;
import java.util.List;

import smartcity.accessibility.mapmanagement.Location;

/**
 * 
 * @author yael This class help finds routs in the city. The class contains
 *         segments of the map that should be avoided in the routes it returns.
 */
public class Navigation {
	List<MapSegment> segmentsToAvoid;

	public Navigation() {
		this.segmentsToAvoid = new ArrayList<MapSegment>();
	}

	public Route showRoute(Location source, Location destination) {
		// TODO request rout from S to D from mapquest and return it.
		return new Route();
	}

	public void addLocationToAvoid(Location __) {
		// TODO this function might get Location or MapSegment or something like
		// that
		// and add it to the segments that need to be avoid in the route.
		// for now the method get location which mean the method will find the
		// right MapSegement for this location.
	}
}
