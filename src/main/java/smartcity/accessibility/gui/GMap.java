package smartcity.accessibility.gui;

import javafx.*;
import smartcity.accessibility.database.DatabaseManager;

import java.awt.BorderLayout;
import java.awt.Button;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.teamdev.jxmaps.Geocoder;
import com.teamdev.jxmaps.GeocoderCallback;
import com.teamdev.jxmaps.GeocoderRequest;
import com.teamdev.jxmaps.GeocoderResult;
import com.teamdev.jxmaps.GeocoderStatus;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.MapComponentType;
import com.teamdev.jxmaps.MapReadyHandler;
import com.teamdev.jxmaps.MapServices;
import com.teamdev.jxmaps.MapStatus;
import com.teamdev.jxmaps.MapViewOptions;
import com.teamdev.jxmaps.Marker;
import com.teamdev.jxmaps.PlaceNearbySearchCallback;
import com.teamdev.jxmaps.PlaceResult;
import com.teamdev.jxmaps.PlaceSearchPagination;
import com.teamdev.jxmaps.PlaceSearchRequest;
import com.teamdev.jxmaps.swing.*;
import com.teamdev.jxmaps.PlacesService;
import com.teamdev.jxmaps.PlacesServiceStatus;

/**
 * @author KaplanAlexander This is the class we will use to initiate the main
 *         menu of the project (a map) and generally intialize the project
 *         (connect to database and such);
 */
public class GMap extends MapView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5110116311288634986L;
	private Map map;

	public GMap(MapViewOptions options) {
		super(options);
		setOnMapReadyHandler(new MapReadyHandler() {
			@Override
			public void onMapReady(MapStatus s) {
				System.out.println(s.name());
				if (s != MapStatus.MAP_STATUS_OK)
					return;
				map = getMap();
				map.setZoom(17.0);
				GeocoderRequest request = new GeocoderRequest();
				request.setAddress("Eliezer 10, Haifa, Israel");
				Geocoder g = getServices().getGeocoder();
				g.geocode(request, new GeocoderCallback(map) {
					@Override
					public void onComplete(GeocoderResult[] rs, GeocoderStatus s) {
						System.out.println(s.name());
						if (s != GeocoderStatus.OK)
							return;
						map.setCenter(rs[0].getGeometry().getLocation());
						Marker marker = new Marker(map);
						marker.setPosition(rs[0].getGeometry().getLocation());
					}
				});
				
				MapServices ms = getServices();
				PlacesService ps = ms.getPlacesService();
				ps.nearbySearch(new PlaceSearchRequest(), new PlaceNearbySearchCallback(map) {
					
					@Override
					public void onComplete(PlaceResult[] arg0, PlacesServiceStatus arg1, PlaceSearchPagination arg2) {
						for (PlaceResult pr :arg0){
							System.out.println(pr.getName());
						}
						
					}
				});
				
			}
		});
	}

	public static void main(String[] args) {
		DatabaseManager.initialize();

		MapViewOptions options = new MapViewOptions(MapComponentType.HEAVYWEIGHT);
		options.importPlaces();
		final GMap mapView = new GMap(options);
		

		JFrame frame = new JFrame("JxMaps - Hello, World!");

		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(mapView, BorderLayout.CENTER);
		frame.setSize(700, 500);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		Button b = new Button("Hello");
		b.setSize(20, 30);
		b.setEnabled(true);
		b.setVisible(true);
		frame.add(b, BorderLayout.PAGE_END);

	}

}
