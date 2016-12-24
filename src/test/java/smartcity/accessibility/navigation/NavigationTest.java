package smartcity.accessibility.navigation;

import java.util.ArrayList;
import java.util.List;
import org.junit.Ignore;
import org.junit.Test;

import smartcity.accessibility.navigation.exception.CommunicationFailed;
import smartcity.accessibility.navigation.mapquestcommunication.Latlng;
import smartcity.accessibility.navigation.mapquestcommunication.Route;
import smartcity.accessibility.navigation.mapquestcommunication.Shape;

public class NavigationTest {

	@Test
	public void getSimpleRouteFromServers() throws CommunicationFailed {
		// TODO this test is temporal for it relay on things that will change!
		Latlng from = new Latlng(39.750307, -104.999472);
		Latlng to = new Latlng(40.750307, -105.999472);
		Route r = (new Navigation()).getRouteFromMapQuest(from, to, new ArrayList<MapSegment>());
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
		MapSegment m = (new Navigation()).getMapSegmentOfLatLng(31.766932, 34.631666);
		System.out.println(m.getLinkId());
		System.out.println(m.getStreet());
	}

	@Test
	public void getRouteFromServersWithLinksToAvoid() throws CommunicationFailed {
		// TODO this test is temporal for it relay on things that will change!
		Latlng from = new Latlng(31.766932, 34.631666);// sd tel hai 61 ashdod
		Latlng to = new Latlng(31.770981, 34.620567);// HaYam HaTichon Blvd 1
		List<MapSegment> segmentsToAvoid = new ArrayList<MapSegment>();
		segmentsToAvoid.add((new Navigation()).getMapSegmentOfLatLng(31.769955, 34.623123));// bareket
																							// st
		Route r = ((new Navigation()).getRouteFromMapQuest(from, to, segmentsToAvoid));
		Shape shape = r.getShape();
		Double[] shapePoints = shape.getShapePoints();
		if (shapePoints.length % 2 != 0)
			System.out.println("something went wrong :(");
		for (int i = 0; i < shapePoints.length - 1; i += 2) {
			// System.out.println("[" + shapePoints[i] + "," + shapePoints[i +
			// 1] + "]");
		}
		//this code is in comment because i don't want to make the CI wait
		// try {
		// Thread.sleep(1000000000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	@Test
	@Ignore
	public void JxMapsConvertor() {
		JxMapsConvertor.displayRoute((new Double[] { 31.767232, 34.631183, 31.767288, 34.631168, 31.767353, 34.631095,
				31.767377, 34.630981, 31.767366, 34.630924, 31.767372, 34.630813, 31.767435, 34.630638, 31.768041,
				34.629447, 31.768117, 34.629295, 31.768264, 34.629013, 31.768377, 34.628787, 31.768472, 34.628589,
				31.768856, 34.627819, 31.768968, 34.627613, 31.769369, 34.626758, 31.769435, 34.626663, 31.769496,
				34.626621, 31.769496, 34.626621, 31.769567, 34.626533, 31.769601, 34.626392, 31.769699, 34.626136,
				31.77032, 34.624923, 31.77091, 34.623741, 31.770952, 34.623657, 31.771202, 34.623195, 31.77127,
				34.623096, 31.771398, 34.622997, 31.771501, 34.622978, 31.771501, 34.622978, 31.771593, 34.623008,
				31.771688, 34.62302, 31.771831, 34.623004, 31.771968, 34.622951, 31.772115, 34.622833, 31.772226,
				34.622669, 31.77227, 34.622562, 31.772296, 34.622447, 31.772302, 34.622211, 31.772279, 34.622097,
				31.772193, 34.621894, 31.772129, 34.621807, 31.771972, 34.621677, 31.771862, 34.621627, 31.771688,
				34.621604, 31.771625, 34.621612 }));
	}

	@Test
	public void displayMap() throws CommunicationFailed {
		// TODO this test is temporal for it relay on things that will change!
		Latlng from = new Latlng(31.768762, 34.632052);// abba ahimeir
		Latlng to = new Latlng(31.770981, 34.620567);// HaYam HaTichon Blvd 1
		List<MapSegment> segmentsToAvoid = new ArrayList<MapSegment>();
		segmentsToAvoid.add((new Navigation()).getMapSegmentOfLatLng(31.76935, 34.626793));// sd
																							// tel
																							// hai
																							// ashdod
		Route r = ((new Navigation()).getRouteFromMapQuest(from, to, segmentsToAvoid));
		Shape shape = r.getShape();
		Double[] shapePoints = shape.getShapePoints();
		JxMapsConvertor.displayRoute(shapePoints);
		//this code is in comment because i don't want to make the CI wait
//		 try {
//		 Thread.sleep(1000000000);
//		 } catch (InterruptedException e) {
//		 // TODO Auto-generated catch block
//		 e.printStackTrace();
//		 }

	}
}
