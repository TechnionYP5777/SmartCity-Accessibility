package smartcity.accessibility.socialnetwork;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import smartcity.accessibility.categories.UnitTests;
import smartcity.accessibility.database.DatabaseManager;
import smartcity.accessibility.exceptions.UnauthorizedAccessException;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.mapmanagement.LocationBuilder;

public class UserPinTest {
	User admin, user, defaultuser;
	Location location;
	Review review;

	@Before
	public void setUp() throws Exception {
		DatabaseManager.initialize();
		defaultuser = UserBuilder.DefaultUser();
		user = UserBuilder.RegularUser("RegularUser", "", "");
		admin = UserBuilder.Admin("Admin", "", "");
		location = new LocationBuilder().setCoordinates(100,100).build();

		review = new Review(location, 5, "Nothing here", user.getProfile());

		location.addReview(review);
		assert location.getReviews().contains(review);

		assert location.getPinnedReviews().isEmpty();

	}

	@Test
	@Category(UnitTests.class)
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
	@Category(UnitTests.class)
	public void userCantPin() throws UnauthorizedAccessException {
		try {
			location.pinReview(user, review);
		} catch (Exception ¢) {
			nothingHasChangedCheck();
			throw ¢;
		}
	}

	@Test(expected = UnauthorizedAccessException.class)
	@Category(UnitTests.class)
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
