package smartcity.accessibility.database;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.mapmanagement.Location.LocationTypes;
import smartcity.accessibility.socialnetwork.Review;

public class LocationManagerTest {
	
	@BeforeClass
	public static void init(){
		DatabaseManager.initialize();
	}
	
	@Test
	public void saveLocationTest() throws InterruptedException{
		LatLng k = new LatLng(20,20);
		ArrayList<Review> r = new ArrayList<>();
		Location l1,l2,l3,l4;
		l1 = new Location(k);
		l2 = new Location(r,k);
		l3 = new Location(k,Location.LocationTypes.Facility);
		l4 = new Location(k,LocationTypes.Street,Location.LocationSubTypes.Restaurant);
		LocationManager.saveLocation(l1);
		LocationManager.saveLocation(l2);
		LocationManager.saveLocation(l3);
		LocationManager.saveLocation(l4);
		Thread.sleep(3000);
	}
}
