package smartcity.accessibility.socialnetwork;

/**
 * 
 * @author Koral Chapnik
 * This class aimed to calculate the user's helpfulness in choosing the best reviews
 * likes - the number of total likes the user's reviews has
 *
 */
public class Helpfulness {
	private int rating;
	private int numOfReviews;
	
	public Helpfulness() {
		rating = 0;
		numOfReviews = 0;
	}
	
	public void upvote(){
		rating++;
	}
	
	public void downvote(){
		rating--;
	}
	
	public void addReview() {
		numOfReviews++;
	}
	
	public void removeReview() {
		numOfReviews--;
	}
	
	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public int getNumOfReviews() {
		return numOfReviews;
	}

	public void setNumOfReviews(int numOfReviews) {
		this.numOfReviews = numOfReviews;
	}

	/*
	 * This method returns the average likes per comment.
	 * negative value represents dislikes.
	 */
	private double getAvgLikes() {
		return Math.ceil((double)(rating) / numOfReviews);
	}
	
	public Double helpfulness() {
		return getAvgLikes();
	}
}
