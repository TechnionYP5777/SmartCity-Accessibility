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
	private String locationID; // the location id in the db - doesn't have to be
								// added to the default constractor
	private List<ReviewComment> comments = new ArrayList<ReviewComment>();

	public Review(Location l, int r, String c, User u) {
		this.location = l;
		this.rating = new Score(r);
		this.content = c;
		this.user = u.getName();
		this.isPinned = false;
	}

	/**
	 * implemented for the DB functionality
	 * 
	 * @param l
	 * @param r
	 * @param c
	 * @param u
	 */
	public Review(Location l, int r, String c, String u) {
		this.location = l;
		this.rating = new Score(r);
		this.content = c;
		this.user = u;
		this.isPinned = false;
	}

	/**
	 * implemented for the DB functionality
	 * 
	 * @param l
	 * @param r
	 * @param c
	 * @param u
	 */
	public Review(String l, int r, String c, String u) {
		this.locationID = l;
		this.rating = new Score(r);
		this.content = c;
		this.user = u;
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

	public String getLocationID() {
		return this.locationID;
	}

	public void pin(User ¢) throws UnauthorizedAccessException {
		if (!isAccessAllowed(¢))
			throw (new UnauthorizedAccessException(Privilege.minPinLevel()));
		this.isPinned = true;
	}

	/**
	 * implemented for the DB, hence only admin can preform
	 * 
	 * @param u
	 * @param l
	 * @throws UnauthorizedAccessException
	 */
	public void locationSet(User u, Location l) throws UnauthorizedAccessException {
		if (!isAccessAllowed(u))
			throw (new UnauthorizedAccessException(Privilege.minPinLevel()));
		this.location = l;
	}

	private boolean isAccessAllowed(User ¢) {
		return Privilege.pinPrivilegeLevel(¢);
	}

	public void unPin(User ¢) throws UnauthorizedAccessException {
		if (!isAccessAllowed(¢))
			throw (new UnauthorizedAccessException(Privilege.minPinLevel()));
		this.isPinned = false;
	}

	public boolean isPinned() {
		return this.isPinned;
	}

	/**
	 * @return the user name
	 */
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
	public void upvote(User ¢) throws UnauthorizedAccessException {
		comment(¢, ReviewComment.POSITIVE_RATING);
	}

	/**
	 * @author KaplanAlexander
	 * @throws UnauthorizedAccessException
	 */
	public void downvote(User ¢) throws UnauthorizedAccessException {
		comment(¢, ReviewComment.NEGATIVE_RATING);
	}

	/**
	 * @author KaplanAlexander
	 * @throws UnauthorizedAccessException
	 */
	protected void comment(User u, int rating) throws UnauthorizedAccessException {
		if (!Privilege.commentReviewPrivilegeLevel(u))
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
			public boolean test(ReviewComment ¢) {
				return (¢.getRating() == rating);
			}
		}).collect(Collectors.toList()));
	}

	@Override
	public boolean equals(Object ¢) {
		if (¢ == this)
			return true;

		if (!(¢ instanceof Review))
			return false;

		Review $ = (Review) ¢;

		return $.location.equals(this.location) && $.user.equals(this.user) && $.rating.equals(this.rating)
				&& $.content.equals(this.content);
	}

}
