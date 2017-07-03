package smartcity.accessibility.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.mapmanagement.LocationBuilder;
import smartcity.accessibility.navigation.Navigation;
import smartcity.accessibility.navigation.RouteResponse;
import smartcity.accessibility.navigation.exception.CommunicationFailed;
import smartcity.accessibility.services.exceptions.NavigationFailed;

/**
 * This class is web service for navigation
 * 
 * @author yael
 */
@Controller
public class NavigationService {
	private static Logger logger = LoggerFactory.getLogger(NavigationService.class);

	@RequestMapping(value = "/navigation", method = RequestMethod.POST)
	@ResponseBody
	public RouteResponse navigation(@RequestHeader("authToken") String token, @RequestParam("srcLat") Double srcLat,
			@RequestParam("srcLng") Double srcLng, @RequestParam("dstLat") Double dstLat,
			@RequestParam("dstLng") Double dstLng,
			@RequestParam("accessibilityThreshold") Integer accessibilityThreshold) {
		
		logger.info("navigation service started");
		
		LogInService.getUserInfo(token);

		Location source = new LocationBuilder().setCoordinates(srcLat, srcLng).build();
		Location destination = new LocationBuilder().setCoordinates(dstLat, dstLng).build();
		try {
			return Navigation.getRoute(source, destination, accessibilityThreshold);
		} catch (CommunicationFailed e) {
			logger.info("navigation failed", e);
			throw new NavigationFailed(e.getMessage());
		}

	}

}
