package smartcity.accessibility.search;

import java.util.ArrayList;
import java.util.List;

import com.teamdev.jxmaps.LatLng;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.PlaceNearbySearchCallback;
import com.teamdev.jxmaps.PlaceResult;
import com.teamdev.jxmaps.PlaceSearchPagination;
import com.teamdev.jxmaps.PlaceSearchRequest;
import com.teamdev.jxmaps.PlacesServiceStatus;
import com.teamdev.jxmaps.swing.MapView;

import smartcity.accessibility.database.LocationListCallback;
import smartcity.accessibility.mapmanagement.Location;

/**
 * @author Koral Chapnik
 */
public class NearbyPlacesSearch {
	
	/**
	 * This method returns the nearby places according to the following parameters:
	 * @param v - the mapView
	 * @param initLocation - the location which will be the center of our search
	 * @param radius - the radius in meters we want to search by
	 * @param kindsOfLocations - which kinds of nearby places we want to search ? 
	 * 		  for example cafe, restaurant, pub etc
	 * @param c - a callback which gets a list of nearby places and does a desired
	 * 			  operation with it in the "done" method
	 */
	public static void findNearbyPlaces(MapView v, Location initLocation, double radius, 
			List<String> kindsOfLocations, LocationListCallback c) {
		Map map = v.getMap();
		LatLng l = initLocation.getCoordinates();
		PlaceSearchRequest request = new PlaceSearchRequest();
		request.setLocation(l);
		request.setRadius(radius);
		request.setTypes(kindsOfLocations.toArray(new String[kindsOfLocations.size()]));
		v.getServices().getPlacesService().nearbySearch(request, new PlaceNearbySearchCallback(map) {
			@Override
			public void onComplete(PlaceResult[] rs, PlacesServiceStatus s, PlaceSearchPagination __) {
				ArrayList<Location> $ = new ArrayList<Location>();
				if (s == PlacesServiceStatus.OK)
					for (int i = 0; i < rs.length; ++i) {
						PlaceResult result = rs[i];
						LatLng l = result.getGeometry() == null ? null : result.getGeometry().getLocation();
						Location f = new Location(l);
						f.setName(result.getName());
						$.add(f);
					}
				System.out.println("called to callback done");
				c.done($);
			}
		});
	
	}
	
}