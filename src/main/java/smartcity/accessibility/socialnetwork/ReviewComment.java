package smartcity.accessibility.socialnetwork;

/**
 * 
 * @author KaplanAlexander
 *
 */
public class ReviewComment {
	private int rating;
	private final User commentator;
	
	public ReviewComment(int rating,User commentator){
		this.rating = rating;
		this.commentator = commentator;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public User getCommentator() {
		return commentator;
	}
	
	

}
