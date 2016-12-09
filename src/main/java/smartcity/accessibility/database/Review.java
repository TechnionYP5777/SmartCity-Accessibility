package smartcity.accessibility.database;

import smartcity.accessibility.navegation.Route;

/**
 * 
 * @author Koeal Chapnik
 * This class represents a review of some route
 */
public class Review {
	
	private Route route;
	private int rating;
	private String comment;
	
	public Review(Route route, int rating, String comment) {
		this.route = route;
		this.rating = rating;
		this.comment = comment;
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
	
}
