package smartcity.accessibility.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.maps.model.LatLng;

import smartcity.accessibility.database.AbstractUserProfileManager;
import smartcity.accessibility.database.LocationManager;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.socialnetwork.UserProfile;

/**
 * 
 * @author Koral Chapnik
 *
 */

@RestController
public class AdminService {

	@RequestMapping(value = "/adminInfo")
	@ResponseBody
	public UserProfile getUserInfo(@RequestHeader("authToken") String token) {
		UserInfo userInfo = LogInService.getUserInfo(token);
		UserProfile up = userInfo.getUser().getProfile();
		UserProfile copyUserProfile = new UserProfile(up.getUsername());
		copyUserProfile.setHelpfulness(up.getHelpfulness());
		copyUserProfile.setProfileImg(null);
		return copyUserProfile;
	}

	@RequestMapping("/helpfulUsers")
	@ResponseBody
	public List<UserProfile> getMostHelpfulUsers(@RequestHeader("authToken") String token,
			@RequestParam("numOfUsers") Integer numOfUsers) {
		List<UserProfile> userProfiles = AbstractUserProfileManager.instance().mostHelpful(numOfUsers, null);
		List<UserProfile> userProfilesClone = new ArrayList<UserProfile>();
		for (UserProfile up : userProfiles) {
			UserProfile up_copy = new UserProfile(up.getUsername());
			up_copy.setHelpfulness(up.getHelpfulness());
			up_copy.setProfileImg(null);
			userProfilesClone.add(up_copy);
		}
		return userProfilesClone;
	}

	@RequestMapping("/mostRatedLocs")
	@ResponseBody
	public List<Location> getMostRatedLocations(@RequestHeader("authToken") String token,
			@RequestParam("radius") Integer radius, @RequestParam("srcLat") String srcLat,
			@RequestParam("srcLng") String srcLng, @RequestParam("numOfLocs") Integer numOfLocs) {
		LatLng src = new LatLng(Double.parseDouble(srcLat), Double.parseDouble(srcLng));
		List<Location> res = LocationManager.instance().getTopRated(src, radius, numOfLocs, null);
		res.stream().forEach(l -> l.setRating());
		return res;
	}

	@RequestMapping("/numOfUsers")
	@ResponseBody
	public Integer getNumOfUsers(@RequestHeader("authToken") String token) {
		return AbstractUserProfileManager.instance().userCount(null);
	}
}
