package smartcity.accessibility.services;


import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.exceptions.EmptySearchQuery;
import smartcity.accessibility.exceptions.illigalString;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.search.SearchQuery;
import smartcity.accessibility.search.SearchQueryResult;

import org.springframework.web.bind.annotation.RequestHeader;

@Controller
public class ComplexSearchServiece {
	@RequestMapping(value="/complexSearch")
	@ResponseBody
	public List<Location> complexSearch(@RequestHeader("authToken") String token, @RequestParam("type") String type,
			@RequestParam("radius") Integer radius, @RequestParam("srcLat") Double srcLat,
			@RequestParam("srcLng") Double srcLng, @RequestParam("threshold") Integer threshold) {
		if (!LogInService.isUserLoggedIn(token)) {
			throw new UserIsNotLoggedIn();
		}
		
		LatLng c = new LatLng(srcLat, srcLng);
		SearchQuery $ = null;
		try {
			$ = SearchQuery.TypeSearch(type);
		} catch (illigalString e) {
			//TODO : throw exception
		}
		SearchQueryResult esr = $.searchByType(new Location(c), radius);
		
		try {
			esr.convertDummiesToReal();
		} catch (EmptySearchQuery ¢) {
			//TODO : throw exception
		}

		try {
			esr.filterLocations(threshold);
		} catch (EmptySearchQuery ¢) {
			//TODO : throw exception
		}
		
		return esr.getLocations();
		
	}
}
