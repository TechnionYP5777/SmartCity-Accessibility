package smartcity.accessibility.socialnetwork;

import java.util.List;

import smartcity.accessibility.database.AbstractUserProfileManager;

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
	
	/**
	 * The comment itself.
	 * @author ArthurSap
	 */
	private String comment = "";

	public ReviewComment(UserProfile commentator) {
		rating = 0;
		this.commentator = commentator;
	}

	public ReviewComment(int rating, UserProfile commentator) {
		this.rating = rating;
		this.commentator = commentator;
	}
	
	/**
	 * This constructor is for "real" comments (rating is 0 because "real" comments should'nt
	 * conatin rating but only text comment).
	 * @param content - the content of the comment
	 * @author ArthurSap
	 */
	public ReviewComment(String content, UserProfile commentator){
		this.rating = 0;
		this.commentator = commentator;
		this.comment = content;
	}

	/**
	 * @author ArthurSap
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @author ArthurSap
	 */
	public void setComment(String comment) {
		this.comment = comment;
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
		int i = 0;
		for (ReviewComment ¢ : cs)
			i += ¢.getRating();
		return i;
	}
	

	@Override
	public int hashCode() {
		return 31 * (((commentator == null) ? 0 : commentator.hashCode()) + 31 * (((comment == null) ? 0 : comment.hashCode()) + 31)) + rating;
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
		//Commentator is the current commentator
		/*
		 * We want a commentator to be able to comment AND rate. So if the "this" comment is
		 * rating comment (or vice versa) and the other is text comment we want to retain
		 * both comments (so they not "equal")
		 */
		if((rating == 0 && other.rating != 0) || (rating != 0 && other.rating == 0 ))
			return false;
		return true;
	}

	public static String serialize(ReviewComment rc) {
		return rc.commentator.getUsername() + "#" + rc.rating;
	}

	public static ReviewComment deserialize(String s) {
		if (s.contains("#")) {
			String[] ls = s.split("#");
			if (ls.length >= 2) {
				Integer i = tryParse(ls[1]);
				if (i!= null)
					return new ReviewComment(i,
						AbstractUserProfileManager.instance().get(ls[0], null));
			}
		}
		return new ReviewComment(AbstractUserProfileManager.instance().get(s.replace("#", ""), null));

	}

	public static Integer tryParse(String text) {
		try {
			return Integer.parseInt(text);
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
