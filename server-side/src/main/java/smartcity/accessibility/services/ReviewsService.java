package smartcity.accessibility.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.maps.model.LatLng;

import smartcity.accessibility.database.AbstractLocationManager;
import smartcity.accessibility.database.AbstractReviewManager;
import smartcity.accessibility.exceptions.UnauthorizedAccessException;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.mapmanagement.Location.LocationSubTypes;
import smartcity.accessibility.mapmanagement.Location.LocationTypes;
import smartcity.accessibility.navigation.Navigation;
import smartcity.accessibility.navigation.exception.CommunicationFailed;
import smartcity.accessibility.services.exceptions.LocationDoesNotExistException;
import smartcity.accessibility.services.exceptions.ReviewFailed;
import smartcity.accessibility.services.exceptions.UserDoesNotExistException;
import smartcity.accessibility.services.exceptions.UserIsNotLoggedIn;
import smartcity.accessibility.socialnetwork.Review;
import smartcity.accessibility.socialnetwork.User;

@RestController
public class ReviewsService {
	private static Logger logger = LoggerFactory.getLogger(ReviewsService.class);
	
	private Location createLocation(Double lat, Double lng, String type, String subtype) {
		return AbstractLocationManager.instance().
				getLocation(new LatLng(lat, lng),
				LocationTypes.valueOf(type.toUpperCase()),
				LocationSubTypes.valueOf(subtype.toUpperCase()),
				null).orElse(null);
	}
	
	private Review getUserReviewFromLocation(String username, Location loc) {
		if(loc == null)
			throw new LocationDoesNotExistException();
		
		for(Review r :loc.getReviews()){
			if (r.getUser().getUsername().equals(username)){
				r.setLocation(loc);
				return r;
			}
		}
		return null;
	}
	
	private Location createAndAddLocation(Double lat, Double lng, String type, String subtype, String name) {
		Location loc;
		loc = new Location();
		loc.setCoordinates(new LatLng(lat, lng));
		loc.setLocationType(LocationTypes.valueOf(type.toUpperCase()));
		loc.setLocationSubType(LocationSubTypes.valueOf(subtype.toUpperCase()));
		loc.setName(name);
		if(LocationTypes.valueOf(type.toUpperCase()).equals(LocationTypes.STREET)){
			try {
				String segmentId = String.valueOf(Navigation.getMapSegmentOfLatLng(lat, lng).getLinkId());
				loc.setSegmentId(segmentId);
			} catch (CommunicationFailed e) {
				logger.error("CommunicationFailed {} ", e);
				throw new ReviewFailed();
			}
		}
		AbstractLocationManager.instance().uploadLocation(loc, null);
		return loc;
	}
	
	@RequestMapping(value = "/reviews", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
    public Review[] showMeStuff(@RequestParam("lat") Double lat,
    		@RequestParam("lng") Double lng,
			@RequestParam("type") String type,
			@RequestParam("subtype") String subtype,
			@RequestParam("name") String name) {
		
		Location loc = createLocation(lat, lng, type, subtype);
		
		if(loc == null) //Location "l" doesn't exist
			loc = createAndAddLocation(lat, lng, type, subtype, name);
		
		return loc.getReviews().toArray(new Review[0]);
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
		
		User u = LogInService.getUserInfo(token).getUser();
		if (u == null)
			throw new UserDoesNotExistException();
		
		Review r = getUserReviewFromLocation(username, createLocation(lat, lng, type, subtype));
		try {
			if (like > 0) r.upvote(u);
			else r.downvote(u);
			AbstractReviewManager.instance().updateReview(r, null);
		} catch (UnauthorizedAccessException e) {
			logger.error("{}", e);
			throw new UserIsNotLoggedIn();
		}
	}

	
	@RequestMapping(value = "/deleteReview", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public void deleteReview(@RequestParam("lat") Double lat,
			@RequestParam("lng") Double lng,
    		@RequestParam("type") String type,
    		@RequestParam("subtype") String subtype,
    		@RequestParam("username") String username){
		
		Review r = getUserReviewFromLocation(username, createLocation(lat, lng, type, subtype));
		
		AbstractReviewManager.instance().deleteReview(r, null);
	}
	
	@RequestMapping(value = "/pinUnpinReview", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public void pinUnpinReview(@RequestParam("lat") Double lat,
			@RequestParam("lng") Double lng,
    		@RequestParam("type") String type,
    		@RequestParam("subtype") String subtype,
    		@RequestParam("username") String username){
		
		Review r = getUserReviewFromLocation(username, createLocation(lat, lng, type, subtype));
		
		r.setPinned(!r.isPinned());
		
		AbstractReviewManager.instance().updateReview(r, null);
	}

}
