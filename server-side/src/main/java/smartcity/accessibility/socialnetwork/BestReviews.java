package smartcity.accessibility.socialnetwork;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
		this.n = DEFAULT;
	}
	
	public class HelpfulnessCompare implements Comparator<Review> {

		@Override
		public int compare(Review o1, Review o2) {
			if (o2.getUser().getAvgRating() >  o1.getUser().getAvgRating())
				return 1;
			else if (o2.getUser().getAvgRating() >  o1.getUser().getAvgRating())
				return 0;
			else return o2.getRating().getScore() - o1.getRating().getScore();
		}
		
	}
	
	/**
	 * @return a list containing the following listed by priority such that
	 * 		   the list's size will be n :
	 * 		   1. pinned reviews
	 * 		   2. most rated unPinned reviews
	 */
	public List<Review> getMostRated() {
		List<Review> $ = l.getPinnedReviews();
		$.sort(new HelpfulnessCompare());
		List<Review> unPinnedReviews = l.getNotPinnedReviews();
		unPinnedReviews.sort(new HelpfulnessCompare() );
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
	 * @return the rating of the location calculated by weighted mean of the review's rating
	 */
	public int getTotalRatingByAvg() {
		List<Review> $ = getMostRated();
		double sum = 0, totalhelpfulness = 0;
		for (Review r : $) {
			double h = r.getUser().getAvgRating();
			double rating = r.getRating().getScore();
			if (h < 0) {
				// TODO : Koral, review not long holds the user, instead the UserProfile
				// I've commented out the previous code, why is the privilege level of the user who wrote the review important?
				// Did you mean to check whether the r is pinned or not?
				// -- Alex
				if (!r.isPinned())//(!Privilege.pinPrivilegeLevel(r.getUser()))
					continue;
				h = 1;
			}
			if (h == 0) 
				h++;
			sum += h * rating;
			totalhelpfulness += h;
		}
		
		int res = (int) ( sum / totalhelpfulness);	
		return  res <= 0 ? Score.getMinScore() : res ;
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
	
	public Location getLocation(){
		return l;
	}
}