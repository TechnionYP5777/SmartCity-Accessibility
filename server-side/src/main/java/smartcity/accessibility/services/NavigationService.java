package smartcity.accessibility.services;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.navigation.Navigation;
import smartcity.accessibility.navigation.exception.CommunicationFailed;
import smartcity.accessibility.navigation.mapquestcommunication.Latlng;
import smartcity.accessibility.services.exceptions.NavigationFailed;

import org.springframework.web.bind.annotation.RequestHeader;

@Controller
public class NavigationService {
	@RequestMapping(value = "/navigation", method = RequestMethod.POST)
	@ResponseBody
	public Latlng[] navigation(@RequestHeader("authToken") String token, @RequestParam("srcLat") Double srcLat,
			@RequestParam("srcLng") Double srcLng, @RequestParam("dstLat") Double dstLat,
			@RequestParam("dstLng") Double dstLng,
			@RequestParam("accessibilityThreshold") Integer accessibilityThreshold) {
		if (!LogInService.isUserLoggedIn(token)) {
			throw new UserIsNotLoggedIn();
		}
		Location source = new Location(new LatLng(srcLat, srcLng));
		Location destination = new Location(new LatLng(dstLat, dstLng));
		Latlng[] l;
		try {
			l = Navigation.getRoute(source, destination, accessibilityThreshold);
		} catch (CommunicationFailed e) {
			throw new NavigationFailed();
		}
		return l;
	}

}
