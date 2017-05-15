package smartcity.accessibility.database;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.categories.UnitTests;
import smartcity.accessibility.database.exceptions.ObjectNotFoundException;
import smartcity.accessibility.exceptions.UserNotFoundException;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.mapmanagement.LocationBuilder;
import smartcity.accessibility.socialnetwork.Review;
import smartcity.accessibility.socialnetwork.ReviewComment;
import smartcity.accessibility.socialnetwork.User;
import smartcity.accessibility.socialnetwork.UserBuilder;
import smartcity.accessibility.socialnetwork.UserProfile;

public class ReviewManagerTest {
	private static ReviewManager rm;
	@SuppressWarnings("unused") // It is claimed that lm is unused but that is not true, so suppressed
	private static AbstractLocationManager lm;
	private static AbstractUserProfileManager um;
	protected static Database db;
	private static Map<String, Object> m;
	private static Review rev1;
	private static UserProfile up1;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		setUpMock();
		Injector injector = Guice.createInjector(new DatabaseModule());
		rm = injector.getInstance(ReviewManager.class);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test(timeout=500)
	@Category(UnitTests.class)
	public void testBackgroundCalls() {
		assertEquals(0, rm.getReviews("a", nlr -> {}).size());
		assertEquals(0, rm.getReviewWithLocation("a", nlr -> {}).size());
		assertEquals(false, rm.uploadReview(rev1, b -> {}));
		assertEquals(false, rm.deleteReview(rev1, b -> {}));
		assertEquals(false, rm.updateReview(rev1, b -> {}));
	}

	@Test
	@Category(UnitTests.class)
	public void testUpload() {
		Review r = new Review(new Location(), 5, "asdfasd", new UserBuilder()
																.setUsername("asdf")
																.setPassword("asdf")
																.setPrivilege(User.Privilege.DefaultUser)
																.build().getProfile());
		rm.uploadReview(r, null);
		Mockito.verify(db).put(Mockito.anyString(), Mockito.anyMap());
		
	}
	
	@Test
	@Category(UnitTests.class)
	public void getReviewsTest() {
		List<Review> list = rm.getReviews("a", null);
		assertEquals(1, list.size());
		Map<String, Object> nm = ReviewManager.toMap(list.get(0));
		nm.put(ReviewManager.ID_FIELD_NAME, "MY_ID_REV");
		nm.put(ReviewManager.LOCATION_FIELD_NAME, "a");
		assertEquals(m, nm);
	}
	
	@Test
	@Category(UnitTests.class)
	public void getReviewWithLocationTest() {
		List<Review> list = rm.getReviewWithLocation("MY_ID", null);
		assertEquals(1, list.size());
		Map<String, Object> nm = ReviewManager.toMap(list.get(0));
		nm.put(ReviewManager.ID_FIELD_NAME, "MY_ID_REV");
		nm.put(ReviewManager.LOCATION_FIELD_NAME, "a");
		assertEquals(m, nm);
	}
	
	@Test
	@Category(UnitTests.class)
	public void deleteReviewTest() {
		Boolean res = rm.deleteReview(rev1, null);
		assertEquals(true, res);
		Mockito.verify(db).delete(ReviewManager.DATABASE_CLASS, "MY_ID_REV");
	}
	
	@Test
	@Category(UnitTests.class)
	public void updateReviewTest() {
		Boolean res = rm.updateReview(rev1, null);
		assertEquals(true, res);
		Map<String, Object> nm = new HashMap<>(m);
		nm.remove(ReviewManager.ID_FIELD_NAME);
		nm.put(ReviewManager.LOCATION_FIELD_NAME, null);
		Mockito.verify(db).update(ReviewManager.DATABASE_CLASS, "MY_ID_REV", nm);
	}
	
	@Test
	@Category(UnitTests.class)
	public void updateFromToMap() {
		Review my_rev = new Review(null, 3, "my_content", up1);
		List<ReviewComment> lrc = new ArrayList<>();
		lrc.add(new ReviewComment(2, up1));
		my_rev.addComments(lrc);
		Map<String, Object> m = ReviewManager.toMap(my_rev);
		assertEquals("Simba#2", m.get(ReviewManager.COMMENTS_FIELD_NAME));
		assertEquals("my_content", m.get(ReviewManager.CONTENT_FIELD_NAME));
		assertEquals(3, m.get(ReviewManager.RATING_FIELD_NAME));
		assertEquals("Simba", m.get(ReviewManager.USERNAME_FIELD_NAME));
		
		Review map_rev = ReviewManager.fromMap(m);
		assertEquals("Simba",map_rev.getUser().getUsername());
		assertEquals(lrc,map_rev.getComments());
		assertEquals(lrc.get(0).getRating(),map_rev.getComments().get(0).getRating());
		assertEquals(my_rev.getContent(), map_rev.getContent());
		
	}
	
	public static void setUpMock() throws ObjectNotFoundException, UserNotFoundException{
		AbstractLocationManager lm = Mockito.mock(AbstractLocationManager.class);
		AbstractLocationManager.initialize(lm);
		
		um = Mockito.mock(AbstractUserProfileManager.class);
		up1 = new UserProfile("Simba");
		Mockito.when(um.get("Simba", null)).thenReturn(up1);
		Mockito.when(um.get("victor", null))
				.thenReturn(new UserProfile("victor"));
		AbstractUserProfileManager.initialize(um);
		
		db = Mockito.mock(Database.class);
		m = new HashMap<>();
		m.put(ReviewManager.ID_FIELD_NAME, "MY_ID_REV");
		m.put(ReviewManager.LOCATION_FIELD_NAME, "a");
		m.put(ReviewManager.CONTENT_FIELD_NAME, "content");
		m.put(ReviewManager.IS_PINNED_FIELD_NAME, true);
		m.put(ReviewManager.RATING_FIELD_NAME, 3);
		m.put(ReviewManager.USERNAME_FIELD_NAME, "victor");
		m.put(ReviewManager.COMMENTS_FIELD_NAME, "");
		

		List<Map<String, Object>> l = new ArrayList<>();
		l.add(m);
		Mockito.when(db.get(Mockito.startsWith(ReviewManager.DATABASE_CLASS), Mockito.anyMap())).thenReturn(l);
		Mockito.when(db.put(Mockito.anyString(), Mockito.anyMap())).thenReturn("b");
		Mockito.when(db.delete(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
		Mockito.when(db.update(Mockito.anyString(), Mockito.anyString(), Mockito.anyMap())).thenReturn(true);
		rev1 = ReviewManager.fromMap(m);
		
		List<Review> lr = new ArrayList<>();
		lr.add(rev1);
		Location sample = new LocationBuilder()
				.addReviews(lr)
				.setCoordinates(new LatLng())
				.setName("Hello")
				.build();
		
		Map<String, Object> locMap = LocationManager.toMap(sample);
		locMap.put(LocationManager.ID_FIELD_NAME, "MY_ID_LOC");
		Mockito.when(db.get(Mockito.anyString(), Mockito.anyString())).thenReturn(locMap);
		
		lm = Mockito.mock(AbstractLocationManager.class);
		Mockito.when(lm.getLocation(Mockito.any(LatLng.class), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(sample);
		AbstractLocationManager.initialize(lm);
		
		
	}

	public static class DatabaseModule extends AbstractModule {
		@Override
		protected void configure() {
			bind(Database.class).toInstance(db);
		}
	}

}