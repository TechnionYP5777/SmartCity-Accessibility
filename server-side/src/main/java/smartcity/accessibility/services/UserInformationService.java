package smartcity.accessibility.services;

import java.util.concurrent.ExecutionException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import smartcity.accessibility.exceptions.illigalString;
import smartcity.accessibility.search.SearchQuery;
import smartcity.accessibility.socialnetwork.User;
import smartcity.accessibility.socialnetwork.UserImpl;

@RestController
public class UserInformationService {
	@RequestMapping(value="/userInfo/name")
	@ResponseBody public UserImpl getUserInfoName(@RequestHeader("authToken") String token) {	
		
		UserInfo userInfo = LogInService.getUserInfo(token);
		return (UserImpl) userInfo.getUser();
	}
	
	
	@RequestMapping(value="/userInfo/JSONEXAMPLE")
	@ResponseBody public UserImpl getUserInfoName() {	
		
		UserImpl ui = new UserImpl("NAME", "PASS", User.Privilege.RegularUser);
		try {
			ui.addSearchQuery(SearchQuery.adressSearch("yehalom 70"), "home");
		} catch (illigalString e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ui;
	}
}
