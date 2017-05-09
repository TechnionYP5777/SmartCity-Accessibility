package smartcity.accessibility.services;

import java.util.concurrent.ExecutionException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import smartcity.accessibility.socialnetwork.User;
import smartcity.accessibility.socialnetwork.UserImpl;

@RestController
public class UserInformationService {
	@RequestMapping(value="/userInfo/name")
	@ResponseBody public UserImpl getUserInfoName(@RequestHeader("authToken") String authToken) {	
		UserInfo userInfo = null;
		try {
			userInfo = Application.tokenToSession.get(authToken);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		//return (UserImpl)userInfo.getUser();
		return new UserImpl("NAME", "PASS", User.Privilege.RegularUser);
	}
}
