package smartcity.accessibility.database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.socialnetwork.Review;

/**
 * @author KaplanAlexander
 *
 */
public class ReviewManager extends AbstractReviewManager {

	private Database db;
	private static final String DATABASE_CLASS = "Review";
	private static final String ID_FIELD_NAME = "objectId";
	private static Logger logger = LoggerFactory.getLogger(ReviewManager.class);

	@Inject
	public ReviewManager(Database db) {
		this.db = db;
	}

	private Map<String, Object> toMap(Review r) {
		Map<String, Object> map = new HashMap<>();
		Location l = r.getLocation();
		String id = AbstractLocationManager.instance().getId(l.getCoordinates(), null);
		if(id == null){
			id = AbstractLocationManager.instance().uploadLocation(l, null);
		}
		map.put(ID_FIELD_NAME, id);
		
		map.put("rating", r.getRating().getScore());
		map.put("content", r.getContent());
		map.put("isPinned", r.isPinned());
		
		Map<String, Object> userMap = new HashMap<>();
		userMap.put("username", r.getUser().getUsername());
		List<Map<String, Object>> uList = db.get("User", userMap);
		if(uList.isEmpty()){
			// TODO : Throw exception
			logger.error("User used not found in db");
			return new HashMap<>();
		}
		map.put("user",	uList.get(0).get("objectId"));
		
		return map;
	}

	private static Review fromMap(Map<String, Object> m) {
		Map<String, Object> userMap = new HashMap<>();
		//userMap.put("username", value);
		//UserManager.LoginUser(, password)
		
		return null;// new Review(new Location(), 5, "12", "!2"); // TODO :This is
															// comepletely wrong
	}

	public List<Review> getReviews(Map<String, Object> fields) {
		return db.get(DATABASE_CLASS, fields).stream().map(m -> fromMap(m)).collect(Collectors.toList());
	}

	public static void uploadReview(Review r) {
		// db.put(DATABASE_CLASS, toMap(r));
	}

	public static void deleteReview(Review r) {
		// TODO Auto-generated method stub

	}

	public static void updateReview(Review review) {
		// TODO Auto-generated method stub

	}

}
