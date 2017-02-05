package smartcity.accessibility.socialnetwork;

import java.util.List;

import smartcity.accessibility.exceptions.UnauthorizedAccessException;
import smartcity.accessibility.search.SearchQuery;

/**
 * 
 * @author Kolikant
 *
 */

public interface User {

	enum Privilege {
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

	/**
	 * make a search using an existing query and show the user the results
	 * 
	 * @param q
	 *            is the query to be used for the search
	 */
	static void search(SearchQuery __) {
		// q.Search();
	}

	String getName();

	void setLocalName(String name) throws UnauthorizedAccessException;

	String getPassword();

	Privilege getPrivilege();

	List<SearchQuery> getFavouriteSearchQueries();

	void setFavouriteSearchQueries(String favouriteQueries);

	void setFavouriteSearchQueries(List<SearchQuery> favouriteQueries);

	String getLocalName();

}
