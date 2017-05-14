package smartcity.accessibility.services;
import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.database.AbstractReviewManager;
import smartcity.accessibility.mapmanagement.Location;
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
    		@RequestParam("score") String score,
    		@RequestParam("lat") String lat,
    		@RequestParam("lng") String lng) {
		
		
		User u = getUserFromToken(token);
		if (u == null)
			throw new UserIsNotLoggedIn();
		
		
		Location l = new Location();
		l.setCoordinates(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)));
		
		Review r = new Review(l, Integer.parseInt(score), review, u.getProfile());
		
		AbstractReviewManager.instance().uploadReview(r, null);
		
		//TODO pretty pretty finisher    	
    }
	
	//TODO null exception?
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