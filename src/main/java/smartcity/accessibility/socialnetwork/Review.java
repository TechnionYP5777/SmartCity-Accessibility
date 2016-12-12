package smartcity.accessibility.socialnetwork;

import java.util.ArrayList;
import java.util.List;

import smartcity.accessibility.database.ReviewManager;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.navigation.Route;

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
	
	/**
	 * Kolikant
	 */
	private List<User> Likes;
	private List<User> DissLikes;
	
	public Review(Location location , int rating, String comment, User u) {
		this.location = location;
		this.rating = rating;
		this.comment = comment;
		this.user = u;
		/**
		 * Kolikant
		 */
		this.Likes = new ArrayList<User>();
		this.DissLikes = new ArrayList<User>();
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
	
	/**
	 * Kolikant
	 */
	public int calculateOpinion(){
		return Likes.size()-DissLikes.size();
	}
	
	/**
	 * Kolikant
	 */
	public void Like(User u){
		if(Likes.contains(u))
			return;
		if(DissLikes.contains(u))
			DissLikes.remove(u);
		Likes.add(u);
	}
	
	/**
	 * Kolikant
	 */
	public void DissLike(User u){
		if(DissLikes.contains(u))
			return;
		if(Likes.contains(u))
			Likes.remove(u);
		DissLikes.add(u);
	}
	
	
}
