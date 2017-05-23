
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
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.parse4j.ParseGeoPoint;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.categories.UnitTests;
import smartcity.accessibility.database.exceptions.ObjectNotFoundException;
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
	@Category(UnitTests.class)
	public void testBackgroundCalls() {
		
	}

	@Test
	@Category(UnitTests.class)
	public void testGetId() {
		String id = lm.getId(new LatLng(), LocationTypes.Coordinate, LocationSubTypes.Default, null);
		assertEquals("MY_ID", id);
	}
	
	@Test
	@Category(UnitTests.class)
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
	@Category(UnitTests.class)
	public void testGetLocation() {
		List<Location> res = lm.getLocation(new LatLng(), null);
		assertEquals(1, res.size());
		Location lres = res.get(0);
		assertEquals("name", lres.getName());
		assertEquals(1, lres.getReviews().size());
		assertEquals("hey",lres.getReviews().get(0).getContent());
		
	}
	
	@Test
	@Category(UnitTests.class)
	public void testGetLocationsAround(){
		List<Location> res = lm.getLocationsAround(new LatLng(1,1), 1.2, null);
		assertEquals(1, res.size());
		assertEquals(1, res.get(0).getReviews().size());
		Mockito.verify(db).get( LocationManager.DATABASE_CLASS,
								LocationManager.LOCATION_FIELD_NAME, 1, 1, 1.2);
	}
	
	@Test
	@Category(UnitTests.class)
	public void testGetLocationSingle() throws ObjectNotFoundException{
		Location res = lm.getLocation(new LatLng(), LocationTypes.Coordinate, LocationSubTypes.Default, null);
		Map<String, Object> mres = LocationManager.toMap(res);
		assertEquals(m.get(LocationManager.NAME_FIELD_NAME), mres.get(LocationManager.NAME_FIELD_NAME));
		assertEquals(m.get(LocationManager.TYPE_FIELD_NAME), mres.get(LocationManager.TYPE_FIELD_NAME));
		assertEquals(m.get(LocationManager.SUB_TYPE_FIELD_NAME), mres.get(LocationManager.SUB_TYPE_FIELD_NAME));
		Mockito.verify(db).get(LocationManager.DATABASE_CLASS, "MY_ID");
	}
	
	@Test
	@Category(UnitTests.class)
	public void testUpdateLocation() {
		Boolean res = lm.updateLocation(l1, null);
		assertEquals(true, res);
		@SuppressWarnings("unchecked")
		ArgumentCaptor<Map<String,Object>> cp =  ArgumentCaptor.forClass(Map.class);
		Mockito.verify(db).update(Mockito.eq(LocationManager.DATABASE_CLASS),
												Mockito.eq("MY_ID"),
												cp.capture());
		Map<String, Object> m = cp.getValue();
		Map<String, Object> m1 = LocationManager.toMap(l1);
		assertEquals(m.get(LocationManager.NAME_FIELD_NAME), m1.get(LocationManager.NAME_FIELD_NAME));
		assertEquals(m.get(LocationManager.TYPE_FIELD_NAME), m1.get(LocationManager.TYPE_FIELD_NAME));
		assertEquals(m.get(LocationManager.SUB_TYPE_FIELD_NAME), m1.get(LocationManager.SUB_TYPE_FIELD_NAME));
	}
	
	@Test
	@Category(UnitTests.class)
	public void testGetNonAccessibleLocationsInRadius(){
		List<LatLng> ll = lm.getNonAccessibleLocationsInRadius(new LatLng(), new LatLng(), 3, null);
		assertEquals(1, ll.size());
		LatLng loc = ll.get(0);
		assertEquals(1, (int)loc.getLat());
		assertEquals(1, (int)loc.getLng());
		ll = lm.getNonAccessibleLocationsInRadius(new LatLng(), new LatLng(), 5, null);
		assertEquals(0, ll.size());
	}
	
	@Test
	@Category(UnitTests.class)
	public void testGetTopRated(){
		List<Location> ll = lm.getTopRated(new LatLng(), 1, 1, null);
		assertEquals(1, ll.size());
	}

	public static void setUpMock() throws ObjectNotFoundException {
		db = Mockito.mock(Database.class);
		m = new HashMap<>();
		m.put(LocationManager.NAME_FIELD_NAME, "name");
		m.put(LocationManager.SUB_TYPE_FIELD_NAME, "Default");
		m.put(LocationManager.TYPE_FIELD_NAME, "Coordinate");
		m.put(LocationManager.LOCATION_FIELD_NAME, new ParseGeoPoint(1,1));
		m.put(LocationManager.ID_FIELD_NAME, "MY_ID");
		List<Map<String, Object>> l = new ArrayList<>();
		l.add(m);
		Mockito.when(db.get(Mockito.anyString(), Mockito.anyMap())).thenReturn(l);
		Mockito.when(db.get(Mockito.anyString(), Mockito.anyString())).thenReturn(m);
		Mockito.when(db.put(Mockito.anyString(), Mockito.anyMap())).thenReturn("MY_ID2");
		Mockito.when(db.get(Mockito.anyString(), Mockito.anyString(), Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyDouble())).thenReturn(l);
		l1 = LocationManager.fromMap(m);
		Mockito.when(db.update(Mockito.anyString(), Mockito.anyString(), Mockito.anyMap())).thenReturn(true);
		//Mockito.when(db.update(LocationManager.DATABASE_CLASS, "MY_ID", LocationManager.toMap(l1))).thenReturn(true);
		
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