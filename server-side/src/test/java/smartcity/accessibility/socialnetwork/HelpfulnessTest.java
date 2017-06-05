package smartcity.accessibility.socialnetwork;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import smartcity.accessibility.mapmanagement.LatLng;

import smartcity.accessibility.categories.UnitTests;
import smartcity.accessibility.exceptions.UnauthorizedAccessException;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.mapmanagement.LocationBuilder;

/**
 * 
 * @author Koral Chapnik
 *
 */
public class HelpfulnessTest {
	private static User u1;
	private static User u2;
	private static User u3;
	private static Review r1;
	private static Review r2;
	private static Review r3;
	private static Location l;
	
	@BeforeClass
	public static void init(){
		u1 = UserBuilder.RegularUser("Koral","123","");
		u2 = UserBuilder.RegularUser("Koral2","123","");
		u3 = UserBuilder.Admin("Simba", "355", "");
		LatLng c = new LatLng(39.750307, -104.999472);
		l = new LocationBuilder().setCoordinates(c).build();//new Location(c);
		r1 = new Review(l, Score.getMinScore(), "very unaccessible place!", u1.getProfile());
		r2 = new Review(l, 2, "middle accessibility level", u1.getProfile());
		r3 = new Review(l, Score.getMaxScore(), "high accessibility level", u1.getProfile());
		l.addReview(r1);
		l.addReview(r2);
		l.addReview(r3);

	}
	
	@Test
	@Category(UnitTests.class)
	public void test() {
		try {
			r1.upvote(u1);
			r2.upvote(u2);
			r2.upvote(u3);
			r2.downvote(u1);
			r3.upvote(u1);
			r3.upvote(u2);
		} catch (UnauthorizedAccessException e) {
			fail("shouldn't fail");
		}
		
		assertEquals(2, u1.getProfile().getHelpfulness().helpfulness().intValue());
	}
}
