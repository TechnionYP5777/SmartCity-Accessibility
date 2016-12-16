package smartcity.accessibility.database;
import java.util.HashMap;
import java.util.Map;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;

import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.socialnetwork.Review;
import smartcity.accessibility.socialnetwork.User;
/**
 * @author assaflu
 *
 */
public class ReviewManager {
	
	public static void uploadReview(Review r) throws ParseException{
		Map<String, Object> m = new HashMap<String,Object>();
		m.put("user", r.getUser());
		m.put("location", r.getLocation());
		m.put("rating",r.getRating());
		m.put("comment",r.getComment());
		ParseObject p = DatabaseManager.putValue("Review",m);
		
		//TODO: why putValue have a return value (check with alex)
		//TODO: is there anything that can make a review unvalid
	}

}
