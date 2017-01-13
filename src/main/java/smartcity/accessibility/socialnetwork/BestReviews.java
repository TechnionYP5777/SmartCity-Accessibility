package smartcity.accessibility.socialnetwork;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * @author Koral Chapnik
 * This class is responsible for getting the best n reviews from
 * a given reviews list.
 */
public class BestReviews {
	private int n;
	private List<Review> reviews;
	
	/**
	 * @param n - the number of best reviews to return from this class methods
	 * @param r - the list of reviews from which we need to choose the best reviews
	 */
	public BestReviews(int n, List<Review> r) {
		this.n = n;
		this.reviews = r;
	}
	
	/**
	 * @return the n reviews with the highest rating
	 */
	public List<Review> getMostRated() {
		reviews.sort((Review r1, Review r2) -> r2.getRating().getScore() - r1.getRating().getScore());
		return reviews.subList(0, n);
	}
	
	public int getTotalRating() {
		List<Review> mostRated = getMostRated();
		return mostRated.stream().collect(Collectors.summingInt(a -> a.getRating().getScore())) / mostRated.size();
	}
	
	public void setN(int n) {
		this.n = n;
	}
	
	public List<Review> getReviews() {
		return this.reviews;
	}
}
