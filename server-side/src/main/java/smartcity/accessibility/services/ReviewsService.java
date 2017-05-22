package smartcity.accessibility.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.database.AbstractReviewManager;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.socialnetwork.Review;

@RestController
public class ReviewsService {
	
	@RequestMapping(value = "/reviews", produces = "application/json")
	@ResponseBody
    public void getreviews(@RequestParam("review") String review,
    		@RequestParam("lat") Double lat,
    		@RequestParam("lng") Double lng) {
		
		Location l = new Location();
		l.setCoordinates(new LatLng(lat, lng));
		
		List<Review> lst = new ArrayList<Review>(); //AbstractReviewManager.instance().
		
		for(Review r : lst){
			//convert them to json objects
		}
		
		//return somehow
    }

}
