package smartcity.accessibility.search;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.teamdev.jxmaps.GeocoderResult;
import com.teamdev.jxmaps.LatLng;
import com.teamdev.jxmaps.swing.MapView;

import smartcity.accessibility.mapmanagement.JxMapsFunctionality;
import smartcity.accessibility.mapmanagement.JxMapsFunctionality.waitableMap;;

/**
 * @author Kolikant
 *
 */
public class SearchTest{
	

	 @Ignore
	 @Test 
     public void test1(){ 
     	 MapView mapView = JxMapsFunctionality.getMapView();

         SearchQuery s1 = new SearchQuery("Modi'in Yehalom St, 20");
         SearchQuery s2 = new SearchQuery("Modi'in Yehalom 30");
         
         JxMapsFunctionality.waitForMapReady((waitableMap) mapView);
         
         SearchQueryResult sqr1= s1.SearchByAddress(mapView);
         SearchQueryResult sqr2= s2.SearchByAddress(mapView);
         
         s1.waitOnSearch();
         s2.waitOnSearch();
         
         LatLng position1 = sqr1.getCoordinations().get(0).getGeometry().getLocation();
         LatLng position2 = sqr2.getCoordinations().get(0).getGeometry().getLocation();
         
         JxMapsFunctionality.putMarker((waitableMap) mapView, position1, "result1");
         JxMapsFunctionality.putMarker((waitableMap) mapView, position2, "result2");
         
         JxMapsFunctionality.openFrame(mapView, "JxMaps - Hello, World!", 16.0);
        
         

		 try {
			Thread.sleep(300000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }
	 
	 @Test
	 public void test2(){
		 MapView mapView = JxMapsFunctionality.getMapView();
		 SearchQuery s1 = new SearchQuery("coffee shops");
		 
		 JxMapsFunctionality.waitForMapReady((waitableMap) mapView);
         
     //    SearchQueryResult sqr1= s1.SearchByFreeText(mapView);
         
         s1.waitOnSearch();
         
      //   List<GeocoderResult> positions = sqr1.getCoordinations();
         //JxMapsFunctionality.putMarker((helper2) mapView, position1, "result1");
         
	 }
            
}