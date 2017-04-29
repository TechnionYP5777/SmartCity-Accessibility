package smartcity.accessibility.services;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import smartcity.accessibility.socialnetwork.Review;

@RestController
public class AddReviewService {
	@RequestMapping(value = "/addreview", method = RequestMethod.POST,
			produces = "application/json")
	@ResponseBody
    public void addreview(@RequestParam("review") String review,
    		@RequestParam("score") String score) {
		//TODO receive user credentials
		Review r = new Review(Integer.parseInt(score), review, null);
		//TODO wait until alex finishes DBs and upload the review
		//TODO pretty pretty finisher    	
    }
}