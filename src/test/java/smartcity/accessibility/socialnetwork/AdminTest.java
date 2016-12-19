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
		Mockito.verify(location.getPinnedReviews()).add(review);
		Mockito.verify(location, VerificationModeFactory.times(2)).getPinnedReviews();
		//Removed from regular
		Mockito.verify(location.getReviews()).remove(review);
		Mockito.verify(location, VerificationModeFactory.times(2)).getReviews();
		
		Mockito.verifyNoMoreInteractions(review, location);
	}
	
	@Test
	public void testUnPinReview(){
		admin.unpinReview(review);
		
		Mockito.verify(review, VerificationModeFactory.times(2)).getLocation();
		
		//Added to un-pinned
		Mockito.verify(location.getReviews()).add(review);
		Mockito.verify(location, VerificationModeFactory.times(2)).getReviews();

		
		//Removed from regular
		Mockito.verify(location.getPinnedReviews()).remove(review);
		Mockito.verify(location, VerificationModeFactory.times(2)).getPinnedReviews();
		
		Mockito.verifyNoMoreInteractions(review, location);
	}

}
