package smartcity.accessibility.socialnetwork;

import java.util.ArrayList;
import java.util.List;

import org.parse4j.ParseException;

import smartcity.accessibility.database.ReviewManager;
import smartcity.accessibility.exceptions.UnauthorizedAccessException;

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
		//pinUnpinElement(r, r , r.getLocation().getReviews());
		try {
			r.getLocation().pinReview(this, r);
		} catch (UnauthorizedAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Reverts the effects of pinReview.
	 */
	public void unpinReview(Review r){
		pinUnpinElement(r, r.getLocation().getReviews(), r.getLocation().getPinnedReviews());
	}
	
	private void pinUnpinElement(Review r, List<Review> toAdd, List<Review> toRemove){
		if(toAdd.contains(r)){
			toRemove.remove(r);
			return;
		}
		toAdd.add(r);
		toRemove.remove(r);
	}
	
	public void deleteReview(Review __){
		//TODO implement once the DB has enough info
		//issue #32
	}
	
	/**
	 * Uploads Admins' reviews. 
	 * Pins them automatically.
	 * @throws ParseException 
	 */
	public void uploadReview(Review r) throws ParseException{
		r.getLocation().addReview(this, r.getRating().getScore(), r.getContent());
		ReviewManager.uploadReview(r);
		pinReview(r);
	}
	

	@Override
	public String getUserName() {
		return super.getUserName();
	}

	@Override
	public String getPassword() {
		return super.getPassword();
	}

}
