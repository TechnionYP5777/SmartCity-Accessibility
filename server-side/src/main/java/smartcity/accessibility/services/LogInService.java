package smartcity.accessibility.services;

import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import smartcity.accessibility.socialnetwork.User;
import smartcity.accessibility.socialnetwork.UserImpl;

@Controller
public class LogInService {

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Integer login(@RequestParam("name") String name, @RequestParam("password") String password) {
		User u = UserImpl.RegularUser(name, password, "");
		Application.tokenToSession.put(u.hashCode(), new UserInfo(u));
		return u.hashCode();
	}

	static boolean isUserLoggedIn(Integer token) {
		UserInfo userInfo = null;
		try {
			userInfo = Application.tokenToSession.get(token);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		if (userInfo.getUser() == null) {
			return false;
		}
		return true;
	}
}
