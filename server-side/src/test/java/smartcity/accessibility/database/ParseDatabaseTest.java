package smartcity.accessibility.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
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
import org.parse4j.ParseGeoPoint;
import org.parse4j.ParseObject;
import org.parse4j.ParseUser;

import smartcity.accessibility.categories.NetworkTests;
import smartcity.accessibility.database.exceptions.ObjectNotFoundException;

public class ParseDatabaseTest {
	private static ParseDatabase pd;
	private static String databaseClass = "DatabaseManagerTestClass";

	private static Map<String, Map<String, Object>> testObjects;
	private static String sampleObjectId = null;

	@Rule
	public Timeout globalTimeout = Timeout.seconds(30);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ParseUser.currentUser = new ParseUser();
		pd = ParseDatabase.get();
		initTestObjects();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		deleteTestObjects();
	}


	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	@Category(NetworkTests.class)
	public void testGet() throws ObjectNotFoundException {
		Map<String, Object> res = pd.get(databaseClass, sampleObjectId);
		for (Entry<String, Object> e : testObjects.get(sampleObjectId).entrySet()) {
			if(e.getKey().equals("location"))
				continue;
			assertEquals(e.getValue(), res.get(e.getKey()));
		}
	}
	
	@Test
	@Category(NetworkTests.class)
	public void testGetList() {
		List<Map<String, Object>> res = pd.get(databaseClass, testObjects.get(sampleObjectId));
		for (Map<String, Object> m : res) {
			if(m.get("objectId").equals(sampleObjectId))
				return;
		}
		fail("couldn't find the sample object");
	}
	
	@Test
	@Category(NetworkTests.class)
	public void testGetLocation() {
		List<Map<String, Object>> res = pd.get(databaseClass, "location", 0.1, 0.1, 1.0);
		for (Map<String, Object> m : res) {
			if(m.get("objectId").equals(sampleObjectId))
				return;
		}
		fail("couldn't find the sample object");
	}

	@Test
	@Category(NetworkTests.class)
	public void testPut() throws ObjectNotFoundException {
		Map<String, Object> object1 = testObjects.get(sampleObjectId);

		String id = pd.put(databaseClass, object1);
		Map<String, Object> res = pd.get(databaseClass, id);
		testObjects.put(id, res);
		for (Entry<String, Object> e : object1.entrySet()) {
			if(e.getKey().equals("location"))
				continue;
			assertEquals(e.getValue(), res.get(e.getKey()));
		}
	}

	@Test
	@Category(NetworkTests.class)
	public void testUpdate() throws ObjectNotFoundException {
		Map<String, Object> object1 = testObjects.get(sampleObjectId);

		String id = pd.put(databaseClass, object1);
		Map<String, Object> m = new HashMap<>();
		m.put("test1", 6);
		assertTrue(pd.update(databaseClass, id, m));
		
		Map<String, Object> res = pd.get(databaseClass, id);
		testObjects.put(id, res);
		for (Entry<String, Object> e: object1.entrySet()){
			if(e.getKey().equals("test1"))
				assertEquals(6, res.get(e.getKey()));
			else {
				if(e.getKey().equals("location"))
					continue;
				assertEquals(e.getValue(), res.get(e.getKey()));
			}
		}
	}
	
	@Test
	@Category(NetworkTests.class)
	public void testDelete() {
		Map<String, Object> object1 = testObjects.get(sampleObjectId);

		String id = pd.put(databaseClass, object1);
		assertTrue(pd.delete(databaseClass, id));
		try {
			pd.get(databaseClass, id);
		} catch (ObjectNotFoundException e){
			return;
		}
		fail("object not deleted from db");
	}
	
	@Test
	@Category(NetworkTests.class)
	public void testCount() {
		assertEquals(testObjects.size() ,pd.countEntries(databaseClass));
	}
	
	@Test
	@Category(NetworkTests.class)
	public void testGetHighestBy() {
		Map<String, Object> object1 = new HashMap<>();
		object1.put("test1", 1);
		object1.put("test2", "val2");
		object1.put("test3", 66);
		String id = pd.put(databaseClass, object1);
		testObjects.put(id, object1);
		
		List<Map<String, Object>> lm = pd.getHighestBy(databaseClass, "test3", 1);
		assertEquals(1, lm.size());
		assertEquals(id, lm.get(0).get("objectId"));
	}

	public static void initTestObjects() throws ParseException {
		testObjects = new HashMap<>();
		Map<String, Object> object1 = new HashMap<>();
		object1.put("test1", 1);
		object1.put("test2", "val2");
		object1.put("test3", 3);
		object1.put("location", new ParseGeoPoint(0.1,0.1));
		final ParseObject po1 = new ParseObject(databaseClass);
		for (Entry<String, Object> e : object1.entrySet()) {
			po1.put(e.getKey(), e.getValue());
		}
		po1.save();
		testObjects.put(po1.getObjectId(), object1);

		sampleObjectId = po1.getObjectId();
	}
	
	private static void deleteTestObjects() {
		for ( String s : testObjects.keySet()){
			boolean succ = false;
			for (int i= 0 ; i< 3; i++){
				if (pd.delete(databaseClass, s)){
					succ = true;
					break;
				}
			}
			if (!succ)
				fail("Failed to delete objects from test class, delete manually");
		}
	}


}
