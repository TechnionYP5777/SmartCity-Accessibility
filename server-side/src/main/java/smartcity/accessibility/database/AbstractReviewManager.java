package smartcity.accessibility.database;

import java.util.List;

import smartcity.accessibility.database.callbacks.ICallback;
import smartcity.accessibility.socialnetwork.Review;

/**
 * All functions can either run in the background and return with a callback method
 * Or block the operation until complete (not advised) and return the value
 * Blocking is done when callback method given is null
 * @author KaplanAlexander
 *
 */
public abstract class AbstractReviewManager {
	protected static AbstractReviewManager instance;
	
	public static void initialize(AbstractReviewManager m) {
		instance = m;
	}
	
	public static AbstractReviewManager instance() {
		return instance;
	}
	
	/*
	 * Restores the review with an EMPTY location as to not restore the Location (with all its reveiws) from the db
	 */
	public abstract List<Review> getReviews(String locationId, ICallback<List<Review>> callback);
	
	/*
	 * Restores the review with an EMPTY location as to not restore the Location (with all its reveiws) from the db
	 */
	public abstract List<Review> getReviewWithLocation(String locationId, ICallback<List<Review>> callback);
	
	public abstract Boolean uploadReview(Review r, ICallback<Boolean> callback);
	
	public abstract Boolean deleteReview(Review r, ICallback<Boolean> callback);
	
	public abstract Boolean updateReview(Review review, ICallback<Boolean> callback);
}
