package smartcity.accessibility.jxMapsFunctionality;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

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

import smartcity.accessibility.mapmanagement.JxMapsFunctionality;
import smartcity.accessibility.mapmanagement.JxMapsFunctionality.extendedMapView;

public class placeSearchAttempt extends extendedMapView{
	
	/**
	 * Kolikant
	 */
	private static final long serialVersionUID = 1L;

	public placeSearchAttempt(MapViewOptions options) {
		super(options);
		placeSearchAttempt thisis = this;
        setOnMapReadyHandler(new MapReadyHandler() {
            @SuppressWarnings("deprecation")
			@Override
            public void onMapReady(MapStatus __) {
            	 final Map map = getMap();
                 // Creating places search request
                 final PlaceSearchRequest request = new PlaceSearchRequest(map);
                 // Setting start point for places search
                 map.setCenter(new LatLng(32.794623, 35.019539));
                 map.setZoom(17.0);
                 request.setLocation(map.getCenter());
                 // Setting radius for places search
                 request.setRadius(2);
                 
                 getServices().getPlacesService().nearbySearch(request, new PlaceNearbySearchCallback(map) {
					@Override
					public void onComplete(PlaceResult[] arg0, PlacesServiceStatus arg1, PlaceSearchPagination arg2) {
						 if (arg1 == PlacesServiceStatus.OK)
							for (PlaceResult p : arg0)
								JxMapsFunctionality.putMarker(thisis, p.getGeometry().getLocation(), "banana");
					}
                 });
            }
        });
	}
	
	 public static void main(String[] args) {

	        MapViewOptions options = new MapViewOptions();
	        options.importPlaces();
	        final placeSearchAttempt mapView = new placeSearchAttempt(options);

	        JFrame frame = new JFrame("JxMaps - Hello, World!");

	        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	        frame.add(mapView, BorderLayout.CENTER);
	        frame.setSize(700, 500);
	        frame.setLocationRelativeTo(null);
	        frame.setVisible(true);
	 }
}
