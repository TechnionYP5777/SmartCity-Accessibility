package smartcity.accessibility.socialnetwork;

/**
 * 
 * @author Koral Chapnik
 * This class aimed to calculate the user's helpfulness in choosing the best reviews
 * likes - the number of total likes the user's reviews has
 *
 */
public class Helpfulness {
	private int likes;
	private int dislikes;
	private int numOfReviews;
	
	public Helpfulness() {
		likes = 0;
		dislikes = 0;
		numOfReviews = 0;
	}
	
	public void incLikes() {
		likes++;
	}
	
	public void incDislikes() {
		dislikes++;
	}
	
	public void incNumOfReviews() {
		numOfReviews++;
	}
	
	int getHelpfulness(User u) {
		
		return 0;
	}
}
