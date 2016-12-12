package smartcity.accessibility.socialnetwork;

import java.util.ArrayList;
import java.util.List;

import smartcity.accessibility.mapmanagement.Location;

/**
 * 
 * @author Koral Chapnik
 * This class represents a review of some location
 */
public class Review {
	
	private Location location;
	private User user;
	private Score rating;
	private String comment;
	private List<ReviewComment> comments = new ArrayList<ReviewComment>();
	
	public Review(Location l , int r, String c, User u) {
		this.location = l;
		this.rating = new Score(r);
		this.comment = c;
		this.user = u;
	}
	
	public Score getRating() {
		return this.rating;
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	public String getComment() {
		return this.comment;
	}
	
	public User getUser() {
		return this.user;
	}
	
	/**
	 * @author AlexanderKaplan
	 */
	public int calculateOpinion(){
		return ReviewComment.summarizeComments(comments);
	}
	
	/**
	 * @author KaplanAlexander
	 */
	public void like(User u){
		comment(u,ReviewComment.POSITIVE_RATING);
	}
	
	/**
	 * @author KaplanAlexander
	 */
	public void dislike(User u){
		comment(u,ReviewComment.NEGATIVE_RATING);
	}
	
	/**
	 * @author KaplanAlexander
	 */
	protected void comment(User u, int rating){
		if(comments.contains(new ReviewComment(u)))
			comments.remove(new ReviewComment(u));
		comments.add(new ReviewComment(rating,u));
	}
	
	
}
