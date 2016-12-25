package smartcity.accessibility.search;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.junit.Assume;
import org.junit.Ignore;
import org.junit.Test;

import com.teamdev.jxmaps.GeocoderRequest;
import com.teamdev.jxmaps.InfoWindow;
import com.teamdev.jxmaps.LatLng;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.MapReadyHandler;
import com.teamdev.jxmaps.MapStatus;
import com.teamdev.jxmaps.MapViewOptions;
import com.teamdev.jxmaps.Marker;
import com.teamdev.jxmaps.swing.MapView;

/**
 * @author Kolikant
 *
 */
public class SearchTest{
	
	public static class helper2 extends MapView{
		helper2(MapViewOptions options){
			setOnMapReadyHandler(new MapReadyHandler() {
				@Override
				public void onMapReady(MapStatus arg0) {
					;
				}
			});
		}
		
	}
	
	/*private class helper extends MapView{
		helper(MapViewOptions options, SearchQuery sq, SearchQuery sq2) {
			super(options);
	        setOnMapReadyHandler(new MapReadyHandler() {
	            @Override
	            public void onMapReady(MapStatus status) {
	                if (status == MapStatus.MAP_STATUS_OK) {
	                    final Map map = getMap();
	                    map.setZoom(16.0);
	                    
	                    SearchQueryResult sqr = sq.Search();
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
	                    });
	                }
	            }
	        });
		}
	}*/
	

	 @Ignore
	 @Test 
     public void test1(){ 
     	 MapViewOptions options = new MapViewOptions();
         options.importPlaces();
         final helper2 mapView = new helper2(options);
         try {
			Thread.sleep(5000);
		} catch (InterruptedException e2) {
			e2.printStackTrace();
		}
         SearchQuery s1 = new SearchQuery("Modi'in Yehalom St, 20");
         SearchQuery s2 = new SearchQuery("Modi'in Yehalom 30");
         Map map = mapView.getMap();
         
         map.setZoom(16.0);
         SearchQuery.mapView = mapView;
         SearchQueryResult sqr1= s1.Search();
         SearchQueryResult sqr2= s2.Search();
         try {
 			Thread.sleep(5000);
 		} catch (InterruptedException e2) {
 			e2.printStackTrace();
 		}
         
         LatLng position1 = sqr1.getCoordinations().get(0).getGeometry().getLocation();
         LatLng position2 = sqr2.getCoordinations().get(0).getGeometry().getLocation();
         
         Marker m1 = new Marker(map);
         m1.setPosition(position1);
         map.setCenter(position1);
         final InfoWindow window = new InfoWindow(map);
         window.setContent("result1");
         window.open(map, m1);
         
         try {
 			Thread.sleep(1000);
 		} catch (InterruptedException e1) {
 			// TODO Auto-generated catch block
 			e1.printStackTrace();
 		}
         
         Marker m2 = new Marker(map);
         m2.setPosition(position2);
         map.setCenter(position2);
         final InfoWindow window2 = new InfoWindow(map);
         window2.setContent("result2");
         window2.open(map, m2);
         
        
         JFrame frame = new JFrame("JxMaps - Hello, World!");

         frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
         frame.add(mapView, BorderLayout.CENTER);
         frame.setSize(700, 500);
         frame.setLocationRelativeTo(null);
         frame.setVisible(true);

		 try {
			Thread.sleep(300000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }
            
}
	
