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
	private String user;
	private Score rating;
	private String content;
	private List<ReviewComment> comments = new ArrayList<ReviewComment>();
	
	public Review(Location l , int r, String c, User u) {
		this.location = l;
		this.rating = new Score(r);
		this.content = c;
		this.user = u.getName();
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
	
	public String getUser() {
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
	public void upvote(User u){
		comment(u,ReviewComment.POSITIVE_RATING);
	}
	
	/**
	 * @author KaplanAlexander
	 */
	public void downvote(User u){
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
	
	 @Override
	    public boolean equals(Object o) {
	        if (o == this) {
	            return true;
	        }

	        if (!(o instanceof Review)) {
	            return false;
	        }
	 
	        Review r = (Review) o;
	 
	        return r.location.equals(this.location) &&
	        		r.user.equals(this.user) &&
	        		r.rating.equals(this.rating) &&
	        		r.content.equals(this.content);
	    }

}
