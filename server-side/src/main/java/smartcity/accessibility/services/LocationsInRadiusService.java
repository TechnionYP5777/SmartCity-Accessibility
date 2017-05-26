package smartcity.accessibility.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.database.AbstractLocationManager;
import smartcity.accessibility.database.callbacks.ICallback;
import smartcity.accessibility.exceptions.illigalString;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.search.SearchQuery;
import smartcity.accessibility.search.SearchQueryResult;
/*
 * auth ariel
 */

//notice, this is the line to use:
//localhost:8090/locationsInRadius?srcLat=31.906953&srcLng=34.992489
//localhost:8090/locationsInRadius?srcLat=39.9129266808733&srcLng=-104.7956711
@RestController
public class LocationsInRadiusService {
	private static final double radius = 1;
	@RequestMapping(value="/locationsInRadius")
	@ResponseBody public List<Location> getLocationsInRadius(/*@RequestHeader("authToken") String token,*/ @RequestParam("srcLat") Double srcLat,
			@RequestParam("srcLng") Double srcLng) {	
		//ExtendedMapView mv = LogInService.getUserInfo(token).getMapView();
		try {
			SearchQuery sq = SearchQuery.TypeSearch("");
			Location dummy = new Location();
			dummy.setCoordinates(new LatLng(srcLat, srcLng));
			SearchQueryResult sqr = sq.searchByType(dummy, radius);
			List<Location> retVal = new ArrayList<Location>();
			
			AbstractLocationManager.instance().getLocationsAround(new LatLng(srcLat, srcLng), radius, new ICallback<List<Location>>() {
				
				@Override
				public void onFinish(List<Location> u) {
					try {
						sq.waitOnSearch();
						retVal.addAll(sqr.getLocations());
						retVal.addAll(u);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			});
			return retVal;
		} catch (illigalString e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
	}

}
