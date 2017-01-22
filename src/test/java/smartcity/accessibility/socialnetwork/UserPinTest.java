package smartcity.accessibility.socialnetwork;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.database.DatabaseManager;
import smartcity.accessibility.exceptions.UnauthorizedAccessException;
import smartcity.accessibility.mapmanagement.Location;

public class UserPinTest {
	User admin, user, defaultuser;
	Location location;
	Review review;

	@Before
	public void setUp() throws Exception {
		DatabaseManager.initialize();
		defaultuser = UserImpl.DefaultUser();
		user = UserImpl.RegularUser("RegularUser", "", "");
		admin = UserImpl.Admin("Admin", "", "");
		location = new Location(new LatLng(100, 100));

		review = new Review(location, 5, "Nothing here", user);

		location.addReview(review);
		// Check successful add
		assertTrue(location.getReviews().contains(review));

		// Nothing is pinned yet
		assertTrue(location.getPinnedReviews().isEmpty());

	}

	@Test
	public void testPinUnPin() throws UnauthorizedAccessException {
		location.pinReview(admin, review);
		// Review was pinned
		assertTrue(location.getPinnedReviews().contains(review));
		// Review was pinned only once
		assertTrue(location.getPinnedReviews().size() == 1);
		// Review was removed from regular reviews
		assertTrue(location.getNotPinnedReviews().isEmpty());

		// Unpin review
		location.unpinReview(admin, review);
		// Review was un-pinned
		assertTrue(location.getNotPinnedReviews().contains(review));
		// Review was un-pinned only once
		assertTrue(location.getNotPinnedReviews().size() == 1);
		// Review was removed from regular reviews
		assertTrue(location.getPinnedReviews().isEmpty());
	}

	@Test(expected = UnauthorizedAccessException.class)
	public void userCantPin() throws UnauthorizedAccessException {
		try {
			location.pinReview(user, review);
		} catch (Exception e) {
			nothingHasChangedCheck();
			throw e;
		}
	}

	@Test(expected = UnauthorizedAccessException.class)
	public void defaultuserCantPin() throws UnauthorizedAccessException {
		try {
			location.pinReview(defaultuser, review);
		} catch (Exception e) {
			nothingHasChangedCheck();
			throw e;
		}
	}

	private void nothingHasChangedCheck() {
		assertTrue(location.getNotPinnedReviews().contains(review));
		assertTrue(location.getPinnedReviews().isEmpty());
	}

}
