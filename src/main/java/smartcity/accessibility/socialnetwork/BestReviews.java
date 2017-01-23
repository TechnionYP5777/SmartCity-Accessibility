package smartcity.accessibility.socialnetwork;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import smartcity.accessibility.mapmanagement.Location;

/**
 * 
 * @author Koral Chapnik
 * This class is responsible for getting the best n reviews from
 * a given reviews list.
 */
public class BestReviews {
	private int n;
	private Location l;
	public static int DEFAULT = 5;
	
	/**
	 * @param n - the number of best reviews to return from this class methods
	 * @param l - the Location we want to calculate the best n reviews for
	 */
	public BestReviews(int n, Location l) {
		this.n = n;
		this.l = l;
	}
	
	public BestReviews(Location l) {
		this.l = l;
	}
	
	/**
	 * @return a list containing the following listed by priority such that
	 * 		   the list's size will be n :
	 * 		   1. pinned reviews
	 * 		   2. most rated unPinned reviews
	 */
	public List<Review> getMostRated() {
		List<Review> $ = l.getPinnedReviews();
		$.sort((Review r1, Review r2) -> r2.getRating().getScore() - r1.getRating().getScore());
		List<Review> unPinnedReviews = l.getNotPinnedReviews();
		unPinnedReviews.sort((Review r1, Review r2) -> r2.getRating().getScore() - r1.getRating().getScore());
	
		if ($.isEmpty())
			return unPinnedReviews.size() < n ? unPinnedReviews : unPinnedReviews.subList(0, n);
		if ($.size() >= n )
			return $.subList(0, n); 
		
		List<Review> reviews = new ArrayList<Review>();
		reviews.addAll($);
		int needToAdd = n - $.size();
		if (needToAdd > unPinnedReviews.size()) 
			needToAdd = unPinnedReviews.size();
		reviews.addAll(unPinnedReviews.subList(0,needToAdd));
		return reviews;
	}
	
	/**
	 * @return the rating of the location calculated by average of the review's rating
	 */
	public int getTotalRatingByAvg() {
		List<Review> $ = getMostRated();
		return $.stream().collect(Collectors.summingInt(a -> a.getRating().getScore())) / $.size();
	}
	
	/**
	 * setting n
	 */
	public void setN(int ¢) {
		this.n = ¢;
	}
	
	public List<Review> getReviews() {
		return l.getReviews();
	}
}