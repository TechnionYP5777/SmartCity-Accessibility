package smartcity.accessibility.socialnetwork;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import smartcity.accessibility.exceptions.UnauthorizedAccessException;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.socialnetwork.User.Privilege;

/**
 * 
 * @author Koral Chapnik This class represents a review of some location
 */
public class Review {

	private Location location;
	private String user;
	private Score rating;
	private String content;
	private boolean isPinned;
	private List<ReviewComment> comments = new ArrayList<ReviewComment>();

	public Review(Location l, int r, String c, User u) {
		this.location = l;
		this.rating = new Score(r);
		this.content = c;
		this.user = u.getName();
		this.isPinned = false;
	}

	public Score getRating() {
		return this.rating;
	}

	public Location getLocation() {
		return this.location;
	}

	public String getContent() {
		return this.content;
	}
	
	public void pin(User u) throws UnauthorizedAccessException {
		if (!Privilege.pinPrivilegeLevel(u))
			if (!isAccessAllowed(u))
				throw (new UnauthorizedAccessException(Privilege.minPinLevel()));
		this.isPinned = true;
	}
	
	private boolean isAccessAllowed(User u) {
		return Privilege.pinPrivilegeLevel(u);
	}
	
	public void unPin(User u) throws UnauthorizedAccessException {
		if (!isAccessAllowed(u))
			if (!isAccessAllowed(u))
				throw (new UnauthorizedAccessException(Privilege.minPinLevel()));
		this.isPinned = false;
	}

	public boolean isPinned() {
		return this.isPinned;
	}
	public String getUser() {
		return this.user;
	}

	/**
	 * @author AlexanderKaplan
	 */
	public int calculateOpinion() {
		return ReviewComment.summarizeComments(comments);
	}

	/**
	 * @author KaplanAlexander
	 * @throws UnauthorizedAccessException 
	 */
	public void upvote(User u) throws UnauthorizedAccessException {
		comment(u, ReviewComment.POSITIVE_RATING);
	}

	/**
	 * @author KaplanAlexander
	 * @throws UnauthorizedAccessException 
	 */
	public void downvote(User u) throws UnauthorizedAccessException {
		comment(u, ReviewComment.NEGATIVE_RATING);
	}

	/**
	 * @author KaplanAlexander
	 * @throws UnauthorizedAccessException 
	 */
	protected void comment(User u, int rating) throws UnauthorizedAccessException {
		if(!Privilege.commentReviewPrivilegeLevel(u))
			throw (new UnauthorizedAccessException(Privilege.minCommentLevel()));
		if (comments.contains(new ReviewComment(u)))
			comments.remove(new ReviewComment(u));
		comments.add(new ReviewComment(rating, u));
	}
	
	/**
	 * @author KaplanAlexander
	 */
	public int getUpvotes() {
		return getComments(ReviewComment.POSITIVE_RATING);
	}

	/**
	 * @author KaplanAlexander
	 */
	public int getDownvotes() {
		return getComments(ReviewComment.NEGATIVE_RATING);
	}

	/**
	 * @author KaplanAlexander
	 */
	protected int getComments(int rating) {
		return ReviewComment.summarizeComments(comments.stream().filter(new Predicate<ReviewComment>() {
			@Override
			public boolean test(ReviewComment c) {
				return (c.getRating() == rating);
			}
		}).collect(Collectors.toList()));
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;

		if (!(o instanceof Review))
			return false;

		Review $ = (Review) o;

		return $.location.equals(this.location) && $.user.equals(this.user) && $.rating.equals(this.rating)
				&& $.content.equals(this.content);
	}

}
