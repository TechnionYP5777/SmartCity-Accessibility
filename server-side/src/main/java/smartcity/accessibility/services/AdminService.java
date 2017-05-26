package smartcity.accessibility.services;

import java.util.List;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.database.AbstractUserProfileManager;
import smartcity.accessibility.database.LocationManager;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.socialnetwork.UserProfile;

@RestController
public class AdminService {
	
	@RequestMapping(value="/adminInfo")
	@ResponseBody public UserProfile getUserInfo(@RequestHeader("authToken") String token) {	
		UserInfo userInfo = LogInService.getUserInfo(token);
		return userInfo.getUser().getProfile();
	}
	
	@RequestMapping("/helpfulUsers")
	@ResponseBody public List<UserProfile> getMostHelpfulUsers(@RequestHeader("authToken") String token,
															   @RequestParam("numOfUsers") Integer numOfUsers) {
		
		return AbstractUserProfileManager.instance().mostHelpful(numOfUsers, null);	
	}
	
	@RequestMapping("/mostRatedLocs")
	@ResponseBody public List<Location> getMostRatedLocations(@RequestHeader("authToken") String token,
															   @RequestParam("radius") Integer radius,
															   @RequestParam("srcLat") Double srcLat,
															   @RequestParam("srcLng") Double srcLng,
															   @RequestParam("numOfLocs") Integer numOfLocs) {
		LatLng src = new LatLng(srcLat, srcLng);
		return LocationManager.instance().getTopRated(src, radius, numOfLocs, null);
	}
}
