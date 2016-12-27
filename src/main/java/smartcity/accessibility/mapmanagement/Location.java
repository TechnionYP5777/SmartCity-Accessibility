package smartcity.accessibility.mapmanagement;

import java.util.ArrayList;
import org.parse4j.ParseException;

import com.teamdev.jxmaps.LatLng;
import com.teamdev.jxmaps.swing.MapView;

import smartcity.accessibility.database.ReviewManager;
import smartcity.accessibility.exceptions.ScoreNotInRangeException;
import smartcity.accessibility.search.SearchQuery;
import smartcity.accessibility.search.SearchQueryResult;
import smartcity.accessibility.socialnetwork.User;
import smartcity.accessibility.socialnetwork.User.Privilege;
import smartcity.accessibility.socialnetwork.BestReviews;
import smartcity.accessibility.socialnetwork.Review;
import smartcity.accessibility.socialnetwork.Score;

/**
 * @author Koral Chapnik
 */

public abstract class Location {

	private static final long serialVersionUID = -9204783865281694652L;
	
	private ArrayList<Review> reviews;
	private ArrayList<Review> pinnedReviews;	//ArthurSap
	private LatLng coordinates;

	//ArthurSap
	public ArrayList<Review> getReviews() {
		return reviews;
	}

	//ArthurSap
	public ArrayList<Review> getPinnedReviews() {
		return pinnedReviews;
	}

	public Location(){
		initiateArrays();
		this.coordinates = null;
	}
	
	public Location(LatLng c){
		initiateArrays();
		this.coordinates = c;		
	}
	
	public Location(ArrayList<Review> r, ArrayList<Review> pr, LatLng c) {
		this.reviews = r;
		this.pinnedReviews = pr;
		this.coordinates = c;
	}
	
	private void initiateArrays(){
		this.reviews = new ArrayList<Review>();
		this.pinnedReviews = new ArrayList<Review>();
	}
	
	// n is the number of reviews we want to calculate the Location's rating by
	public Score getRating(int n){
		int rating = -1;
		try {
			if( pinnedReviews.isEmpty() && reviews.isEmpty())
				return new Score(Score.getMinScore());
			BestReviews br = !pinnedReviews.isEmpty() ? new BestReviews(n, pinnedReviews) : new BestReviews(n, reviews);
			rating = br.getTotalRating();
			return new Score(rating);
		} catch (ScoreNotInRangeException e) {
			// TODO: implement 
		}
		return null;
	}

	
	public String getAddress(MapView mapView){
		SearchQuery sq = new SearchQuery("");
		SearchQueryResult result = sq.searchByCoordinates(mapView, coordinates);
		return result.getCoordinations().get(0).getFormattedAddress();
	}
	
	public LatLng getCoordinates() {
		return this.coordinates;
	}
	
	/**
	 * @author Kolikant
	 * @throws ParseException 
	 */
	public void addReview(User u, int rating, String review) throws ParseException {
		Review r = new Review(this, rating, review, u);
		actuallyAddReview(r);
	}
	
	/**
	 * @author ArthurSap
	 * @throws ParseException 
	 */
	public void addReview(Review r) throws ParseException{
		actuallyAddReview(r);
	}

	/**
	 * @author ArthurSap
	 * @throws ParseException
	 */
	private void actuallyAddReview(Review r) throws ParseException {
		reviews.add(r);
		ReviewManager.uploadReview(r);
	}
	
	/**
	 * Marks a review as important - whilst calculating the location's 
	 * accessibility level always takes this review in the calculation.
	 * Also, always show this review in the top reviews.
	 **/
	public void pinReview(User u, Review r){
		if(!isAccessAllowed(u)){
			System.out.println("Action is not allowed! You are no authorized for it!");
			return; 
		}
		
		if(!reviews.contains(r)){
			System.out.print("ERROR! This review doesn't exist in current location!");
			System.out.println("\tCurrent Location: "+this.coordinates);
			return;
		}
		
		if(pinnedReviews.contains(r)){
			System.out.println("Review is already pinned.");
			return;
		}
		
		pinUnpinElement(r, pinnedReviews, reviews);
	}
	
	/**
	 * Reverts the effects of pinReview.
	 */
	public void unpinReview(User u, Review r){
		if(!isAccessAllowed(u)){
			System.out.println("Action is not allowed! You are no authorized for it!");
			return; 
		}
		
		if(reviews.contains(r)){
			System.out.println("Review is already un-pinned.");
			return;
		}
		
		if(!pinnedReviews.contains(r)){
			if (reviews.contains(r))
				System.out.println("Review is already un-pinned.");
			else {
				System.out.print("ERROR! This review doesn't exist in current location!");
				System.out.println("\tCurrent Location: " + this.coordinates);
			}
			return;
		}
		
		pinUnpinElement(r, reviews, pinnedReviews);
	}

	private boolean isAccessAllowed(User u) {
		return u.getPrivilege().compareTo(Privilege.RegularUser) >= 0;
	}
	
	private void pinUnpinElement(Review r, ArrayList<Review> toAdd, ArrayList<Review> toRemove){
		if(toAdd.contains(r)){
			toRemove.remove(r);
			return;
		}
		toAdd.add(r);
		toRemove.remove(r);
	}
	
	
	
	 @Override
	    public boolean equals(Object o) {
	        if (o == this)
				return true;

	        if (!(o instanceof Location))
				return false;
	 
	        return ((Location) o).coordinates.equals(this.coordinates);
	    }

	
}