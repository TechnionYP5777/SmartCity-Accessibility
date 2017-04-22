package smartcity.accessibility.database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.annotations.Nullable;
import io.reactivex.schedulers.Schedulers;
import smartcity.accessibility.exceptions.UserNotFoundException;
import smartcity.accessibility.socialnetwork.UserProfile;

public class UserProfileManager {
	public static final String NUM_OF_REVIEWS_FIELD = "numOfReviews";
	public static final String RATING_FIELD = "rating";
	public static final String USERNAME_FIELD = "username";
	public static final String DATABASE_CLASS = "UserProfile";
	private static UserProfileManager instance;
	private static Logger logger = LoggerFactory.getLogger(UserProfileManager.class);
	private Database db;
	
	

	@Inject
	public UserProfileManager(Database db) {
		this.db = db;
	}

	public static void initialize(UserProfileManager m) {
		instance = m;
	}

	public static UserProfileManager instance() {
		return instance;
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
		u.setRating((int) m.get(RATING_FIELD));
		u.setNumOfReviews((int) m.get(NUM_OF_REVIEWS_FIELD));
		logger.info("got review with username {} raintg {} numOfReviews {}" , m.get(USERNAME_FIELD), m.get(RATING_FIELD), m.get(NUM_OF_REVIEWS_FIELD));
		return u;
	}

	public Optional<UserProfile> get(String username, UserProfileCallback c) throws UserNotFoundException {
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
			return Optional.of(res.blockingFirst());
		res.subscribe(c::onFinish, Throwable::printStackTrace);	
		return Optional.empty();
	}
	
	public Optional<Boolean> put(UserProfile up, boolean block){
		logger.debug("put UserProfile {} and block={}", up.getUsername(),block);
		Flowable<Boolean> res = Flowable.fromCallable(() -> {
			db.put(DATABASE_CLASS, toMap(up));
			return true;
		})
		.subscribeOn(Schedulers.io())
		.observeOn(Schedulers.single());
		if(block)
			return Optional.of(res.blockingFirst());
		res.subscribe();
		return Optional.empty();
	}

	public interface UserProfileCallback {
		void onFinish(UserProfile u);
	}

}
