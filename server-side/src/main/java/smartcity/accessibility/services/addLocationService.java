package smartcity.accessibility.services;

import org.bouncycastle.crypto.tls.SRPTlsClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.database.AbstractLocationManager;
import smartcity.accessibility.database.callbacks.ICallback;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.mapmanagement.LocationBuilder;
import smartcity.accessibility.mapmanagement.Location.LocationSubTypes;
import smartcity.accessibility.mapmanagement.Location.LocationTypes;

@RestController
public class addLocationService {
	
	@RequestMapping(value = "/addLocation", method = RequestMethod.POST,
			produces = "application/json")
	@ResponseBody public void getLocationsInRadius(@RequestParam("name") String name, @RequestParam("srcLat") Double srcLat, @RequestParam("srcLng") Double srcLng) {	
		Location dummy = new LocationBuilder().setCoordinates(new LatLng(srcLat, srcLng))
				.setName(name)
				.setType(LocationTypes.Coordinate)
				.setSubType(LocationSubTypes.Default)
				.build();
		AbstractLocationManager.instance().uploadLocation(dummy, new ICallback<String>() {
			@Override
			public void onFinish(String u) {
				;
			}
		});
	}
}
