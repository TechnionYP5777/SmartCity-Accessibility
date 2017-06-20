package smartcity.accessibility.socialnetwork;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import smartcity.accessibility.search.SearchQuery;

public class User {
	private static Logger logger = LoggerFactory.getLogger(User.class);
	
	private final UserProfile profile;
	private final String password;
	private List<SearchQuery> favouriteSearchQueries;
	private Privilege privilegeLevel;
	
	public enum Privilege {
		GodUser,
		Admin, RegularUser, DefaultUser;

		private static Privilege[] allValues = values();

		public static Privilege fromOrdinal(int ¢) {
			return allValues[¢];
		}

		public static boolean pinPrivilegeLevel(User ¢) {
			return ¢.getPrivilege().compareTo(Admin) <= 0;
		}

		public static boolean deletePrivilegeLevel(User ¢) {
			return ¢.getPrivilege().compareTo(Admin) <= 0;
		}

		public static boolean addReviewPrivilegeLevel(User ¢) {
			return ¢.getPrivilege().compareTo(RegularUser) <= 0;
		}

		public static boolean commentReviewPrivilegeLevel(User ¢) {
			return ¢.getPrivilege().compareTo(RegularUser) <= 0;
		}

		public static Privilege minCommentLevel() {
			return RegularUser;
		}

		public static Privilege minPinLevel() {
			return Admin;
		}

		public static Privilege minDeleteLevel() {
			return Admin;
		}
	}
	
	public User(String username, String password, Privilege p){
		profile = new UserProfile(username);
		favouriteSearchQueries = new ArrayList<>();
		this.password = password;
		this.privilegeLevel = p;
	}
	
	public UserProfile getProfile(){
		return profile;
	}
	
	public String getUsername(){
		return getProfile().getUsername();
	}
	
	public Privilege getPrivilege() {
		return privilegeLevel;
	}
	
	public void addSearchQuery(SearchQuery sq, String QueryName){
		favouriteSearchQueries.add(sq.RenameSearchQuery(QueryName));
	}

	public String getPassword() {
		return password;
	}

	public Privilege getPrivilegeLevel() {
		return privilegeLevel;
	}

	public void setPrivilegeLevel(Privilege privilegeLevel) {
		this.privilegeLevel = privilegeLevel;
	}

	public List<SearchQuery> getFavouriteSearchQueries() {
		return favouriteSearchQueries;
	}
	
	private int findQueryIndexByName(String QueryName){
		return favouriteSearchQueries.indexOf(SearchQuery.makeDummy(QueryName));
	}
	
	public SearchQuery getSearchQuery(String QueryName){
		try {
			return favouriteSearchQueries.get(findQueryIndexByName(QueryName));
		}catch (IndexOutOfBoundsException e){
			logger.error("IndexOutOfBounds {}", e);
			return null;
		}
	}
	
	public void removeSearchQuery(String QueryName){
		try {
			favouriteSearchQueries.remove(findQueryIndexByName(QueryName));
		}catch (IndexOutOfBoundsException e){
			logger.error("IndexOutOfBounds {}", e);
			//already gone :O
		}
	}
	
	public void setFavouriteSearchQueries(String favouriteQueries) {
		favouriteSearchQueries = SearchQuery.String2QueriesList(favouriteQueries);
	}
	
	public void setFavouriteSearchQueries(List<SearchQuery> favouriteQueries) {
		favouriteSearchQueries = favouriteQueries;
	}
	
	public boolean canDeleteReview(Review r){
		if (r.getUser().getUsername().equals(getUsername()))
			return true;
		return Privilege.deletePrivilegeLevel(this) ? true : false;
	}
	
	public boolean canPinReview(){
		return Privilege.pinPrivilegeLevel(this);
	}
	
	@Override
	public boolean equals(Object ¢) {
		if (¢ == this)
			return true;
		return (¢ instanceof User && this.profile.getUsername().equals(((User) ¢).profile.getUsername()))
				|| (¢ instanceof String && profile.getUsername().equals(¢));
	}
	
	@Override
	public int hashCode() {
		return profile.getUsername().hashCode();
	}
}
