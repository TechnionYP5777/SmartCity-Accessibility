package smartcity.accessibility.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//import org.eclipse.persistence.internal.sessions.remote.SequencingFunctionCall.GetNextValue;
import org.parse4j.ParseException;
import org.parse4j.ParseGeoPoint;
import org.parse4j.ParseObject;
import org.parse4j.callback.FindCallback;
import org.parse4j.callback.GetCallback;
import org.parse4j.callback.SaveCallback;

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.exceptions.UnauthorizedAccessException;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.socialnetwork.Review;
import smartcity.accessibility.socialnetwork.Score;
import smartcity.accessibility.socialnetwork.User;
import smartcity.accessibility.socialnetwork.UserImpl;

/**
 * @author assaflu
 *
 */
public class LocationManager {

	/**
	 * If the location saved return it to the callback function
	 * If it's not saved notify the callback function
	 * @param l
	 * @param o
	 */
	static void checkLocationInDB(Location l,GetCallback<ParseObject> o){
		Map<String, Object> m = new HashMap<String,Object>();
		m.put("coordinates", new ParseGeoPoint(l.getCoordinates().getLat(),l.getCoordinates().getLng()));
		if(l.getLocationSubType()==null){
			m.put("subtype", Location.LocationSubTypes.Default.toString());
		}else{
			m.put("subtype", l.getLocationSubType().toString());
		}
		if(l.getLocationType()==null){
			m.put("subtype", Location.LocationTypes.Coordinate.toString());
		}else{
			m.put("type", l.getLocationType().toString());
		}
		GetCallback<ParseObject> hiddenCallBack = new GetCallback<ParseObject>() {
			@Override
			public void done(ParseObject arg0, ParseException arg1) {
				if(arg1==null && arg0!=null){
					o.done(arg0, null);
				}
				else{
					o.done(null, null);
				}				
			}
		};
		DatabaseManager.getObjectByFields("Location",m,hiddenCallBack);
	}
	
	/**
	 * calculate the distance between the source point and destination point in meters
	 * @param source
	 * @param destination
	 * @return
	 */
	private static double distanceBtween(LatLng source,LatLng destination){
		double lat1 = source.getLat();
	    double lat2 = destination.getLat();
	    double lon1 = source.getLng();
	    double lon2 = destination.getLng();
	    double dLat = Math.toRadians(lat2-lat1);
	    double dLon = Math.toRadians(lon2-lon1);
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	    Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
	    Math.sin(dLon/2) * Math.sin(dLon/2);
	    double c = 2 * Math.asin(Math.sqrt(a));
	    return 6366000 * c;
	}
	
