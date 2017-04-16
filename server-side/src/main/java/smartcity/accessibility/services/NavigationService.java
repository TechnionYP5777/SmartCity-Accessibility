package smartcity.accessibility.services;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Controller
public class NavigationService {
	@RequestMapping(value = "/navigation", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String navigation(@RequestHeader("authToken") String token) {
		if (LogInService.isUserLoggedIn(token)) {
			throw new UserIsNotLoggedIn();
		}
		return "work";
	}

}
