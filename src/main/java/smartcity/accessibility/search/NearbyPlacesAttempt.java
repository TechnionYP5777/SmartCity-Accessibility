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
import smartcity.accessibility.mapmanagement.JxMapsFunctionality;
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

	public ArrayList<Location> findNearbyPlaces(Location initLocation, double radius, List<String> kindsOfLocations) {
		
        ArrayList<Location> res = new ArrayList<Location>();
        setOnMapReadyHandler(new MapReadyHandler() {
            @Override
            public void onMapReady(MapStatus status) {
                // Check if the map is loaded correctly
                if (status == MapStatus.MAP_STATUS_OK) {
                    mapIsReady = true;
                }
            }
        });
        while(!mapIsReady){
        	 System.out.println("waiting to map");
        }
		Map map = getMap();
		assert(map != null);
		LatLng l = initLocation.getCoordinates();
		map.setCenter(l);
		map.setZoom(17.0);
		PlaceSearchRequest request = new PlaceSearchRequest(getMap());
		request.setLocation(map.getCenter());
		request.setRadius(radius);
		String[] types = new String[kindsOfLocations.size()];
		types = kindsOfLocations.toArray(types);
		request.setTypes(types);
		getServices().getPlacesService().nearbySearch(request, new PlaceNearbySearchCallback(map) {
            @Override
            public void onComplete(PlaceResult[] results, PlacesServiceStatus status, PlaceSearchPagination pagination) {
                if (status == PlacesServiceStatus.OK) {
                    for (int i=0; i< results.length; ++i) {
                        PlaceResult result = results[i];
                        String name = result.getName();
                        LatLng l = result.getGeometry() != null ? result.getGeometry().getLocation() : null;
                        Facility f = new Facility(l);
                        f.setName(result.getName());
                        res.add(f);
                    }
                }
                onComplete = true;
                return;
            }
		});
	
		while(!onComplete){
          System.out.println("waiting");
		}
		
		this.mapIsReady = false;
		this.onComplete = false;
		
		return res;
	}
}