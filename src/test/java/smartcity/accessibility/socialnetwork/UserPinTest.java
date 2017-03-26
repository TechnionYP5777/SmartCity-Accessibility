package smartcity.accessibility.socialnetwork;

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
		assert location.getReviews().contains(review);

		assert location.getPinnedReviews().isEmpty();

	}

	@Test
	public void testPinUnPin() throws UnauthorizedAccessException {
		location.pinReview(admin, review);
		assert location.getPinnedReviews().contains(review);
		assert location.getPinnedReviews().size() == 1;
		assert location.getNotPinnedReviews().isEmpty();

		// Unpin review
		location.unpinReview(admin, review);
		assert location.getNotPinnedReviews().contains(review);
		assert location.getNotPinnedReviews().size() == 1;
		assert location.getPinnedReviews().isEmpty();
	}

	@Test(expected = UnauthorizedAccessException.class)
	public void userCantPin() throws UnauthorizedAccessException {
		try {
			location.pinReview(user, review);
		} catch (Exception ¢) {
			nothingHasChangedCheck();
			throw ¢;
		}
	}

	@Test(expected = UnauthorizedAccessException.class)
	public void defaultuserCantPin() throws UnauthorizedAccessException {
		try {
			location.pinReview(defaultuser, review);
		} catch (Exception ¢) {
			nothingHasChangedCheck();
			throw ¢;
		}
	}

	private void nothingHasChangedCheck() {
		assert location.getNotPinnedReviews().contains(review);
		assert location.getPinnedReviews().isEmpty();
	}

}
