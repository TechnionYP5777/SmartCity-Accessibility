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
	 * If the review saved in the DB as hidden review - return the review
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
				if(arg0==null){
					DatabaseManager.getObjectByFields("Review",m,new GetCallback<ParseObject>() {

						@Override
						public void done(ParseObject arg0, ParseException arg1) {
							if(arg0!=null)
								o.done(arg0, null);
							else{
								
								o.done(null,null);
							}
															
						}
						
					});
				}
				else{
					o.done(arg0, null); 
				}
					
				//arg1.printStackTrace();
				
			}
		};
		DatabaseManager.getObjectByFields("HiddenReview",m,hiddenCallBack);
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
		LocationManager.checkLocationInDB(r.getLocation(),new GetCallback<ParseObject>(){
			@Override
			public void done(ParseObject arg0, ParseException arg1) {
				if(arg0==null){
					LocationManager.saveLocation(r.getLocation(),new SaveCallback() {
						
						@Override
						public void done(ParseException arg0) {
							LocationManager.checkLocationInDB(r.getLocation(),new GetCallback<ParseObject>() {

								@Override
								public void done(ParseObject arg0, ParseException arg1) {
									if(arg0==null){
										System.out.println("something went worng");
									}
									else{
										m.put("locationID",arg0.getObjectId());
										checkReviewInDB(r,new GetCallback<ParseObject>() {

											@Override
											public void done(ParseObject arg0, ParseException arg1) {
												if(arg0!=null && arg0.getClassName().equals("HiddenReview")){
													DatabaseManager.putValue("Review",m,new SaveCallback() {			
														@Override
														public void done(ParseException arg0) {
															// do nothing
															
														}
													});
												}
												else{
													if(arg0==null){
														DatabaseManager.putValue("Review",m,new SaveCallback() {			
															@Override
															public void done(ParseException arg0) {
																// do nothing
																
															}
														});
													}
												}
												
											}
										});
									}
								}
							});
						}
					});
				}
				else{
					m.put("locationID",arg0.getObjectId());
					checkReviewInDB(r,new GetCallback<ParseObject>() {

						@Override
						public void done(ParseObject arg0, ParseException arg1) {
							if(arg0!=null && arg0.getClassName().equals("HiddenReview")){
								DatabaseManager.putValue("Review",m,new SaveCallback() {			
									@Override
									public void done(ParseException arg0) {
										// do nothing
										
									}
								});
							}
							else{
								if(arg0==null){
									DatabaseManager.putValue("Review",m,new SaveCallback() {			
										@Override
										public void done(ParseException arg0) {
											// do nothing
												
										}
									});
								}
							}
							
						}
					});
				}
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
		GetCallback<ParseObject> p = new GetCallback<ParseObject>() {
			@Override
			public void done(ParseObject arg0, ParseException arg1) {
				if(arg0==null){
					o.done(null,arg1);
				}else{
					Map<String, Object> m = new HashMap<String,Object>();
					m.put("user", u.getName());
					m.put("locationID", arg0.getObjectId());
					DatabaseManager.queryByFields("Review",m,new FindCallback<ParseObject>() {

						@Override
						public void done(List<ParseObject> arg0, ParseException arg1) {
							if(arg0!=null){
								o.done(arg0.get(0),arg1);
							}
							else{
								o.done(null, arg1);
							}
							
						}
					});
				}				
			}
		};
		LocationManager.checkLocationInDB(l, p);
		
			
	}
	
	/**
	 * Remove the review from the "Review" object class and put it back in the HiddenReview object class
	 * HiddenReview are deleted every 24h
	 * @param r
	 */
	public static void deleteReview(Review r){
		Map<String, Object> m = new HashMap<String,Object>();
		m.put("user", r.getUser());
			
		GetCallback<ParseObject> p = new GetCallback<ParseObject>() {
			@Override
			public void done(ParseObject arg0, ParseException arg1) {
				m.put("locationID",arg0.getObjectId());
				DatabaseManager.getObjectByFields("Review",m,new GetCallback<ParseObject>() {
					
					@Override
					public void done(ParseObject arg0, ParseException arg1) {
						if(arg1==null && arg0!=null){
							DatabaseManager.deleteById("Review",(arg0.getObjectId() + ""));
							m.put("location",arg0.getParseGeoPoint("location"));
							m.put("rating",arg0.getInt("rating"));
							m.put("comment",arg0.getString("comment"));
							m.put("pined",arg0.getInt("pined"));
							DatabaseManager.putValue("HiddenReview",m,new SaveCallback() {
								
								@Override
								public void done(ParseException arg0) {
									// do nothing
									
								}
							});
						}
						//arg1.printStackTrace();
						
					}
				});
			}
		};
		LocationManager.checkLocationInDB(r.getLocation(), p);
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
			if(arg0==null){
				uploadReview(r);
			}
			else {
				if(!arg0.getClassName().equals("HiddenReview")){
					Map<String, Object> m = new HashMap<String,Object>();
					m.put("user", r.getUser());
					m.put("location", new ParseGeoPoint(r.getLocation().getCoordinates().getLat(),r.getLocation().getCoordinates().getLng()));
					m.put("rating",r.getRating().getScore());
					m.put("comment",r.getContent());
					if(r.isPinned())
						m.put("pined",1);
					else
						m.put("pined",0);
					DatabaseManager.update("Review",arg0.getObjectId(), m);
				}
			}	
			
		}
	};
		
		checkReviewInDB(r,p);
		// needs to be done in background
	}

}