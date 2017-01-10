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

import smartcity.accessibility.mapmanagement.JxMapsFunctionality;
import smartcity.accessibility.mapmanagement.JxMapsFunctionality.ExtendedMapView;

public class NavigationTest {

	@Test
	public void getMapSegmentFromLatLng() {
		// TODO this test is temporal for it relay on things that will change!
		MapSegment m = Navigation.getMapSegmentOfLatLng(31.766932, 34.631666);
		System.out.println(m.getLinkId());
		System.out.println(m.getStreet());
	}

	@Test
	public void avoidOneSegement() throws CommunicationFailed {
		// TODO this test is temporal for it relay on things that will change!
		Latlng from = new Latlng(31.768762, 34.632052);// abba ahimeir
		Latlng to = new Latlng(31.770981, 34.620567);// HaYam HaTichon Blvd 1
		List<MapSegment> segmentsToAvoid = new ArrayList<MapSegment>();
		segmentsToAvoid.add(Navigation.getMapSegmentOfLatLng(31.76935, 34.626793));// sd
		Double[] shapePoints = Navigation.getRouteFromMapQuest(from, to, segmentsToAvoid).getShape().getShapePoints();
		ExtendedMapView mapview = JxMapsFunctionality.getMapView();
		JxMapsFunctionality.waitForMapReady(mapview);
		JxMapsConvertor.displayRoute(mapview, Navigation.arrayToLatLng(shapePoints));
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void avoidTwoSegement() throws CommunicationFailed {
		// TODO this test is temporal for it relay on things that will change!
		Latlng from = new Latlng(31.768762, 34.632052);// abba ahimeir
		Latlng to = new Latlng(31.770981, 34.620567);// HaYam HaTichon Blvd 1
		List<MapSegment> segmentsToAvoid = new ArrayList<MapSegment>();
		segmentsToAvoid.add(Navigation.getMapSegmentOfLatLng(31.76935, 34.626793));// sd
																					// tel
																					// hai
		segmentsToAvoid.add(Navigation.getMapSegmentOfLatLng(31.769937, 34.627658));
		Double[] shapePoints = Navigation.getRouteFromMapQuest(from, to, segmentsToAvoid).getShape().getShapePoints();
		ExtendedMapView mapview = JxMapsFunctionality.getMapView();
		JxMapsFunctionality.waitForMapReady(mapview);
		JxMapsConvertor.displayRoute(mapview, Navigation.arrayToLatLng(shapePoints));
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void displayMap() throws CommunicationFailed {
		Location fromLocation = new Street(new LatLng(31.768762, 34.632052));
		Location toLocation = new Street(new LatLng(31.770981, 34.620567));
		LatLng[] shapePoints = Navigation.showRoute(fromLocation, toLocation, 0);
		ExtendedMapView mapview = JxMapsFunctionality.getMapView();
		JxMapsFunctionality.waitForMapReady(mapview);
		JxMapsConvertor.displayRoute(mapview, shapePoints);
		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
