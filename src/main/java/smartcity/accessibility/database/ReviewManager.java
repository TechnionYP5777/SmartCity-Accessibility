package smartcity.accessibility.database;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.callback.FindCallback;

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
		m.put("rating",r.getRating().getScore());
		m.put("comment",r.getComment());
		ParseObject p = DatabaseManager.putValue("Review",m); // 
		
		//TODO: why putValue have a return value (check with alex)
		//TODO: is there anything that can make a review unvalid
	}
	
	public static Review getReviewByUserAndLocation(User u,Location l){
		final StringBuilder c = new StringBuilder();
		final StringBuilder r = new StringBuilder();
		FindCallback<ParseObject> p = new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> arg0, ParseException arg1) {
				// TODO Auto-generated method stub
                if (arg1 == null) {
                	c.append(arg0.get(0).get("rating"));
                	r.append(arg0.get(0).get("comment"));
                	//There might be a better way but for now i think it is ok
                	//I look more into it later when i have time
                } else {
                    //TODO: check if this part needed
                }
				
			}
		};
		Map<String, Object> m = new HashMap<String,Object>();
		m.put("user", u);
		m.put("location", l);
		DatabaseManager.queryByFields("Review",m,p);
		Review rev = new Review(l, Integer.valueOf(r.toString()), c.toString(), u);
		return rev;	
	}
	
	public static void deleteReview(Review r){
		Map<String, Object> m = new HashMap<String,Object>();
		m.put("user", r.getUser());
		m.put("location", r.getLocation());
		
		/* TODO : assaf, I change getObjectId to work in the background \
		 * 			it's now called getObjectByFields, the callback will get 
		 * 			get the ParseObject relevant and you can get the id with 
		 * 			o.getObjectId()
		 * 			update this to work correctly 
		 * 												-alex
		 */
		//String id = DatabaseManager.getObjectId("Review",m);
		//DatabaseManager.deleteById("Review",id);
		
		
		//TODO: handle Extreme case  - review not found
		//TODO: maybe change the implementation to refer to the result value
	}

}
