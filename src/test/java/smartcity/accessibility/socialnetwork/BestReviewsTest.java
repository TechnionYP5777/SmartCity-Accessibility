package smartcity.accessibility.socialnetwork;

import java.util.List;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.mapmanagement.Coordinates;
import smartcity.accessibility.mapmanagement.Location;

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
	private static ArrayList<Review> r;
	
	@BeforeClass
	public static void init(){
		u1 = new AuthenticatedUser("Koral","123","");
		u2 = new AuthenticatedUser("Koral2","123","");
		u3 = new Admin("Simba", "355", "");
		LatLng c = new LatLng(39.750307, -104.999472);
		Location l = new Coordinates(c);
		r1 = new Review(l, Score.getMinScore(), "very unaccessible place!", u1);
		r2 = new Review(l, 5, "middle accessibility level", u2);
		r3 = new Review(l, Score.getMaxScore(), "high accessibility level", u3);
		r = new ArrayList<>();
		r.add(r1);
		r.add(r2);
		r.add(r3);
	}
	
	@Test
	public void getMostRatedTest() {
		BestReviews br = new BestReviews(1, r);
		List<Review> mostRated = br.getMostRated();
		assertEquals(mostRated.size(), 1);
		assertEquals(mostRated.get(0), r3);
		br.setN(2);
		mostRated = br.getMostRated();
		assertEquals(mostRated.size(), 2);
	}
	
	@Test
	public void getTotalRatingTest() {
		assertEquals((new BestReviews(3, r)).getTotalRating(),
				((r1.getRating().getScore() + r2.getRating().getScore() + r3.getRating().getScore()) / 3));
	}
}