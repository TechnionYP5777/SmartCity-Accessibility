package smartcity.accessibility.database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.parse4j.Parse;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParseDatabase implements Database {

	public static final String SERVER_URL = "https://smartcityaccessibility.herokuapp.com/parse";
	public static final String REST_KEY = "2139d-231cb2-738fe";
	public static final String APP_ID = "smartcityaccessibility";
	private static Logger logger = LoggerFactory.getLogger(ParseDatabase.class);
	private static boolean init = false;
	private static ParseDatabase pd = null;

	private ParseDatabase() {

	}

	public static ParseDatabase get() {
		if (pd == null)
			pd = new ParseDatabase();
		return pd;
	}

	public static synchronized void initialize() {
		logger.info("initializing db");
		if (init)
			return;
		Parse.initialize(APP_ID, REST_KEY, SERVER_URL);
		init = true;
	}

	private static Map<String, Object> toMap(ParseObject po) {
		logger.debug("entered toMap");
		Map<String, Object> map = new HashMap<>();
		for (String os : po.keySet()) {
			logger.debug("got key " + os);
			map.put(os, po.get(os));
		}
		return map;
	}

	private static ParseObject fromMap(String objectClass, Map<String, Object> m) {
		logger.debug("entered fromMap");
		ParseObject po = new ParseObject(objectClass);
		for (Entry<String, Object> e : m.entrySet()) {
			logger.debug("put key " + e.getKey());
			po.put(e.getKey(), e.getValue());
		}
		return po;
	}

	@Override
	public Map<String, Object> get(String objectClass, String id) {
		try {
			ParseObject po = ParseQuery.getQuery(objectClass).get(id);
			return toMap(po);
		} catch (ParseException e) {
			logger.error("get object failed with error " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> get(String objectClass, Map<String, Object> baseObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> get(String objectClass, String field, double latitude, double longitude,
			double radius) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> getAll(String objectClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String put(String objectClass, Map<String, Object> object) {
		ParseObject po = fromMap(objectClass, object);
		try {
			po.save();
		} catch (ParseException e) {
			logger.error("put object failed with message " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		return po.getObjectId();
	}

}
