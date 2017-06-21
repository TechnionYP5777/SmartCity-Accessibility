package smartcity.accessibility.services;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import smartcity.accessibility.database.AbstractReviewManager;
import smartcity.accessibility.services.exceptions.UserDoesNotExistException;
import smartcity.accessibility.socialnetwork.Review;
import smartcity.accessibility.socialnetwork.User;

@RestController
public class CommentService {

	@RequestMapping(value = "/addcomment", method = RequestMethod.POST,
			produces = "application/json")
	public void addComment(@RequestHeader("authToken") String token,
    		@RequestParam("rev") Review rev,
    		@RequestParam("comment") String comment,
    		@RequestParam("username") String username) throws Exception{
		
		User u = LogInService.getUserInfo(token).getUser();
		if (u == null)
			throw new UserDoesNotExistException();
		
		if(rev.getLocation() == null)
			throw new Exception("Location not found wtf is this");
		
		rev.addComment(u, comment);
		
		AbstractReviewManager.instance().updateReview(rev, null);
	}
}
