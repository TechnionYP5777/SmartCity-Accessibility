package smartcity.accessibility.services;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.database.AbstractLocationManager;
import smartcity.accessibility.exceptions.illigalString;
import smartcity.accessibility.mapmanagement.JxMapsFunctionality.ExtendedMapView;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.mapmanagement.LocationBuilder;
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
	private static final double Radius = 0.001;
	@RequestMapping(value="/locationsInRadius")
	@ResponseBody public List<Location> getLocationsInRadius(@RequestHeader("authToken") String token, @RequestParam("srcLat") Double srcLat,
			@RequestParam("srcLng") Double srcLng) {	
		try {
			ExtendedMapView mapView = LogInService.getMapView(token);
			synchronized(mapView){
				SearchQuery sq = SearchQuery.TypeSearch("", mapView);
				Location dummy = new Location();
				dummy.setCoordinates(new LatLng(srcLat, srcLng));
				SearchQueryResult sqr = sq.searchByType(dummy, Radius);
				List<Location> retVal = AbstractLocationManager.instance().getLocationsAround(new LatLng(srcLat, srcLng), Radius, null);	
				sq.waitOnSearch();
				return preferDBLocations(sqr.getLocations(), retVal);
			}
		} catch (illigalString | InterruptedException e) {
			return null;
		} 
	}
	
	List<Location> preferDBLocations(List<Location> fromGM, List<Location> fromDB){
		List<Location> DBasDefualtValues = new ArrayList<>();
		DBasDefualtValues.addAll(fromDB);
		DBasDefualtValues.stream().map(new Function<Location, Location>() {
			@Override
			public Location apply(Location arg0) {
				return new LocationBuilder().setCoordinates(arg0.getCoordinates()).setName(arg0.getName())
				.setType(Location.LocationTypes.Coordinate).setSubType(Location.LocationSubTypes.Default).build();
			}
        });
		List<Location> retVal = new ArrayList<>();
		retVal.addAll(fromGM.stream().filter(p -> !DBasDefualtValues.contains(p)).collect(Collectors.toList()));
		retVal.addAll(fromDB);
		return retVal;
	}

}
