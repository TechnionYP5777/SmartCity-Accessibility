package smartcity.accessibility.services;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.database.AbstractLocationManager;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.mapmanagement.Location.LocationSubTypes;
import smartcity.accessibility.mapmanagement.Location.LocationTypes;
import smartcity.accessibility.mapmanagement.LocationBuilder;

@RestController
public class AddLocationService {

	@RequestMapping(value = "/addLocation", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public void getLocationsInRadius(@RequestParam("name") String name, @RequestParam("srcLat") Double srcLat,
			@RequestParam("srcLng") Double srcLng, @RequestParam("type") String sstype) {
		
		String stype = Capitilize(sstype); 
		Location.LocationSubTypes subtype = Location.LocationSubTypes.valueOf(stype);
		Location.LocationTypes type = subtype.getParentype();
		
		Location dummy = new LocationBuilder().setCoordinates(new LatLng(srcLat, srcLng)).setName(name)
				.setType(type).setSubType(subtype).build();
		AbstractLocationManager.instance().uploadLocation(dummy, s -> {});
	}

	private String Capitilize(String sstype) {
		return sstype.substring(0, 1).toUpperCase() + sstype.substring(1);
	}
}
