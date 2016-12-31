package smartcity.accessibility.search;

import static org.junit.Assert.*;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.junit.BeforeClass;
import org.junit.Test;
import org.parse4j.ParseException;

import com.teamdev.jxmaps.InfoWindow;
import com.teamdev.jxmaps.LatLng;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.MapReadyHandler;
import com.teamdev.jxmaps.MapStatus;
import com.teamdev.jxmaps.MapViewOptions;
import com.teamdev.jxmaps.Marker;
import com.teamdev.jxmaps.swing.MapView;

import smartcity.accessibility.database.DatabaseManager;
import smartcity.accessibility.mapmanagement.Coordinates;
import smartcity.accessibility.mapmanagement.Facility;
import smartcity.accessibility.mapmanagement.JxMapsFunctionality;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.mapmanagement.JxMapsFunctionality.extendedMapView;
import smartcity.accessibility.socialnetwork.Review;
import smartcity.accessibility.socialnetwork.Score;
import smartcity.accessibility.socialnetwork.User;
import smartcity.accessibility.socialnetwork.UserImpl;

/**
 * @author Koral Chapnik
 */
public class NearbyPlacesTest {
	
	@Test
	public void nearByPlacesTest() {
		LatLng c = new LatLng(31.90588, 34.997571); //Modi'in Yehalom St, 20
		double radius = 1000;
		ArrayList<String> kindsOfLocations = new ArrayList<String>();
		kindsOfLocations.add("hotels");
		Location initLocation = new Facility(c);
		MapViewOptions options = new MapViewOptions();
        options.importPlaces();
		NearbyPlacesAttempt n = new NearbyPlacesAttempt(options);
		ArrayList<Location> places = n.findNearbyPlaces(initLocation, radius, kindsOfLocations);
		System.out.println("the length is : " + places.size());

		MapView mapView = JxMapsFunctionality.getMapView();
		JxMapsFunctionality.waitForMapReady((extendedMapView) mapView);
 
        
			for (Location l : places) {
				LatLng a = l.getCoordinates();
				JxMapsFunctionality.putMarker((extendedMapView) mapView, a, l.getName());
				System.out.println("lat is : " + a.getLat() + " lng is : " + a.getLng());
		//		System.out.println("the name of the restaurant is : " + l.getAddress(mapView));
			}
            
            
		JxMapsFunctionality.openFrame(mapView, "JxMaps - Hello, World!", 16.0);
	
		try {
			Thread.sleep(90000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}