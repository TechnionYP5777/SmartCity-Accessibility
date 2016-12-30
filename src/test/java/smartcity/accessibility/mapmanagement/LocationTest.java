package smartcity.accessibility.mapmanagement;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.parse4j.ParseException;

import com.teamdev.jxmaps.LatLng;
import com.teamdev.jxmaps.MapViewOptions;
import com.teamdev.jxmaps.swing.MapView;

import smartcity.accessibility.database.DatabaseManager;
import smartcity.accessibility.mapmanagement.Coordinates;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.mapmanagement.JxMapsFunctionality.extendedMapView;
import smartcity.accessibility.socialnetwork.Review;
import smartcity.accessibility.socialnetwork.Score;
import smartcity.accessibility.socialnetwork.User;
import smartcity.accessibility.socialnetwork.UserImpl;

/**
 * @author Koral Chapnik
 */
public class LocationTest {

	private static Review r1;
	private static Review r2;
	private static Review r3;
	private static Location l;
	
	@BeforeClass
	public static void init() throws ParseException{
		DatabaseManager.initialize();
		User u1 = UserImpl.RegularUser("Koral","123","");
		User u2 = UserImpl.RegularUser("Koral2","123","");
		User u3 = UserImpl.RegularUser("Koral3","123","");
		LatLng c = new LatLng(31.90588, 34.997571); //Modi'in Yehalom St, 20
		l = new Coordinates(c);
		r1 = new Review(l, Score.getMinScore(), "very unaccessible place!", u1);
		r2 = new Review(l, 5, "middle accessibility level", u2);
		r3 = new Review(l, Score.getMaxScore(), "high accessibility level", u3);
			l.addReview(r1);
			l.addReview(r2);
			l.addReview(r3);
		
	}
	
	@Test
	public void getCoordinatesTest() {
		assert(l.getCoordinates().equals(new LatLng(31.90588, 34.997571)));
	}
	
	@Test
	public void getReviewsTest() {
		assertEquals(l.getReviews().size(), 3);
	}
	
	@Test
	public void getAddressTest() {
		new MapViewOptions().importPlaces();
		MapView mapView = JxMapsFunctionality.getMapView();
		JxMapsFunctionality.waitForMapReady((extendedMapView) mapView);
		System.out.println(l.getAddress(mapView));
	}
	
}
