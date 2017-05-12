package smartcity.accessibility.socialnetwork;

import java.util.ArrayList;
import java.util.List;

import smartcity.accessibility.search.SearchQuery;
import smartcity.accessibility.socialnetwork.User.Privilege;

/**
 * @author KaplanAlexander
 *
 */
public class UserBuilder {

	private String username = null;
	private String password = null;
	private List<SearchQuery> queries;
	private int rating = 0;
	private Privilege privilege = Privilege.RegularUser;
	private int numReviews = 0;
	private UserProfile userProfile = null;
	
	public UserBuilder(){
		queries = new ArrayList<>();
	}
	
	public static User DefaultUser() {
		return new User("", "", Privilege.DefaultUser);
	}

	public static User RegularUser(String uName, String pass, String FavouriteQueries) {
		User u = new User(uName, pass, Privilege.RegularUser);
		u.setFavouriteSearchQueries(FavouriteQueries);
		return u;
	}

	public static User Admin(String uName, String pass, String FavouriteQueries) {
		User u = new User(uName, pass, Privilege.Admin);
		u.setFavouriteSearchQueries(FavouriteQueries);
		return u;
	}
	
	public UserBuilder setUsername(String username){
		this.username = username;
		return this;
	}
	
	public UserBuilder setPassword(String password){
		this.password = password;
		return this;
	}
	
	public UserBuilder addSearchQuery(SearchQuery query, String quString){
		query.RenameSearchQuery(quString);
		queries.add(query);
		return this;
	}
	
	public UserBuilder setSearchQueries(List<SearchQuery> queries){
		this.queries = queries;
		return this;
	}
	
	public UserBuilder setSearchQueries(String queries){
		this.queries = SearchQuery.String2QueriesList(queries);
		return this;
	}
	
	public UserBuilder setRating(int rating){
		this.rating = rating;
		return this;
	}
	
	public UserBuilder setNumReviews(int numReviews){
		this.numReviews = numReviews;
		return this;
	}
	
	public UserBuilder setPrivilege(Privilege p){
		privilege = p;
		return this;
	}
	
	public UserBuilder setProfile(UserProfile up){
		userProfile = up;
		return this;
	}
	
	/**
	 * if username or password not set then returns null
	 * @return new User2
	 */
	public User build(){
		if(username == null || password == null)
			return null;
		User u = new User(username, password, privilege);
		for(SearchQuery sq : queries){
			u.addQuery(sq, sq.getName());
		}
		if (userProfile != null){
			u.getProfile().setNumOfReviews(userProfile.getNumOfReviews());
			u.getProfile().setRating(userProfile.getRating());
		} else {
			u.getProfile().setNumOfReviews(numReviews);
			u.getProfile().setRating(rating);
		}
		u.setPrivilegeLevel(privilege);
		return u;
	}
}
