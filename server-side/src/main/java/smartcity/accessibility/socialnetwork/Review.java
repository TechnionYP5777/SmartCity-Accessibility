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
 * @author Koral Chapnik 
 * This class represents a review of some location
 */
public class Review {

	private Location location;
	private UserProfile user;
	private Score rating;
	private String content;
	private boolean isPinned;
	private String locationID; // the location id in the db - doesn't have to be
								// added to the default constractor
	private List<ReviewComment> comments = new ArrayList<ReviewComment>();

	public Review(int r, String c, UserProfile u) {
		this.rating = new Score(r);
		this.content = c;
		this.user = u;
		this.isPinned = false;
		u.addReview();
	}
	
	public Review(Location l, int r, String c, UserProfile u) {
		this(r, c, u);
		this.location = l;
	}



	/**
	 * implemented for the DB functionality
	 */
	public Review(String l, int r, String c, UserProfile u) {
		this(r, c, u);
		this.locationID = l;
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
	
	

	/**
	 * This method pins the review.
	 * Only admin is allowed to use it
	 * @param ¢ - the user who wants to pin the review
	 * @throws UnauthorizedAccessException - if the user is not an admin
	 */
	public void pin(User ¢) throws UnauthorizedAccessException {
		checkPermissions(¢);
		this.isPinned = true;
	}
	
	/**
	 * This method pins the review.
	 * Only admin is allowed to use it
	 * @param ¢ - the user who wants to pin the review
	 * @throws UnauthorizedAccessException - if the user is not an admin
	 */
	public void unPin(User ¢) throws UnauthorizedAccessException {
		checkPermissions(¢);
		this.isPinned = false;
	}

	private void checkPermissions(User ¢) throws UnauthorizedAccessException {
		if (!isAccessAllowed(¢))
			throw (new UnauthorizedAccessException(Privilege.minPinLevel()));
	}

	/**
	 * implemented for the DB, hence only administrator can performs
	 */
	public void locationSet(User u, Location l) throws UnauthorizedAccessException {
		if (!isAccessAllowed(u))
			throw (new UnauthorizedAccessException(Privilege.minPinLevel()));
		this.location = l;
	}

	private boolean isAccessAllowed(User ¢) {
		return Privilege.pinPrivilegeLevel(¢);
	}

	public boolean isPinned() {
		return this.isPinned;
	}

	/**
	 * @return the user
	 */
	public UserProfile getUser() {
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
		u.getProfile().upvote();
		//TODO: update the DB
	}

	/**
	 * @author KaplanAlexander
	 * @throws UnauthorizedAccessException
	 */
	public void downvote(User u) throws UnauthorizedAccessException {
		comment(u, ReviewComment.NEGATIVE_RATING);
		u.getProfile().downvote();
		//TODO: update the DB
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
