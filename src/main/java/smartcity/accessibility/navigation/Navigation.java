package smartcity.accessibility.navigation;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.database.LocationManager;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.navigation.exception.CommunicationFailed;
import smartcity.accessibility.navigation.mapquestcommunication.Latlng;
import smartcity.accessibility.navigation.mapquestcommunication.Route;
import smartcity.accessibility.navigation.mapquestcommunication.RouteWraper;
import smartcity.accessibility.navigation.mapquestcommunication.Shape;

/**
 * This class help finds routes in the city. The class contains
 *         segments of the map that should be avoided in the routes it returns.
 * @author yael 
 */
public abstract class Navigation {

	private static final String mapquestKey = "oa35ASKtNrnBGwF1cW0xV6fXujJ81j2Y";

	
	public static LatLng[] showRoute(Location source, Location destination, Integer accessibilityThreshold) throws CommunicationFailed {
		List<MapSegment> segmentsToAvoid = getSegmentsToAvoid(source, destination, accessibilityThreshold);
		Latlng from = new Latlng(source.getCoordinates().getLat(),source.getCoordinates().getLng());
		Latlng to = new Latlng(destination.getCoordinates().getLat(),destination.getCoordinates().getLng());
		Route r = getRouteFromMapQuest(from,to,segmentsToAvoid);
		Shape s = r.getShape();
		return arrayToLatLng(s.getShapePoints());
	}

	private static LatLng[] arrayToLatLng(Double[] shapePointsArr) {
		LatLng[] $ = new LatLng[shapePointsArr.length / 2];
		int k = 0;
		for (int i = 0; i < shapePointsArr.length - 1; i += 2) {
			$[k] = (new LatLng(shapePointsArr[i], shapePointsArr[i + 1]));
			++k;
		}
		return $;
	}


	public static Route getRouteFromMapQuest(Latlng from, Latlng to, List<MapSegment> segmentsToAvoid) throws CommunicationFailed {
		// TODO make this code more OOP style...

		Client client = ClientBuilder.newClient();
		String path = "http://www.mapquestapi.com/directions/v2/route?";
		WebTarget target = client.target(path).queryParam("key", mapquestKey).queryParam("from", from.getLat() + "," + from.getLng())
				.queryParam("to", to.getLat() + "," + to.getLng()).queryParam("fullShape", true)
				.queryParam("shapeFormat", "raw");
		String mustAvoidLinkIds = "";
		if(!segmentsToAvoid.isEmpty()){
			for (MapSegment m : segmentsToAvoid)
				mustAvoidLinkIds += m.getLinkId();
			mustAvoidLinkIds = String.join((CharSequence) ",", (CharSequence) mustAvoidLinkIds);
			 target = target.queryParam("mustAvoidLinkIds", mustAvoidLinkIds);
		}
		
		Response response = target.request().get();
		if(response.getStatus() != 200)
			throw new CommunicationFailed();
		RouteWraper routeWraper = response.readEntity(RouteWraper.class);
		if(routeWraper.getInfo().getStatuscode() != 0)
			throw new CommunicationFailed();
		return routeWraper.getRoute();
	}

	private static List<MapSegment> getSegmentsToAvoid(Location source, Location destination, Integer accessibilityThreshold) {
		List<LatLng> locationsToAvoid = LocationManager.getNonAccessibleLocationsInRadius(source, destination,
				accessibilityThreshold);
		List<MapSegment> $ = new ArrayList<MapSegment>();
		for (LatLng l : locationsToAvoid)
			$.add(getMapSegmentOfLatLng(l.getLat(), l.getLng()));
		return $;
	}

	
	public static MapSegment getMapSegmentOfLatLng(double lat, double lng) {
		return ClientBuilder.newClient().target(("http://www.mapquestapi.com/directions/v2/findlinkid?" + "key="
				+ mapquestKey + "&" + "lat=" + lat + "&" + "lng=" + lng)).request().get(MapSegment.class);
	}

}
;