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

	//return the id of an object if it is in the db null otherwise
	private static String checkLocationInDB(Location l){
		//TODO: need to implement
		return null;
	}
	
	/**
	 * calculate the distance btween the source point and destination point in meters
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
	 * 
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
	 * Save the locaion in the DB happen in the background
	 * @param l
	 */
	public static void saveLocation (Location l){
		Map<String, Object> m = new HashMap<String,Object>();
		m.put("subtype", l.getLocationSubType()); //check if it is possible to save such data
		//check about the location type
		m.put("coordinates", new ParseGeoPoint(l.getCoordinates().getLat(),l.getCoordinates().getLng()));
		DatabaseManager.putValue("Location",m, new SaveCallback() {
			
			@Override
			public void done(ParseException arg0) {
				// does nothing
				
			}
		});
		for(Review r :l.getPinnedReviews()){
			ReviewManager.updateReview(r);
		}
		
	}
	
	public static void updateLocation(Location l) {
		// Note this should be a background operation -- alex
		Map<String, Object> m = new HashMap<String,Object>();
		m.put("coordinates", new ParseGeoPoint(l.getCoordinates().getLat(),l.getCoordinates().getLng()));
		String locationId = checkLocationInDB(l);
		if(locationId!=null){
			DatabaseManager.update("Location",locationId,m);
		}
		else{ //the location is not in the DB
			saveLocation(l);
		}
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
