package smartcity.acessibility.jxMapsFunctionality;

import java.awt.BorderLayout;

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
	
	public static MapView mv =null;
	
	public static class helper2 extends MapView{
		helper2(MapViewOptions __){
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
		return mv = new helper2(options);
	}
	
	public static MapView getMapView(MapViewOptions o){
	        o.importPlaces();
	        return mv = new helper2(o);
	}
	
	public static void putMarker(Map m, LatLng l, String name) {
		Marker m1 = new Marker(m);
		m1.setPosition(l);
		m.setCenter(l);
		final InfoWindow window = new InfoWindow(m);
		window.setContent(name);
		window.open(m, m1);
	}

	public static void openFrame(MapView mapview, String string, double zoom) {
		JFrame frame = new JFrame(string);
		mapview.getMap().setZoom(zoom);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(mapview, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
		
	}
}
