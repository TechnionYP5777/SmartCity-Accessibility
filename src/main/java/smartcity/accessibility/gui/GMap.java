package smartcity.accessibility.gui;


import com.teamdev.jxmaps.Geocoder;
import com.teamdev.jxmaps.GeocoderCallback;
import com.teamdev.jxmaps.GeocoderRequest;
import com.teamdev.jxmaps.GeocoderResult;
import com.teamdev.jxmaps.GeocoderStatus;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.MapReadyHandler;
import com.teamdev.jxmaps.MapStatus;
import com.teamdev.jxmaps.MapViewOptions;
import com.teamdev.jxmaps.Marker;
import com.teamdev.jxmaps.swing.*;


       
       
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
				
			}
		});
	}

}
