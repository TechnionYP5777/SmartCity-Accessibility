package smartcity.accessibility.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseUser;
import org.parse4j.callback.FindCallback;
import org.parse4j.callback.GetCallback;
import org.parse4j.callback.SaveCallback;

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.exceptions.UsernameAlreadyTakenException;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.search.SearchQuery;
import smartcity.accessibility.socialnetwork.Review;
import smartcity.accessibility.socialnetwork.User;
import smartcity.accessibility.socialnetwork.UserImpl;

public class ReviewManagerTest {
	public static String testParseClass = "DatabaseManagerTestClass";
	public static String id_result = "";

	@Rule
	public Timeout globalTimeout = Timeout.seconds(20);

	
	@BeforeClass
	public static void init(){
		DatabaseManager.initialize();
	}
	
	@Test
	public void UploadReviewTest() throws InterruptedException{
		LatLng k = new LatLng(20,20);
		Location L = new Location(k);
		Review r = new Review(L, 5, "firstTest","assaf");
		ReviewManager.uploadReview(r);
		Thread.sleep(6000);
	}
	
	@Test
	public void getReviewByUserAndLocationTest() throws InterruptedException{
		LatLng k = new LatLng(20,20);
		Location L = new Location(k);
		Review r1 = new Review(L, 5, "secondTest1","assaf");
		Review r2 = new Review(L, 5, "secondTest2","artur");
		ReviewManager.uploadReview(r1);
		ReviewManager.uploadReview(r2);
		Thread.sleep(6000);
		UserImpl u1 = new UserImpl("assaf", "132456", null);
		UserImpl u2 = new UserImpl("artur", "132456", null);
		ArrayList<Review> pinned = new ArrayList<Review>();;
		GetCallback<ParseObject> g = new GetCallback<ParseObject>() {
			
			@Override
			public void done(ParseObject arg0, ParseException arg1) {
				pinned.add(new Review(L, arg0.getInt("rating"),arg0.getString("comment"), arg0.getString("user")));		
			}
		};
		ReviewManager.getReviewByUserAndLocation(u1,L,g);
		ReviewManager.getReviewByUserAndLocation(u2,L,g);
		Thread.sleep(6000);
		System.out.println(pinned.get(0).getRating().getScore()+"  "+pinned.get(0).getContent());
		System.out.println(pinned.get(1).getRating().getScore()+"  "+pinned.get(1).getContent());
		
	}
	
	@Test(timeout = 60000)
	public void deleteReviewTest() throws InterruptedException{
		LatLng k = new LatLng(21,20);
		Location L = new Location(k);
		Review r1 = new Review(L, 5, "theardtest1","assaf");
		Review r2 = new Review(L, 5, "theardtest2","artur");
		ReviewManager.uploadReview(r1);
		ReviewManager.uploadReview(r2);
		Thread.sleep(6000);
		ReviewManager.deleteReview(r1);
		Thread.sleep(10000);

		
	}
}
