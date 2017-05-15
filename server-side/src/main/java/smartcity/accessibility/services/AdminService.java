package smartcity.accessibility.services;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import smartcity.accessibility.socialnetwork.UserProfile;

@RestController
public class AdminService {
	@RequestMapping(value="/adminInfo")
	@ResponseBody public UserProfile getUserInfo(@RequestHeader("authToken") String token) {	
		UserInfo userInfo = LogInService.getUserInfo(token);
		return userInfo.getUser().getProfile();
	}
	
	
}
