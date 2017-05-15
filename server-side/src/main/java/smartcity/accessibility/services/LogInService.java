package smartcity.accessibility.services;

import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import smartcity.accessibility.database.UserManager;
import smartcity.accessibility.exceptions.UsernameAlreadyTakenException;
import smartcity.accessibility.services.exceptions.SignUpFailed;
import smartcity.accessibility.services.exceptions.UserDoesNotExistException;
import smartcity.accessibility.services.exceptions.UserIsNotLoggedIn;
import smartcity.accessibility.socialnetwork.User;
/**
 * @author yael
 */
@Controller
public class LogInService {

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Token login(@RequestParam("name") String name, @RequestParam("password") String password) {
		User u = UserManager.LoginUser(name, password);
		if (u == null) {
			throw new UserDoesNotExistException();
		}
		Token t = Token.calcToken(u);
		Application.tokenToSession.put(t.getToken(), new UserInfo(u));
		return t;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Token signup(@RequestParam("name") String name, @RequestParam("password") String password) {
		User u;
		try {
			u = UserManager.SignUpUser(name, password, User.Privilege.RegularUser);
			if (u == null)
				throw new SignUpFailed();
		} catch (UsernameAlreadyTakenException e) {
			throw new SignUpFailed();
		}
		Token t = Token.calcToken(u);
		Application.tokenToSession.put(t.getToken(), new UserInfo(u));
		return t;
	}

	static UserInfo getUserInfo(String token) {
		UserInfo userInfo = null;
		try {
			userInfo = Application.tokenToSession.get(token);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		if (userInfo.getUser() == null) {
			throw new UserIsNotLoggedIn();
		}
		return userInfo;
	}
}