package smartcity.accessibility.socialnetwork;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import smartcity.accessibility.categories.UnitTests;
import smartcity.accessibility.exceptions.UnauthorizedAccessException;
import smartcity.accessibility.mapmanagement.Location;

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
	private static Location l;

	@BeforeClass
	public static void init() {
		u1 = UserBuilder.RegularUser("Alex", "123", "");
		u2 = UserBuilder.RegularUser("Alex2", "123", "");
		u3 = UserBuilder.Admin("Simba", "355", "");
		Location l = new Location();
	}

	@Test
	@Category(UnitTests.class)
	public void a() throws UnauthorizedAccessException {
		r1 = new Review(l, 4, "?", u1);
		r1.upvote(u2);
		assertEquals(ReviewComment.POSITIVE_RATING, r1.calculateOpinion());
	}

	@Test
	@Category(UnitTests.class)
	public void b() throws UnauthorizedAccessException {
		r1 = new Review(l, 4, "?", u1);
		r1.upvote(u2);
		r1.downvote(u3);
		assertEquals(ReviewComment.POSITIVE_RATING + ReviewComment.NEGATIVE_RATING, r1.calculateOpinion());
	}

	@Test
	@Category(UnitTests.class)
	public void c() throws UnauthorizedAccessException {
		r1 = new Review(l, 4, "?", u1);
		r1.upvote(u2);
		r1.downvote(u3);
		r1.upvote(u2);
		r1.upvote(u2);
		r1.upvote(u2);
		r1.downvote(u3);

		assertEquals(ReviewComment.POSITIVE_RATING + ReviewComment.NEGATIVE_RATING, r1.calculateOpinion());
	}

	@Test
	@Category(UnitTests.class)
	public void d() throws UnauthorizedAccessException {
		r1 = new Review(l, 4, "?", u1);
		r1.upvote(u2);
		r1.downvote(u2);
		assertEquals(ReviewComment.NEGATIVE_RATING, r1.calculateOpinion());
	}

	@Test
	@Category(UnitTests.class)
	public void e() throws UnauthorizedAccessException {
		r1 = new Review(l, 4, "?", u1);
		r1.comment(u1, 123);
		r1.comment(u2, 3);
		r1.comment(u1, 3);
		assertEquals(6, r1.calculateOpinion());

	}

}
