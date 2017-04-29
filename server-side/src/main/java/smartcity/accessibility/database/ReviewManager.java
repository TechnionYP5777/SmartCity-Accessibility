package smartcity.accessibility.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import smartcity.accessibility.database.callbacks.ICallback;
import smartcity.accessibility.exceptions.UserNotFoundException;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.socialnetwork.Review;
import smartcity.accessibility.socialnetwork.UserProfile;

/**
 * @author KaplanAlexander
 *
 */
public class ReviewManager extends AbstractReviewManager {

	private Database db;
	private static final String DATABASE_CLASS = "Review";

	private static Logger logger = LoggerFactory.getLogger(ReviewManager.class);

	private static final String ID_FIELD_NAME = "objectId";
	public static final String LOCATION_FIELD_NAME = "locationId";
	private static final String CONTENT_FIELD_NAME = "content";
	private static final String IS_PINNED_FIELD_NAME = "isPinned";
	private static final String RATING_FIELD_NAME = "rating";
	private static final String USERNAME_FIELD_NAME = "username";

	@Inject
	public ReviewManager(Database db) {
		this.db = db;
	}

	private Map<String, Object> toMap(Review r) {
		Map<String, Object> map = new HashMap<>();
		Location l = r.getLocation();
		String id = AbstractLocationManager.instance().getId(l.getCoordinates(), l.getLocationType(),
				l.getLocationSubType(), null);
		if (id == null) {
			id = AbstractLocationManager.instance().uploadLocation(l, null);
		}
		map.put(LOCATION_FIELD_NAME, id);
		map.put(RATING_FIELD_NAME, r.getRating().getScore());
		map.put(CONTENT_FIELD_NAME, r.getContent());
		map.put(IS_PINNED_FIELD_NAME, r.isPinned());
		map.put(USERNAME_FIELD_NAME, r.getUser().getUsername());
		// TODO : Add ReviewComments
		return map;
	}

	private static Review fromMap(Map<String, Object> m) {
		int rating = (int) m.get(RATING_FIELD_NAME);
		String content = m.get(CONTENT_FIELD_NAME).toString();
		boolean isPinned = (boolean) m.get(IS_PINNED_FIELD_NAME);
		UserProfile up = null;
		try {
			up = AbstractUserProfileManager.instance().get(m.get(USERNAME_FIELD_NAME).toString(), null);
		} catch (UserNotFoundException e) {
			logger.error("User not found for review {}", m.get(ID_FIELD_NAME));
		}
		Review r = new Review(null, rating, content, up);
		r.setPinned(isPinned);
		// TODO : Add ReviewComments
		return r;
	}

	@Override
	public List<Review> getReviews(String locationId, ICallback<List<Review>> callback) {
		logger.debug("getReviews for location {}", locationId);
		Flowable<List<Review>> res = Flowable.fromCallable(() -> {
			Map<String, Object> map = new HashMap<>();
			map.put(LOCATION_FIELD_NAME, locationId);
			List<Map<String, Object>> reviews = db.get(DATABASE_CLASS, map);
			return reviews.stream().map(m -> fromMap(m)).collect(Collectors.toList());
		})
		.subscribeOn(Schedulers.io())
		.observeOn(Schedulers.single());
		if(callback == null)
			return res.blockingFirst();
		res.subscribe(callback::onFinish, Throwable::printStackTrace);	
		return new ArrayList<>();
	}

	@Override
	public List<Review> getReviewWithLocation(String locationId, ICallback<List<Review>> callback) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean uploadReview(Review r, ICallback<Boolean> callback) {
		logger.debug("uploadReview for user {}", r.getUser().getUsername());
		Flowable<Boolean> res = Flowable.fromCallable(() -> {
			return (db.put(DATABASE_CLASS, toMap(r)) != null);
		})
		.subscribeOn(Schedulers.io())
		.observeOn(Schedulers.single());
		if(callback == null)
			return res.blockingFirst();
		res.subscribe(callback::onFinish, Throwable::printStackTrace);	
		return null;
	}

	@Override
	public Boolean deleteReview(Review r, ICallback<Boolean> callback) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean updateReview(Review review, ICallback<Boolean> callback) {
		// TODO Auto-generated method stub
		return null;
	}

}
