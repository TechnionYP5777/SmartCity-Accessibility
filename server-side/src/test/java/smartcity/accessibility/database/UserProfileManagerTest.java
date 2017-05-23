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

import smartcity.accessibility.categories.UnitTests;
import smartcity.accessibility.exceptions.UserNotFoundException;
import smartcity.accessibility.socialnetwork.UserProfile;

public class UserProfileManagerTest {
	private static AbstractUserProfileManager manager;
	private static Map<String, Object> m;
	private static Map<String, Object> m_not;
	private static Map<String, Object> m_noid;
	private static UserProfile user1;
	protected static Database db;
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		setUpMock();
		Injector injector = Guice.createInjector(new DatabaseTestModule());
		UserProfileManager.initialize(injector.getInstance(UserProfileManager.class));
		manager = UserProfileManager.instance();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test(timeout=500)
	@Category(UnitTests.class)
	public void testBackgroundCalls() throws UserNotFoundException {
		assertEquals(null, manager.get("a", c->{}));
		assertEquals(false, manager.put(new UserProfile("a"), c->{}));
		assertEquals(false, manager.update(new UserProfile("a"), c->{}));
		assertEquals(false, manager.delete(new UserProfile("a"), c->{}));
	}

	@Test
	@Category(UnitTests.class)
	public void testGet() throws UserNotFoundException, InterruptedException {
		UserProfile pf = manager.get("alexaxa", null);
		assertEquals(25, pf.getRating());
		assertEquals(5, pf.getNumOfReviews());
		assertEquals("alexaxa", pf.getUsername());
	}
	
	@Test
	@Category(UnitTests.class)
	public void testPut() {
		assertEquals(true, manager.put(user1, null));
		Mockito.verify(db).put(UserProfileManager.DATABASE_CLASS, m_noid);
	}
	
	@Test
	@Category(UnitTests.class)
	public void testUpdate() {
		assertEquals(true, manager.update(user1, null));
		Mockito.verify(db).update(UserProfileManager.DATABASE_CLASS, "MY_ID", m_noid);
	}
	
	@Test
	@Category(UnitTests.class)
	public void testDelete() {
		assertEquals(true, manager.delete(user1, null));
		Mockito.verify(db).delete(UserProfileManager.DATABASE_CLASS, "MY_ID");
	}
	
	@Test
	@Category(UnitTests.class)
	public void testMostHelpful() {
		List<UserProfile> lu = manager.mostHelpful(1, null);
		assertEquals(1, lu.size());
		Mockito.verify(db).getHighestBy(UserProfileManager.DATABASE_CLASS, UserProfileManager.RATING_FIELD, 1);
	}
	
	@Test
	@Category(UnitTests.class)
	public void testCount() {
		assertEquals(1, manager.userCount(null).intValue());
		Mockito.verify(db).countEntries(UserProfileManager.DATABASE_CLASS);
	}

	
	public static void setUpMock(){
		db = Mockito.mock(Database.class);
		m = new HashMap<>();
		m.put(UserProfileManager.ID_FIELD_NAME, "MY_ID");
		m.put(UserProfileManager.USERNAME_FIELD, "alexaxa");
		m.put(UserProfileManager.RATING_FIELD, 25);
		m.put(UserProfileManager.NUM_OF_REVIEWS_FIELD, 5);
		m_noid = new HashMap<>(m);
		m_noid.remove(UserProfileManager.ID_FIELD_NAME);
		List<Map<String, Object>> l = new ArrayList<>();
		l.add(m);
		Mockito.when(db.get(Mockito.anyString(), Mockito.anyMap())).thenReturn(l);
		Mockito.when(db.update(Mockito.anyString(), Mockito.anyString(), Mockito.anyMap())).thenReturn(true);
		Mockito.when(db.delete(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
		Mockito.when(db.getHighestBy(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt())).thenReturn(l);
		Mockito.when(db.countEntries(Mockito.anyString())).thenReturn(1);
		
		m_not = new HashMap<>();
		m_not.put(UserProfileManager.USERNAME_FIELD, "I_DONT_EXIST");	
		Mockito.when(db.get(UserProfileManager.DATABASE_CLASS, m_not)).thenReturn(new ArrayList<>());
		user1 = UserProfileManager.fromMap(m);
	}
	
	public static class DatabaseTestModule extends AbstractModule {
		@Override
		protected void configure() {
			bind(Database.class).toInstance(db);
		}
	}

}
