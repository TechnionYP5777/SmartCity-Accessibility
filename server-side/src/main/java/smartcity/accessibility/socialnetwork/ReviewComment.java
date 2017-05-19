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
		int i = 0;
		for (ReviewComment ¢ : cs)
			i += ¢.getRating();
		return i;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commentator == null) ? 0 : commentator.hashCode());
		result = prime * result + rating;
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
		ReviewComment other = (ReviewComment) obj;
		if (commentator == null) {
			if (other.commentator != null)
				return false;
		} else if (!commentator.equals(other.commentator))
			return false;
		if (rating != other.rating)
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
				if (i != null)
					return new ReviewComment(i, AbstractUserProfileManager.instance().get(ls[0], null));
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
