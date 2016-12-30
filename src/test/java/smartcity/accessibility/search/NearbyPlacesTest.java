package smartcity.accessibility.search;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import org.parse4j.ParseException;

import com.teamdev.jxmaps.LatLng;
import com.teamdev.jxmaps.MapViewOptions;
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
		new MapViewOptions().importPlaces();
		MapView mapView = JxMapsFunctionality.getMapView();
		JxMapsFunctionality.waitForMapReady((extendedMapView) mapView);
		ArrayList<String> kindsOfLocations = new ArrayList<String>();
		kindsOfLocations.add("restaurant");
		Location initLocation = new Facility(c);
		MapViewOptions options = new MapViewOptions();
        options.importPlaces();
		NearbyPlacesAttempt n = new NearbyPlacesAttempt(options);
		ArrayList<Location> places = n.findNearbyPlaces(mapView, initLocation, radius, kindsOfLocations);
		System.out.println("the length is : " + places.size());
		for (Location l : places) {
			LatLng a = l.getCoordinates();
			System.out.println("lat is : " + a.getLat() + " lng is : " + a.getLng());
		}
		
		
	}
}
