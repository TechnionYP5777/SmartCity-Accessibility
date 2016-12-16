
package smartcity.accessibility.database;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.BeforeClass;
import org.junit.Test;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.callback.GetCallback;
import org.parse4j.callback.SaveCallback;

/**
 * @author KaplanAlexander
 *
 */
public class DatabaseManagerTest {

	public String testParseClass = "DatabaseManagerTestClass";


	@BeforeClass
	public static void init() {
		DatabaseManager.initialize();
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
		ParseObject pe = DatabaseManager.getValue(testParseClass, "0ZunSaeCSE");
		assertNotNull(pe);
		assertEquals("res1", pe.get("test2"));
		assertEquals(65, pe.get("test1"));
	}

	@Test(timeout = 20000) // fail after 20 seconds, means callback wasnt called
	public void c() {
		final AtomicInteger res = new AtomicInteger();

		DatabaseManager.getValue(testParseClass, "0ZunSaeCSE", new GetCallback<ParseObject>() {
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
		while (value==0)
			value = res.getAndSet(0);

		assertEquals(1, value);
	}
	
	@Test
	public void d(){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("a", "b");
		m.put("c",5);
		m.put("e", false);
		ParseObject o = null;
		try {
			o = DatabaseManager.putValue(testParseClass, m);
		} catch (ParseException e) {
			e.printStackTrace();
			fail("failed to put value to server");
		}
		assertNotNull(o);

		assertEquals(m.get("a"),o.get("a"));
		assertEquals(m.get("c"),o.get("c"));
		assertEquals(m.get("e"),o.get("e"));
		
	}
	
	@Test(timeout=20000)
	public void e(){
		final AtomicInteger res = new AtomicInteger();
		
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("a", "b");
		m.put("c", 5);
		m.put("e", false);
		DatabaseManager.putValue(testParseClass, m, new SaveCallback() {
			
			@Override
			public void done(ParseException arg0) {
				if(arg0!=null)
					res.compareAndSet(0, -1);
				res.compareAndSet(0, 1);
				
			}
		});
		
		int value = 0;
		while (value==0)
			value = res.getAndSet(0);
		
		assertEquals(1,value);
	}

}
