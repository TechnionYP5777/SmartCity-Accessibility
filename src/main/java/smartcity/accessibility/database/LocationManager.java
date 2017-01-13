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

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.socialnetwork.Review;

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
	
	
	private static double distanceBtween(LatLng source,LatLng destination){ //return distance in meter
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
	
	/*
	 * the method returns all the latlng of the locations that are bellow the threshold
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
	
	public static void saveLocation (Location l) throws ParseException{
		Map<String, Object> m = new HashMap<String,Object>();
		m.put("coordinates", new ParseGeoPoint(l.getCoordinates().getLat(),l.getCoordinates().getLng()));
		ParseObject p = DatabaseManager.putValue("Location",m);
		//TODO: pin the pinned comment
	}
	
	public static void updateLocation(Location l) throws ParseException {
		// Note this should be a background operation -- alex
		Map<String, Object> m = new HashMap<String,Object>();
		m.put("coordinates", new ParseGeoPoint(l.getCoordinates().getLat(),l.getCoordinates().getLng()));
		String locationId = checkLocationInDB(l);
		if(locationId!=null){
			DatabaseManager.updateObj("Location",locationId,m);
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
