package smartcity.accessibility.database;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.parse4j.ParseException;
import org.parse4j.ParseFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import smartcity.accessibility.database.callbacks.ICallback;
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
	public static final String PROFILE_IMAGE = "profileImg";

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
		putProfileImage(u, m);
		return m;
	}

	/**
	 * @author yael
	 */
	private static void putProfileImage(UserProfile u, Map<String, Object> m) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(u.getProfileImg(), "png", baos);
			baos.flush();
			byte[] imageInByte = baos.toByteArray();
			baos.close();
			ParseFile profileImg = new ParseFile("image_profile", imageInByte);
			profileImg.save();
			m.put(PROFILE_IMAGE, profileImg);
		} catch (IOException | ParseException e) {
			m.put(PROFILE_IMAGE, null);
		}
	}

	/**
	 * @author yael
	 */
	private static void getProfileImage(Map<String, Object> m, UserProfile u) {
		try {
			ParseFile profileImgFile = (ParseFile) m.get(PROFILE_IMAGE);
			if(profileImgFile == null){
				return;
			}
			profileImgFile.save();
			byte[] img = profileImgFile.getData();
			if (img != null) {
				InputStream in = new ByteArrayInputStream(img);
				BufferedImage profileImg = ImageIO.read(in);
				u.setProfileImg(profileImg);
			} else {
				u.setProfileImg(ImageIO.read(new File("res/profileImgDef.png")));
			}
		} catch (ParseException | IOException e) {
		}
	}

	public static UserProfile fromMap(Map<String, Object> m) {
		UserProfile u = new UserProfile(m.get(USERNAME_FIELD).toString());
		u.getHelpfulness().setRating((int) m.get(RATING_FIELD));
		u.getHelpfulness().setNumOfReviews((int) m.get(NUM_OF_REVIEWS_FIELD));
		getProfileImage(m, u);
		logger.info("got review with username {} raintg {} numOfReviews {}", m.get(USERNAME_FIELD), m.get(RATING_FIELD),
				m.get(NUM_OF_REVIEWS_FIELD));
		return u;
	}

	@Override
	public UserProfile get(String username, ICallback<UserProfile> c) {
		logger.debug("get UserProfile {}", username);
		Flowable<UserProfile> res = Flowable.fromCallable(() -> {
			Map<String, Object> m = new HashMap<>();
			m.put(USERNAME_FIELD, username);
			List<Map<String, Object>> l = db.get(DATABASE_CLASS, m);
			if (l.isEmpty())
				return new UserProfile(username);
			return fromMap(l.get(0));
		}).subscribeOn(Schedulers.io()).observeOn(Schedulers.single());
		if (c == null)
			return res.blockingFirst();
		res.subscribe(c::onFinish, Throwable::printStackTrace);
		return null;
	}

	@Override
	public Boolean put(UserProfile up, ICallback<Boolean> callback) {
		logger.debug("put UserProfile {}", up.getUsername());
		Flowable<Boolean> res = Flowable.fromCallable(() -> {
			db.put(DATABASE_CLASS, toMap(up));
			return true;
		}).subscribeOn(Schedulers.io()).observeOn(Schedulers.single());
		if (callback == null)
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
			if (lm.isEmpty()) {
				logger.error("Not found object to update ");
				return false;
			}
			return db.update(DATABASE_CLASS, lm.get(0).get(ID_FIELD_NAME).toString(), toMap(up));
		}).subscribeOn(Schedulers.io()).observeOn(Schedulers.single());
		if (callback == null)
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
			if (lm.isEmpty()) {
				logger.error("Not found object to delete ");
				return false;
			}
			return db.delete(DATABASE_CLASS, lm.get(0).get(ID_FIELD_NAME).toString());
		}).subscribeOn(Schedulers.io()).observeOn(Schedulers.single());
		if (callback == null)
			return res.blockingFirst();
		res.subscribe();
		return false;
	}

	@Override
	public List<UserProfile> mostHelpful(int n, ICallback<List<UserProfile>> callback) {
		logger.debug("most helpful {} ", n);
		Flowable<List<UserProfile>> res = Flowable
				.fromCallable(() -> db.getHighestBy(DATABASE_CLASS, RATING_FIELD, n).stream()
						.map(UserProfileManager::fromMap).collect(Collectors.toList()))
				.subscribeOn(Schedulers.io()).observeOn(Schedulers.single());
		if (callback == null)
			return res.blockingFirst();
		res.subscribe();
		return new ArrayList<>();
	}

	@Override
	public Integer userCount(ICallback<Integer> callback) {
		logger.debug("count UserProfile");
		Flowable<Integer> res = Flowable.fromCallable(() -> db.countEntries(DATABASE_CLASS))
				.subscribeOn(Schedulers.io()).observeOn(Schedulers.single());
		if (callback == null)
			return res.blockingFirst();
		res.subscribe();
		return 0;
	}

}
