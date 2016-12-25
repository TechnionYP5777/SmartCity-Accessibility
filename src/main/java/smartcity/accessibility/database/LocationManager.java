package smartcity.accessibility.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.persistence.internal.sessions.remote.SequencingFunctionCall.GetNextValue;
import org.parse4j.ParseException;
import org.parse4j.ParseGeoPoint;
import org.parse4j.ParseObject;
import org.parse4j.callback.FindCallback;

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.socialnetwork.BestReviews;

/**
 * @author assaflu
 *
 */
public class LocationManager {

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
	
	private static Location getLocation (ParseObject loc){
		return null;
		/*
		 * TODO: convert location parseObject to location
		 */
	}
	
	public static List<Location> getNonAccessibleLocationsInRadius(Location source, Location destination,
			Integer accessibilityThreshold) {
		// TODO Auto-generated method stub
		// TODO this method will return List of Locations the are in the
		// radius of the source and destination
		// and the are below the threshold
		
		double radius = distanceBtween(source.getCoordinates(),destination.getCoordinates());
		LatLng center = new LatLng((source.getCoordinates().getLat()+destination.getCoordinates().getLat())/2,
				(source.getCoordinates().getLng()+destination.getCoordinates().getLng())/2);
		//temporary implementation (read at the end of the function for more, but highly recommand to change to ArrayList<LatLng>
		ArrayList<LatLng> points = new ArrayList<LatLng>();
		
		FindCallback<ParseObject> callBack = new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> arg0, ParseException arg1) {
                if (arg1 == null) {
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
		/*
		 * consider changing the return value of this method to ArrayList<LatLng>
		 * I dont see any need for the upcoming part, of geting the location which is just
		 * 		reviews for a current location
		 * ***I don't see and field of threshold in the location class so right now it 
		 * returns all the location that answer on the radius query
		 */
		
		/*
		 * function not finished (unless you decide to change to ArrayList<LatLng>
		 * because Location and Review class are incomplete see getLocation function for more
		 */		
		return new ArrayList<Location>();
	}
	
	public static Location getLocation(LatLng point){
		return null;
		//TODO: return location according to LatLng point
		/*
		 * can't be complited until Review and Location class are complited - need to add pinned field to review
		 * need to add location create 
		 */
	}
	
	public static void saveLocation (Location l) throws ParseException{
		Map<String, Object> m = new HashMap<String,Object>();
		m.put("coordinates", new ParseGeoPoint(l.getCoordinates().getLat(),l.getCoordinates().getLng()));
		ParseObject p = DatabaseManager.putValue("Location",m); 
	}
	
	/*
	 * @Author Kolikant
	 */
	public static List<Location> FilterToAllBellow(List<Location> totalLocation, int ReviewsTakenToAccount, int accessibilityLevel){
		return totalLocation.stream().
				filter(p ->  new BestReviews(ReviewsTakenToAccount, p.getReviews()).getTotalRating() < accessibilityLevel).
				collect(Collectors.toList());
	}
	
	/*
	 * @Author Kolikant
	 */
	public static List<Location> FilterToAllAbove(List<Location> totalLocation, int ReviewsTakenToAccount, int accessibilityLevel){
		return totalLocation.stream().
				filter(p ->  new BestReviews(ReviewsTakenToAccount, p.getReviews()).getTotalRating() >= accessibilityLevel).
				collect(Collectors.toList());
	}

}
