package smartcity.accessibility.services;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.database.AbstractLocationManager;
import smartcity.accessibility.database.callbacks.ICallback;
import smartcity.accessibility.mapmanagement.Location;

@RestController
public class addLocationService {
	
	@RequestMapping(value = "/addLocation", method = RequestMethod.POST,
			produces = "application/json")
	@ResponseBody public void getLocationsInRadius(@RequestParam("name") String name, @RequestParam("srcLat") Double srcLat, @RequestParam("srcLng") Double srcLng) {	
		Location dummy = new Location();
		dummy.setCoordinates(new LatLng(srcLat, srcLng));
		dummy.setName(name);
		AbstractLocationManager.instance().uploadLocation(dummy, new ICallback<String>() {
			@Override
			public void onFinish(String u) {
				;
			}
		});
	}
}
