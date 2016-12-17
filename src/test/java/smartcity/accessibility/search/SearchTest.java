package smartcity.accessibility.search;

import static org.junit.Assert.*;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.List;

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
import com.teamdev.jxmaps.LatLng;
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
		helper(MapViewOptions options, SearchQuery sq, SearchQuery sq2) {
			super(options);
	        setOnMapReadyHandler(new MapReadyHandler() {
	            @Override
	            public void onMapReady(MapStatus status) {
	                if (status == MapStatus.MAP_STATUS_OK) {
	                    final Map map = getMap();
	                    map.setZoom(16.0);
	                    
	                    SearchQueryResult sqr = sq.Search(map);
	                    try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                    LatLng position = sqr.getCoordinations().get(0).getGeometry().getLocation();
	                    Marker m1 = new Marker(map);
	                    m1.setPosition(position);
	                    map.setCenter(position);
	                    final InfoWindow window = new InfoWindow(map);
                        window.setContent("result1");
                        window.open(map, m1);
	                    
	                   GeocoderRequest request2 = new GeocoderRequest(map);
	                    request2.setAddress("Modi'in Yehalom 30");

	                    /*  Geocoder g2 =getServices().getGeocoder();
	                    g2.geocode(request2, new GeocoderCallback(map) {
	                        @Override
	                        public void onComplete(GeocoderResult[] result, GeocoderStatus status) {
	                            if (status == GeocoderStatus.OK) {
	                            	 map.setCenter(result[0].getGeometry().getLocation());
	                                Marker marker = new Marker(map);
	                                marker.setPosition(result[0].getGeometry().getLocation());

	                                final InfoWindow window = new InfoWindow(map);
	                                window.setContent("result 2");
	                                window.open(map, marker);
	                            }
	                        }
	                    });*/
	                }
	            }
	        });
		}
	}
	
	 @Test 
     public void test1(){
     	 MapViewOptions options = new MapViewOptions();
         options.importPlaces();
         final helper mapView = new helper(options, new SearchQuery("Modi'in Yehalom St, 20"), new SearchQuery("Modi'in Yehalom 30"));
         try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
         JFrame frame = new JFrame("JxMaps - Hello, World!");

         frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
         frame.add(mapView, BorderLayout.CENTER);
         frame.setSize(700, 500);
         frame.setLocationRelativeTo(null);
         frame.setVisible(true);
		 /*try {
			Thread.sleep(300000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
     }
            
}
	
