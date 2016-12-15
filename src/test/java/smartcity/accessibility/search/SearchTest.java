package smartcity.accessibility.search;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.naming.directory.SearchResult;

import org.junit.Test;

/**
 * @author Kolikant
 *
 */
public class SearchTest {

	@Test
	public void test() {
		SearchQuery s = new SearchQuery("Tel-Aviv, Weizmann St, 1");
		SearchQueryResult sr = s.Search();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			fail();
		}
		sr.showResults();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			fail();
		}
	}
	
}
