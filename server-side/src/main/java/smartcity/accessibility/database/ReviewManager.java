package smartcity.accessibility.database;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;

import smartcity.accessibility.socialnetwork.Review;

public class ReviewManager {

	private Database db;
	private static final String DATABASE_CLASS = "Review";

	@Inject
	public ReviewManager(Database db) {
		this.db = db;
	}

	private static Map<String, Object> toMap(Review r) {
		Map<String, Object> map = new HashMap<>();
		map.put("test1", 69);
		return map;
	}

	public static void uploadReview(Review r) {
		//db.put(DATABASE_CLASS, toMap(r));
	}

	public static void deleteReview(Review r) {
		// TODO Auto-generated method stub
		
	}

	public static void updateReview(Review review) {
		// TODO Auto-generated method stub
		
	}

}
