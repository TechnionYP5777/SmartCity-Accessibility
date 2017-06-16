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
    public void addQuery(@RequestHeader("authToken") String token,
    		@RequestParam("query") String query, @RequestParam("queryName") String queryName, @RequestParam("querytype") String querytype){
		
		System.out.println("starting add query");
		UserInfo userInfo = LogInService.getUserInfo(token);
		SearchQuery sq = null;
		try {
			switch(Integer.parseInt(querytype)){
				case(adressSearch):
					sq = SearchQuery.adressSearch(query);
					break;
				case(typeSearch):
					sq = SearchQuery.TypeSearch(query);
					break;
				default:
					throw new QueryTypeDoesNotExist();
			}
		}catch(illigalString e){
			e.printStackTrace();
		}
		
		userInfo.getUser().addSearchQuery(sq, queryName);
		System.out.println("added the query, now need to upload it");
		try {
			UserManager.updatefavouriteQueries(userInfo.getUser());
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		//TODO pretty pretty finisher    	
    }
}
