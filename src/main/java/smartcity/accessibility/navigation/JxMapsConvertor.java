package smartcity.accessibility.navigation;

import com.teamdev.jxmaps.ControlPosition;
import com.teamdev.jxmaps.LatLng;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.MapOptions;
import com.teamdev.jxmaps.MapReadyHandler;
import com.teamdev.jxmaps.MapStatus;
import com.teamdev.jxmaps.MapTypeControlOptions;
import com.teamdev.jxmaps.Polyline;
import com.teamdev.jxmaps.PolylineOptions;
import com.teamdev.jxmaps.swing.MapView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class JxMapsConvertor extends MapView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JxMapsConvertor(LatLng[] shapePoints) {
		setOnMapReadyHandler(new MapReadyHandler() {
			@Override
			public void onMapReady(MapStatus s) {
				if (s != MapStatus.MAP_STATUS_OK)
					return;
				final Map map = getMap();
				MapOptions mapOptions = new MapOptions();
				MapTypeControlOptions controlOptions = new MapTypeControlOptions();
				controlOptions.setPosition(ControlPosition.TOP_RIGHT);
				mapOptions.setMapTypeControlOptions(controlOptions);
				map.setOptions(mapOptions);
				map.setCenter(new LatLng(31.768762, 34.632052));
				map.setZoom(9.0);
				Polyline polyline = new Polyline(map);
				polyline.setPath(shapePoints);
				PolylineOptions options = new PolylineOptions();
				options.setGeodesic(true);
				options.setStrokeColor("#FF0000");
				options.setStrokeOpacity(1.0);
				options.setStrokeWeight(2.0);
				polyline.setOptions(options);
			}
		});
	}

	public static void displayRoute(Double[] shapePointsArr) {
		LatLng[] shapeLatlng = new LatLng[shapePointsArr.length / 2];
		int k = 0;
		for (int i = 0; i < shapePointsArr.length - 1; i += 2) {
			shapeLatlng[k] = (new LatLng(shapePointsArr[i], shapePointsArr[i + 1]));
			++k;
		}
		final JxMapsConvertor convertor = new JxMapsConvertor(shapeLatlng);

		JFrame frame = new JFrame("displayRoute");

		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(convertor, BorderLayout.CENTER);
		frame.setSize(700, 500);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
