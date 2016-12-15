package smartcity.accessibility.search;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.teamdev.jxmaps.Geocoder;
import com.teamdev.jxmaps.GeocoderCallback;
import com.teamdev.jxmaps.GeocoderRequest;
import com.teamdev.jxmaps.GeocoderResult;
import com.teamdev.jxmaps.GeocoderStatus;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.MapViewOptions;
import com.teamdev.jxmaps.Marker;
import com.teamdev.jxmaps.swing.MapView;

import smartcity.acessibility.jxMapsExamples.HelloWorld;

/**
 * @author Kolikant
 *
 */
public class SearchQueryResult extends MapView{

	Map map;
	private List<GeocoderRequest> coordinations;

	SearchQueryResult(List<GeocoderRequest> c, Map m){
		this.coordinations = c;
		this.map = m;
	}
	
	public List<GeocoderRequest> getCoordinations(){
		return coordinations;
	}
	
	public void showResults(){
		for(GeocoderRequest gr: coordinations){
			getServices().getGeocoder().geocode(gr, new GeocoderCallback(map) {
                @Override
                public void onComplete(GeocoderResult[] result, GeocoderStatus status) {
                    if (status == GeocoderStatus.OK) {
                        map.setCenter(result[0].getGeometry().getLocation());
                        Marker marker = new Marker(map);
                        marker.setPosition(result[0].getGeometry().getLocation());
                    }
                }
            });
		}
		
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
