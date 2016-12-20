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
				map.setCenter(new LatLng(0, -180));
				map.setZoom(3.0);
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
		List<LatLng> shapeLatlng = new ArrayList<LatLng>();
		for(int i=0;i<shapePointsArr.length-1;i+=2)
			shapeLatlng.add(new LatLng(shapePointsArr[i], shapePointsArr[i + 1]));
		final JxMapsConvertor convertor = new JxMapsConvertor((LatLng[]) shapeLatlng.toArray());

		JFrame frame = new JFrame("displayRoute");

		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(convertor, BorderLayout.CENTER);
		frame.setSize(700, 500);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
