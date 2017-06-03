package smartcity.accessibility.services;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.database.AbstractLocationManager;
import smartcity.accessibility.database.AbstractReviewManager;
import smartcity.accessibility.exceptions.UnauthorizedAccessException;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.mapmanagement.Location.LocationSubTypes;
import smartcity.accessibility.mapmanagement.Location.LocationTypes;
import smartcity.accessibility.services.exceptions.LocationDoesNotExistException;
import smartcity.accessibility.services.exceptions.UserDoesNotExistException;
import smartcity.accessibility.services.exceptions.UserIsNotLoggedIn;
import smartcity.accessibility.socialnetwork.Review;
import smartcity.accessibility.socialnetwork.User;

@RestController
public class ReviewsService {
	
	@RequestMapping(value = "/reviews", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
    public Review[] showMeStuff(@RequestParam("lat") Double lat,
    		@RequestParam("lng") Double lng,
			@RequestParam("type") String type,
			@RequestParam("subtype") String subtype) {
		
		Location location = AbstractLocationManager.instance().
				getLocation(new LatLng(lat, lng),
				LocationTypes.valueOf(type),
				LocationSubTypes.valueOf(subtype),
				null).orElse(null);
		
		if(location == null)
			throw new LocationDoesNotExistException();
		
		return location.getReviews().toArray(new Review[0]);
    }
	
	@RequestMapping(value = "/reviews", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public void changeRevLikes(@RequestHeader("authToken") String token,
			@RequestParam("lat") Double lat,
			@RequestParam("lng") Double lng,
    		@RequestParam("type") String type,
    		@RequestParam("subtype") String subtype,
    		@RequestParam("username") String username,
    		@RequestParam("likes") Integer like){
		
		User u = AddReviewService.getUserFromToken(token);
		if (u == null)
			throw new UserDoesNotExistException();
		
		LatLng coords = new LatLng(lat, lng);
		Location loc = AbstractLocationManager.instance().getLocation(
				coords,
				LocationTypes.valueOf(type),
				LocationSubTypes.valueOf(subtype),
				null).orElse(null);
		
		if(loc == null)
			throw new LocationDoesNotExistException();
		
		//TODO Why are you trying to upvote all the reviews that are by the same user?  your supposed to upvote\downvote a single review that the user chose
		//	and then update the gui
		// double upvotes are taken care of inside ReviewComment, and privileges also, so you only need to try and upvote\downvote, and if it doesnt fail
		// update gui and db -- Alex
		for(Review r :loc.getReviews())
			if (r.getUser().getUsername().equals(username)){
				try {
					if (like > 0) r.upvote(u);
					else r.downvote(u);
					AbstractReviewManager.instance().updateReview(r, null);
				} catch (UnauthorizedAccessException e) {
					throw new UserIsNotLoggedIn();
				}
				break;
			}
		
		AbstractLocationManager.instance().updateLocation(loc, null);
	}

}
