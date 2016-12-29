package smartcity.accessibility.socialnetwork;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * @author KaplanAlexander
 *
 */
public class ReviewCommentTest {
	private static User u1;
	private static User u2;
	private static User u3;
	private static Review r1;

	
	@BeforeClass
	public static void init(){
		u1 = UserImpl.RegularUser("Alex","123","");
		u2 = UserImpl.RegularUser("Alex2","123","");
		u3 = UserImpl.Admin("Simba", "355", "");
	}
	
	@Test
	public void a(){
		r1 = new Review(null, 4, "?", u1);
		r1.upvote(u2);
		assertEquals(ReviewComment.POSITIVE_RATING,r1.calculateOpinion());
	}
	
	@Test
	public void b(){
		r1 = new Review(null, 4, "?", u1);
		r1.upvote(u2);
		r1.downvote(u3);
		assertEquals(ReviewComment.POSITIVE_RATING+ReviewComment.NEGATIVE_RATING
				,r1.calculateOpinion());
	}
	
	@Test
	public void c(){
		r1 = new Review(null, 4, "?", u1);
		r1.upvote(u2);
		r1.downvote(u3);
		r1.upvote(u2);
		r1.upvote(u2);
		r1.upvote(u2);
		r1.downvote(u3);

		assertEquals(ReviewComment.POSITIVE_RATING+ReviewComment.NEGATIVE_RATING
				,r1.calculateOpinion());
	}
	
	@Test 
	public void d(){
		r1 = new Review(null, 4, "?", u1);
		r1.upvote(u2);
		r1.downvote(u2);
		assertEquals(ReviewComment.NEGATIVE_RATING
				,r1.calculateOpinion());
	}
	
	@Test
	public void e(){
		r1 = new Review(null, 4, "?", u1);
		r1.comment(u1, 123);
		r1.comment(u2, 3);
		r1.comment(u1, 3);
		assertEquals(6
				,r1.calculateOpinion());
		
	}
	

}
