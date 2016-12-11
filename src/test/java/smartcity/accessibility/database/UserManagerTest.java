package smartcity.accessibility.database;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

/**
 * @author Kolikant
 *
 */
public class UserManagerTest {
	@BeforeClass
	public static void init(){
		DatabaseManager.initialize();
	}
	
	@Test
	public void test() {
		ParseObject test1 = new ParseObject("test1");
		test1.put("field1", 123);
		try {
			test1.save();
		} catch (ParseException e) {
			e.printStackTrace();
			fail("could not save test object");
		}
		
		ParseObject test2 = new ParseObject("test1");
		test2.put("test1", 0);
		try {
			test2.save();
		} catch (ParseException e) {
			e.printStackTrace();
			fail("could not save test object");
		}
		
		ParseQuery.getQuery("test1");
		// test
	}
}
