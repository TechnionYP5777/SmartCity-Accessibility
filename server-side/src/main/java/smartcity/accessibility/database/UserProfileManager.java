package smartcity.accessibility.database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import smartcity.accessibility.exceptions.UserNotFoundException;
import smartcity.accessibility.socialnetwork.UserProfile;

public class UserProfileManager {
	private Database db;
	private static final String DATABASE_CLASS = "UserProfile";
	private static UserProfileManager instance;
	private static Logger logger = LoggerFactory.getLogger(UserProfileManager.class);

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

	private Map<String, Object> toMap(UserProfile u) {
		Map<String, Object> m = new HashMap<>();
		m.put("username", u.getUsername());
		m.put("rating", u.getRating());
		m.put("numOfReviews", u.getNumOfReviews());
		return m;
	}

	private UserProfile fromMap(Map<String, Object> m) {
		UserProfile u = new UserProfile(m.get("username").toString());
		u.setRating((int) m.get("rating"));
		u.setNumOfReviews((int) m.get("numOfReviews"));
		logger.info("got review with username {} raintg {} numOfReviews {}" , m.get("username"), m.get("rating"), m.get("numOfReviews"));
		return u;
	}

	public UserProfile get(String username, UserProfileCallback c) throws UserNotFoundException {
		logger.debug("get UserProfile {}", username);
		Flowable<UserProfile> res = Flowable.fromCallable(() -> {
			Map<String, Object> m = new HashMap<>();
			m.put("username", username);
			List<Map<String, Object>> l = db.get(DATABASE_CLASS, m);
			if (l.isEmpty())
				throw new UserNotFoundException();
			UserProfile up = fromMap(l.get(0));
			return up;
		})
		.subscribeOn(Schedulers.io())
		.observeOn(Schedulers.single());
		if(c == null)
			return res.blockingFirst();
		else
			res.subscribe(c::onFinish, Throwable::printStackTrace);	
		return null;
	}

	public interface UserProfileCallback {
		void onFinish(UserProfile u);
	}

}
