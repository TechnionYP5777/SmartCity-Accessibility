package smartcity.accessibility.socialnetwork;

import java.util.ArrayList;
import java.util.Collection;
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


	
	private List<ReviewComment> comments = new ArrayList<>();
	
	//Stores comments that are not up/down-votes
	private List<ReviewComment> realComments = new ArrayList<>();

	public Review(Location l, int r, String c, UserProfile u) {
		this.rating = new Score(r);
		this.content = c;
		this.user = new UserProfile(u.getUsername());
		this.user.setHelpfulness(u.getHelpfulness());
		this.user.setProfileImg(null);
		this.isPinned = false;
		u.addReview();
		this.location = l;
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

	
	public void setPinned(boolean pinned){
		isPinned = pinned;
	}

	public void setLocation(Location location) {
		this.location = location;
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
		user.upvote();
	}

	/**
	 * @author KaplanAlexander
	 * @throws UnauthorizedAccessException
	 */
	public void downvote(User u) throws UnauthorizedAccessException {
		comment(u, ReviewComment.NEGATIVE_RATING);
		user.downvote();
	}

	/**
	 * @author KaplanAlexander
	 * @throws UnauthorizedAccessException
	 */
	protected void comment(User u, int rating) throws UnauthorizedAccessException {
		if (!Privilege.commentReviewPrivilegeLevel(u))
			throw (new UnauthorizedAccessException(Privilege.minCommentLevel()));
		ReviewComment rc = new ReviewComment(rating, u.getProfile());
		if (comments.contains(rc))
			comments.remove(rc);
		comments.add(rc);
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

	public List<ReviewComment> getComments() {
		return comments;
	}
	
	/**
	 * @author ArthurSap
	 */
	public List<ReviewComment> getRealComments(){
		return realComments;
	}

	public void addComments(Collection<ReviewComment> comments) {
		this.comments.addAll(comments);
		filterRealComments();
	}
	
	/**
	 * Adds all "real" comments to realComments
	 * @author ArthurSap
	 */
	protected void filterRealComments(){
		//Real comment have rating 0 so they are the only ones we need
		for(ReviewComment rc : comments)
			if(rc.getRating() == 0)
				realComments.add(rc);
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
