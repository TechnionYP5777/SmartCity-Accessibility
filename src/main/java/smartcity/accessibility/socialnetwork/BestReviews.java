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
	
	/**
	 * @param n - the number of best reviews to return from this class methods
	 * @param l - the Location we want to calculate the best n reviews for
	 */
	public BestReviews(int n, Location l) {
		this.n = n;
		this.l = l;
	}
	
	/**
	 * @return the n reviews with the highest rating.
	 * if there are pinned reviews, then it includes them in the list.
	 */
	public List<Review> getMostRated() {
		List<Review> pinnedReviews = l.getPinnedReviews();
		pinnedReviews.sort((Review r1, Review r2) -> r2.getRating().getScore() - r1.getRating().getScore());
		List<Review> unPinnedReviews = l.getNotPinnedReviews();
		unPinnedReviews.sort((Review r1, Review r2) -> r2.getRating().getScore() - r1.getRating().getScore());
	
		if (pinnedReviews.isEmpty()) {
			return unPinnedReviews.size() < n ? unPinnedReviews : unPinnedReviews.subList(0, n);
		}
		if (pinnedReviews.size() > n ) {
			return pinnedReviews.size() < n ? pinnedReviews : pinnedReviews.subList(0, n);
		} 
		
		List<Review> reviews = new ArrayList<Review>();
		reviews.addAll(pinnedReviews);
		reviews.addAll(unPinnedReviews.subList(0, n - pinnedReviews.size()));

		return reviews;
	}
	
	/**
	 * @return the rating of the location calculated by average of the review's rating
	 */
	public int getTotalRatingByAvg() {
		List<Review> mostRated = getMostRated();
		return mostRated.stream().collect(Collectors.summingInt(a -> a.getRating().getScore())) / mostRated.size();
	}
	
	/**
	 * setting n
	 */
	public void setN(int n) {
		this.n = n;
	}
	
	public List<Review> getReviews() {
		return l.getReviews();
	}
}