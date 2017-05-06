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
import org.parse4j.ParseGeoPoint;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.categories.BranchTests;
import smartcity.accessibility.categories.UnitTests;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.mapmanagement.Location.LocationSubTypes;
import smartcity.accessibility.mapmanagement.Location.LocationTypes;
import smartcity.accessibility.mapmanagement.LocationBuilder;
import smartcity.accessibility.socialnetwork.Review;
import smartcity.accessibility.socialnetwork.UserProfile;

public class LocationManagerTest {

	private static LocationManager lm;
	protected static Database db;
	private static Map<String, Object> m;
	private static AbstractReviewManager rm;
	private static Location l1;

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
		lm = injector.getInstance(LocationManager.class);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(timeout = 500)
	@Category({ BranchTests.class, UnitTests.class })
	public void testBackgroundCalls() {
		
	}

	@Test
	@Category({ BranchTests.class, UnitTests.class })
	public void testGetId() {
		String id = lm.getId(new LatLng(), LocationTypes.Coordinate, LocationSubTypes.Default, null);
		assertEquals("MY_ID", id);
	}
	
	@Test
	@Category({ BranchTests.class, UnitTests.class })
	public void testUpload() {
		Location ml = new LocationBuilder().setCoordinates(new LatLng())
				.setName("asd")
				.setType(LocationTypes.Coordinate)
				.setSubType(LocationSubTypes.Default)
				.build();
		String res = lm.uploadLocation(ml, null);
		Mockito.verify(db).put(Mockito.anyString(), Mockito.anyMap());
		assertEquals("MY_ID2", res);
		
	}
	
	@Test
	@Category({ BranchTests.class, UnitTests.class })
	public void testGetLocation() {
		List<Location> res = lm.getLocation(new LatLng(), null);
		assertEquals(1, res.size());
		Location lres = res.get(0);
		assertEquals("name", lres.getName());
		assertEquals(1, lres.getReviews().size());
		assertEquals("hey",lres.getReviews().get(0).getContent());
		
	}

	public static void setUpMock() {
		db = Mockito.mock(Database.class);
		m = new HashMap<>();
		m.put(LocationManager.NAME_FIELD_NAME, "name");
		m.put(LocationManager.SUB_TYPE_FIELD_NAME, "Default");
		m.put(LocationManager.TYPE_FIELD_NAME, "Coordinate");
		m.put(LocationManager.LOCATION_FIELD_NAME, new ParseGeoPoint(0,0));
		m.put(LocationManager.ID_FIELD_NAME, "MY_ID");
		List<Map<String, Object>> l = new ArrayList<>();
		l.add(m);
		Mockito.when(db.get(Mockito.anyString(), Mockito.anyMap())).thenReturn(l);
		Mockito.when(db.put(Mockito.anyString(), Mockito.anyMap())).thenReturn("MY_ID2");
		l1 = LocationManager.fromMap(m);
		
		List<Review> lr = new ArrayList<>();
		lr.add(new Review(l1,4,"hey",new UserProfile("alexa")));
		rm = Mockito.mock(AbstractReviewManager.class);
		Mockito.when(rm.getReviews(Mockito.anyString(), Mockito.any())).thenReturn(lr);
		AbstractReviewManager.initialize(rm);
	}
	

	public static class DatabaseModule extends AbstractModule {
		@Override
		protected void configure() {
			bind(Database.class).toInstance(db);
		}
	}

}
