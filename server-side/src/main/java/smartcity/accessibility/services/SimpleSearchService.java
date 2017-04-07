package smartcity.accessibility.services;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleSearchService {
	@RequestMapping("/simpleSearch")
    public String test() {
    	return "Omg So Much Searching added successfuly";
    }
}