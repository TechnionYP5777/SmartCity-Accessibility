package smartcity.accessibility.socialnetwork;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.exceptions.ScoreNotInRangeException;
import smartcity.accessibility.mapmanagement.Coordinates;
import smartcity.accessibility.mapmanagement.Location;

/**
 * @author Koral Chapnik
 */
public class ReviewTest {
	private static User u1;
	private static Review r1;
	
	@BeforeClass
	public static void init(){
		u1 = new AuthenticatedUser("Koral","123","");
		LatLng c = new LatLng(39.750307, -104.999472);
		Location l = new Coordinates(c);
		r1 = new Review(l, Score.getMinScore(), "very unaccessible place!", u1);
	}
	
	@Test
	public void getLocationTest() {
		assert(r1.getLocation().getCoordinates().equals(new LatLng(39.750307, -104.999472)));
	}
	
	@Test
	public void getRetingTest() {
		Score s = null;
		try {
			s = new Score(Score.getMinScore());
		} catch (ScoreNotInRangeException e) {
		}
		assertEquals(r1.getRating(), s);
	}
	
	@Test
	public void getCommentTest() {
		assertEquals(r1.getComment(), "very unaccessible place!");
	}
	
	@Test
	public void getUserTest() {
		assertEquals(r1.getUser(), "Koral");
	}
	
	//TODO : calculate opinions after getting response from alex
}

