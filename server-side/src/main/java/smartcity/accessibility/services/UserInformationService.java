package smartcity.accessibility.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import smartcity.accessibility.exceptions.illigalString;
import smartcity.accessibility.search.SearchQuery;
import smartcity.accessibility.socialnetwork.User;
import smartcity.accessibility.socialnetwork.UserBuilder;
import smartcity.accessibility.socialnetwork.UserProfile;

/**
 * @author ariel
 */

@RestController
public class UserInformationService {
	private static Logger logger = LoggerFactory.getLogger(UserInformationService.class);
	
	@RequestMapping(value="/userInfo/name")
	@ResponseBody public UserProfile getUserInfoName(@RequestHeader("authToken") String token) {	
		UserInfo userInfo = LogInService.getUserInfo(token);
		UserProfile up =  userInfo.getUser().getProfile();
		UserProfile copyUserProfile = new UserProfile(up.getUsername());
		copyUserProfile.setHelpfulness(up.getHelpfulness());
		copyUserProfile.setProfileImg(null);
		return copyUserProfile;
	}
	
	@RequestMapping(value="/userInfo/queries")
	@ResponseBody public wrapper getUserInfoQueries(@RequestHeader("authToken") String token) {	
		UserInfo userInfo = LogInService.getUserInfo(token);
		return new wrapper(userInfo.getUser().getFavouriteSearchQueries());
	}
	
	@RequestMapping(value="/userInfo/queries/example")
	@ResponseBody public List<SearchQuery> getUserInfoQueriesExample() {	
		User ui = new UserBuilder()
				.setUsername("NAME")
				.setPassword("PASS")
				.setPrivilege(User.Privilege.RegularUser)
				.build();
		try {
			ui.addSearchQuery(SearchQuery.adressSearch("yehalom 70"), "home");
			ui.addSearchQuery(SearchQuery.adressSearch("haifa"), "haifa for some reasons");
		} catch (illigalString e) {
			logger.error("Illegal String {} ", e);
		}
		return ui.getFavouriteSearchQueries();
	}
	
	@RequestMapping(value="/userInfo/JSONEXAMPLE")
	@ResponseBody public UserProfile getUserInfoName() {	
		
		User ui = new UserBuilder()
				.setUsername("NAME")
				.setPassword("PASS")
				.setPrivilege(User.Privilege.RegularUser)
				.build();
		
		try {
			ui.addSearchQuery(SearchQuery.adressSearch("yehalom 70"), "home");
			ui.addSearchQuery(SearchQuery.adressSearch("haifa"), "haifa for some reasons");
		} catch (illigalString e) {
			logger.error("Illegal String {} ", e);
		}
		return ui.getProfile();
	}
}
