package smartcity.accessibility.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.database.AbstractLocationManager;
import smartcity.accessibility.exceptions.UnauthorizedAccessException;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.mapmanagement.Location.LocationSubTypes;
import smartcity.accessibility.mapmanagement.Location.LocationTypes;
import smartcity.accessibility.services.exceptions.LocationDoesNotExistException;
import smartcity.accessibility.services.exceptions.UserDoesNotExistException;
import smartcity.accessibility.services.exceptions.UserIsNotLoggedIn;
import smartcity.accessibility.socialnetwork.Review;
import smartcity.accessibility.socialnetwork.User;
import smartcity.accessibility.socialnetwork.UserProfile;

@RestController
public class ReviewsService {
	
	@RequestMapping(value = "/reviews", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
    public Review[] showMeStuff(@RequestParam("lat") Double lat,
    		@RequestParam("lng") Double lng) {
		
		List<Location> lstLocation = AbstractLocationManager.instance().getLocation(new LatLng(lat, lng), null);
		List<Review> lstReviews = new ArrayList<>();
		
		for(Location l : lstLocation)
			lstReviews.addAll(l.getReviews());
		
		//TODO change when deployable
		//return lstReviews.toArray(null);
		
		Location l = new Location();
		l.setCoordinates(new LatLng(lat, lng));
		UserProfile u = new UserProfile("ayyy");
		return new Review[] { new Review(l, 5, "amazing", u), new Review(l, 3, "so lame bruh", u) };
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
		
		//TODO ask Alex if it is enough to do this in order to up/downvote a review
		for(Review r :loc.getReviews())
			if (r.getUser().getUsername().equals(username)){
				try {
					if (like > 0) r.upvote(u);
					else r.downvote(u);
				} catch (UnauthorizedAccessException e) {
					throw new UserIsNotLoggedIn();
				}
				break;
			}
		
		AbstractLocationManager.instance().updateLocation(loc, null);
	}

}
