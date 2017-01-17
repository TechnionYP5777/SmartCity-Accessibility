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
import smartcity.accessibility.socialnetwork.User;
import smartcity.accessibility.socialnetwork.UserImpl;

/**
 * @author assaflu
 *
 */
public class LocationManager {

	/**
	 * If the location saved it to the callback function
	 * If it's not saved notify the callback function
	 * @param l
	 * @param o
	 */
	private static void checkLocationInDB(Location l,GetCallback<ParseObject> o){
		Map<String, Object> m = new HashMap<String,Object>();
		m.put("coordinates", new ParseGeoPoint(l.getCoordinates().getLat(),l.getCoordinates().getLng()));
		m.put("subtype", l.getLocationSubType());
		//check about the location type issue #57
		GetCallback<ParseObject> hiddenCallBack = new GetCallback<ParseObject>() {
			@Override
			public void done(ParseObject arg0, ParseException arg1) {
				if(arg1==null){
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
				
			}
		};
		DatabaseManager.queryByLocation("Location",center,radius/1000,callBack);
		List<Location> loc =  new ArrayList<Location>();
		for(LatLng l:points){
			loc.add(getLocation(l));
		}
		
		loc = FilterToAllAbove(loc, 1, accessibilityThreshold); // '1' will be changed in the future
		
		for(Location l:loc){
			points.remove(l.getCoordinates());
		}
		
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
                if (arg1 == null) {
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
	 * return list of location that belong to the point. since the reviews belong to
	 * a LatLng point (as we save only the coordinates) the list of reviews will
	 * return to all the locations.
	 * @param point
	 * @param o
	 */
	public static void getLocation(LatLng point,LocationListCallback o){
		ArrayList<Review> reviews = new ArrayList<Review>();
		ArrayList<Location> ls = new ArrayList<Location>();
		Map<String, Object> values = new HashMap<String,Object>();
		values.put("location", new ParseGeoPoint(point.getLat(),point.getLng()));
		FindCallback<ParseObject> callBackL = new FindCallback<ParseObject>(){
			@Override
			public void done(List<ParseObject> arg0, ParseException arg1) {
				for (ParseObject obj :arg0){
					ls.add(new Location(point,Location.stringToEnumTypes(obj.getString("type")),
							Location.stringToEnumSubTypes(obj.getString("subtype")),reviews));
            	}
				o.done(ls);
			}
		};
		FindCallback<ParseObject> callBackR = new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> arg0, ParseException arg1) {
                if (arg1 == null) {
                	for (ParseObject obj :arg0){
                		//reviews.add(new Review(l, r, c, u)) will be complete after issue #124 will close
                	}
                	DatabaseManager.queryByFields("Location", values, callBackL);
                }				
			}
		};
		DatabaseManager.queryByFields("Review", values, callBackR);
	}
	
	/**
	 * Save the location in the DB happen in the background
	 * @param l
	 */
	public static void saveLocation (Location l){
		Map<String, Object> m = new HashMap<String,Object>();
		m.put("subtype", l.getLocationSubType().toString());
		m.put("type", l.getLocationType().toString());
		m.put("coordinates", new ParseGeoPoint(l.getCoordinates().getLat(),l.getCoordinates().getLng()));
		DatabaseManager.putValue("Location",m, new SaveCallback() {
			
			@Override
			public void done(ParseException arg0) {
				// does nothing
				
			}
		});
		for(Review r :l.getReviews()){
			ReviewManager.updateReview(r);
		}
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
						saveLocation(l);
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
	
	public static void getLocationsNearPoint(LatLng __, LocationListCallback c){
		// call c.done with the result --alex
	}

}
