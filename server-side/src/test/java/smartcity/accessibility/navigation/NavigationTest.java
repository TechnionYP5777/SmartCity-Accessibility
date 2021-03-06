package smartcity.accessibility.navigation;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.Timeout;
import org.mockito.Mockito;

import smartcity.accessibility.categories.NetworkTests;
import smartcity.accessibility.database.AbstractLocationManager;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.mapmanagement.LocationBuilder;
import smartcity.accessibility.navigation.exception.CommunicationFailed;
import smartcity.accessibility.navigation.mapquestcommunication.Latlng;

/**
 * This class contains test for Navigation. the tests are for the Navigation as
 * a whole unit and not for specific class but the main class is
 * Navigation.java. (Add sleep in the end of tests in order to view the map
 * before the test ends!)
 * 
 * @author yael
 *
 */
public class NavigationTest {

	@Rule
	public Timeout globalTimeout = Timeout.seconds(10000);

	@Before
	public void setup() throws Exception {
		AbstractLocationManager mock_LocationManagerProfile = Mockito.mock(AbstractLocationManager.class);
		AbstractLocationManager.initialize(mock_LocationManagerProfile);
	}
	
	@Test
	@Category(NetworkTests.class)
	public void getMapSegmentFromLatLng() {
		try {
			MapSegment m = Navigation.getMapSegmentOfLatLng(31.766932, 34.631666);
			assertNotNull(m.getLinkId());
			assertNotNull(m.getStreet());
		} catch (CommunicationFailed e) {
		}
	}

	
	@Test
	@Category(NetworkTests.class)
	public void avoidOneSegement() throws CommunicationFailed {
		Latlng from = new Latlng(31.768762, 34.632052), to = new Latlng(31.770981, 34.620567);
		List<MapSegment> segmentsToAvoid = new ArrayList<MapSegment>();
		segmentsToAvoid.add(Navigation.getMapSegmentOfLatLng(31.76935, 34.626793));// sd
		Navigation.getRouteFromMapQuest(from, to, segmentsToAvoid).getShape().getShapePoints();
	}

	@Test
	@Category(NetworkTests.class)
	public void avoidTwoSegement() throws CommunicationFailed {
		Latlng from = new Latlng(31.768762, 34.632052), to = new Latlng(31.770981, 34.620567);
		List<MapSegment> segmentsToAvoid = new ArrayList<MapSegment>();
		segmentsToAvoid.add(Navigation.getMapSegmentOfLatLng(31.76935, 34.626793));// sd
																					// tel
																					// hai
		segmentsToAvoid.add(Navigation.getMapSegmentOfLatLng(31.769937, 34.627658));
		Navigation.getRouteFromMapQuest(from, to, segmentsToAvoid).getShape().getShapePoints();
	}

	@Test
	@Category(NetworkTests.class)
	public void getRoute() throws CommunicationFailed {
		Location fromLocation = new LocationBuilder().setCoordinates(31.768762, 34.632052).build(),
				toLocation = new LocationBuilder().setCoordinates(31.770981, 34.620567).build();//new Location(new LatLng(31.770981, 34.620567));
		Navigation.getRoute(fromLocation, toLocation, 0);
	}

}
