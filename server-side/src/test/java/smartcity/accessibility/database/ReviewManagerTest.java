
package smartcity.accessibility.database;

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
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.socialnetwork.Review;
import smartcity.accessibility.socialnetwork.User;
import smartcity.accessibility.socialnetwork.UserBuilder;

public class ReviewManagerTest {
	private static ReviewManager rm;
	protected static Database db;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		db = Mockito.mock(Database.class);
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
	public void a() throws ParseException{
		
	}

	public static class DatabaseModule extends AbstractModule {
		@Override
		protected void configure() {
			bind(Database.class).toInstance(db);
		}
	}

}

