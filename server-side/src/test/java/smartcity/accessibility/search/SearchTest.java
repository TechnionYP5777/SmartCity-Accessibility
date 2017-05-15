package smartcity.accessibility.search;

import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.teamdev.jxmaps.swing.MapView;

import smartcity.accessibility.categories.UnitTests;
import smartcity.accessibility.exceptions.illigalString;
import smartcity.accessibility.mapmanagement.JxMapsFunctionality;
import smartcity.accessibility.mapmanagement.JxMapsFunctionality.ExtendedMapView;
//import smartcity.accessibility.mapmanagement.Location;;

/**
 * @author Kolikant
 *
 */
public class SearchTest{
	

	 @Category(UnitTests.class)
	 @Ignore 
	 @Test
     public void test1() throws InterruptedException, illigalString{ 
     	 MapView mapView = JxMapsFunctionality.getMapView();

         SearchQuery s1 = SearchQuery.adressSearch("Modi'in Yehalom St, 20"),
				s2 = SearchQuery.adressSearch("Modi'in Yehalom 30");
         JxMapsFunctionality.waitForMapReady((ExtendedMapView) mapView);
         
     //    SearchQueryResult sqr1 = s1.SearchByAddress(mapView), sqr2 = s2.SearchByAddress(mapView);
         s1.waitOnSearch();
         s2.waitOnSearch();
         
       //  Location location2 = sqr2.getLocations().get(0);
       //OMGDEPRECATION!JxMapsFunctionality.putMarker((ExtendedMapView) mapView, sqr1.getLocations().get(0).getCoordinates(), "result1");
       //OMGDEPRECATION!JxMapsFunctionality.putMarker((ExtendedMapView) mapView, location2.getCoordinates(), "result2");
         
       //OMGDEPRECATION!JxMapsFunctionality.openFrame(mapView, "JxMaps - Hello, World!", 16.0);
        
        // JxMapsFunctionality.ClearMarkers((extendedMapView) mapView);

		 try {
			Thread.sleep(300000);
		} catch (InterruptedException ¢) {
			¢.printStackTrace();
		}
     }
	 
//	 @Ignore
	 @Category(UnitTests.class)
	 @Test
	 public void test2() throws InterruptedException, illigalString{
		 MapView mapView = JxMapsFunctionality.getMapView();
		 SearchQuery s1 = SearchQuery.adressSearch("coffee shops");
		 
		 JxMapsFunctionality.waitForMapReady((ExtendedMapView) mapView);
         
     //    SearchQueryResult sqr1= s1.SearchByFreeText(mapView);
         
         s1.waitOnSearch();
         
      //   List<GeocoderResult> positions = sqr1.getCoordinations();
         //JxMapsFunctionality.putMarker((helper2) mapView, position1, "result1");
         
	 }
	 
	 
	 @Category(UnitTests.class) 
	 @Test
	 public void test3() throws InterruptedException, illigalString{
		 MapView mapView1 = JxMapsFunctionality.getMapView();

	        SearchQuery s1 = SearchQuery.adressSearch("Rothschild 22, Rothschild Boulevard, Tel Aviv");
	        
	        JxMapsFunctionality.waitForMapReady((ExtendedMapView) mapView1);
	        
	        SearchQueryResult sqr1= s1.SearchByAddress(mapView1);
	        
	        
	        s1.waitOnSearch();
	        
	        if (sqr1.getLocations().isEmpty())
				fail();
			else
				sqr1.getLocations().get(0).getCoordinates();
	 }
	 
            
}