package smartcity.accessibility.search;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.google.maps.model.LatLng;

import smartcity.accessibility.categories.NetworkTests;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.mapmanagement.LocationBuilder;

/**
 * @author Koral Chapnik
 */
public class NearbyPlacesTest {

	@Test
	@Category(NetworkTests.class)
	public void nearByPlacesTest() {
		LatLng c = new LatLng(31.90588, 34.997571); // Modi'in Yehalom St, 20
		int radius = 1000;
		ArrayList<String> kindsOfLocations = new ArrayList<String>();
		kindsOfLocations.add("RESTAURANT");
		Location initLocation = new LocationBuilder().setCoordinates(c).build();
		NearbyPlacesSearch.findNearbyPlaces(initLocation, radius, kindsOfLocations, ls -> {
			System.out.println("the length is : " + ls.size());
			for (Location l : ls) {
				LatLng a = l.getCoordinates();
				System.out.println("lat is : " + a.lat + " lng is : " + a.lng);
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