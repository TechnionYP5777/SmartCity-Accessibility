package smartcity.accessibility.socialnetwork;

import java.util.List;

import smartcity.accessibility.database.AbstractUserProfileManager;
import smartcity.accessibility.exceptions.UserNotFoundException;

/**
 * 
 * @author KaplanAlexander
 *
 */
public class ReviewComment {
	public static final int POSITIVE_RATING = 1;
	public static final int NEGATIVE_RATING = -1;

	private int rating;
	private final UserProfile commentator;

	public ReviewComment(UserProfile commentator) {
		rating = 0;
		this.commentator = commentator;
	}

	public ReviewComment(int rating, UserProfile commentator) {
		this.rating = rating;
		this.commentator = commentator;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public UserProfile getCommentator() {
		return commentator;
	}

	static int summarizeComments(List<ReviewComment> cs) {
		int $ = 0;
		for (ReviewComment ¢ : cs)
			$ += ¢.getRating();
		return $;
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
	
	public static String serialize(ReviewComment rc){
		return rc.commentator.getUsername()+"#"+rc.rating;
	}

	public static ReviewComment deserialize(String s) throws UserNotFoundException, NumberFormatException{
		if (s.contains("#")){
			String[] ls = s.split("#");
			if (ls.length >= 2)
				return new ReviewComment(Integer.parseInt(ls[1]), AbstractUserProfileManager.instance().get(ls[0], null));
		}
		return new ReviewComment(AbstractUserProfileManager.instance().get(s.replace("#", ""), null));
		
	}
}