	/**
	 * return list of LatLng that qualify the radius and threshold
	 * (not yet know if to implement in background or not - opened issue on it)
	 * @param source
	 * @param destination
	 * @param accessibilityThreshold
	 * @return
	 */
	public static List<LatLng> getNonAccessibleLocationsInRadius(Location source, Location destination,
			Integer accessibilityThreshold) {
		StringBuilder mutex = new StringBuilder();
		double radius = distanceBtween(source.getCoordinates(),destination.getCoordinates());
		LatLng center = new LatLng((source.getCoordinates().getLat()+destination.getCoordinates().getLat())/2,
				(source.getCoordinates().getLng()+destination.getCoordinates().getLng())/2);
		ArrayList<LatLng> points = new ArrayList<LatLng>();
		FindCallback<ParseObject> callBack = new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> arg0, ParseException arg1) {
                if (arg1 == null && arg0 != null) {
                	for (ParseObject obj :arg0){
                		ParseGeoPoint point = (ParseGeoPoint) obj.get("coordinates");
                		points.add(new LatLng (point.getLatitude(),point.getLongitude()));
                	}
                } else {
                    //TODO: check if this part needed
                }
            	synchronized (mutex) {
            		mutex.append("done");
        			mutex.notifyAll();
        		}
				
			}
		};
		DatabaseManager.queryByLocation("Location",center,radius/1000,"coordinates",callBack);
		synchronized (mutex) {
			if(!mutex.equals("done")){
				try {
					mutex.wait();
				} catch (InterruptedException e) {
				}
			}
		}
		final List<Location> loc =  new ArrayList<Location>();
		LocationListCallback list = new LocationListCallback() {
			
			@Override
			public void done(List<Location> ls) {
				for(Location l :ls){
					if(l.getLocationType().equals(Location.LocationTypes.Street)){
						loc.add(l);
					}
				}
			}
		};
		
		for(LatLng l:points){
			getLocation(l,list);
		}

		
		List<Location> returnMe = FilterToAllAbove(loc, 1, accessibilityThreshold); // '1' will be changed in the future
		
		for(Location l:returnMe){
			points.remove(l.getCoordinates());
		}
		
		points.remove(destination.getCoordinates());
		points.remove(source.getCoordinates());
		return points;
	}
	
	/**
	 * don't happen in background
	 * @param point
	 * @return
	 */
	public static Location getLocation(LatLng point){
		ArrayList<Review> pinned = new ArrayList<Review>();
		ArrayList<Review> notPinned = new ArrayList<Review>();
		Map<String, Object> values = new HashMap<String,Object>();
		values.put("location", new ParseGeoPoint(point.getLat(),point.getLng()));
		FindCallback<ParseObject> callBack = new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> arg0, ParseException arg1) {
                if (arg1 == null && arg0!=null) {
                	for (ParseObject obj :arg0){
                		if((int)obj.get("pined")==0){
                			//TODO: pinned.add(new Review(l, r, c, u));
                		}else{
                			//TODO: notPinned.add(new Review(l, r, c, u));
                		}
                	}
                }				
			}
		};
		
		DatabaseManager.queryByFields("Review", values, callBack);
		Location l = new Location(notPinned,pinned,point) {};
		return l;
	}
	
	/**
	 * this function will return a specific location, not happened in the background
	 * @param point
	 * @param type
	 * @param subtype
	 * @return
	 */
	public static Location getLocation(LatLng point, Location.LocationTypes type, Location.LocationSubTypes subtype){
		StringBuilder mutex = new StringBuilder();
		ArrayList<Review> reviews = new ArrayList<Review>();
		Map<String, Object> values = new HashMap<String,Object>();
		values.put("location", new ParseGeoPoint(point.getLat(),point.getLng()));
		FindCallback<ParseObject> callBackR = new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> arg0, ParseException arg1) {
                if (arg1 == null && arg0!=null) {
                	for (ParseObject obj :arg0){
                		//the location in the review doesn't have the reviews
                		reviews.add(new Review(new Location(point,type,subtype),obj.getInt("rating"),obj.getString("comment"),obj.getString("user")));
                	}
                }
            	synchronized (mutex) {
            		mutex.append("done");
        			mutex.notifyAll();
        		}
			}
		};
		
		DatabaseManager.queryByFields("Review", values, callBackR);
		synchronized (mutex) {
			if(!mutex.equals("done")){
				try {
					mutex.wait();
				} catch (InterruptedException e) {
				}
			}
		}
		//System.out.println("omg I am a stub? what does that mean!!!! what am I??? ahhhhh");
		return new Location(point, type, subtype,reviews);
	}
	
	/**
	 * return list of location that belong to the point. since the reviews belong to
	 * a LatLng point (as we save only the coordinates) the list of reviews will
	 * return to all the locations.
	 * @param point
	 * @param o
	 */
	public static void getLocation(LatLng point,LocationListCallback o){
		ArrayList<Review> reviews = new ArrayList<Review>();
		ArrayList<Location> ls = new ArrayList<Location>();
		Map<String, Object> valuesR = new HashMap<String,Object>();
		Map<String, Object> valuesL = new HashMap<String,Object>();
		valuesR.put("location", new ParseGeoPoint(point.getLat(),point.getLng()));
		valuesL.put("coordinates", new ParseGeoPoint(point.getLat(),point.getLng()));
		FindCallback<ParseObject> callBackL = new FindCallback<ParseObject>(){
			@Override
			public void done(List<ParseObject> arg0, ParseException arg1) {
				if(arg0!=null){
					for (ParseObject obj :arg0){
						ls.add(new Location(point,Location.stringToEnumTypes(obj.getString("type")),
								Location.stringToEnumSubTypes(obj.getString("subtype")),reviews));
	            	}
				}
				o.done(ls);
			}
		};
		FindCallback<ParseObject> callBackR = new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> arg0, ParseException arg1) {
                if (arg1 == null && arg0 != null) {
                	for (ParseObject obj :arg0){
                		//reviews.add(new Review(l, r, c, u)) will be complete after issue #124 will close
                	}
                	
                }	
                DatabaseManager.queryByFields("Location", valuesL, callBackL);
			}
		};
		DatabaseManager.queryByFields("Review", valuesR, callBackR);
	}
	
	/**
	 * Save the location in the DB happen in the background
	 * @param l
	 */
	public static void saveLocation (Location l,SaveCallback o){
		Map<String, Object> m = new HashMap<String,Object>();
		if(l.getLocationSubType()==null){
			m.put("subtype", Location.LocationSubTypes.Default.toString());
		}else{
			m.put("subtype", l.getLocationSubType().toString());
		}
		if(l.getLocationType()==null){
			m.put("subtype", Location.LocationTypes.Coordinate.toString());
		}else{
			m.put("type", l.getLocationType().toString());
		}
		m.put("coordinates", new ParseGeoPoint(l.getCoordinates().getLat(),l.getCoordinates().getLng()));
		checkLocationInDB(l,new GetCallback<ParseObject>() {

			@Override
			public void done(ParseObject arg0, ParseException arg1) {
				if(arg0==null){
					DatabaseManager.putValue("Location",m, new SaveCallback() {
						
						@Override
						public void done(ParseException arg0) {
							o.done(arg0);
							for(Review r :l.getReviews()){
								ReviewManager.updateReview(r);
							}
						}
					});
				}				
			}
		});
	}
	
	/**
	 * If the location is not in the DB save it
	 * else update it (include updating the reviews belong to this location)
	 * @param l
	 */
	public static void updateLocation(Location l) {
		// Note this should be a background operation -- alex
		GetCallback<ParseObject> p = new GetCallback<ParseObject>() {
			@Override
				public void done(ParseObject arg0, ParseException arg1) {
					Map<String, Object> m = new HashMap<String,Object>();
					m.put("coordinates", new ParseGeoPoint(l.getCoordinates().getLat(),l.getCoordinates().getLng()));
					if(arg0!=null){
						DatabaseManager.update("Location",arg0.getObjectId(),m);
						for(Review r :l.getReviews()){
							ReviewManager.updateReview(r);
						}
					}
					else{
						saveLocation(l,new SaveCallback() {
							
							@Override
							public void done(ParseException arg0) {
								// TODO Auto-generated method stub
								
							}
						});
					}
					//arg1.printStackTrace();
				}
			};
		checkLocationInDB(l,p);
		
	}
	
	/*
	 * @Author Kolikant
	 */
	public static List<Location> FilterToAllBellow(List<Location> totalLocation, int ReviewsTakenToAccount, int accessibilityLevel){
		return totalLocation.stream().
				filter(p ->  p.getRating(ReviewsTakenToAccount).getScore() < accessibilityLevel).
				collect(Collectors.toList());
	}
	
	/*
	 * @Author Kolikant
	 */
	public static List<Location> FilterToAllAbove(List<Location> totalLocation, int ReviewsTakenToAccount, int accessibilityLevel){
		return totalLocation.stream().
				filter(p ->  p.getRating(ReviewsTakenToAccount).getScore() >= accessibilityLevel).
				collect(Collectors.toList());
	}
	
	/**
	 * return the location into the call back (without their reviews)
	 * @param point
	 * @param c
	 * @param radius
	 */
	public static void getLocationsNearPoint(LatLng point, LocationListCallback c, double radius){
		ArrayList<Location> ls = new ArrayList<Location>();
		Map<String, Object> values = new HashMap<String,Object>();
		values.put("location", new ParseGeoPoint(point.getLat(),point.getLng()));
		FindCallback<ParseObject> callBackL = new FindCallback<ParseObject>(){
			@Override
			public void done(List<ParseObject> arg0, ParseException arg1) {
				for (ParseObject obj :arg0){
					ls.add(new Location(point,Location.stringToEnumTypes(obj.getString("type")),
							Location.stringToEnumSubTypes(obj.getString("subtype"))));
            	}
				c.done(ls);
			}
		};
		DatabaseManager.queryByLocation("Location",point,radius,"coordinates", callBackL);
	}

}
