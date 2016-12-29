package smartcity.accessibility.socialnetwork;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Koral Chapnik
 */
public class ScoreTest {
	private static final int TEST_MIN_SCORE = 0;
	private static final int TEST_MAX_SCORE = 5;
	
	@Test
	public void testGetScore() {
		Score s = null;
		s = new Score(5);
		assert(s.getScore() == 5);
	}
	
//	@Test
//	public void testSetScore() {
//		Score newScore = null;
//			newScore = new Score(4);
//		
//		
//		try {
//			newScore.setScore(100);
//			Assert.fail("should't create the score cause 100 exceeds valis values");
//		} catch (ScoreNotInRangeException e) {
//		}
//		
//		try {
//			newScore.setScore(2);
//		} catch (ScoreNotInRangeException e) {
//			Assert.fail("should't throw an exception cause 2 is legal value");
//		}
//	}
//	
	@Test
	public void testGetMinMaxScore() {
		assert(Score.getMinScore() == TEST_MIN_SCORE);
		assert(Score.getMaxScore() == TEST_MAX_SCORE);
	}
}
