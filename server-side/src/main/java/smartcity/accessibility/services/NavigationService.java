package smartcity.accessibility.services;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.mapmanagement.LocationBuilder;
import smartcity.accessibility.navigation.Navigation;
import smartcity.accessibility.navigation.exception.CommunicationFailed;
import smartcity.accessibility.navigation.mapquestcommunication.Latlng;
import smartcity.accessibility.services.exceptions.NavigationFailed;

/**
 * @author yael
 */
@Controller
public class NavigationService {
	@RequestMapping(value = "/navigation", method = RequestMethod.POST)
	@ResponseBody
	public Latlng[] navigation(@RequestHeader("authToken") String token, @RequestParam("srcLat") Double srcLat,
			@RequestParam("srcLng") Double srcLng, @RequestParam("dstLat") Double dstLat,
			@RequestParam("dstLng") Double dstLng,
			@RequestParam("accessibilityThreshold") Integer accessibilityThreshold) {

		// example to get userInformation with the token. no need to check for
		// null, an exception will rise if needed
		// 		UserInfo userInfo = LogInService.getUserInfo(token);

		Location source = new LocationBuilder().setCoordinates(srcLat, srcLng).build();
		Location destination = new LocationBuilder().setCoordinates(dstLat, dstLng).build();
		Latlng[] l;
		try {
			l = Navigation.getRoute(source, destination, accessibilityThreshold);
		} catch (CommunicationFailed e) {
			throw new NavigationFailed();
		}
		return l;
	}

}
