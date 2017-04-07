package smartcity.accessibility.database;

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
import smartcity.accessibility.categories.NetworkTests;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.socialnetwork.Review;

public class ReviewManager2Test {
	private static ReviewManager2 rm;
	protected static Database db;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		db = Mockito.mock(Database.class);
		Injector injector = Guice.createInjector(new DatabaseModule());
		rm = injector.getInstance(ReviewManager2.class);
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
	public void testUpload() {
		Review r = new Review(new Location(), 5, "asdfasd", "asdfas");
		rm.uploadReview(r);
		Mockito.verify(db).put(Mockito.any(), Mockito.any());
	}

	public static class DatabaseModule extends AbstractModule {
		@Override
		protected void configure() {
			bind(Database.class).toInstance(db);
		}
	}

}
