package smartcity.accessibility.search;

import java.util.ArrayList;
import java.util.List;

import com.teamdev.jxmaps.LatLng;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.MapServices;
import com.teamdev.jxmaps.MapViewOptions;
import com.teamdev.jxmaps.PlaceNearbySearchCallback;
import com.teamdev.jxmaps.PlaceResult;
import com.teamdev.jxmaps.PlaceSearchPagination;
import com.teamdev.jxmaps.PlaceSearchRequest;
import com.teamdev.jxmaps.PlacesService;
import com.teamdev.jxmaps.PlacesServiceStatus;
import com.teamdev.jxmaps.swing.MapView;

import javassist.tools.Callback;
import smartcity.accessibility.database.LocationListCallback;
import smartcity.accessibility.mapmanagement.JxMapsFunctionality;
import smartcity.accessibility.mapmanagement.JxMapsFunctionality.ExtendedMapView;
import smartcity.accessibility.mapmanagement.Location;

/**
 * @author Koral Chapnik
 */
public class NearbyPlacesSearch {

	public static void findNearbyPlaces(MapView mapView, Location initLocation, double radius, List<String> kindsOfLocations, LocationListCallback c) {
		Map map = mapView.getMap();
		LatLng l = initLocation.getCoordinates();
		PlaceSearchRequest request = new PlaceSearchRequest();
		request.setLocation(l);
		request.setRadius(radius);
		String[] types = kindsOfLocations.toArray((new String[kindsOfLocations.size()]));
		request.setTypes(types);
		MapServices ms = mapView.getServices();
		PlacesService ps = ms.getPlacesService();
		System.out.println("doing the nearby search");
		ps.nearbySearch(request, new PlaceNearbySearchCallback(map) {
            @Override
            public void onComplete(PlaceResult[] rs, PlacesServiceStatus s, PlaceSearchPagination __) {
            	System.out.println("arrived to OnComplete");
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
	
	/*
	 * Kolikant
	 */
	public static void displayResults(String type, int radius, LatLng c, MapView mapView){
//		ArrayList<String> kindsOfLocations = new ArrayList<String>();
//		kindsOfLocations.add(type);
//		Location initLocation = new Location(c);
//		MapViewOptions options = new MapViewOptions();
//		options.importPlaces();
//		NearbyPlacesAttempt n = new NearbyPlacesAttempt();
//		ArrayList<Location> places = n.findNearbyPlaces(mapView, initLocation, radius, kindsOfLocations);
//		JxMapsFunctionality.waitForMapReady((ExtendedMapView) mapView);
//
//		for (Location l : places)
//			JxMapsFunctionality.putExtendedMarker((ExtendedMapView) mapView, l, l.getName());
	}
	
}