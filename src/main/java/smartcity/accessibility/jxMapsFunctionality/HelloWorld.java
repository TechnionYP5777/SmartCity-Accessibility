package smartcity.accessibility.jxMapsFunctionality;

import com.teamdev.jxmaps.GeocoderRequest;
import com.teamdev.jxmaps.Geocoder;
import com.teamdev.jxmaps.GeocoderCallback;
import com.teamdev.jxmaps.Marker;
import com.teamdev.jxmaps.MapViewOptions;
import com.teamdev.jxmaps.InfoWindow;
import com.teamdev.jxmaps.GeocoderStatus;
import com.teamdev.jxmaps.GeocoderResult;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.MapReadyHandler;
import com.teamdev.jxmaps.MapStatus;
import com.teamdev.jxmaps.swing.MapView;

import javax.swing.*;
import java.awt.*;

public class HelloWorld extends MapView {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HelloWorld(MapViewOptions options) {
        super(options);
        setOnMapReadyHandler(new MapReadyHandler() {
            @SuppressWarnings("deprecation")
			@Override
            public void onMapReady(MapStatus s) {
                if (s != MapStatus.MAP_STATUS_OK)
					return;
				final Map map = getMap();
				map.setZoom(17.0);
				GeocoderRequest request = new GeocoderRequest(map);
				request.setAddress("Tel-Aviv, Weizmann St, 30");
				Geocoder g = getServices().getGeocoder();
				g.geocode(request, new GeocoderCallback(map) {
					@Override
					public void onComplete(GeocoderResult[] rs, GeocoderStatus s) {
						if (s != GeocoderStatus.OK)
							return;
						map.setCenter(rs[0].getGeometry().getLocation());
						Marker marker = new Marker(map);
						marker.setPosition(rs[0].getGeometry().getLocation());
					}
				});
				GeocoderRequest request2 = new GeocoderRequest(map);
				request2.setAddress("Tel-Aviv, Weizmann St, 1");
				Geocoder g2 = getServices().getGeocoder();
				g2.geocode(request2, new GeocoderCallback(map) {
					@Override
					public void onComplete(GeocoderResult[] rs, GeocoderStatus s) {
						if (s != GeocoderStatus.OK)
							return;
						map.setCenter(rs[0].getGeometry().getLocation());
						Marker marker = new Marker(map);
						marker.setPosition(rs[0].getGeometry().getLocation());
						final InfoWindow window = new InfoWindow(map);
						window.setContent("Hello, World!");
						window.open(map, marker);
					}
				});
            }
        });
    }

    public static void main(String[] args) {

        MapViewOptions options = new MapViewOptions();
        options.importPlaces();
        final HelloWorld mapView = new HelloWorld(options);

        JFrame frame = new JFrame("JxMaps - Hello, World!");

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(mapView, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}