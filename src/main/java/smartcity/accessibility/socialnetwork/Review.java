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
	private int rating;
	private String comment;
	private List<ReviewComment> comments = new ArrayList<ReviewComment>();
	
	public Review(Location location , int rating, String comment, User u) {
		this.location = location;
		this.rating = rating;
		this.comment = comment;
		this.user = u;
	}
	
	public int getRating() {
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
	 * Kolikant
	 */
	public void like(User u){
		comment(u,ReviewComment.POSITIVE_RATING);
	}
	
	/**
	 * Kolikant
	 */
	public void dislike(User u){
		comment(u,ReviewComment.NEGATIVE_RATING);
	}
	
	protected void comment(User u, int rating){
		if(comments.contains(new ReviewComment(u)))
			comments.remove(new ReviewComment(u));
		comments.add(new ReviewComment(rating,u));
	}
	
	
}
