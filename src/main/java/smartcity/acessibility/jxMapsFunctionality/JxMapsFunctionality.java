package smartcity.acessibility.jxMapsFunctionality;

import java.awt.BorderLayout;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.teamdev.jxmaps.InfoWindow;
import com.teamdev.jxmaps.LatLng;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.MapReadyHandler;
import com.teamdev.jxmaps.MapStatus;
import com.teamdev.jxmaps.MapViewOptions;
import com.teamdev.jxmaps.Marker;
import com.teamdev.jxmaps.swing.MapView;

/*
 * Author Kolikant
 */

public abstract class JxMapsFunctionality {
	
	public static MapView mv;
	
	public static class helper2 extends MapView{
		final AtomicInteger mapReady = new AtomicInteger();
		helper2(MapViewOptions __){
			mapReady.set(0);
			setOnMapReadyHandler(new MapReadyHandler() {
				@Override
				public void onMapReady(MapStatus arg0) {
					mapReady.set(1);
				}
			});
		}
		
	}
	
	public static void waitForMapReady(helper2 h2){
		while(h2.mapReady.get()!=1)
			;
		return;
	}
	
	public static MapView getMapView(){
		if (mv != null)
			return mv;
		MapViewOptions options = new MapViewOptions();
		options.importPlaces();
		return mv = new helper2(options);
	}
	
	public static MapView getMapView(MapViewOptions o){
	        o.importPlaces();
	        return mv = new helper2(o);
	}
	
	public static void putMarker(helper2 mv, LatLng l, String name) {
		waitForMapReady(mv);
		Map m =mv.getMap();
		Marker m1 = new Marker(m);
		m1.setPosition(l);
		m.setCenter(l);
		final InfoWindow window = new InfoWindow(m);
		window.setContent(name);
		window.open(m, m1);
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
}