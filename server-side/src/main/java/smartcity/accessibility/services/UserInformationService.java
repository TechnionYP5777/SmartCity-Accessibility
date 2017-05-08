package smartcity.accessibility.services;

import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInformationService {
	@RequestMapping(value="/userInfo/name/{token}")
	@ResponseBody public String getUserInfoName(@PathVariable("token") String token) {	
		UserInfo userInfo = null;
		try {
			userInfo = Application.tokenToSession.get(token);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return userInfo.getUser().getName();
	}
}
