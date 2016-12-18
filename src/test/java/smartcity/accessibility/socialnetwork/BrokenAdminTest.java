package smartcity.accessibility.socialnetwork;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.mapmanagement.Location;

public class BrokenAdminTest {
	Admin admin;
	Location location;
	Review review;
	User user = new AuthenticatedUser();

	@Before
	public void setUp() throws Exception {
		admin = new Admin("admin", "", "");
		location = new Location() {
			@Override
			public LatLng getCoordinates() {
				// TODO Auto-generated method stub
				return null;
			}
			@Override
			public String getAddress() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		review = new Review(location, 5, "Nothing here", user);
		location.addReview(review);
		//Check successful add
		assertTrue(location.getReviews().contains(review));
		
	}

	@Test
	public void testPin() {
		//Nothing is pinned yet
		assertTrue(location.getPinnedReviews().isEmpty());
		admin.pinReview(review);
		//Review was pinned
		assertTrue(location.getPinnedReviews().contains(review));
		//Review was pinned only once
		assertTrue(location.getPinnedReviews().size() == 1);
		//Review was removed from regular reviews
		assertTrue(location.getReviews().isEmpty());		
	}

}
