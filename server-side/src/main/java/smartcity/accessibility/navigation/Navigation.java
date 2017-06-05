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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import smartcity.accessibility.mapmanagement.LatLng;

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

	private static final String MAP_KEY = "oa35ASKtNrnBGwF1cW0xV6fXujJ81j2Y";
	private static Logger logger = LoggerFactory.getLogger(Navigation.class);

	private Navigation() {
	}

	public static RouteResponse getRoute(Location source, Location destination, Integer accessibilityThreshold)
			throws CommunicationFailed {
		List<MapSegment> segmentsToAvoid = getSegmentsToAvoid(source, destination, accessibilityThreshold);
		Latlng from = new Latlng(source.getCoordinates().getLat(), source.getCoordinates().getLng());
		Latlng to = new Latlng(destination.getCoordinates().getLat(), destination.getCoordinates().getLng());
		Route route = getRouteFromMapQuest(from, to, segmentsToAvoid);
		Double[] shapePointsArr = route.getShape().getShapePoints();
		Latlng[] latlng = new Latlng[shapePointsArr.length / 2];
		for (int k = 0, i = 0; i < shapePointsArr.length - 1; i += 2, k++)
			latlng[k] = new Latlng(shapePointsArr[i], shapePointsArr[i + 1]);
		RouteResponse r = new RouteResponse(route);
		r.setLatlng(latlng);
		return r;
	}

	public static Route getRouteFromMapQuest(Latlng from, Latlng to, List<MapSegment> segmentsToAvoid)
			throws CommunicationFailed {
		Client client = ClientBuilder.newClient();
		String path = "http://www.mapquestapi.com/directions/v2/route?";
		WebTarget target = client.target(path).queryParam("key", MAP_KEY)
				.queryParam("from", from.getLat() + "," + from.getLng())
				.queryParam("to", to.getLat() + "," + to.getLng()).queryParam("fullShape", true)
				.queryParam("shapeFormat", "raw").queryParam("routeType", "pedestrian").queryParam("unit", "k");
		Set<String> mustAvoidLinkIds = new HashSet<>();
		if (!segmentsToAvoid.isEmpty()) {
			for (MapSegment ¢ : segmentsToAvoid)
				mustAvoidLinkIds.add(¢.getLinkId() + "");
			target = target.queryParam("mustAvoidLinkIds", String.join(",", mustAvoidLinkIds));
		}
		Response response;
		try {
			response = target.request().get();
		} catch (ProcessingException e) {
			logger.info("Navigation: there is problem with the processing of the response", e);
			throw new CommunicationFailed("");
		}
		if (response.getStatus() != 200)
			throw new CommunicationFailed("");
		RouteWraper rw = response.readEntity(RouteWraper.class);
		if (rw.getInfo().getStatuscode() != 0)
			throw new CommunicationFailed(String.join(",", rw.getInfo().getMessages()));
		return rw.getRoute();
	}

	private static List<MapSegment> getSegmentsToAvoid(Location source, Location destination,
			Integer accessibilityThreshold) throws CommunicationFailed {
		List<LatLng> locationsToAvoid = AbstractLocationManager.instance().getNonAccessibleLocationsInRadius(
				source.getCoordinates(), destination.getCoordinates(), accessibilityThreshold, null);
		List<MapSegment> l = new ArrayList<>();
		for (LatLng ¢ : locationsToAvoid)
			l.add(getMapSegmentOfLatLng(¢.getLat(), ¢.getLng()));
		return l;
	}

	public static MapSegment getMapSegmentOfLatLng(double lat, double lng) throws CommunicationFailed {

		try {
			Response r = ClientBuilder.newClient().target("http://www.mapquestapi.com/directions/v2/findlinkid?")
					.queryParam("key", MAP_KEY).queryParam("lat", lat).queryParam("lng", lng).request().get();
			if (r.getStatus() != 200)
				throw new CommunicationFailed("");
			return r.readEntity(MapSegment.class);
		} catch (ProcessingException e) {
			logger.info("Navigation: there is problem with the processing of the response", e);
			throw new CommunicationFailed("");
		}

	}

}