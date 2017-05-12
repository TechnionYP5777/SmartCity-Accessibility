package smartcity.accessibility.socialnetwork;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import smartcity.accessibility.categories.UnitTests;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.mapmanagement.LocationBuilder;


public class DeleteReviewsTest {
	User admin, user, defaultuser;
	Location location;
	Review review, review2;

	@Before
	public void setUp() throws Exception {
		defaultuser = UserBuilder.DefaultUser();
		user = UserBuilder.RegularUser("RegularUser", "", "");
		admin = UserBuilder.Admin("Admin", "", "");
		location = new LocationBuilder().setCoordinates(100, 100).build();

		review = new Review(location, 5, "Nothing here", user.getProfile());
		review2 = new Review(location, 3, "Nothing here either", admin.getProfile());

		location.addReview(review);
		location.addReview(review2);
		// Check successful add
		nothingHasChangedCheck();
	}

	private void nothingHasChangedCheck() {
		assertEquals(true, location.getReviews().contains(review));
		assertEquals(true, location.getReviews().contains(review2));
		assertEquals(true, location.getReviews().size() == 2);
	}

	@Test
	@Category(UnitTests.class)
	public void regularUserCantDelete() {
		assertEquals(false, user.canDeleteReview(review2));
	}

	@Test
	@Category(UnitTests.class)
	public void defaultUserCantDelete()  {
		assertEquals(false, defaultuser.canDeleteReview(review));
	}

	@Test
	@Category(UnitTests.class)
	public void adminCanDelete()  {
		assertEquals(true, admin.canDeleteReview(review));
		location.deleteReview(review);
		assertEquals(false, location.getReviews().contains(review));

		assertEquals(true, location.getReviews().contains(review2));
		assertEquals(1, location.getReviews().size());
	}

	@Test
	@Category(UnitTests.class)
	public void userCanOwnedDelete() {
		assertEquals(true, user.canDeleteReview(review));
		location.deleteReview(review);
		assertEquals(false, location.getReviews().contains(review));

		assertEquals(true, location.getReviews().contains(review2));
		assertEquals(1, location.getReviews().size());
	}

}
