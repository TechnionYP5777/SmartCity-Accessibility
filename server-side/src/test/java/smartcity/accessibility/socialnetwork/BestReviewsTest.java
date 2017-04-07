package smartcity.accessibility.socialnetwork;

import java.util.List;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.parse4j.ParseException;

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.categories.UnitTests;
import smartcity.accessibility.exceptions.UnauthorizedAccessException;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.socialnetwork.User;
import smartcity.accessibility.socialnetwork.UserImpl;

/**
 * @author Koral Chapnik
 */
@Ignore
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
	public static void init(){
		u1 = UserImpl.RegularUser("Koral","123","");
		u2 = UserImpl.RegularUser("Koral2","123","");
		u3 = UserImpl.Admin("Simba", "355", "");
		LatLng c = new LatLng(39.750307, -104.999472);
		l = new Location(c);
		r1 = new Review(l, Score.getMinScore(), "very unaccessible place!", u1);
		r2 = new Review(l, 2, "middle accessibility level", u2);
		r3 = new Review(l, Score.getMaxScore(), "high accessibility level", u3);
		try {
			l.addReview(r1);
			l.addReview(r2);
			l.addReview(r3);
		} catch (ParseException e) {
			fail("shouldn't fail");
		}
		
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
		try {
			r1.pin(u3);
		} catch (UnauthorizedAccessException e) {
			fail("shouldn't fail");
		}
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