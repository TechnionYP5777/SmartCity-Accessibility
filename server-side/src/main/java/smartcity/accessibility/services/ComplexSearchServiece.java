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

@Controller
public class ComplexSearchServiece {
	@RequestMapping("/complexSearch")
	@ResponseBody
	public List<Location> complexSearch(@RequestParam("type") String type,
			@RequestParam("radius") Integer radius, @RequestParam("srcLat") Double srcLat,
			@RequestParam("srcLng") Double srcLng, @RequestParam("threshold") Integer threshold) {

		LatLng c = new LatLng(srcLat, srcLng);
		SearchQuery $ = null;
		try {
			$ = SearchQuery.TypeSearch(type);
		} catch (illigalString e) {
			throw new SearchFailed("illegal string");
		}
		SearchQueryResult esr = $.searchByType(new Location(c), radius);
		
		try {
		//	esr.convertDummiesToReal();
			esr.filterLocations(threshold);
		} catch (EmptySearchQuery ¢) {
			throw new SearchFailed("empty search query");
		}

		return esr.getLocations();
		
	}
}
