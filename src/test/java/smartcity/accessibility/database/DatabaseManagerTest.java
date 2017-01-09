
package smartcity.accessibility.database;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.parse4j.ParseException;
import org.parse4j.ParseGeoPoint;
import org.parse4j.ParseObject;
import org.parse4j.ParseUser;
import org.parse4j.callback.DeleteCallback;
import org.parse4j.callback.FindCallback;
import org.parse4j.callback.GetCallback;
import org.parse4j.callback.SaveCallback;

import com.teamdev.jxmaps.LatLng;

/**
 * @author KaplanAlexander
 *
 */
public class DatabaseManagerTest {

	public static String testParseClass = "DatabaseManagerTestClass";
	public static String id_result = "";

	@Rule
	public Timeout globalTimeout = Timeout.seconds(20);

	@BeforeClass
	public static void init() throws ParseException {
		try {
			ParseUser.currentUser = new ParseUser();
			DatabaseManager.initialize();
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("test2", "res1");
			m.put("test1", 65);
			DatabaseManager.putValue(testParseClass, m);

			final AtomicInteger res = new AtomicInteger();
			DatabaseManager.queryByFields(testParseClass, m, new FindCallback<ParseObject>() {

				@Override
				public void done(List<ParseObject> arg0, ParseException arg1) {
					id_result = arg0.get(0).getObjectId();
					res.compareAndSet(0, 1);
				}
			});
			for (int value = 0; value == 0;)
				value = res.getAndSet(0);

			m.put("test1", 65834);
			DatabaseManager.putValue(testParseClass, m);
		} catch (ParseException e) {
			// failed to communicate with the server to create base for these
			// tests
			e.printStackTrace();
			Assume.assumeFalse(true);
		}
	}

	@Test
	public void a() {
		ParseObject test = new ParseObject(testParseClass);
		test.put("test1", 123);
		test.put("test2", "is good");
		try {
			test.save();
		} catch (ParseException e) {
			e.printStackTrace();
			fail("could not save test object, means server connection failed");
		}
	}

	@Test
	public void b() {
		ParseObject pe = DatabaseManager.getValue(testParseClass, id_result);
		assertNotNull(pe);
		assertEquals("res1", pe.get("test2"));
		assertEquals(65, pe.get("test1"));
	}

	@Test(timeout = 20000) // fail after 20 seconds, means callback wasnt called
	public void c() {
		final AtomicInteger res = new AtomicInteger();

		DatabaseManager.getValue(testParseClass, id_result, new GetCallback<ParseObject>() {
			@Override
			public void done(ParseObject arg0, ParseException arg1) {
				if (arg1 != null)
					res.compareAndSet(0, -1);
				if (arg0.get("test1").equals(65))
					res.compareAndSet(0, 1);
				res.compareAndSet(0, 2);
			}
		});

		int value = 0;
		while (value == 0)
			value = res.getAndSet(0);

		assertEquals(1, value);
	}

	@Test
	public void d() {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("a", "b");
		m.put("c", 5);
		m.put("e", false);
		ParseObject o = null;
		try {
			o = DatabaseManager.putValue(testParseClass, m);
		} catch (ParseException e) {
			e.printStackTrace();
			fail("failed to put value to server");
		}
		assertNotNull(o);

		assertEquals(m.get("a"), o.get("a"));
		assertEquals(m.get("c"), o.get("c"));
		assertEquals(m.get("e"), o.get("e"));

	}

	@Test(timeout = 20000)
	public void e() {
		final AtomicInteger res = new AtomicInteger();

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("a", "b");
		m.put("c", 5);
		m.put("e", false);
		DatabaseManager.putValue(testParseClass, m, new SaveCallback() {

			@Override
			public void done(ParseException arg0) {
				if (arg0 != null)
					res.compareAndSet(0, -1);
				res.compareAndSet(0, 1);

			}
		});

		int value = 0;
		while (value == 0)
			value = res.getAndSet(0);

		assertEquals(1, value);
	}

	@SuppressWarnings("serial")
	@Test
	public void f() throws ParseException {
		final AtomicInteger res = new AtomicInteger();
		HashMap<String, Object> h = new HashMap<String, Object>() {
			{
				put("test1", 65834);
			}
		};
		DatabaseManager.putValue(testParseClass, h);

		DatabaseManager.queryByFields(testParseClass, h, new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> arg0, ParseException arg1) {
				if (arg1 != null || arg0.isEmpty()) {
					res.compareAndSet(0, -1);
					return;
				}
				final ParseObject po = arg0.get(0);
				DatabaseManager.deleteById(testParseClass, po.getObjectId(), new DeleteCallback() {

					@Override
					public void done(ParseException arg0) {
						if (arg0 != null) {
							res.compareAndSet(0, -2);
							return;
						}
						ParseObject p = DatabaseManager.getValue(testParseClass, po.getObjectId());
						if (p != null)
							res.compareAndSet(0, -3);
						res.compareAndSet(0, 1);
					}
				});

			}
		});

		int value = 0;
		while (value == 0)
			value = res.getAndSet(0);

		assertEquals(1, value);

	}

	@Test
	public void g() {
		final AtomicInteger res = new AtomicInteger();
		try {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("location", new ParseGeoPoint(32.777891, 35.021760));
			DatabaseManager.putValue(testParseClass, m);
			m.put("location", new ParseGeoPoint(32.086690, 34.789864));
			DatabaseManager.putValue(testParseClass, m);

		} catch (Exception e) {
			fail("failed to put test values to server");
		}

		DatabaseManager.queryByLocation(testParseClass, new LatLng(32.776520, 35.022962), 1,
				new FindCallback<ParseObject>() {
					@Override
					public void done(List<ParseObject> arg0, ParseException arg1) {
						boolean sol = false;

						for (ParseObject po : arg0) {
							ParseGeoPoint point = (ParseGeoPoint) po.get("location");
							if (point.getLatitude() == 32.086690 && point.getLongitude() == 34.789864)
								res.compareAndSet(0, -1);
							if (point.getLatitude() == 32.777891 && point.getLongitude() == 35.021760)
								sol = true;
						}
						if (sol)
							res.compareAndSet(0, 1);
						res.compareAndSet(0, -2);
					}
				});

		int value = 0;
		while (value == 0)
			value = res.getAndSet(0);

		assertEquals(1, value);

	}

	@Test
	public void h() {
		final AtomicInteger res = new AtomicInteger();
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("test1", 1673452);
		m.put("test2", "unique-value1");
		m.put("test3", 167);
		DatabaseManager.putValue(testParseClass, m, new SaveCallback() {
			@Override
			public void done(ParseException arg0) {
				if (arg0 != null)
					res.compareAndSet(0, -1);
				res.compareAndSet(0, 1);
			}
		});

		int value = 0;
		while (value == 0)
			value = res.getAndSet(0);

		assertEquals(1, value);

		m.remove("test3");
		DatabaseManager.getObjectByFields(testParseClass, m, new GetCallback<ParseObject>() {
			@Override
			public void done(ParseObject arg0, ParseException arg1) {
				if (arg0 == null || arg1 != null) {
					res.compareAndSet(0, -2);
					return;
				}
				if (arg0.getObjectId() == null)
					res.compareAndSet(0, -3);
				if (!arg0.get("test3").equals(167))
					res.compareAndSet(0, -4);
				DatabaseManager.deleteById(testParseClass, arg0.getObjectId());
				res.compareAndSet(0, 2);

			}
		});

		value = 0;
		while (value == 0)
			value = res.getAndSet(0);

		assertEquals(2, value);
	}

}
