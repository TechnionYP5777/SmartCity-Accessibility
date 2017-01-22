package smartcity.accessibility.database;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.callback.GetCallback;
import org.parse4j.callback.SaveCallback;

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.mapmanagement.Location.LocationTypes;
import smartcity.accessibility.socialnetwork.Review;
import smartcity.accessibility.socialnetwork.UserImpl;

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
		SaveCallback s = new SaveCallback() {
			
			@Override
			public void done(ParseException arg0) {
			}
		};
		LocationManager.saveLocation(l1,s);
		LocationManager.saveLocation(l2,s);
		LocationManager.saveLocation(l3,s);
		LocationManager.saveLocation(l4,s);
		Thread.sleep(3000);
	}
	
	@Test
	public void getReviewByUserAndLocationsyncronizedTest() throws InterruptedException{
		LatLng k = new LatLng(20,20);
		Location L = new Location(k,Location.LocationTypes.Coordinate,Location.LocationSubTypes.Bar);
		Review r1 = new Review(L, 5, "secondTestLocation1","assaf");
		Review r2 = new Review(L, 5, "secondTestLocation1","artur");
		ReviewManager.uploadReview(r1);
		Thread.sleep(7000);
		ReviewManager.uploadReview(r2);
		Thread.sleep(7000);
		Location newLoc = LocationManager.getLocation(k,Location.LocationTypes.Coordinate,Location.LocationSubTypes.Bar);
		assert(newLoc.getReviews().get(0).getUser().equals("assaf"));
	}
	
}
