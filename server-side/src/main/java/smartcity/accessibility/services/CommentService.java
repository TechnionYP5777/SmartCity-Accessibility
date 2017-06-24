package smartcity.accessibility.services;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.maps.model.LatLng;

import smartcity.accessibility.database.AbstractLocationManager;
import smartcity.accessibility.database.AbstractReviewManager;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.mapmanagement.Location.LocationSubTypes;
import smartcity.accessibility.mapmanagement.Location.LocationTypes;
import smartcity.accessibility.services.exceptions.LocationDoesNotExistException;
import smartcity.accessibility.services.exceptions.ReviewDoesNotExist;
import smartcity.accessibility.services.exceptions.UserDoesNotExistException;
import smartcity.accessibility.socialnetwork.Review;
import smartcity.accessibility.socialnetwork.User;

@RestController
public class CommentService {

	@RequestMapping(value = "/addcomment", method = RequestMethod.POST,
			produces = "application/json")
	public void addComment(@RequestHeader("authToken") String token,
    		@RequestParam("rev") String rrev,
    		@RequestParam("comment") String comment,
    		@RequestParam("lat") Double lat,
    		@RequestParam("lng") Double lng,
			@RequestParam("type") String type,
			@RequestParam("subtype") String subtype) throws Exception{
		System.out.println(rrev); //TODO delete
		User u = LogInService.getUserInfo(token).getUser();
		if (u == null)
			throw new UserDoesNotExistException();
		
		Location loc = AbstractLocationManager.instance().
				getLocation(new LatLng(lat, lng),
				LocationTypes.valueOf(type.toUpperCase()),
				LocationSubTypes.valueOf(subtype.toUpperCase()),
				null).orElse(null);
		if(loc == null) throw new LocationDoesNotExistException();
		
		Review rev = getReviewFromString(rrev, loc);
		if(rev == null) throw new ReviewDoesNotExist();
		
		rev.addComment(u, comment);
		
		AbstractReviewManager.instance().updateReview(rev, null);
	}
	
	private Review getReviewFromString(String rrev, Location l){
		int first = rrev.lastIndexOf("\"username\":\"");
		int second = rrev.lastIndexOf("\",\"avgRating\":") - "\",\"avgRating\":".length();
		String username = rrev.substring(first, second);
		for(Review r : l.getReviews())
			if(r.getUser().getUsername().equals(username))
				return r;
		return null;
	}
}
