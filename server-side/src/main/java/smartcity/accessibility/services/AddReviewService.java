/**
 * @author ArthurSap
 */
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
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.mapmanagement.Location.LocationSubTypes;
import smartcity.accessibility.mapmanagement.Location.LocationTypes;
import smartcity.accessibility.navigation.Navigation;
import smartcity.accessibility.navigation.exception.CommunicationFailed;
import smartcity.accessibility.services.exceptions.AddReviewFailed;
import smartcity.accessibility.services.exceptions.UserIsNotLoggedIn;
import smartcity.accessibility.socialnetwork.Review;
import smartcity.accessibility.socialnetwork.User;

@RestController
public class AddReviewService {
	private static Logger logger = LoggerFactory.getLogger(AddReviewService.class);
	
	
	@RequestMapping(value = "/addreview", method = RequestMethod.POST,
			produces = "application/json")
	@ResponseBody
    public void addreview(@RequestHeader("authToken") String token,
    		@RequestParam("review") String review,
    		@RequestParam("score") String score,
    		@RequestParam("lat") Double lat,
    		@RequestParam("lng") Double lng,
			@RequestParam("type") String type,
			@RequestParam("subtype") String subtype,
			@RequestParam("name") String name) {
		
		
		User u = LogInService.getUserInfo(token).getUser();
		if (u == null)
			throw new UserIsNotLoggedIn();
		
		LatLng coordinates = new LatLng(lat, lng);
		Location l = AbstractLocationManager.instance().
				getLocation(coordinates,
				LocationTypes.valueOf(type.toUpperCase()),
				LocationSubTypes.valueOf(subtype.toUpperCase()),
				null).orElse(null);
		
		if(l == null){ //Location "l" doesn't exist
			l = new Location();
			l.setCoordinates(coordinates);
			l.setLocationType(LocationTypes.valueOf(type.toUpperCase()));
			l.setLocationSubType(LocationSubTypes.valueOf(subtype.toUpperCase()));
			l.setName(name);
			if(LocationTypes.valueOf(type.toUpperCase()).equals(LocationTypes.STREET)){
				try {
					String segmentId;
					segmentId = String.valueOf(Navigation.getMapSegmentOfLatLng(lat, lng).getLinkId());
					l.setSegmentId(segmentId );
				} catch (CommunicationFailed e) {
					logger.error("CommunicationFailed {} ", e);
					throw new AddReviewFailed();
				}
			}
			AbstractLocationManager.instance().uploadLocation(l, null);
		}
		
		
		Review r = new Review(l, Integer.parseInt(score), review, u.getProfile());
		if(u.canPinReview()) r.setPinned(true);
		
		AbstractReviewManager.instance().uploadReview(r, null);   	
    }
}