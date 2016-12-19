package smartcity.accessibility.navigation;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import smartcity.accessibility.database.LocationManager;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.navigation.mapquestcommunication.Latlng;
import smartcity.accessibility.navigation.mapquestcommunication.Route;
import smartcity.accessibility.navigation.mapquestcommunication.RouteWraper;

/**
 * 
 * @author yael This class help finds routes in the city. The class contains
 *         segments of the map that should be avoided in the routes it returns.
 */
public class Navigation {

	final String mapquestKey = "oa35ASKtNrnBGwF1cW0xV6fXujJ81j2Y";

	public Navigation() {
	}

	/**
	 * [[SuppressWarningsSpartan]]
	 */
	@SuppressWarnings("unused")
	public Route showRoute(Location source, Location destination, Integer accessibilityThreshold) {
		List<MapSegment> segmentsToAvoid = getSegmentsToAvoid(source, destination, accessibilityThreshold);
		// Route route =
		// getRouteFromMapQuest(source,destination,segmentsToAvoid);
		// TODO display route for now
		// TODO convert route to a format that can be displayed in JXMap
		return null;
	}

	/**
	 * [[SuppressWarningsSpartan]]
	 */
	public Route getRouteFromMapQuest(Latlng from, Latlng to, List<MapSegment> segmentsToAvoid) {
		// (1) TODO request from server to avoid the segments of segmentsToAvoid
		// (2) TODO request route form servers
		// TODO make this code more OOP style...
		Client client = ClientBuilder.newClient();
		String path = "http://www.mapquestapi.com/directions/v2/route?";
		path += "key=" + mapquestKey + "&";
		path += "from=" + from.getLat() + "," + from.getLng() + "&";
		path += "to=" + to.getLat() + "," + to.getLng();
		path += "&fullShape=true&shapeFormat=raw";
		WebTarget target = client.target(path);
		RouteWraper routeWraper = target.request().get(RouteWraper.class);
		return routeWraper.getRoute();
	}

	/**
	 * [[SuppressWarningsSpartan]]
	 */
	private List<MapSegment> getSegmentsToAvoid(Location source, Location destination, Integer accessibilityThreshold) {
		List<Location> locationsToAvoid = LocationManager.getNonAccessibleLocationsInRadius(source, destination,
				accessibilityThreshold);
		List<MapSegment> mapSegmentsToAVoid = new ArrayList<MapSegment>();
		for(Location l : locationsToAvoid){
			MapSegment mapSegment = getMapSegmentOfLatLng(l.getCoordinates().getLat(),l.getCoordinates().getLng());
			mapSegmentsToAVoid.add(mapSegment);
		}
		// TODO return list of segment to avoid within the given radius
		// this method will use LocationManager to get the locations and then
		// identify their MapSegment
		return null;
	}

	/**
	 * [[SuppressWarningsSpartan]]
	 */
	private MapSegment getMapSegmentOfLatLng(double lat, double lng) {
		//convert lanlng to locationId
		Client client = ClientBuilder.newClient();
		String path = "http://www.mapquestapi.com/directions/v2/findlinkid?";
		path += "key=" + mapquestKey + "&";
		path += "lat="+lat+"&";
		path += "lng="+lng;
		WebTarget target = client.target(path);
		MapSegment mapSegment = target.request().get(MapSegment.class);
		return mapSegment;
	}

}
