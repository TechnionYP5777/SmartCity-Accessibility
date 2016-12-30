package smartcity.accessibility.search;

import java.util.ArrayList;
import java.util.List;

import com.teamdev.jxmaps.LatLng;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.MapReadyHandler;
import com.teamdev.jxmaps.MapStatus;
import com.teamdev.jxmaps.MapViewOptions;
import com.teamdev.jxmaps.PlaceNearbySearchCallback;
import com.teamdev.jxmaps.PlaceResult;
import com.teamdev.jxmaps.PlaceSearchPagination;
import com.teamdev.jxmaps.PlaceSearchRequest;
import com.teamdev.jxmaps.PlacesServiceStatus;
import com.teamdev.jxmaps.swing.MapView;

import smartcity.accessibility.mapmanagement.Facility;
import smartcity.accessibility.mapmanagement.JxMapsFunctionality.extendedMapView;
import smartcity.accessibility.mapmanagement.Location;

/**
 * @author Koral Chapnik
 */
public class NearbyPlacesAttempt extends MapView {
	Map map; 
	boolean mapIsReady;
	boolean onComplete;
	protected NearbyPlacesAttempt(MapViewOptions mapOptions) {
		super(mapOptions);
		mapIsReady = false;
		onComplete = false;
	}

	public ArrayList<Location> findNearbyPlaces(MapView mapView, Location initLocation, double radius, List<String> kindsOfLocations) {
		
        ArrayList<Location> res = new ArrayList<Location>();
        // Setting of a ready handler to MapView object. onMapReady will be called when map initialization is done and
        // the map object is ready to use. Current implementation of onMapReady customizes the map object.
        setOnMapReadyHandler(new MapReadyHandler() {
            @Override
            public void onMapReady(MapStatus status) {
                // Check if the map is loaded correctly
                if (status == MapStatus.MAP_STATUS_OK) {
                	System.out.println("map is ready");
                    mapIsReady = true;
                }
            }
        });
        while(!mapIsReady){
        	System.out.println("mappppp");
        }
		Map map = getMap();
		assert(map != null);
		System.out.println("got a map");
		LatLng l = initLocation.getCoordinates();
		map.setCenter(l);
		map.setZoom(17.0);
		PlaceSearchRequest request = new PlaceSearchRequest(mapView.getMap());
		request.setLocation(map.getCenter());
		request.setRadius(radius);
		String[] types = new String[kindsOfLocations.size()];
		types = kindsOfLocations.toArray(types); // check if it works
		System.out.println("kinds are " + types[0]);
		request.setTypes(types);
		getServices().getPlacesService().nearbySearch(request, new PlaceNearbySearchCallback(map) {
            @Override
            public void onComplete(PlaceResult[] results, PlacesServiceStatus status, PlaceSearchPagination pagination) {
                // Checking operation status
            	System.out.println("got to onComplete");
                if (status == PlacesServiceStatus.OK) {
                    for (int i=0; i< results.length; ++i) {
                    	System.out.println("adding to result");
                        PlaceResult result = results[i];
                        LatLng l = result.getGeometry() != null ? result.getGeometry().getLocation() : null;
                        res.add(new Facility(l));
                    }
                }
                onComplete = true;
                return;
            }
		});
	
		while(!onComplete){
         
			System.out.println("waiting to OnComplete");
		}
		
		this.mapIsReady = false;
		this.onComplete = false;
		
		return res;
	}
}