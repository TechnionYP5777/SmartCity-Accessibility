package smartcity.accessibility.mapmanagement;

import smartcity.accessibility.database.ReviewManager;
import smartcity.accessibility.socialnetwork.User;
import smartcity.accessibility.socialnetwork.Review;

/**
 * @author Kolikant 
 */
public abstract class Location {
	public void addReview(User u, int rating, String review) {
		ReviewManager.uploadReview((new Review(this, rating, review, u)), u);
	}
}
