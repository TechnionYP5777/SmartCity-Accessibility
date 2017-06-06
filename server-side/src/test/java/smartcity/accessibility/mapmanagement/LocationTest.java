package smartcity.accessibility.mapmanagement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.parse4j.ParseException;

import com.google.maps.model.LatLng;
import com.google.maps.model.PlaceType;

import smartcity.accessibility.categories.UnitTests;
import smartcity.accessibility.socialnetwork.Review;
import smartcity.accessibility.socialnetwork.Score;
import smartcity.accessibility.socialnetwork.User;
import smartcity.accessibility.socialnetwork.UserBuilder;

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
		//DatabaseManager.initialize();
		User u1 = UserBuilder.RegularUser("Koral", "123", ""), u2 = UserBuilder.RegularUser("Koral2", "123", ""),
				u3 = UserBuilder.RegularUser("Koral3", "123", ""), u4 = UserBuilder.Admin("Koral4", "123", "");
		LatLng c = new LatLng(31.90588, 34.997571); // Modi'in Yehalom St, 20
		l = new LocationBuilder().setCoordinates(c).build();
		r1 = new Review(l, Score.getMinScore(), "very unaccessible place!", u1.getProfile());
		r2 = new Review(l, 5, "middle accessibility level", u2.getProfile());
		r3 = new Review(l, Score.getMaxScore(), "high accessibility level", u3.getProfile());
		r4 = new Review(l,2, "high accessibility level", u3.getProfile());
		if (u4.canPinReview())
			r4.setPinned(true);
		else
			fail("shouldn't fail");
		l.addReview(r1);
		l.addReview(r2);
		l.addReview(r3);
		l.addReview(r4);
	}

	@Test
	@Category(UnitTests.class)
	public void getCoordinatesTest() {
		assertEquals(new LatLng(31.90588000, 34.99757100).toString(),l.getCoordinates().toString());
	}

	@Test
	@Category(UnitTests.class)
	public void getReviewsTest() {
		assertEquals(l.getReviews().size(), 4);
	}
	
	@Test
	@Category(UnitTests.class)
	public void getPinnedReviewsTest() {
		assertEquals(l.getPinnedReviews().size(),1);
	}
	
	@Test
	@Category(UnitTests.class)
	public void getUnPinnedReviewsTest() {
		assertEquals(l.getNotPinnedReviews().size(),3);
	}
	
	@Test
	@Category(UnitTests.class)
	public void getRating() {
		assertEquals(l.getRating(1).getScore(),2);
		assertEquals(l.getRating(2).getScore(),3);
		assertEquals(l.getRating(5).getScore(), 3);
		
	}
	
	@Test
	@Category(UnitTests.class)
	public void subTypeTest() {
		PlaceType r = PlaceType.valueOf(Location.LocationSubTypes.RESTAURANT.getSearchType());
		assertEquals(PlaceType.RESTAURANT, r);
	}

	

}
