package smartcity.accessibility.socialnetwork;

import java.util.ArrayList;
import java.util.List;

import smartcity.accessibility.navigation.Route;

/**
 * 
 * @author Koral Chapnik
 * This class represents a review of some route
 */
public class Review {
	
	private Route route;
	private int rating;
	private String comment;
	
	/**
	 * Kolikant
	 */
	private List<User> Likes;
	private List<User> DissLikes;
	
	public Review(Route route, int rating, String comment) {
		this.route = route;
		this.rating = rating;
		this.comment = comment;
		
		/**
		 * Kolikant
		 */
		this.Likes = new ArrayList<User>();
		this.DissLikes = new ArrayList<User>();
	}
	
	public int getRating() {
		return this.rating;
	}
	
	public Route getRoute() {
		return this.route;
	}
	
	public String getComment() {
		return this.comment;
	}
	
	/**
	 * Kolikant
	 */
	public int calculateOpinion(){
		return Likes.size()-DissLikes.size();
	}
	
	/**
	 * Kolikant
	 */
	public void Like(User ¢){
		if(Likes.contains(¢))
			return;
		if(DissLikes.contains(¢))
			DissLikes.remove(¢);
		Likes.add(¢);
	}
	
	/**
	 * Kolikant
	 */
	public void DissLike(User ¢){
		if(DissLikes.contains(¢))
			return;
		if(Likes.contains(¢))
			Likes.remove(¢);
		DissLikes.add(¢);
	}
	
}
