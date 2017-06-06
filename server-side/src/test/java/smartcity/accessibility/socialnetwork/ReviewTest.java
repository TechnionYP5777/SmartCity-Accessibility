package smartcity.accessibility.socialnetwork;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.google.maps.model.LatLng;

import smartcity.accessibility.categories.UnitTests;
import smartcity.accessibility.mapmanagement.LocationBuilder;;

/**
 * @author Koral Chapnik
 */
public class ReviewTest {
	private static User u1;
	private static User u2;
	private static Review r1;
	
	@BeforeClass
	public static void init(){
		u1 = UserBuilder.RegularUser("Koral","123","");
		u2 = UserBuilder.Admin("KoralAdmin","123","");
		r1 = new Review(new LocationBuilder().setCoordinates(39.750307, -104.999472).build(),
				Score.getMinScore(),
				"very unaccessible place!",
				u1.getProfile());
	}
	
	@Test
	@Category(UnitTests.class)
	public void getLocationTest() {
		assertEquals(new LatLng(39.75030700, -104.99947200).toString(),r1.getLocation().getCoordinates().toString());
	}
	
	@Test
	@Category(UnitTests.class)
	public void getRetingTest() {
		assertEquals(r1.getRating(), new Score(Score.getMinScore()));
	}
	
	@Test
	@Category(UnitTests.class)
	public void getCommentTest() {
		assertEquals(r1.getContent(), "very unaccessible place!");
	}
	
	@Test
	@Category(UnitTests.class)
	public void getUserTest() {
		assertEquals(r1.getUser().getUsername(), "Koral");
	}
	
	@Test
	@Category(UnitTests.class)
	public void isPinnedTest() {
		assert !r1.isPinned();
		if (u2.canPinReview())
			r1.setPinned(true);
		else
			fail("shouldn't fail");
		assert r1.isPinned();
	}

}

