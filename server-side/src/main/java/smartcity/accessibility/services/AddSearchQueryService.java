package smartcity.accessibility.services;

//import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import smartcity.accessibility.database.UserManager;
import smartcity.accessibility.exceptions.UserNotFoundException;
import smartcity.accessibility.exceptions.illigalString;
import smartcity.accessibility.search.SearchQuery;
import smartcity.accessibility.services.exceptions.QueryTypeDoesNotExist;
//import smartcity.accessibility.socialnetwork.User;

@RestController
public class AddSearchQueryService {

	public final int adressSearch = 1;
	public final int typeSearch = 2;
	
	@RequestMapping(value = "/addQuery", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
    public SearchQuery addQuery(@RequestHeader("authToken") String token, @RequestParam("query") String query, @RequestParam("queryName") String queryName){
		
		System.out.println("starting add query");
		UserInfo userInfo = LogInService.getUserInfo(token);
		SearchQuery sq = null;

		try {
			sq = SearchQuery.adressSearch(query);
		} catch (illigalString e1) {
			e1.printStackTrace();
		}
		
		userInfo.getUser().addSearchQuery(sq, queryName);
		System.out.println("added the query, now need to upload it");
		try {
			UserManager.updatefavouriteQueries(userInfo.getUser());
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}	
		return sq;
    }
}
