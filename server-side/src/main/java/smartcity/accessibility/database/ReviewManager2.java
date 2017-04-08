package smartcity.accessibility.database;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;

import smartcity.accessibility.socialnetwork.Review;

public class ReviewManager2 {

	private Database db;
	private static final String DATABASE_CLASS = "Review";

	@Inject
	public ReviewManager2(Database db) {
		this.db = db;
	}

	private static Map<String, Object> toMap(Review r) {
		Map<String, Object> map = new HashMap<>();
		map.put("test1", 69);
		return map;
	}

	public void uploadReview(Review r) {
		db.put(DATABASE_CLASS, toMap(r));
	}

}
