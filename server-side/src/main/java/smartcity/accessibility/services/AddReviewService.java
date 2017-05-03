package smartcity.accessibility.services;
import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import smartcity.accessibility.services.exceptions.UserIsNotLoggedIn;
import smartcity.accessibility.socialnetwork.Review;
import smartcity.accessibility.socialnetwork.User;

@RestController
public class AddReviewService {
	@RequestMapping(value = "/addreview", method = RequestMethod.POST,
			produces = "application/json")
	@ResponseBody
    public void addreview(@RequestHeader("authToken") String token,
    		@RequestParam("review") String review,
    		@RequestParam("score") String score) {
		
		if (!LogInService.isUserLoggedIn(token))
			throw new UserIsNotLoggedIn();
		
		User u = getUserFromToken(token);
		if (u == null)
			throw new UserIsNotLoggedIn();
		
		Review r = new Review(Integer.parseInt(score), review, u);
		//TODO wait until alex finishes DBs and upload the review
		//TODO pretty pretty finisher    	
    }
	
	private User getUserFromToken(String token){
		UserInfo userInfo = null;
		try {
			userInfo = Application.tokenToSession.get(token);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return userInfo.getUser();
	}
}