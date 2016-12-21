package smartcity.accessibility.navigation;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import smartcity.accessibility.database.LocationManager;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.navigation.mapquestcommunication.Latlng;
import smartcity.accessibility.navigation.mapquestcommunication.Route;
import smartcity.accessibility.navigation.mapquestcommunication.RouteWraper;
import smartcity.accessibility.navigation.mapquestcommunication.Shape;

/**
 * 
 * @author yael This class help finds routes in the city. The class contains
 *         segments of the map that should be avoided in the routes it returns.
 */
public class Navigation {

	final String mapquestKey = "oa35ASKtNrnBGwF1cW0xV6fXujJ81j2Y";

	public Navigation() {
	}

	
	public Route showRoute(Location source, Location destination, Integer accessibilityThreshold) {
		List<MapSegment> segmentsToAvoid = getSegmentsToAvoid(source, destination, accessibilityThreshold);
		Latlng from = new Latlng(source.getCoordinates().getLat(),source.getCoordinates().getLng());
		Latlng to = new Latlng(destination.getCoordinates().getLat(),destination.getCoordinates().getLng());
		Route r = getRouteFromMapQuest(from,to,segmentsToAvoid);
		Shape s = r.getShape();
		JxMapsConvertor.displayRoute(s.getShapePoints());
		//TODO deal with the return value
		return null;
	}

	/**
	 * [[SuppressWarningsSpartan]]
	 */
	public Route getRouteFromMapQuest(Latlng from, Latlng to, List<MapSegment> segmentsToAvoid) {
		// TODO make this code more OOP style...

		Client client = ClientBuilder.newClient();
		String path = "http://www.mapquestapi.com/directions/v2/route?";
		WebTarget target = client.target(path);
		target = target.queryParam("key", mapquestKey)
			  .queryParam("from", from.getLat() + "," + from.getLng())
			  .queryParam("to", to.getLat() + "," + to.getLng())
			  .queryParam("fullShape", true)
			  .queryParam("shapeFormat", "raw");
		String mustAvoidLinkIds = "";
		if(!segmentsToAvoid.isEmpty()){
			for (MapSegment m : segmentsToAvoid) {
				mustAvoidLinkIds += m.getLinkId();
			}
			mustAvoidLinkIds = String.join((CharSequence) ",", (CharSequence) mustAvoidLinkIds);
			//path+="&mustAvoidLinkIds="+mustAvoidLinkIds;
			 target = target.queryParam("mustAvoidLinkIds", mustAvoidLinkIds);
		}
		
		Response response = target.request().get();
		RouteWraper routeWraper = response.readEntity(RouteWraper.class);
		return routeWraper.getRoute();
	}

	private List<MapSegment> getSegmentsToAvoid(Location source, Location destination, Integer accessibilityThreshold) {
		List<Location> locationsToAvoid = LocationManager.getNonAccessibleLocationsInRadius(source, destination,
				accessibilityThreshold);
		List<MapSegment> $ = new ArrayList<MapSegment>();
		for (Location l : locationsToAvoid)
			$.add(getMapSegmentOfLatLng(l.getCoordinates().getLat(), l.getCoordinates().getLng()));
		return $;
	}

	
	public MapSegment getMapSegmentOfLatLng(double lat, double lng) {
		return ClientBuilder.newClient().target(("http://www.mapquestapi.com/directions/v2/findlinkid?" + "key="
				+ mapquestKey + "&" + "lat=" + lat + "&" + "lng=" + lng)).request().get(MapSegment.class);
	}

}
;