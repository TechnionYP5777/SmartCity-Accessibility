package smartcity.accessibility.socialnetwork;

import java.util.List;

/**
 * 
 * @author KaplanAlexander
 *
 */
public class ReviewComment {
	public static final int POSITIVE_RATING = 1;
	public static final int NEGATIVE_RATING = -1;
	
	private int rating;
	private final User commentator;
	
	public ReviewComment(User commentator){
		rating = 0;
		this.commentator = commentator;
	}
	
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
	
	/**
	 * not implemented
	 * @param comments
	 * @return
	 */
	static int summarizeComments(List<ReviewComment> comments){
		
		return 0;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ReviewComment other = (ReviewComment) o;
		if (commentator == null) {
			if (other.commentator != null)
				return false;
		} else if (!commentator.equals(other.commentator))
			return false;
		return true;
	}


	
	
	
	

}
