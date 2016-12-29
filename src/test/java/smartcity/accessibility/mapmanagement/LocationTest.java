package smartcity.accessibility.mapmanagement;

import java.util.List;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import org.parse4j.ParseException;

import com.teamdev.jxmaps.LatLng;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.MapViewOptions;
import com.teamdev.jxmaps.swing.MapView;

import smartcity.accessibility.database.DatabaseManager;
import smartcity.accessibility.mapmanagement.Coordinates;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.mapmanagement.JxMapsFunctionality.extendedMapView;
import smartcity.accessibility.search.SearchQuery;
import smartcity.accessibility.search.SearchTest;
import smartcity.accessibility.socialnetwork.Admin;
import smartcity.accessibility.socialnetwork.AuthenticatedUser;
import smartcity.accessibility.socialnetwork.Review;
import smartcity.accessibility.socialnetwork.Score;
import smartcity.accessibility.socialnetwork.User;

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
		User u1 = new AuthenticatedUser("Koral","123","");
		User u2 = new AuthenticatedUser("Koral2","123","");
		User u3 = new Admin("Simba", "355", "");
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
		MapViewOptions options = new MapViewOptions();
        options.importPlaces();
        MapView mapView = JxMapsFunctionality.getMapView();
        JxMapsFunctionality.waitForMapReady((extendedMapView) mapView);
		String result = l.getAddress(mapView);
		System.out.println(result);
		return;
	}
	
}
