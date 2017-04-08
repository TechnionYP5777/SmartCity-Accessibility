package smartcity.accessibility.database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.inject.Inject;

import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.socialnetwork.Review;

public class ReviewManager {

	private Database db;
	private static final String DATABASE_CLASS = "Review";
	private static ReviewManager instance;

	@Inject
	public ReviewManager(Database db) {
		this.db = db;
	}

	public static void initialize(ReviewManager m) {
		instance = m;
	}

	public static ReviewManager instance() {
		return instance;
	}

	private static Map<String, Object> toMap(Review r) {
		Map<String, Object> map = new HashMap<>();

		return map;
	}

	private static Review fromMap(Map<String, Object> m) {
		return new Review(new Location(), 5, "12", "!2"); // TODO :This is
															// comepletely wrong
	}

	public List<Review> getReviews(Map<String, Object> fields) {
		return db.get(DATABASE_CLASS, fields).stream().map(m -> fromMap(m)).collect(Collectors.toList());
	}

	public static void uploadReview(Review r) {
		// db.put(DATABASE_CLASS, toMap(r));
	}

	public static void deleteReview(Review r) {
		// TODO Auto-generated method stub

	}

	public static void updateReview(Review review) {
		// TODO Auto-generated method stub

	}

}
