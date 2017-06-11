package smartcity.accessibility.services;


import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.maps.model.LatLng;

import smartcity.accessibility.exceptions.EmptySearchQuery;
import smartcity.accessibility.exceptions.illigalString;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.mapmanagement.LocationBuilder;
import smartcity.accessibility.search.SearchQuery;
import smartcity.accessibility.search.SearchQueryResult;
import smartcity.accessibility.services.exceptions.SearchFailed;

@Controller
public class ComplexSearchServiece {
	@RequestMapping("/complexSearchAddress")
	@ResponseBody
	public List<Location> complexSearch(@RequestHeader("authToken") String token, @RequestParam("") String type,
			@RequestParam("radius") Integer radius, @RequestParam("startLocation") String startLoc,
			@RequestParam("threshold") Integer threshold) {

		SearchQuery $ = null;
		SearchQueryResult esr;
		
		try {
			$ = SearchQuery.TypeSearch(Location.LocationSubTypes.valueOf(type.toUpperCase()).getSearchType());
			esr = $.searchByType(startLoc, radius);
		} catch (illigalString | InterruptedException e) {
			throw new SearchFailed("illegal strings");
		}
		
		try {
		//	esr.convertDummiesToReal();
			esr.filterLocations(threshold);
		} catch (EmptySearchQuery ¢) {
			throw new SearchFailed("empty search query");
		}

		return esr.getLocations();
		
	}
	
	@RequestMapping("/complexSearchCoords")
	@ResponseBody
	public List<Location> complexSearchCoords(@RequestHeader("authToken") String token, @RequestParam("type") String type,
			@RequestParam("radius") Integer radius, @RequestParam("lat") Double lat, @RequestParam("lng") Double lng,
			@RequestParam("threshold") Integer threshold) {

		SearchQuery $ = null;
		SearchQueryResult esr;
		
		
			try {
				$ = SearchQuery.TypeSearch(Location.LocationSubTypes.valueOf(type.toUpperCase()).getSearchType());
			} catch (illigalString e) {
				throw new SearchFailed("type doesn't exists");
			}
			Location l = new LocationBuilder().build();
			l.setCoordinates(new LatLng(lat,lng));
			esr = $.typeSearch(l, radius);
	
		
		try {
		//	esr.convertDummiesToReal();
			esr.filterLocations(threshold);
		} catch (EmptySearchQuery ¢) {
			throw new SearchFailed("empty search query");
		}

		return esr.getLocations();
		
	}
	
}
