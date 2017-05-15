package smartcity.accessibility.database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import smartcity.accessibility.database.callbacks.ICallback;
import smartcity.accessibility.exceptions.UserNotFoundException;
import smartcity.accessibility.socialnetwork.UserProfile;

/**
 * @author KaplanAlexander
 *
 */
public class UserProfileManager extends AbstractUserProfileManager {
	public static final String NUM_OF_REVIEWS_FIELD = "numOfReviews";
	public static final String RATING_FIELD = "rating";
	public static final String USERNAME_FIELD = "username";
	public static final String DATABASE_CLASS = "UserProfile";
	public static final String ID_FIELD_NAME = "objectId";
	private static Logger logger = LoggerFactory.getLogger(UserProfileManager.class);
	private Database db;
	
	

	@Inject
	public UserProfileManager(Database db) {
		this.db = db;
	}

	public static Map<String, Object> toMap(UserProfile u) {
		Map<String, Object> m = new HashMap<>();
		m.put(USERNAME_FIELD, u.getUsername());
		m.put(RATING_FIELD, u.getRating());
		m.put(NUM_OF_REVIEWS_FIELD, u.getNumOfReviews());
		return m;
	}

	public static UserProfile fromMap(Map<String, Object> m) {
		UserProfile u = new UserProfile(m.get(USERNAME_FIELD).toString());
		u.getHelpfulness().setRating((int) m.get(RATING_FIELD));
		u.getHelpfulness().setNumOfReviews((int) m.get(NUM_OF_REVIEWS_FIELD));
		logger.info("got review with username {} raintg {} numOfReviews {}" , m.get(USERNAME_FIELD), m.get(RATING_FIELD), m.get(NUM_OF_REVIEWS_FIELD));
		return u;
	}

	@Override
	public UserProfile get(String username, ICallback<UserProfile> c) throws UserNotFoundException {
		logger.debug("get UserProfile {}", username);
		Flowable<UserProfile> res = Flowable.fromCallable(() -> {
			Map<String, Object> m = new HashMap<>();
			m.put(USERNAME_FIELD, username);
			List<Map<String, Object>> l = db.get(DATABASE_CLASS, m);
			if (l.isEmpty())
				throw new UserNotFoundException();
			return fromMap(l.get(0));
		})
		.subscribeOn(Schedulers.io())
		.observeOn(Schedulers.single());
		if(c == null)
			return res.blockingFirst();
		res.subscribe(c::onFinish, Throwable::printStackTrace);	
		return null;
	}
	
	@Override
	public Boolean put(UserProfile up, ICallback<Boolean> callback){
		logger.debug("put UserProfile {}", up.getUsername());
		Flowable<Boolean> res = Flowable.fromCallable(() -> {
			db.put(DATABASE_CLASS, toMap(up));
			return true;
		})
		.subscribeOn(Schedulers.io())
		.observeOn(Schedulers.single());
		if(callback == null)
			return res.blockingFirst();
		res.subscribe();
		return false;
	}

	@Override
	public Boolean update(UserProfile up, ICallback<Boolean> callback) {
		logger.debug("update UserProfile {}", up.getUsername());
		Flowable<Boolean> res = Flowable.fromCallable(() -> {
			Map<String, Object> m = new HashMap<>();
			m.put(USERNAME_FIELD, up.getUsername());
			List<Map<String, Object>> lm = db.get(DATABASE_CLASS, m);
			if (lm.isEmpty()){
				logger.error("Not found object to update ");
				return false;
			}		
			return db.update(DATABASE_CLASS, lm.get(0).get(ID_FIELD_NAME).toString(), toMap(up));
		})
		.subscribeOn(Schedulers.io())
		.observeOn(Schedulers.single());
		if(callback == null)
			return res.blockingFirst();
		res.subscribe();
		return false;
	}

	@Override
	public Boolean delete(UserProfile up, ICallback<Boolean> callback) {
		logger.debug("delete UserProfile {}", up.getUsername());
		Flowable<Boolean> res = Flowable.fromCallable(() -> {
			Map<String, Object> m = new HashMap<>();
			m.put(USERNAME_FIELD, up.getUsername());
			List<Map<String, Object>> lm = db.get(DATABASE_CLASS, m);
			if (lm.isEmpty()){
				logger.error("Not found object to delete ");
				return false;
			}		
			return db.delete(DATABASE_CLASS, lm.get(0).get(ID_FIELD_NAME).toString());
		})
		.subscribeOn(Schedulers.io())
		.observeOn(Schedulers.single());
		if(callback == null)
			return res.blockingFirst();
		res.subscribe();
		return false;
	}

	
}
