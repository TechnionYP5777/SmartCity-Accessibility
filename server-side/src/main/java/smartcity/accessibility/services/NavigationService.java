package smartcity.accessibility.services;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import smartcity.accessibility.services.exceptions.UserDoesNotExistException;


@Controller
public class NavigationService {
	@RequestMapping(value = "/navigation", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Integer login(@RequestParam("token") String token) {
		if(LogInService.isUserLoggedIn(token)){
			throw new UserDoesNotExistException();
		}
		return 0;
	}

}
