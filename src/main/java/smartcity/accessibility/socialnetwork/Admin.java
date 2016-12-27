package smartcity.accessibility.socialnetwork;

import java.util.ArrayList;

import org.parse4j.ParseException;

import smartcity.accessibility.database.ReviewManager;

/**
 * @author ArthurSap
 *
 */
@Deprecated 
public class Admin extends AuthenticatedUser {

	public Admin(String un, String pass, String FavouriteQueries){
		super(un,pass,FavouriteQueries);
		//TODO more stuff on Admin
	}
	
	
	/**
	 * Marks a review as important - whilst calculating the location's 
	 * accessibility level always takes this review in the calculation.
	 * Also, always show this review in the top reviews.
	 **/
	public void pinReview(Review r){
		pinUnpinElement(r, r.getLocation().getPinnedReviews(), r.getLocation().getReviews());
	}
	
	/**
	 * Reverts the effects of pinReview.
	 */
	public void unpinReview(Review r){
		pinUnpinElement(r, r.getLocation().getReviews(), r.getLocation().getPinnedReviews());
	}
	
	private void pinUnpinElement(Review r, ArrayList<Review> toAdd, ArrayList<Review> toRemove){
		if(toAdd.contains(r)){
			toRemove.remove(r);
			return;
		}
		toAdd.add(r);
		toRemove.remove(r);
	}
	
	public void deleteReview(Review r){
		//TODO implement once the DB has enough info
		//issue #32
	}
	
	/**
	 * Uploads Admins' reviews. 
	 * Pins them automatically.
	 * @throws ParseException 
	 */
	public void uploadReview(Review r) throws ParseException{
		r.getLocation().addReview(this, r.getRating().getScore(), r.getComment());
		ReviewManager.uploadReview(r);
		pinReview(r);
	}
	

	@Override
	public String getUserName() {
		String $ = super.getUserName();
		//TODO special stuff on userName
		return $;
	}

	@Override
	public String getPassword() {
		String password = super.getPassword();
		//TODO special stuff on password
		return password;
	}

}
