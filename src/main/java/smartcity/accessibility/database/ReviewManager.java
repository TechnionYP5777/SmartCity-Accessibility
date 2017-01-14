package smartcity.accessibility.database;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.parse4j.ParseException;
import org.parse4j.ParseGeoPoint;
import org.parse4j.ParseObject;
import org.parse4j.callback.FindCallback;
import org.parse4j.callback.GetCallback;
import org.parse4j.callback.SaveCallback;

import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.socialnetwork.Review;
import smartcity.accessibility.socialnetwork.User;
/**
 * @author assaflu
 *
 */
public class ReviewManager {
	
	/**
	 * If the review saved in the DB as hidden review - does nothing
	 * If the review saved as review return it to the callback function
	 * If it's not saved notify the callback function
	 * @param r
	 * @param o
	 */
	public static void checkReviewInDB(Review r,GetCallback<ParseObject> o){
		Map<String, Object> m = new HashMap<String,Object>();
		m.put("user", r.getUser());
		m.put("location", new ParseGeoPoint(r.getLocation().getCoordinates().getLat(),r.getLocation().getCoordinates().getLng()));
		
		GetCallback<ParseObject> hiddenCallBack = new GetCallback<ParseObject>() {
			@Override
			public void done(ParseObject arg0, ParseException arg1) {
				if(arg1==null){
					DatabaseManager.getObjectByFields("Review",m,new GetCallback<ParseObject>() {

						@Override
						public void done(ParseObject arg0, ParseException arg1) {
							if(arg0!=null)
								o.done(arg0, null);
							else
								o.done(null,null);							
						}
						
					});
				}
				else{
					//do nothing 
				}
					
				//arg1.printStackTrace();
				
			}
		};
		DatabaseManager.getObjectByFields("HiddenReviews",m,hiddenCallBack);
	}
	
	/**
	 * saves the review in the DB  - happen in the background
	 * @param r
	 * @throws ParseException
	 */
	public static void uploadReview(Review r){
		Map<String, Object> m = new HashMap<String,Object>();
		m.put("user", r.getUser());
		m.put("location", new ParseGeoPoint(r.getLocation().getCoordinates().getLat(),r.getLocation().getCoordinates().getLng()));
		m.put("rating",r.getRating().getScore());
		m.put("comment",r.getContent());
		if(r.isPinned())
			m.put("pined",1);
		else
			m.put("pined",0);
		DatabaseManager.putValue("Review",m,new SaveCallback() {
			
			@Override
			public void done(ParseException arg0) {
				// do nothing
				
			}
		}); 
	}
	
	/**
	 * Return the review that belong to the user and location given.
	 * The function get CallBack function to which it returns the review
	 * @param u
	 * @param l
	 * @param o
	 */
	public static void getReviewByUserAndLocation(User u,Location l,GetCallback<ParseObject> o){
		FindCallback<ParseObject> p = new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> arg0, ParseException arg1) {
				if (arg1 != null)
					o.done(null, arg1);
				else{
					o.done(arg0.get(0),null);
				}
				
			}
		};
		Map<String, Object> m = new HashMap<String,Object>();
		m.put("user", u.getName());
		m.put("location", new ParseGeoPoint(l.getCoordinates().getLat(),l.getCoordinates().getLng()));
		DatabaseManager.queryByFields("Review",m,p);	
	}
	
	/**
	 * Remove the review from the "Review" object class and put it back in the HiddenReview object class
	 * HiddenReview are deleted every 24h
	 * @param r
	 */
	public static void deleteReview(Review r){
		Map<String, Object> m = new HashMap<String,Object>();
		m.put("user", r.getUser());
		m.put("location", new ParseGeoPoint(r.getLocation().getCoordinates().getLat(),r.getLocation().getCoordinates().getLng()));
		
		GetCallback<ParseObject> p = new GetCallback<ParseObject>() {
			@Override
			public void done(ParseObject arg0, ParseException arg1) {
				if(arg1!=null){
					DatabaseManager.deleteById("Review",(arg0.getObjectId() + ""));
					Map<String, Object> m = new HashMap<String,Object>();
					m.put("user", arg0.getParseObject("user"));
					m.put("location",arg0.getParseObject("location"));
					m.put("rating",arg0.getParseObject("rating"));
					m.put("comment",arg0.getParseObject("comment"));
					m.put("pined",arg0.getParseObject("comment"));
					DatabaseManager.putValue("HiddenReviews",m,new SaveCallback() {
						
						@Override
						public void done(ParseException arg0) {
							// do nothing
							
						}
					});
				}
				//arg1.printStackTrace();
			}
		};
		DatabaseManager.getObjectByFields("Review",m,p);	
	}
	
	/**
	 * If the review is in the DB as review update it
	 * If the review is in the DB as HiddenReview ignore
	 * else add the review
	 * @param r
	 */
	public static void updateReview(Review r){
	GetCallback<ParseObject> p = new GetCallback<ParseObject>() {
		@Override
			public void done(ParseObject arg0, ParseException arg1) {
				Map<String, Object> m = new HashMap<String,Object>();
				m.put("user", r.getUser());
				m.put("location", new ParseGeoPoint(r.getLocation().getCoordinates().getLat(),r.getLocation().getCoordinates().getLng()));
				m.put("rating",r.getRating().getScore());
				m.put("comment",r.getContent());
				if(r.isPinned())
					m.put("pined",1);
				else
					m.put("pined",0);
				if(arg1!=null){
					DatabaseManager.update("Review",arg0.getObjectId(), m);
				}
				else{
					uploadReview(r);
				}
				//arg1.printStackTrace();
			}
		};
		
		checkReviewInDB(r,p);
		// needs to be done in background
	}

}