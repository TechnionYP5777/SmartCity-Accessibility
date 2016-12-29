package smartcity.accessibility.socialnetwork;

import smartcity.accessibility.exceptions.ScoreNotInRangeException;

/**
 * @author Koral Chapnik
 */
public class Score {
	private int score;
	private static int MIN_SCORE;
	private static int MAX_SCORE = 5;
	
	public Score(int s) throws ScoreNotInRangeException{
		if(!isValidScore(s))
			throw new ScoreNotInRangeException();
		this.score = s;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public void setScore(int o) throws ScoreNotInRangeException {
		if(!isValidScore(o))
			throw new ScoreNotInRangeException();
		this.score = o;
	}
	
	public static int getMinScore() {
		return MIN_SCORE;
	}
	
	public static int getMaxScore() {
		return MAX_SCORE;
	}
	private boolean isValidScore(int s) {
		return s >= MIN_SCORE && s <= MAX_SCORE;
	}
    @Override
    public boolean equals(Object o) {
		return o == this || (o instanceof Score && ((Score) o).getScore() == this.score);
	}


}
