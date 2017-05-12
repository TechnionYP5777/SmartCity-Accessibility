package smartcity.accessibility.socialnetwork;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import smartcity.accessibility.categories.UnitTests;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.mapmanagement.LocationBuilder;

public class UserPinTest {
	User admin, user, defaultuser;
	Location location;
	Review review;

	@Before
	public void setUp() throws Exception {
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
	public void testPinUnPin() {
		if(admin.canPinReview())
			review.setPinned(true);
		assert location.getPinnedReviews().contains(review);
		assert location.getPinnedReviews().size() == 1;
		assert location.getNotPinnedReviews().isEmpty();

		if(admin.canPinReview())
			review.setPinned(false);
		assert location.getNotPinnedReviews().contains(review);
		assert location.getNotPinnedReviews().size() == 1;
		assert location.getPinnedReviews().isEmpty();
	}

	@Test
	@Category(UnitTests.class)
	public void userCantPin() {
		assertEquals(false, user.canPinReview());
	}

	@Test
	@Category(UnitTests.class)
	public void defaultuserCantPin() {
		assertEquals(false, defaultuser.canPinReview());
	}


}
