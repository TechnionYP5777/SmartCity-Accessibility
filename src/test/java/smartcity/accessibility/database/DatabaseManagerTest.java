
package smartcity.accessibility.database;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;

/**
 * @author KaplanAlexander
 *
 */
public class DatabaseManagerTest {
	
	@BeforeClass
	public static void init(){
		DatabaseManager.initialize();
	}

	@Test
	public void test() {
		ParseObject test = new ParseObject("testytest");
		test.put("test1", 123);
		test.put("test2", "is good");
		try {
			test.save();
		} catch (ParseException e) {
			e.printStackTrace();
			fail("could not save test object");
		}
		// test 4
	}

}
