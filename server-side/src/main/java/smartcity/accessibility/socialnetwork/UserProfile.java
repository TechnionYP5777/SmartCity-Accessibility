package smartcity.accessibility.socialnetwork;

/**
 * @author KaplanAlexander
 *
 */
public class UserProfile {

	private final String username;
	private int rating;
	private int numOfReviews;
	
	public UserProfile(String username){
		this.username = username;
		this.rating = 0;
		this.numOfReviews = 0;
	}

	public String getUsername() {
		return username;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public int getNumOfReviews() {
		return numOfReviews;
	}

	public void setNumOfReviews(int numOfReviews) {
		this.numOfReviews = numOfReviews;
	}
	
	public void upvote(){
		rating++;
	}
	
	public void downvote(){
		rating--;
	}
	
	public void addReview(){
		numOfReviews++;
	}
	
	public void removeReview(){
		numOfReviews--;
	}
	
	public double getAvgRating(){
		if(numOfReviews == 0)
			return 0;
		return rating/(double)numOfReviews;
	}

	@Override
	public String toString() {
		return "UserProfile [username=" + username + "]";
	}
	
	
}
