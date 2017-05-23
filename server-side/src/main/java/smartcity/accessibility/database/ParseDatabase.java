package smartcity.accessibility.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.parse4j.Parse;
import org.parse4j.ParseException;
import org.parse4j.ParseGeoPoint;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import smartcity.accessibility.database.exceptions.ObjectNotFoundException;

/**
 * @author KaplanAlexander
 *
 */
public class ParseDatabase implements Database {

	public static final String SERVER_URL = "https://smartcityaccessibility.herokuapp.com/parse";
	public static final String REST_KEY = "2139d-231cb2-738fe";
	public static final String APP_ID = "smartcityaccessibility";
	public static final String OBJECT_ID_FIELD = "objectId";
	private static Logger logger = LoggerFactory.getLogger(ParseDatabase.class);
	private static boolean init = false;
	private static ParseDatabase pd = null;

	private ParseDatabase() {

	}

	public static synchronized ParseDatabase get() {
		if (pd == null) {
			initialize();
			pd = new ParseDatabase();
		}
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
		if (po == null)
			return null;
		Map<String, Object> map = new HashMap<>();
		for (String os : po.keySet()) {
			logger.debug("got key {}", os);
			map.put(os, po.get(os));
		}
		map.put(OBJECT_ID_FIELD, po.getObjectId());
		return map;
	}

	private static List<Map<String, Object>> toMap(List<ParseObject> lpo) {
		List<Map<String, Object>> lm = new ArrayList<>();
		if (lpo == null)
			return lm;
		for (ParseObject po : lpo) {
			lm.add(toMap(po));
		}
		return lm;
	}

	private static ParseObject fromMap(String objectClass, Map<String, Object> m) {
		logger.debug("entered fromMap");
		if (m == null || objectClass == null)
			return null;
		ParseObject po = new ParseObject(objectClass);
		for (Entry<String, Object> e : m.entrySet()) {
			logger.debug("put key " + e.getKey());
			if (e.getKey().equals(OBJECT_ID_FIELD))
				continue;
			po.put(e.getKey(), e.getValue());
		}
		return po;
	}

	@Override
	public Map<String, Object> get(String objectClass, String id) throws ObjectNotFoundException {
		logger.debug("getting object with id {}", id);
		try {
			ParseObject po = ParseQuery.getQuery(objectClass).get(id);
			if (po == null)
				throw new ObjectNotFoundException();
			return toMap(po);
		} catch (ParseException e) {
			logger.error("get object failed with error " + e.getMessage());
			throw new ObjectNotFoundException();
		}
	}

	@Override
	public List<Map<String, Object>> get(String objectClass, Map<String, Object> baseObject) {
		logger.debug("getting object with map {}", baseObject);
		ParseQuery<ParseObject> pq = ParseQuery.getQuery(objectClass);
		for (Entry<String, Object> e : baseObject.entrySet())
			pq.whereEqualTo(e.getKey(), e.getValue());

		try {
			return toMap(pq.find());
		} catch (ParseException e) {
			logger.error("get object failed with message {}", e);
		}
		return new ArrayList<>();
	}

	@Override
	public List<Map<String, Object>> get(String objectClass, String locationField, double latitude, double longitude,
			double radius) {
		logger.debug("getting object with location {}, {} within radius {}", latitude, longitude, radius);
		ParseQuery<ParseObject> pq = ParseQuery.getQuery(objectClass);
		pq.whereWithinKilometers(locationField, new ParseGeoPoint(latitude, longitude), radius);

		try {
			return toMap(pq.find());
		} catch (ParseException e) {
			logger.error("get object failed with message {}", e);
		}
		return new ArrayList<>();
	}

	@Override
	public String put(String objectClass, Map<String, Object> object) {
		ParseObject po = fromMap(objectClass, object);
		if (po == null)
			return null;
		try {
			po.save();
		} catch (ParseException e) {
			logger.error("put object failed with message ", e);
			return null;
		}
		return po.getObjectId();
	}

	@Override
	public boolean delete(String objectClass, String id) {
		try {
			ParseObject po = ParseQuery.getQuery(objectClass).get(id);
			if (po == null)
				return false;
			po.delete();
		} catch (ParseException e) {
			logger.error("delete object failed with error {}", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean update(String objectClass, String id, Map<String, Object> m) {
		try {
			ParseObject po = ParseQuery.getQuery(objectClass).get(id);
			if (po == null)
				return false;
			for (Entry<String, Object> e : m.entrySet())
				po.put(e.getKey(), e.getValue());
			po.save();
		} catch (ParseException e) {
			logger.error("update object failed with error {}", e);
			return false;
		}
		return true;
	}

	@Override
	public int countEntries(String objectClass) {
		try {
			return ParseQuery.getQuery(objectClass).count();
		} catch (ParseException e) {
			logger.error("count entries {} failed with error {}", objectClass, e);
		}
		return 0;
	}

	@Override
	public List<Map<String, Object>> getHighestBy(String objectClass, String field, int n) {
		try {
			return ParseQuery.getQuery(objectClass).orderByDescending(field).find().stream().limit(n)
					.map(ParseDatabase::toMap).collect(Collectors.toList());
		} catch (ParseException e) {
			logger.error("get highest rated of {} by {} failed with error {}", objectClass, field, e);
		}
		return new ArrayList<>();
	}

}
