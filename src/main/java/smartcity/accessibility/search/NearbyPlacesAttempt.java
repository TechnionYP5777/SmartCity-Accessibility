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
import smartcity.accessibility.mapmanagement.JxMapsFunctionality.ExtendedMapView;
import smartcity.accessibility.mapmanagement.Location;

/**
 * @author Koral Chapnik
 */
public class NearbyPlacesAttempt extends MapView {
	Map map; 
	boolean mapIsReady;
	boolean onComplete;
	public NearbyPlacesAttempt(MapViewOptions mapOptions) {
		super(mapOptions);
		onComplete = mapIsReady = false;
	}

	public ArrayList<Location> findNearbyPlaces(Location initLocation, double radius, List<String> kindsOfLocations) {
		
        ArrayList<Location> $ = new ArrayList<Location>();
        setOnMapReadyHandler(new MapReadyHandler() {
            @Override
            public void onMapReady(MapStatus s) {
                // Check if the map is loaded correctly
                if (s == MapStatus.MAP_STATUS_OK)
					mapIsReady = true;
            }
        });
        while(!mapIsReady)
			System.out.println("waiting to map");
		Map map = getMap();
		assert(map != null);
		LatLng l = initLocation.getCoordinates();
		map.setCenter(l);
		map.setZoom(17.0);
		PlaceSearchRequest request = new PlaceSearchRequest(getMap());
		request.setLocation(map.getCenter());
		request.setRadius(radius);
		String[] types = kindsOfLocations.toArray((new String[kindsOfLocations.size()]));
		request.setTypes(types);
		getServices().getPlacesService().nearbySearch(request, new PlaceNearbySearchCallback(map) {
            @Override
            public void onComplete(PlaceResult[] rs, PlacesServiceStatus s, PlaceSearchPagination __) {
                if (s == PlacesServiceStatus.OK)
					for (int i = 0; i < rs.length; ++i) {
						PlaceResult result = rs[i];
						String name = result.getName();
						LatLng l = result.getGeometry() == null ? null : result.getGeometry().getLocation();
						Facility f = new Facility(l);
						f.setName(result.getName());
						$.add(f);
					}
                onComplete = true;
                return;
            }
		});
	
		while(!onComplete)
			System.out.println("waiting");
		
		this.onComplete = this.mapIsReady = false;
		
		return $;
	}
	
	/*
	 * Kolikant
	 */
	public static void yieldResults(String type, int radius, LatLng c){
		ArrayList<String> kindsOfLocations = new ArrayList<String>();
		kindsOfLocations.add(type);
		Location initLocation = new Facility(c);
		MapViewOptions options = new MapViewOptions();
		options.importPlaces();
		NearbyPlacesAttempt n = new NearbyPlacesAttempt(options);
		ArrayList<Location> places = n.findNearbyPlaces(initLocation, radius, kindsOfLocations);
		MapView mapView = JxMapsFunctionality.getMapView();
		JxMapsFunctionality.waitForMapReady((ExtendedMapView) mapView);

		for (Location l : places)
			JxMapsFunctionality.putMarkerNoJump((ExtendedMapView) mapView, l.getCoordinates(), l.getName());
	}
}