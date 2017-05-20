package smartcity.accessibility.services;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.exceptions.illigalString;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.search.SearchQuery;
import smartcity.accessibility.search.SearchQueryResult;

@RestController
public class LocationsInRadiusService {
	private static final int radius = 50;
	@RequestMapping(value="/locationsInRadius")
	@ResponseBody public List<Location> getLocationsInRadius(/*@RequestHeader("authToken") String token, @RequestParam("srcLat") Double srcLat,
			@RequestParam("srcLng") Double srcLng*/) {	
		//ExtendedMapView mv = LogInService.getUserInfo(token).getMapView();
		try {
			double srcLat = 31.906953;
			double srcLng = 34.992489;
			SearchQuery sq = SearchQuery.TypeSearch("");
			Location dummy = new Location();
			dummy.setCoordinates(new LatLng(srcLat, srcLng));
			SearchQueryResult sqr = sq.searchByType(dummy, radius);
			sq.waitOnSearch();
			return sqr.getLocations();
		} catch (illigalString | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
