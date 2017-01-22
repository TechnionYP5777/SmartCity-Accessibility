package smartcity.accessibility.mapmanagement;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.parse4j.ParseException;

import com.teamdev.jxmaps.LatLng;
import smartcity.accessibility.database.DatabaseManager;
import smartcity.accessibility.exceptions.UnauthorizedAccessException;
import smartcity.accessibility.mapmanagement.Location;
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
	private static Review r4;
	private static Location l;

	@BeforeClass
	public static void init() throws ParseException {
		DatabaseManager.initialize();
		User u1 = UserImpl.RegularUser("Koral", "123", "");
		User u2 = UserImpl.RegularUser("Koral2", "123", "");
		User u3 = UserImpl.RegularUser("Koral3", "123", "");
		User u4 = UserImpl.Admin("Koral4", "123", "");
		LatLng c = new LatLng(31.90588, 34.997571); // Modi'in Yehalom St, 20
		l = new Location(c);
		r1 = new Review(l, Score.getMinScore(), "very unaccessible place!", u1);
		r2 = new Review(l, 5, "middle accessibility level", u2);
		r3 = new Review(l, Score.getMaxScore(), "high accessibility level", u3);
		r4 = new Review(l,2, "high accessibility level", u3);
		try {
			r4.pin(u4);
		} catch (UnauthorizedAccessException e) {
			fail("shouldnt fail");
		}
		l.addReview(r1);
		l.addReview(r2);
		l.addReview(r3);
		l.addReview(r4);
	}

	@Test
	public void getCoordinatesTest() {
		assert (l.getCoordinates().equals(new LatLng(31.90588, 34.997571)));
	}

	@Test
	public void getReviewsTest() {
		assertEquals(l.getReviews().size(), 4);
	}
	
	@Test
	public void getPinnedReviewsTest() {
		assertEquals(l.getPinnedReviews().size(),1);
	}
	
	@Test
	public void getUnPinnedReviewsTest() {
		assertEquals(l.getNotPinnedReviews().size(),3);
	}
	

}
