package smartcity.accessibility.services;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleSearchService {
	@RequestMapping(value="/simpleSearch/{search}")
	@ResponseBody
    public String searchService(@PathVariable("search") String search) {
    	return "a, I see you are searching for " + search + " well....tough luck";
    }
}