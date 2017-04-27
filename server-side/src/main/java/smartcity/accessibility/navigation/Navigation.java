package smartcity.accessibility.navigation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.database.AbstractLocationManager;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.navigation.exception.CommunicationFailed;
import smartcity.accessibility.navigation.mapquestcommunication.Latlng;
import smartcity.accessibility.navigation.mapquestcommunication.Route;
import smartcity.accessibility.navigation.mapquestcommunication.RouteWraper;

/**
 * This class help finds routes in the city. The class able to find segments of
 * the map that should be avoided in the routes it returns.
 * 
 * @author yael
 */
public abstract class Navigation {

	private static final String mapquestKey = "oa35ASKtNrnBGwF1cW0xV6fXujJ81j2Y";

	public static LatLng[] showRoute(Location source, Location destination, Integer accessibilityThreshold)
			throws CommunicationFailed {
		List<MapSegment> segmentsToAvoid = getSegmentsToAvoid(source, destination, accessibilityThreshold);
		Latlng from = new Latlng(source.getCoordinates().getLat(), source.getCoordinates().getLng()),
				to = new Latlng(destination.getCoordinates().getLat(), destination.getCoordinates().getLng());
		Route $ = getRouteFromMapQuest(from, to, segmentsToAvoid);
		return arrayToLatLng($.getShape().getShapePoints());
	}

	public static LatLng[] arrayToLatLng(Double[] shapePointsArr) {
		LatLng[] $ = new LatLng[shapePointsArr.length / 2];
		for (int k = 0, ¢ = 0; ¢ < shapePointsArr.length - 1; ¢ += 2)
			$[k++] = (new LatLng(shapePointsArr[¢], shapePointsArr[¢ + 1]));
		return $;
	}

	public static Route getRouteFromMapQuest(Latlng from, Latlng to, List<MapSegment> segmentsToAvoid)
			throws CommunicationFailed {
		Client client = ClientBuilder.newClient();
		String path = "http://www.mapquestapi.com/directions/v2/route?";
		WebTarget target = client.target(path).queryParam("key", mapquestKey)
				.queryParam("from", from.getLat() + "," + from.getLng())
				.queryParam("to", to.getLat() + "," + to.getLng()).queryParam("fullShape", true)
				.queryParam("shapeFormat", "raw").queryParam("routeType", "pedestrian");
		Set<String> mustAvoidLinkIds = new HashSet<String>();
		if (!segmentsToAvoid.isEmpty()) {
			for (MapSegment ¢ : segmentsToAvoid)
				mustAvoidLinkIds.add(¢.getLinkId() + "");
			target = target.queryParam("mustAvoidLinkIds", String.join(",", mustAvoidLinkIds));
		}
		Response response;
		try {
			response = target.request().get();
		} catch (ProcessingException e) {
			throw new CommunicationFailed("");
		}
		if (response.getStatus() != 200)
			throw new CommunicationFailed("");
		RouteWraper $ = response.readEntity(RouteWraper.class);
		if ($.getInfo().getStatuscode() != 0)
			throw new CommunicationFailed(String.join(",",$.getInfo().getMessages()));
		return $.getRoute();
	}

	private static List<MapSegment> getSegmentsToAvoid(Location source, Location destination,
			Integer accessibilityThreshold) throws CommunicationFailed {
		List<LatLng> locationsToAvoid = AbstractLocationManager.instance().getNonAccessibleLocationsInRadius(source, destination,
				accessibilityThreshold, null);
		List<MapSegment> $ = new ArrayList<MapSegment>();
		for (LatLng ¢ : locationsToAvoid)
			$.add(getMapSegmentOfLatLng(¢.getLat(), ¢.getLng()));
		return $;
	}

	public static MapSegment getMapSegmentOfLatLng(double lat, double lng) throws CommunicationFailed {
		Response $;
		try {
			$ = ClientBuilder.newClient().target("http://www.mapquestapi.com/directions/v2/findlinkid?")
					.queryParam("key", mapquestKey).queryParam("lat", lat).queryParam("lng", lng).request().get();
		} catch (ProcessingException e) {
			throw new CommunicationFailed("");
		}
		if ($.getStatus() != 200)
			throw new CommunicationFailed("");
		return $.readEntity(MapSegment.class);
	}

};