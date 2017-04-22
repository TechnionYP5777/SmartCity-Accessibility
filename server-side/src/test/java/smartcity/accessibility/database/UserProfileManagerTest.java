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

import smartcity.accessibility.categories.BranchTests;
import smartcity.accessibility.categories.UnitTests;
import smartcity.accessibility.exceptions.UserNotFoundException;
import smartcity.accessibility.socialnetwork.UserProfile;

public class UserProfileManagerTest {
	private static UserProfileManager manager;
	protected static Database db;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		setUpMock();
		Injector injector = Guice.createInjector(new DatabaseTestModule());
		UserProfileManager.initialize(injector.getInstance(UserProfileManager.class));
		manager = UserProfileManager.instance();
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
	@Category({ BranchTests.class, UnitTests.class })
	public void testGet() throws UserNotFoundException, InterruptedException {
		UserProfile pf = manager.get("alexaxa", null);
		assertEquals(25, pf.getRating());
		assertEquals(5, pf.getNumOfReviews());
		assertEquals("alexaxa", pf.getUsername());
	}
	
	public static void setUpMock(){
		db = Mockito.mock(Database.class);
		Map<String, Object> m = new HashMap<>();
		m.put(UserProfileManager.USERNAME_FIELD, "alexaxa");
		m.put(UserProfileManager.RATING_FIELD, 25);
		m.put(UserProfileManager.NUM_OF_REVIEWS_FIELD, 5);
		List<Map<String, Object>> l = new ArrayList<>();
		l.add(m);
		Mockito.when(db.get(Mockito.anyString(), Mockito.anyMap())).thenReturn(l);
	}
	
	public static class DatabaseTestModule extends AbstractModule {
		@Override
		protected void configure() {
			bind(Database.class).toInstance(db);
		}
	}

}
