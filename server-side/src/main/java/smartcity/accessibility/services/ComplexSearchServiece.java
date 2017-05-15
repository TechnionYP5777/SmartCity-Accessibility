package smartcity.accessibility.services;


import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import smartcity.accessibility.exceptions.EmptySearchQuery;
import smartcity.accessibility.exceptions.illigalString;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.search.SearchQuery;
import smartcity.accessibility.search.SearchQueryResult;
import smartcity.accessibility.services.exceptions.SearchFailed;

@Controller
public class ComplexSearchServiece {
	@RequestMapping("/complexSearch")
	@ResponseBody
	public List<Location> complexSearch(@RequestParam("type") String type,
			@RequestParam("radius") Integer radius, @RequestParam("startLocation") String startLoc,
			@RequestParam("threshold") Integer threshold) {

		SearchQuery $ = null;
		SearchQueryResult esr;
		try {
			$ = SearchQuery.TypeSearch(type);
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
}