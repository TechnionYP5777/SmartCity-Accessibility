package smartcity.accessibility.navigation;

import java.util.ArrayList;
import java.util.List;
import org.junit.Ignore;
import org.junit.Test;

import com.teamdev.jxmaps.LatLng;
import com.teamdev.jxmaps.swing.MapView;

import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.mapmanagement.Street;
import smartcity.accessibility.navigation.exception.CommunicationFailed;
import smartcity.accessibility.navigation.mapquestcommunication.Latlng;
import smartcity.accessibility.navigation.mapquestcommunication.Route;
import smartcity.accessibility.navigation.mapquestcommunication.Shape;

import smartcity.accessibility.mapmanagement.JxMapsFunctionality;

public class NavigationTest {

	@Test
	public void getSimpleRouteFromServers() throws CommunicationFailed {
		// TODO this test is temporal for it relay on things that will change!
		Latlng from = new Latlng(39.750307, -104.999472);
		Latlng to = new Latlng(40.750307, -105.999472);
		Route r = Navigation.getRouteFromMapQuest(from, to, new ArrayList<MapSegment>());
		Shape shape = r.getShape();
		Double[] shapePoints = shape.getShapePoints();
		if (shapePoints.length % 2 != 0)
			System.out.println("something went wrong :(");
		for (int i = 0; i < shapePoints.length - 1; i += 2) {
			// System.out.println("[" + shapePoints[i] + "," + shapePoints[i +
			// 1] + "]");
		}
	}

	@Test
	public void getMapSegmentFromLatLng() {
		// TODO this test is temporal for it relay on things that will change!
		MapSegment m = Navigation.getMapSegmentOfLatLng(31.766932, 34.631666);
		System.out.println(m.getLinkId());
		System.out.println(m.getStreet());
	}

	@Test
	public void getRouteFromServersWithLinksToAvoid() throws CommunicationFailed {
		// TODO this test is temporal for it relay on things that will change!
		Latlng from = new Latlng(31.766932, 34.631666);// sd tel hai 61 ashdod
		Latlng to = new Latlng(31.770981, 34.620567);// HaYam HaTichon Blvd 1
		List<MapSegment> segmentsToAvoid = new ArrayList<MapSegment>();
		segmentsToAvoid.add(Navigation.getMapSegmentOfLatLng(31.769955, 34.623123));// bareket
																					// st
		Route r = Navigation.getRouteFromMapQuest(from, to, segmentsToAvoid);
		Shape shape = r.getShape();
		Double[] shapePoints = shape.getShapePoints();
		if (shapePoints.length % 2 != 0)
			System.out.println("something went wrong :(");
		for (int i = 0; i < shapePoints.length - 1; i += 2) {
			// System.out.println("[" + shapePoints[i] + "," + shapePoints[i +
			// 1] + "]");
		}
		// this code is in comment because i don't want to make the CI wait
		// try {
		// Thread.sleep(1000000000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	@Test
	public void avoidOneSegement() throws CommunicationFailed {
		// TODO this test is temporal for it relay on things that will change!
		Latlng from = new Latlng(31.768762, 34.632052);// abba ahimeir
		Latlng to = new Latlng(31.770981, 34.620567);// HaYam HaTichon Blvd 1
		List<MapSegment> segmentsToAvoid = new ArrayList<MapSegment>();
		segmentsToAvoid.add(Navigation.getMapSegmentOfLatLng(31.76935, 34.626793));// sd
		Double[] shapePoints = Navigation.getRouteFromMapQuest(from, to, segmentsToAvoid).getShape().getShapePoints();
		MapView mapview = JxMapsFunctionality.getMapView();
		JxMapsConvertor.displayRoute(mapview, Navigation.arrayToLatLng(shapePoints));
		try {
			Thread.sleep(10000000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	@Ignore
	public void displayMap() throws CommunicationFailed {
		Location fromLocation = new Street(new LatLng(31.768762, 34.632052));
		Location toLocation = new Street(new LatLng(31.770981, 34.620567));
		LatLng[] shapePoints = Navigation.showRoute(fromLocation, toLocation, 0);
		MapView mapview = JxMapsFunctionality.getMapView();
		JxMapsConvertor.displayRoute(mapview, shapePoints);
		try {
			Thread.sleep(10000000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
