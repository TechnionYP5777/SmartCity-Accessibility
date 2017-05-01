
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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.parse4j.ParseException;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import smartcity.accessibility.categories.BranchTests;
import smartcity.accessibility.categories.UnitTests;
import smartcity.accessibility.exceptions.UserNotFoundException;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.socialnetwork.Review;
import smartcity.accessibility.socialnetwork.User;
import smartcity.accessibility.socialnetwork.UserBuilder;
import smartcity.accessibility.socialnetwork.UserProfile;

public class ReviewManagerTest {
	private static ReviewManager rm;
	protected static Database db;
	private static Map<String, Object> m;
	private static Review rev1;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		setUpMock();
		Injector injector = Guice.createInjector(new DatabaseModule());
		rm = injector.getInstance(ReviewManager.class);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	@Ignore
	@Category({ BranchTests.class, UnitTests.class })
	public void testUpload() {
		Review r = new Review(new Location(), 5, "asdfasd", new UserBuilder()
																.setUsername("asdf")
																.setPassword("asdf")
																.setPrivilege(User.Privilege.DefaultUser)
																.build().getProfile());
		rm.uploadReview(r, null);
		Mockito.verify(db).put(Mockito.any(), Mockito.any());
	}
	
	@Test
	@Category({ BranchTests.class, UnitTests.class })
	public void getReviewsTest() throws ParseException{
		List<Review> list = rm.getReviews("a", null);
		assertEquals(1, list.size());
		Map<String, Object> nm = rm.toMap(list.get(0));
		nm.put(ReviewManager.LOCATION_FIELD_NAME, "a");
		assertEquals(m, nm);
	}
	
	public static void setUpMock(){
		AbstractLocationManager lm = Mockito.mock(AbstractLocationManager.class);
		//Mockito.when(lm.getId(coordinates, locType, locSubType, null))
		AbstractLocationManager.initialize(lm);
		
		AbstractUserProfileManager um = Mockito.mock(AbstractUserProfileManager.class);
		try {
			Mockito.when(um.get(Mockito.anyString(), Mockito.isNull()))
				.thenReturn(new UserProfile("victor"));
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}
		AbstractUserProfileManager.initialize(um);
		
		db = Mockito.mock(Database.class);
		m = new HashMap<>();
		m.put(ReviewManager.LOCATION_FIELD_NAME, "a");
		m.put(ReviewManager.CONTENT_FIELD_NAME, "content");
		m.put(ReviewManager.IS_PINNED_FIELD_NAME, true);
		m.put(ReviewManager.RATING_FIELD_NAME, 3);
		m.put(ReviewManager.USERNAME_FIELD_NAME, "victor");
		

		List<Map<String, Object>> l = new ArrayList<>();
		l.add(m);
		Mockito.when(db.get(Mockito.anyString(), Mockito.anyMap())).thenReturn(l);
		rev1 = ReviewManager.fromMap(m);
		
		
	}

	public static class DatabaseModule extends AbstractModule {
		@Override
		protected void configure() {
			bind(Database.class).toInstance(db);
		}
	}

}

