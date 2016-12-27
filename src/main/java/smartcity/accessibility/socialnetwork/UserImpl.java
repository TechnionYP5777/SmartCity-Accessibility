package smartcity.accessibility.socialnetwork;

import java.util.List;

import smartcity.accessibility.search.SearchQuery;

public class UserImpl implements User {
	
	private String userName;
	private String password;
	private Privilege privilegeLevel;
	private List<SearchQuery> favouriteSearchQueries;
	
	public UserImpl(String uName, String pass, Privilege p, String FavouriteQueries){
		userName = uName;
		password = pass;
		privilegeLevel = p;
		favouriteSearchQueries = SearchQuery.String2QueriesList(FavouriteQueries);
	}
	
	public UserImpl(String uName, String pass, Privilege p){
		this(uName, pass, p, "");
	}
	
	public UserImpl DefaultUser(String uName, String pass, String FavouriteQueries){
		return new UserImpl("", "", Privilege.DefaultUser, "");
	}
	
	public UserImpl RegularUser(String uName, String pass, String FavouriteQueries){
		return new UserImpl(uName, pass, Privilege.RegularUser, "");
	}
	
	public UserImpl Admin(String uName, String pass, String FavouriteQueries){
		return new UserImpl(uName, pass, Privilege.Admin, "");
	}

	@Override
	public String getName() {
		return userName;
		//TODO stuff on user name for privilege level
	}
	

	@Override
	public void setName(String name) {
		if(privilegeLevel == Privilege.DefaultUser) return; //TODO exception?
		userName = name;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setPassword(String pass) {
		if(privilegeLevel == Privilege.DefaultUser) return; //TODO exception?
		password = pass;		
	}

	@Override
	public Privilege getPrivilege(){
		return privilegeLevel;
	}

	@Override
	public void setPrivilege(Privilege p) {
		privilegeLevel = p;
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


}
