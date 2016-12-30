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

import smartcity.accessibility.mapmanagement.Facility;
import smartcity.accessibility.mapmanagement.Location;

/**
 * @author Koral Chapnik
 */
public class NearbyPlacesAttempt {
	
	public static ArrayList<Location> findNearbyPlaces(MapView mapView, Location initLocation, double radius, List<String> kindsOfLocations) {
		ArrayList<Location> res = new ArrayList<Location>();
		Map map = mapView.getMap();
		PlaceSearchRequest request = new PlaceSearchRequest(mapView.getMap());
		request.setLocation(initLocation.getCoordinates());
		request.setRadius(radius);
		String[] types = new String[kindsOfLocations.size()];
		types = kindsOfLocations.toArray(types); // check if it works
		request.setTypes(types);
		mapView.getServices().getPlacesService().nearbySearch(request, new PlaceNearbySearchCallback(map) {
            @Override
            public void onComplete(PlaceResult[] results, PlacesServiceStatus status, PlaceSearchPagination pagination) {
                // Checking operation status
                if (status == PlacesServiceStatus.OK) {
                    for (int i=0; i< results.length; ++i) {
                        PlaceResult result = results[i];
                        LatLng l = result.getGeometry() != null ? result.getGeometry().getLocation() : null;
                        res.add(new Facility(l));
                    }
                }
            }
		});
		return res;
	}
}