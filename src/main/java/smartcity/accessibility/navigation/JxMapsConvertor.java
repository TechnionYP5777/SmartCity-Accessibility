package smartcity.accessibility.navigation;

import com.teamdev.jxmaps.ControlPosition;
import com.teamdev.jxmaps.LatLng;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.MapOptions;
import com.teamdev.jxmaps.MapTypeControlOptions;
import com.teamdev.jxmaps.Polyline;
import com.teamdev.jxmaps.PolylineOptions;
import com.teamdev.jxmaps.swing.MapView;

import javax.swing.*;
import java.awt.*;

/**
 * This class convert the route from mapquest to JxMap
 * 
 * @author yael
 *
 */
public class JxMapsConvertor{

	public static void displayRoute(MapView mapView, LatLng[] shapeLatlng) {
		final Map map = mapView.getMap();
		MapOptions mapOptions = new MapOptions();
		MapTypeControlOptions controlOptions = new MapTypeControlOptions();
		controlOptions.setPosition(ControlPosition.TOP_RIGHT);
		mapOptions.setMapTypeControlOptions(controlOptions);
		map.setOptions(mapOptions);
		map.setCenter(shapeLatlng[0]);
		map.setZoom(15.0);
		Polyline polyline = new Polyline(map);
		polyline.setPath(shapeLatlng);
		PolylineOptions options = new PolylineOptions();
		options.setGeodesic(true);
		options.setStrokeColor("#FF0000");
		options.setStrokeWeight(2.0);
		polyline.setOptions(options);
		
		JFrame frame = new JFrame("displayRoute");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(mapView, BorderLayout.CENTER);
		frame.setSize(700, 500);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
