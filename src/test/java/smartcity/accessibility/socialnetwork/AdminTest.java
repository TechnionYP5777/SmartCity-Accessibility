package smartcity.accessibility.socialnetwork;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;

import smartcity.accessibility.mapmanagement.Location;

public class AdminTest {

	Admin admin;
	Location location;
	Review review;
	
	@Before
	public void setUp() throws Exception {
		//Create classes
		admin = new Admin("admin", "", "");
		location = Mockito.mock(Location.class);
		review = Mockito.mock(Review.class);
		
		//Define what happens
		Mockito.when(review.getLocation()).thenReturn(location);
		Mockito.when(location.getReviews()).thenReturn(Mockito.mock((new ArrayList<Review>()).getClass()));
		Mockito.when(location.getPinnedReviews()).thenReturn(Mockito.mock((new ArrayList<Review>()).getClass()));
		
		//Add the review
		location.addReview(review);
		
		//Verify successful add
		Mockito.verify(location).addReview(review);
		Mockito.verifyNoMoreInteractions(location, review);
	}

	@Test
	public void testPinReview() {
		admin.pinReview(review);
		
		Mockito.verify(review, VerificationModeFactory.times(2)).getLocation();
		//Added to pinned
		Mockito.verify(location).getPinnedReviews();
		Mockito.verify(location.getPinnedReviews()).add(review);
		//Removed from regular
		Mockito.verify(location).getReviews();
		Mockito.verify(location.getReviews()).remove(review);
	}

}
