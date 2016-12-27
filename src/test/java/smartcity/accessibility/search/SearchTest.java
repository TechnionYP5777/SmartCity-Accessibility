package smartcity.accessibility.search;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.junit.Ignore;
import org.junit.Test;

import com.teamdev.jxmaps.InfoWindow;
import com.teamdev.jxmaps.LatLng;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.MapReadyHandler;
import com.teamdev.jxmaps.MapStatus;
import com.teamdev.jxmaps.MapViewOptions;
import com.teamdev.jxmaps.Marker;
import com.teamdev.jxmaps.swing.MapView;

import smartcity.acessibility.jxMapsFunctionality.JxMapsFunctionality;
import smartcity.acessibility.jxMapsFunctionality.JxMapsFunctionality.helper2;

/**
 * @author Kolikant
 *
 */
public class SearchTest{
	
	public static class helper2 extends MapView{
		helper2(MapViewOptions __){
			setOnMapReadyHandler(new MapReadyHandler() {
				@Override
				public void onMapReady(MapStatus arg0) {
				}
			});
		}
		
	}
	

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
         
<<<<<<< HEAD
         Map map = mapView.getMap();
         
         map.setZoom(16.0);
         SearchQuery.mapView = mapView;
         SearchQueryResult sqr1= s1.Search();
         SearchQueryResult sqr2= s2.Search();
=======
         SearchQueryResult sqr1= s1.SearchByAddress(mapView);
         SearchQueryResult sqr2= s2.SearchByAddress(mapView);
>>>>>>> f9a6350635507e352650e92475ffcd43c0a2ed6f
         try {
 			Thread.sleep(5000);
 		} catch (InterruptedException e2) {
 			e2.printStackTrace();
 		}
         
         LatLng position1 = sqr1.getCoordinations().get(0).getGeometry().getLocation();
         LatLng position2 = sqr2.getCoordinations().get(0).getGeometry().getLocation();
         
         JxMapsFunctionality.putMarker((helper2) mapView, position1, "result1");
         JxMapsFunctionality.putMarker((helper2) mapView, position2, "result2");
         
         JxMapsFunctionality.openFrame(mapView, "JxMaps - Hello, World!");
        
         

		 try {
			Thread.sleep(300000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }
            
}
	
