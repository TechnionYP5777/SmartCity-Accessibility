package smartcity.accessibility.database;

import java.util.List;
import java.util.Map;

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
	
	public abstract List<Review> getReviews(Map<String, Object> fields);
	//public abstract void uploadReview(Review r);
}
