package smartcity.accessibility.services;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;

@Controller
public class NavigationService {
	@RequestMapping(value = "/navigation", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin(origins = "http://localhost:8100")
	public String navigation(@RequestHeader("authToken") String token) {
		if (!LogInService.isUserLoggedIn(token)) {
			throw new UserIsNotLoggedIn();
		}
		return "work";
	}

}
