package smartcity.accessibility.socialnetwork;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.categories.UnitTests;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.mapmanagement.LocationBuilder;

/**
 * @author Koral Chapnik
 */

public class BestReviewsTest {
	private static User u1;
	private static User u2;
	private static User u3;
	private static Review r1;
	private static Review r2;
	private static Review r3;
	private static Location l;
	private static ArrayList<Review> r;

	@BeforeClass
	public static void init() {
		u1 = UserBuilder.RegularUser("Koral", "123", "");
		u2 = UserBuilder.RegularUser("Koral2", "123", "");
		u3 = UserBuilder.Admin("Simba", "355", "");
		LatLng c = new LatLng(39.750307, -104.999472);
		l = new LocationBuilder().setCoordinates(c.getLat(), c.getLng()).build();
		r1 = new Review(l, Score.getMinScore(), "very unaccessible place!", u1.getProfile());
		r2 = new Review(l, 2, "middle accessibility level", u2.getProfile());
		r3 = new Review(l, Score.getMaxScore(), "high accessibility level", u3.getProfile());
		l.addReview(r1);
		l.addReview(r2);
		l.addReview(r3);

		r = new ArrayList<>();
		r.add(r1);
		r.add(r2);
		r.add(r3);
	}

	@Test
	@Category(UnitTests.class)
	public void getMostRatedTest() {
		BestReviews br = new BestReviews(1, l);
		List<Review> mostRated = br.getMostRated();
		assertEquals(mostRated.size(), 1);
		assertEquals(mostRated.get(0), r3);
		if (u3.canPinReview())
			r1.setPinned(true);
		else
			fail("shouldn't fail");
		br.setN(2);
		mostRated = br.getMostRated();
		assertEquals(mostRated.size(), 2);
		assert mostRated.contains(r1);
		assert mostRated.contains(r3);
		assertEquals(br.getTotalRatingByAvg(), r1.getRating().getScore() + r3.getRating().getScore() / 2);
	}

	@Test
	@Category(UnitTests.class)
	public void getTotalRatingTest() {
		assertEquals((new BestReviews(3, l)).getTotalRatingByAvg(),
				(r1.getRating().getScore() + r2.getRating().getScore() + r3.getRating().getScore()) / 3);
	}
}