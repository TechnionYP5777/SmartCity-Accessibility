package smartcity.accessibility.socialnetwork;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.exceptions.UnauthorizedAccessException;
import smartcity.accessibility.mapmanagement.Coordinates;

/**
 * @author Koral Chapnik
 */
public class ReviewTest {
	private static User u1;
	private static User u2;
	private static Review r1;
	
	@BeforeClass
	public static void init(){
		u1 = UserImpl.RegularUser("Koral","123","");
		u2 = UserImpl.Admin("KoralAdmin","123","");
		r1 = new Review(new Coordinates(new LatLng(39.750307, -104.999472)), Score.getMinScore(),
				"very unaccessible place!", u1);
	}
	
	@Test
	public void getLocationTest() {
		assert(r1.getLocation().getCoordinates().equals(new LatLng(39.750307, -104.999472)));
	}
	
	@Test
	public void getRetingTest() {
		Score s = null;
		s = new Score(Score.getMinScore());
		assertEquals(r1.getRating(), s);
	}
	
	@Test
	public void getCommentTest() {
		assertEquals(r1.getContent(), "very unaccessible place!");
	}
	
	@Test
	public void getUserTest() {
		assertEquals(r1.getUser(), "Koral");
	}
	
	@Test
	public void isPinnedTest() {
		assertFalse(r1.isPinned());
		try {
			r1.pin(u2);
		} catch (UnauthorizedAccessException e) {
			fail("shouldn't throw an exception");
		}
		assertTrue(r1.isPinned());
	}

}

