package smartcity.accessibility.search;

import static org.junit.Assert.*;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.naming.directory.SearchResult;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.junit.BeforeClass;
import org.junit.Test;

import com.teamdev.jxmaps.Geocoder;
import com.teamdev.jxmaps.GeocoderCallback;
import com.teamdev.jxmaps.GeocoderRequest;
import com.teamdev.jxmaps.GeocoderResult;
import com.teamdev.jxmaps.GeocoderStatus;
import com.teamdev.jxmaps.InfoWindow;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.MapReadyHandler;
import com.teamdev.jxmaps.MapStatus;
import com.teamdev.jxmaps.MapViewOptions;
import com.teamdev.jxmaps.Marker;
import com.teamdev.jxmaps.swing.MapView;

import org.junit.BeforeClass;

import smartcity.acessibility.jxMapsExamples.HelloWorld;

/**
 * @author Kolikant
 *
 */
public class SearchTest{
	
	private class helper extends MapView{
		helper(MapViewOptions options, SearchQuery sq) {
			super(options);
	        setOnMapReadyHandler(new MapReadyHandler() {
	            @Override
	            public void onMapReady(MapStatus status) {
	                if (status == MapStatus.MAP_STATUS_OK) {
	                    final Map map = getMap();
	                    map.setZoom(17.0);
	                    GeocoderRequest request = new GeocoderRequest(map);
	                    request.setAddress("Tel-Aviv, Weizmann St, 30");

	                    Geocoder g = getServices().getGeocoder();
	                    g.geocode(request, new GeocoderCallback(map) {
	                        @Override
	                        public void onComplete(GeocoderResult[] result, GeocoderStatus status) {
	                            if (status == GeocoderStatus.OK) {
	                                map.setCenter(result[0].getGeometry().getLocation());
	                                Marker marker = new Marker(map);
	                                marker.setPosition(result[0].getGeometry().getLocation());
	                            }
	                        }
	                    });
	                    
	                   GeocoderRequest request2 = new GeocoderRequest(map);
	                    request2.setAddress("Tel-Aviv, Weizmann St, 1");

	                    Geocoder g2 =getServices().getGeocoder();
	                    g2.geocode(request2, new GeocoderCallback(map) {
	                        @Override
	                        public void onComplete(GeocoderResult[] result, GeocoderStatus status) {
	                            if (status == GeocoderStatus.OK) {
	                                map.setCenter(result[0].getGeometry().getLocation());
	                                Marker marker = new Marker(map);
	                                marker.setPosition(result[0].getGeometry().getLocation());

	                                final InfoWindow window = new InfoWindow(map);
	                                window.setContent("Hello, World!");
	                                window.open(map, marker);
	                            }
	                        }
	                    });
	                }
	            }
	        });
		}
	}
	
	 @Test 
     public void test1(){
     	 MapViewOptions options = new MapViewOptions();
         options.importPlaces();
         final helper mapView = new helper(options, new SearchQuery("Modi'in Yehalom St, 1"));
         
         JFrame frame = new JFrame("JxMaps - Hello, World!");

         frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
         frame.add(mapView, BorderLayout.CENTER);
         frame.setSize(700, 500);
         frame.setLocationRelativeTo(null);
         frame.setVisible(true);
         try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
     }
            
}
	
