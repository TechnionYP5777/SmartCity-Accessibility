package smartcity.accessibility.database;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.Timeout;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;

import smartcity.accessibility.categories.BranchTests;
import smartcity.accessibility.categories.NetworkTests;

public class ParseDatabaseTest {
	private static ParseDatabase pd;
	private static String databaseClass = "DatabaseManagerTestClass";

	private static Map<String, Map<String, Object>> testObjects;
	private static String sampleObjectId = null;

	@Rule
	public Timeout globalTimeout = Timeout.seconds(30);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ParseDatabase.initialize();
		pd = ParseDatabase.get();
		initTestObjects();
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
	@Category({ BranchTests.class, NetworkTests.class })
	public void testGet() {
		Map<String, Object> res = pd.get(databaseClass, sampleObjectId);
		for (Entry<String, Object> e : testObjects.get(sampleObjectId).entrySet()) {
			assertEquals(e.getValue(), res.get(e.getKey()));
		}
	}

	public static void initTestObjects() throws ParseException {
		testObjects = new HashMap<>();
		Map<String, Object> object1 = new HashMap<>();
		object1.put("test1", 1);
		object1.put("test2", "val2");
		object1.put("test3", 3);
		final ParseObject po1 = new ParseObject(databaseClass);
		for (Entry<String, Object> e : object1.entrySet()) {
			po1.put(e.getKey(), e.getValue());
		}
		po1.save();
		testObjects.put(po1.getObjectId(), object1);

		sampleObjectId = po1.getObjectId();
	}

}
