package smartcity.accessibility.services;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static Logger logger = LoggerFactory.getLogger(LogInService.class);

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Token login(@RequestParam("name") String name, @RequestParam("password") String password) {
		User u = UserManager.loginUser(name, password);
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
			u = UserManager.signUpUser(name, password, User.Privilege.RegularUser);
			if (u == null)
				throw new SignUpFailed();
		} catch (UsernameAlreadyTakenException e) {
			logger.info("signup failed", e);
			throw new SignUpFailed();
		}
		Token t = Token.calcToken(u);
		Application.tokenToSession.put(t.getToken(), new UserInfo(u));
		return t;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public void logout(@RequestHeader("authToken") String token) {
			Application.tokenToSession.invalidate(token);
	}

	static UserInfo getUserInfo(String token) {
		UserInfo userInfo = null;
		try {
			userInfo = Application.tokenToSession.get(token);
			if (userInfo.getUser() == null) {
				throw new UserIsNotLoggedIn();
			}
		} catch (ExecutionException e) {
			logger.info("getting user from cache failed", e);
		}
		return userInfo;
	}
}
