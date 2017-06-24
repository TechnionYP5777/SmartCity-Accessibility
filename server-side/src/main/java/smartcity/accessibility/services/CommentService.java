package smartcity.accessibility.services;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import smartcity.accessibility.database.AbstractReviewManager;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.services.exceptions.UserDoesNotExistException;
import smartcity.accessibility.socialnetwork.Review;
import smartcity.accessibility.socialnetwork.User;

@RestController
public class CommentService {

	@RequestMapping(value = "/addcomment", method = RequestMethod.POST,
			produces = "application/json")
	public void addComment(@RequestHeader("authToken") String token,
    		@RequestParam("rev") String rrev,
    		@RequestParam("comment") String comment,
    		@RequestParam("username") String username) throws Exception{
		System.out.println(rrev);
		User u = LogInService.getUserInfo(token).getUser();
		if (u == null)
			throw new UserDoesNotExistException();
		
		Review rev = getReviewFromString(rrev, null);
		
		rev.addComment(u, comment);
		
		AbstractReviewManager.instance().updateReview(rev, null);
	}
	
	private Review getReviewFromString(String rrev, Location l){
		int first = rrev.lastIndexOf("\"username\":\"");
		int second = rrev.lastIndexOf("\",\"avgRating\":") - "\",\"avgRating\":".length();
		String username = rrev.substring(first, second);
		for(Review r : l.getReviews())
			if(r.getUser().getUsername().equals(username))
				return r;
		return null;
	}
}
