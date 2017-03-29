package smartcity.accessibility.socialnetwork;

import java.util.List;

import smartcity.accessibility.exceptions.UnauthorizedAccessException;
import smartcity.accessibility.search.SearchQuery;

public class UserImpl implements User {

	private String userName;
	private String localName;
	private String password;
	private Privilege privilegeLevel;
	private List<SearchQuery> favouriteSearchQueries;

	public UserImpl(String uName, String pass, Privilege p, String FavouriteQueries) {
		localName = userName = uName;
		password = pass;
		privilegeLevel = p;
		favouriteSearchQueries = SearchQuery.String2QueriesList(FavouriteQueries);
	}

	public UserImpl(String uName, String pass, Privilege p) {
		this(uName, pass, p, "");
	}

	public static UserImpl DefaultUser() {
		return new UserImpl("", "", Privilege.DefaultUser);
	}

	public static UserImpl RegularUser(String uName, String pass, String FavouriteQueries) {
		return new UserImpl(uName, pass, Privilege.RegularUser, FavouriteQueries);
	}

	public static UserImpl Admin(String uName, String pass, String FavouriteQueries) {
		return new UserImpl(uName, pass, Privilege.Admin, FavouriteQueries);
	}

	@Override
	public String getName() {
		return userName;
	}

	@Override
	public String getLocalName() {
		return localName;
	}

	@Override
	public void setLocalName(String name) throws UnauthorizedAccessException {
		if (privilegeLevel == Privilege.DefaultUser)
			throw (new UnauthorizedAccessException(Privilege.RegularUser));

		localName = name;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Privilege getPrivilege() {
		return privilegeLevel;
	}

	@Override
	public List<SearchQuery> getFavouriteSearchQueries() {
		return favouriteSearchQueries;
	}

	@Override
	public void setFavouriteSearchQueries(String favouriteQueries) {
		favouriteSearchQueries = SearchQuery.String2QueriesList(favouriteQueries);
	}

	@Override
	public void setFavouriteSearchQueries(List<SearchQuery> favouriteQueries) {
		favouriteSearchQueries = favouriteQueries;
	}
	
	public void addSearchQuery(SearchQuery sq, String QueryName){
		favouriteSearchQueries.add(sq.RenameSearchQuery(QueryName));
	}
	
	private int findQueryIndexByName(String QueryName){
		return favouriteSearchQueries.indexOf(SearchQuery.makeDummy(QueryName));
	}
	
	public SearchQuery getSearchQuery(String QueryName){
		return favouriteSearchQueries.get(findQueryIndexByName(QueryName));
	}
	
	public void removeSearchQuery(String QueryName){
		favouriteSearchQueries.remove(findQueryIndexByName(QueryName));
	}

	@Override
	public boolean equals(Object ¢) {
		return ¢ == this
				|| (¢ instanceof UserImpl && this.userName.equals(((UserImpl) ¢).userName)
						&& this.password.equals(((UserImpl) ¢).password)
						&& this.privilegeLevel == ((UserImpl) ¢).privilegeLevel)
				|| (¢ instanceof String && userName.equals(¢));
	}

}
