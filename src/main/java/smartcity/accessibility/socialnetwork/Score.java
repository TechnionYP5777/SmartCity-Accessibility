package smartcity.accessibility.socialnetwork;

/**
 * @author Koral Chapnik
 */
public class Score {
	private int score;
	private static int MIN_SCORE;
	private static int MAX_SCORE = 10;
	
	public Score(int s){	
		this.score = s;
	}
	
	public int getScore(){
		return this.score;
	}
}
