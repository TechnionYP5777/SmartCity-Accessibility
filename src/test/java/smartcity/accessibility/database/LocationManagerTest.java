package smartcity.accessibility.database;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseUser;
import org.parse4j.callback.GetCallback;
import org.parse4j.callback.SaveCallback;

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.exceptions.UnauthorizedAccessException;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.mapmanagement.Location.LocationTypes;
import smartcity.accessibility.socialnetwork.Review;
import smartcity.accessibility.socialnetwork.User.Privilege;
import smartcity.accessibility.socialnetwork.UserImpl;

public class LocationManagerTest {

	@BeforeClass
	public static void init() {
		ParseUser.currentUser = new ParseUser();
		DatabaseManager.initialize();
	}

	@Test
	public void saveLocationTest() throws InterruptedException {
		LatLng k = new LatLng(20, 20);
		ArrayList<Review> r = new ArrayList<>();
		Location l1 = new Location(k), l2 = new Location(r, k), l3 = new Location(k, Location.LocationTypes.Facility), l4 = new Location(k, LocationTypes.Street, Location.LocationSubTypes.Restaurant);
		SaveCallback s = new SaveCallback() {

			@Override
			public void done(ParseException arg0) {
			}
		};
		LocationManager.saveLocation(l1, s);
		LocationManager.saveLocation(l2, s);
		LocationManager.saveLocation(l3, s);
		LocationManager.saveLocation(l4, s);
		Thread.sleep(3000);
	}

	@Test
	public void getLocationsyncronizedTest() throws InterruptedException {
		LatLng k = new LatLng(40, 40);
		Location L = new Location(k, Location.LocationTypes.Coordinate, Location.LocationSubTypes.Bar);
		Review r1 = new Review(L, 5, "secondTestLocation1", "assafL"),
				r2 = new Review(L, 5, "secondTestLocation1", "arturL");
		ReviewManager.uploadReview(r1);
		Thread.sleep(10000);
		ReviewManager.uploadReview(r2);
		Thread.sleep(10000);
		assert ("assafL".equals(LocationManager.getLocation(k, Location.LocationTypes.Coordinate, Location.LocationSubTypes.Bar)
				.getReviews().get(0).getUser()));
	}

	@Test
	public void getLocationbackground() throws InterruptedException {
		LatLng k = new LatLng(41, 40);
		Location L = new Location(k, Location.LocationTypes.Coordinate, Location.LocationSubTypes.Bar);
		Review r1 = new Review(L, 5, "secondTestLocation2", "assafL"),
				r2 = new Review(L, 5, "secondTestLocation2", "arturL");
		ReviewManager.uploadReview(r1);
		Thread.sleep(7000);
		ReviewManager.uploadReview(r2);
		Thread.sleep(7000);
		ArrayList<Review> pinned = new ArrayList<Review>();
		LocationManager.getLocation(k, new LocationListCallback() {

			@Override
			public void done(List<Location> ls) {
				for (Location ¢ : ls)
					pinned.addAll(¢.getReviews());
			}
		});
		Thread.sleep(10000);
		assert (pinned.size() == 2);
	}

	@Test
	public void updateLocationTest() throws InterruptedException, ParseException {
		LatLng k = new LatLng(42, 42);
		Location L = new Location(k);
		UserImpl u2 = new UserImpl("arturL", "132456", Privilege.Admin);
		Review r1 = new Review(L, 5, "forthtestL1", "assafL"), r2 = new Review(L, 5, "forthtesLt2", "arturL"),
				r3 = new Review(L, 5, "forthtestL3", "userrrrL");
		L.addReview(r1);
		L.addReview(r2);
		L.addReview(r3);
		Thread.sleep(10000);
		try {
			r2.pin(u2);
		} catch (UnauthorizedAccessException e) {
		}
		LocationManager.updateLocation(L);
		Thread.sleep(15000);
		ArrayList<Review> pinned = new ArrayList<Review>();
		GetCallback<ParseObject> g = new GetCallback<ParseObject>() {

			@Override
			public void done(ParseObject arg0, ParseException arg1) {
				if (arg0 == null)
					return;
				pinned.add(new Review(L, arg0.getInt("rating"), arg0.getString("comment"), arg0.getString("user")));
				if (arg0.getInt("pined") == 1)
					try {
						pinned.get(0).pin(u2);
					} catch (UnauthorizedAccessException e) {
					}
			}
		};

		ReviewManager.getReviewByUserAndLocation(u2, L, g);
		Thread.sleep(6000);
		if (pinned.isEmpty()) {
			System.out.println("here2");
			assert (false);
		}
		if (pinned.get(0).isPinned())
			return;
		System.out.println("here3");
		assert (false);
	}
}
