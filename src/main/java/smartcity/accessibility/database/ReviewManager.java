package smartcity.accessibility.database;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.parse4j.ParseException;
import org.parse4j.ParseGeoPoint;
import org.parse4j.ParseObject;
import org.parse4j.callback.FindCallback;
import org.parse4j.callback.GetCallback;

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
		m.put("location", new ParseGeoPoint(r.getLocation().getCoordinates().getLat(),r.getLocation().getCoordinates().getLng()));
		m.put("rating",r.getRating().getScore());
		m.put("comment",r.getComment());
		ParseObject p = DatabaseManager.putValue("Review",m); 
	}
	
	public static Review getReviewByUserAndLocation(User u,Location l){
		final StringBuilder c = new StringBuilder();
		final StringBuilder r = new StringBuilder();
		FindCallback<ParseObject> p = new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> arg0, ParseException arg1) {
				if (arg1 != null)
					return;
				c.append(arg0.get(0).get("rating"));
				r.append(arg0.get(0).get("comment"));
				
			}
		};
		Map<String, Object> m = new HashMap<String,Object>();
		m.put("user", u.getName());
		m.put("location", new ParseGeoPoint(l.getCoordinates().getLat(),l.getCoordinates().getLng()));
		DatabaseManager.queryByFields("Review",m,p);
		return new Review(l, Integer.valueOf((r + "")), c + "", u);	
	}
	
	public static void deleteReview(Review r){
		Map<String, Object> m = new HashMap<String,Object>();
		final StringBuilder objectID = new StringBuilder();
		m.put("user", r.getUser());
		m.put("location", new ParseGeoPoint(r.getLocation().getCoordinates().getLat(),r.getLocation().getCoordinates().getLng()));
		
		GetCallback<ParseObject> p = new GetCallback<ParseObject>() {
			@Override
			public void done(ParseObject arg0, ParseException arg1) {
				if(arg1!=null)
					objectID.append(arg0.getObjectId());
				arg1.printStackTrace();
			}
		};
		
		DatabaseManager.getObjectByFields("Review",m,p);
		DatabaseManager.deleteById("Review",(objectID + ""));	
	}

}