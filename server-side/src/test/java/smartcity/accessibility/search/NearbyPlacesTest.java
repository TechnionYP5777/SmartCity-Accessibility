package smartcity.accessibility.search;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.teamdev.jxmaps.LatLng;
import com.teamdev.jxmaps.MapViewOptions;
import com.teamdev.jxmaps.swing.MapView;

import smartcity.accessibility.categories.UnitTests;
import smartcity.accessibility.mapmanagement.JxMapsFunctionality;
import smartcity.accessibility.mapmanagement.JxMapsFunctionality.ExtendedMapView;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.mapmanagement.LocationBuilder;

/**
 * @author Koral Chapnik
 */
public class NearbyPlacesTest {

	@Test
	@Category(UnitTests.class)
	public void nearByPlacesTest() {
		LatLng c = new LatLng(31.90588, 34.997571); // Modi'in Yehalom St, 20
		double radius = 1000000;
		ArrayList<String> kindsOfLocations = new ArrayList<String>();
		kindsOfLocations.add("restaurant");
		Location initLocation = new LocationBuilder().setCoordinates(c).build();
		new MapViewOptions().importPlaces();
		MapView mapView = JxMapsFunctionality.getStaticMapView();
		JxMapsFunctionality.waitForMapReady((ExtendedMapView) mapView);
		NearbyPlacesSearch.findNearbyPlaces(mapView, initLocation, radius, kindsOfLocations, ls -> {
			System.out.println("the length is : " + ls.size());
			for (Location l : ls) {
				LatLng a = l.getCoordinates();
				System.out.println("lat is : " + a.getLat() + " lng is : " + a.getLng());
				//OMGDEPRECATION!JxMapsFunctionality.putMarker((ExtendedMapView) mapView, a, l.getName());
			}
			//OMGDEPRECATION!JxMapsFunctionality.openFrame(mapView, "JxMaps - Hello, World!", 16.0);
		});

		try {
			Thread.sleep(900);
		} catch (InterruptedException e) {
		}

	}
}