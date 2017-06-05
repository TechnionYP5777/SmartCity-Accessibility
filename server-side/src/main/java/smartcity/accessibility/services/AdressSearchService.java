package smartcity.accessibility.services;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import smartcity.accessibility.exceptions.illigalString;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.search.SearchQuery;
import smartcity.accessibility.search.SearchQueryResult;

/**
 * @author ariel
 */

@RestController
public class AdressSearchService {
	@RequestMapping(value="/simpleSearch/{search}")
	@ResponseBody
    public Location searchService(@RequestHeader("authToken") String token, @PathVariable("search") String search) {	
		
		
		try {
				SearchQuery $ = SearchQuery.adressSearch(search);
		        SearchQueryResult sqr1 = $.SearchByAddress();
		        $.waitOnSearch();
		        Location location2 = sqr1.getLocations().get(0);
		        return location2;	
		} catch (illigalString | InterruptedException e) {
			return null;
		}
    }
}