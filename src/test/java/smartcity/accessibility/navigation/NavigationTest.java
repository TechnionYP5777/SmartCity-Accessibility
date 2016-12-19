package smartcity.accessibility.navigation;

import org.junit.Test;

import smartcity.accessibility.navigation.mapquestcommunication.Latlng;
import smartcity.accessibility.navigation.mapquestcommunication.Route;
import smartcity.accessibility.navigation.mapquestcommunication.Shape;

public class NavigationTest {

	/**
	 * [[SuppressWarningsSpartan]]
	 */
	@Test
	public void getSimpleRouteFromServers() {
		// TODO this test is temporal for it relay on things that will change!
		Latlng from = new Latlng(39.750307, -104.999472);
		Latlng to = new Latlng(40.750307, -105.999472);
		Route r = (new Navigation()).getRouteFromMapQuest(from, to, null);
		Shape shape = r.getShape();
		Double[] shapePoints = shape.getShapePoints();
		if (shapePoints.length % 2 != 0) {
			System.out.println("something went wrong :(");
		}
		for (int i = 0; i < shapePoints.length - 1; i += 2) {
			//i'm leaving it comment for now
			//System.out.println("[" + shapePoints[i] + "," + shapePoints[i + 1] + "]");
		}
	}
	
	/**
	 * [[SuppressWarningsSpartan]]
	 */
	@Test
	public void getMapSegmentFromLatLng() {
		// TODO this test is temporal for it relay on things that will change!
		MapSegment m = (new Navigation()).getMapSegmentOfLatLng(31.766932, 34.631666);
		System.out.println(m.getLinkId());
		System.out.println(m.getStreet());
	}
}
