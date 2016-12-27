package smartcity.accessibility.mapmanagement;

import java.awt.BorderLayout;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.teamdev.jxmaps.Geocoder;
import com.teamdev.jxmaps.GeocoderCallback;
import com.teamdev.jxmaps.GeocoderRequest;
import com.teamdev.jxmaps.GeocoderResult;
import com.teamdev.jxmaps.GeocoderStatus;
import com.teamdev.jxmaps.InfoWindow;
import com.teamdev.jxmaps.LatLng;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.MapReadyHandler;
import com.teamdev.jxmaps.MapStatus;
import com.teamdev.jxmaps.MapViewOptions;
import com.teamdev.jxmaps.Marker;
import com.teamdev.jxmaps.swing.MapView;

import smartcity.accessibility.mapmanagement.JxMapsFunctionality.waitableMap;

/*
 * Author Kolikant
 */

public abstract class JxMapsFunctionality {
	
	public static MapView mv;
	
	public static class waitableMap extends MapView{
		protected waitableMap(MapViewOptions __){
			setOnMapReadyHandler(new MapReadyHandler() {
				@Override
				public void onMapReady(MapStatus arg0) {
				}
			});
		}
		
	}
	
	
	public static MapView getMapView(){
		if (mv != null)
			return mv;
		MapViewOptions options = new MapViewOptions();
		options.importPlaces();
		return mv = new waitableMap(options);
	}
	
	public static void DestroyMapView(){
		mv.dispose();
		mv = null;
	}
	
	public static MapView getMapView(MapViewOptions o){
	        o.importPlaces();
	        return mv = new waitableMap(o);
	}
	
	public static void putMarker(waitableMap mv, LatLng l, String name) {
		waitForMapReady(mv);
		Map m =mv.getMap();
		Marker m1 = new Marker(m);
		m1.setPosition(l);
		m.setCenter(l);
		final InfoWindow window = new InfoWindow(m);
		window.setContent(name);
		window.open(m, m1);
	}

	public static void waitForMapReady(waitableMap mv) {
		mv.waitReady();
	}

	public static void openFrame(MapView v, String s, double zoom) {
		JFrame frame = new JFrame(s);
		v.getMap().setZoom(zoom);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(v, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);	
	}

	public static void initMapLocation(MapView mapView) {
		Map map = mapView.getMap();
		map.setZoom(17.0);
		GeocoderRequest request = new GeocoderRequest();
		request.setAddress("Eliezer 10, Haifa, Israel");
		Geocoder g = mapView.getServices().getGeocoder();
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
		
	}
}