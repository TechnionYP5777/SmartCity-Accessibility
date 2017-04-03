package smartcity.accessibility.socialnetwork;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import smartcity.accessibility.categories.UnitTests;

/**
 * @author Koral Chapnik
 */
public class ScoreTest {
	private static final int TEST_MIN_SCORE = 1;
	private static final int TEST_MAX_SCORE = 5;

	@Test
	@Category(UnitTests.class)
	public void testGetScore() {
		assert ((new Score(5)).getScore() == 5);
		assert (new Score(3)).getScore() == 3;
	}
	
	@Test
	@Category(UnitTests.class)
	public void testGetMinMaxScore() {
		assert (Score.getMinScore() == TEST_MIN_SCORE);
		assert (Score.getMaxScore() == TEST_MAX_SCORE);
	}
	
}
