package smartcity.accessibility.socialnetwork;

/**
 * @author KaplanAlexander
 *
 */
public class UserProfile {

	private final String username;
	private Helpfulness hlp;
	
	public UserProfile(String username){
		this.username = username;
		hlp = new Helpfulness();
	}

	public String getUsername() {
		return username;
	}

	public int getRating() {
		return hlp.getRating();
	}

	public void setHelpfulness(Helpfulness h) {
		this.hlp = h;
	}

	public int getNumOfReviews() {
		return hlp.getNumOfReviews();
	}
	
	public void upvote(){
		hlp.upvote();
	}
	
	public void downvote(){
		hlp.downvote();
	}
	
	public void addReview(){
		hlp.addReview();
	}
	
	public void removeReview(){
		hlp.removeReview();
	}
	
	public double getAvgRating(){
		return hlp.helpfulness();
	}

	@Override
	public String toString() {
		return "UserProfile [username=" + username + "]";
	}

	public Helpfulness getHelpfulness() {
		return hlp;
	}
	
	
}
