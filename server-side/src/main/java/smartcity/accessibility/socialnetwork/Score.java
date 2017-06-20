package smartcity.accessibility.socialnetwork;

/**
 * @author Koral Chapnik
 * This class represents a score given by the social network's users to some location
 * The more low the score is - the less accessible the location is
 */
public class Score {
	private int score;
	private final static int MIN_SCORE = 1;
	private final static int MAX_SCORE = 5;

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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + score;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Score other = (Score) obj;
		return score != other.score ? false : true;
	}
	
	
}
