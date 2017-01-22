package smartcity.accessibility.search;

import java.util.ArrayList;
import java.util.List;

import com.teamdev.jxmaps.LatLng;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.PlaceNearbySearchCallback;
import com.teamdev.jxmaps.PlaceResult;
import com.teamdev.jxmaps.PlaceSearchPagination;
import com.teamdev.jxmaps.PlaceSearchRequest;
import com.teamdev.jxmaps.PlacesService;
import com.teamdev.jxmaps.PlacesServiceStatus;
import com.teamdev.jxmaps.swing.MapView;

import smartcity.accessibility.database.LocationListCallback;
import smartcity.accessibility.mapmanagement.Location;

/**
 * @author Koral Chapnik
 */
public class NearbyPlacesSearch {

	public static void findNearbyPlaces(MapView v, Location initLocation, double radius, List<String> kindsOfLocations, LocationListCallback c) {
		Map map = v.getMap();
		LatLng l = initLocation.getCoordinates();
		PlaceSearchRequest request = new PlaceSearchRequest();
		request.setLocation(l);
		request.setRadius(radius);
		request.setTypes(kindsOfLocations.toArray((new String[kindsOfLocations.size()])));
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