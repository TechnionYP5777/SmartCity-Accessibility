package smartcity.accessibility.services;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import smartcity.accessibility.exceptions.illigalString;
import smartcity.accessibility.mapmanagement.JxMapsFunctionality;
import smartcity.accessibility.mapmanagement.JxMapsFunctionality.ExtendedMapView;
import smartcity.accessibility.search.SearchQuery;
import smartcity.accessibility.socialnetwork.User;
import smartcity.accessibility.socialnetwork.UserBuilder;
import smartcity.accessibility.socialnetwork.UserProfile;

/**
 * @author ariel
 */

@RestController
public class UserInformationService {
	@RequestMapping(value="/userInfo/name")
	@ResponseBody public UserProfile getUserInfoName(@RequestHeader("authToken") String token) {	
		UserInfo userInfo = LogInService.getUserInfo(token);
		return userInfo.getUser().getProfile();
	}
	
	@RequestMapping(value="/userInfo/JSONEXAMPLE")
	@ResponseBody public UserProfile getUserInfoName() {	
		
		User ui = new UserBuilder()
				.setUsername("NAME")
				.setPassword("PASS")
				.setPrivilege(User.Privilege.RegularUser)
				.build();
				//new UserImpl("NAME", "PASS", User.Privilege.RegularUser);
		try {
			ExtendedMapView mapView = JxMapsFunctionality.getStaticMapView();
			ui.addSearchQuery(SearchQuery.adressSearch("yehalom 70", mapView), "home");
		} catch (illigalString e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ui.getProfile();
	}
}
