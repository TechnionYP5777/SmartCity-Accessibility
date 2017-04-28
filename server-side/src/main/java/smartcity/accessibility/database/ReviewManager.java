package smartcity.accessibility.database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import smartcity.accessibility.database.callbacks.ICallback;
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
		String id = AbstractLocationManager.instance().getId(l.getCoordinates(), l.getLocationType(), l.getLocationSubType(), null);
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
	
	@Override
	public List<Review> getReviews(String locationId, ICallback<List<Review>> callback) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Review> getReviewWithLocation(String locationId, ICallback<List<Review>> callback) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Boolean uploadReview(Review r, ICallback<Boolean> callback) {
		// TODO Auto-generated method stub
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
