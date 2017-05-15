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
import smartcity.accessibility.socialnetwork.ReviewComment;
import smartcity.accessibility.socialnetwork.UserProfile;

/**
 * @author KaplanAlexander
 *
 */
public class ReviewManager extends AbstractReviewManager {

	private Database db;
	public static final String DATABASE_CLASS = "Review";

	private static Logger logger = LoggerFactory.getLogger(ReviewManager.class);

	public static final String ID_FIELD_NAME = "objectId";
	public static final String LOCATION_FIELD_NAME = "locationId";
	public static final String CONTENT_FIELD_NAME = "content";
	public static final String IS_PINNED_FIELD_NAME = "isPinned";
	public static final String RATING_FIELD_NAME = "rating";
	public static final String USERNAME_FIELD_NAME = "username";
	public static final String COMMENTS_FIELD_NAME = "comments";

	@Inject
	public ReviewManager(Database db) {
		this.db = db;
	}

	public static Map<String, Object> toMap(Review r) {
		logger.debug("toMap {} by {}",r.getContent(),r.getUser().getUsername());
		Map<String, Object> map = new HashMap<>();
		Location l = r.getLocation();
		String id = null;
		if(l!=null){
			id = AbstractLocationManager.instance().getId(l.getCoordinates(), l.getLocationType(),
					l.getLocationSubType(), null);
			if (id == null) {
				id = AbstractLocationManager.instance().uploadLocation(l, null);
			}
		}
		map.put(LOCATION_FIELD_NAME, id);
		map.put(RATING_FIELD_NAME, r.getRating().getScore());
		map.put(CONTENT_FIELD_NAME, r.getContent());
		map.put(IS_PINNED_FIELD_NAME, r.isPinned());
		map.put(USERNAME_FIELD_NAME, r.getUser().getUsername());

		List<ReviewComment> lrc = r.getComments();
		StringBuilder sb = new StringBuilder();
		for (ReviewComment rc : lrc){
			logger.debug("serializing reviewcomment {} {}", rc.getCommentator().getUsername(), rc.getRating());
			sb.append(ReviewComment.serialize(rc));
			sb.append("$");
		}
		if (sb.length()>0)
			sb.setLength(sb.length() - 1);
		map.put(COMMENTS_FIELD_NAME, sb.toString());
		
		return map;
	}

	public static Review fromMap(Map<String, Object> m) {
		logger.debug("fromMap {}", m);
		int rating = (int) m.get(RATING_FIELD_NAME);
		String content = m.get(CONTENT_FIELD_NAME).toString();
		boolean isPinned = (boolean) m.get(IS_PINNED_FIELD_NAME);
		UserProfile up = null;
		try {
			up = AbstractUserProfileManager.instance().get(m.get(USERNAME_FIELD_NAME).toString(), null);
		} catch (UserNotFoundException e) {
			logger.error("User not found for review {} with error {}", m.get(ID_FIELD_NAME), e);
		}
		Review r = new Review(null, rating, content, up);
		r.setPinned(isPinned);

		if (!m.containsKey(COMMENTS_FIELD_NAME)){
			logger.debug("m not contains comments field name");
			return r;
		}
		String comments = m.get(COMMENTS_FIELD_NAME).toString();
		if (comments == null || "".equals(comments)){
			logger.debug("comments are null or empty");
			return r;
		}
		String[] ls = comments.split("$");
		List<ReviewComment> lrc = new ArrayList<>();
		for (String comment : ls){
			logger.debug("comments split into {}", comment);
			try {
				lrc.add(ReviewComment.deserialize(comment));
			} catch (NumberFormatException | UserNotFoundException e) {
				logger.error("Couldn't deserialize review comment {}, with exception {}", comment, e);
			}
		}
		r.addComments(lrc);
		
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
		logger.info("get reviews with location for {} ", locationId);
		Flowable<List<Review>> res = Flowable.fromCallable(() -> {
			Map<String, Object> m = db.get(LocationManager.DATABASE_CLASS, locationId);
			Location l = LocationManager.fromMap(m);
			l = AbstractLocationManager.instance()
				.getLocation(l.getCoordinates(),
						l.getLocationType(),
						l.getLocationSubType(),
						null);
			return l.getReviews();
		})
		.subscribeOn(Schedulers.io())
		.observeOn(Schedulers.single());
		if(callback == null)
			return res.blockingFirst();
		res.subscribe(callback::onFinish, Throwable::printStackTrace);	
		return new ArrayList<>();
	}

	@Override
	public Boolean uploadReview(Review r, ICallback<Boolean> callback) {
		logger.debug("uploadReview for user {}", r.getUser().getUsername());
		Flowable<Boolean> res = Flowable.fromCallable(() -> (db.put(DATABASE_CLASS, toMap(r)) != null))
		.subscribeOn(Schedulers.io())
		.observeOn(Schedulers.single());
		if(callback == null)
			return res.blockingFirst();
		res.subscribe(callback::onFinish, Throwable::printStackTrace);	
		return null;
	}

	@Override
	public Boolean deleteReview(Review r, ICallback<Boolean> callback) {
		logger.debug("deleteReview for user {}", r.getUser().getUsername());
		Flowable<Boolean> res = Flowable.fromCallable(() -> {
			Map<String, Object> m = toMap(r);
			List<Map<String, Object>> lm = db.get(DATABASE_CLASS, m);
			if(lm.isEmpty())
				return false;
			if(lm.size() > 1){
				logger.error("Multiple reviews found, not deleting");
				return false;
			}
			return db.delete(DATABASE_CLASS, lm.get(0).get(ID_FIELD_NAME).toString());
		})
		.subscribeOn(Schedulers.io())
		.observeOn(Schedulers.single());
		if(callback == null)
			return res.blockingFirst();
		res.subscribe(callback::onFinish, Throwable::printStackTrace);	
		return null;
	}

	@Override
	public Boolean updateReview(Review review, ICallback<Boolean> callback) {
		logger.debug("udpateReview for user {}", review.getUser().getUsername());
		Flowable<Boolean> res = Flowable.fromCallable(() -> {
			Map<String, Object> m = toMap(review);
			Map<String, Object> m2 = new HashMap<>();
			m2.put(LOCATION_FIELD_NAME, m.get(LOCATION_FIELD_NAME));
			m2.put(USERNAME_FIELD_NAME, m.get(USERNAME_FIELD_NAME));
			List<Map<String, Object>> lm = db.get(DATABASE_CLASS, m2);
			if(lm.isEmpty())
				return false;
			if(lm.size() > 1){
				logger.error("Multiple reviews found, not updating");
				return false;
			}
			return db.update(DATABASE_CLASS, lm.get(0).get(ID_FIELD_NAME).toString(), m);
		})
		.subscribeOn(Schedulers.io())
		.observeOn(Schedulers.single());
		if(callback == null)
			return res.blockingFirst();
		res.subscribe(callback::onFinish, Throwable::printStackTrace);	
		return null;
	}

}
