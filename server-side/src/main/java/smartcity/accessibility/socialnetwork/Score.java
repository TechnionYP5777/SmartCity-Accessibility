package smartcity.accessibility.socialnetwork;

/**
 * @author Koral Chapnik
 * This class represents a score given by the social network's users to some location
 * The more low the score is - the less accessible the location is
 */
public class Score {
	private int score;
	private static int MIN_SCORE = 1;
	private static int MAX_SCORE = 5;

	public Score(int s) {
		this.score = s;
	}

	public int getScore() {
		return this.score;
	}

	public void setScore(int o) {
		this.score = o;
	}

	public static int getMinScore() {
		return MIN_SCORE;
	}

	public static int getMaxScore() {
		return MAX_SCORE;
	}

	@Override
	public boolean equals(Object ¢) {
		return ¢ == this || (¢ instanceof Score && ((Score) ¢).getScore() == this.score);
	}

}
