package smartcity.accessibility.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import smartcity.accessibility.services.exceptions.BadQuery;
import smartcity.accessibility.socialnetwork.User;

@RestController
public class AddSearchQueryService {
	private static Logger logger = LoggerFactory.getLogger(AddSearchQueryService.class);

	public final int adressSearch = 1;
	public final int typeSearch = 2;
	
	@RequestMapping(value = "/addQuery", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
    public SearchQuery addQuery(@RequestHeader("authToken") String token, @RequestParam("query") String query, @RequestParam("queryName") String queryName){
		
		if("undefined".equals(query) || "".equals(query) || "".equals(query.replaceAll("\\s+", ""))){
			throw new BadQuery("No Adress Given!");
		}
		UserInfo userInfo = LogInService.getUserInfo(token);
		SearchQuery sq = null;

		try {
			sq = SearchQuery.adressSearch(query);
		} catch (illigalString e1) {
			logger.error("Illegal String {} ",e1);
		}
		
		User u = userInfo.getUser();
		if(u.getSearchQuery(queryName) != null){
			throw new BadQuery("Name Already Taken!");
		}
		u.addSearchQuery(sq, queryName);
		logger.debug("added the query, now need to upload it");
		try {
			UserManager.updatefavouriteQueries(userInfo.getUser());
		} catch (UserNotFoundException e) {
			logger.error("User not found {}", e);
		}	
		return sq;
    }
}
