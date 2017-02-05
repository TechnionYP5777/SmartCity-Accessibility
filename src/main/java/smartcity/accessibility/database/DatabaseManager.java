
package smartcity.accessibility.database;

import java.util.List;
import java.util.Map;

import org.parse4j.Parse;
import org.parse4j.ParseException;
import org.parse4j.ParseGeoPoint;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.parse4j.callback.DeleteCallback;
import org.parse4j.callback.FindCallback;
import org.parse4j.callback.GetCallback;
import org.parse4j.callback.SaveCallback;

import com.teamdev.jxmaps.LatLng;

/**
 * @author KaplanAlexander
 *
 */
public abstract class DatabaseManager {
	public static final String serverUrl = "https://smartcityaccessibility.herokuapp.com/parse";
	public static final String restKey = "2139d-231cb2-738fe";
	public static final String appId = "smartcityaccessibility";
	private static boolean init;

	public static void initialize() {
		if (init)
			return;
		Parse.initialize(appId, restKey, serverUrl);
		init = true;
	}

	/**
	 * Get item of class objectClass in parse server with id
	 * 
	 * @param objectClass
	 * @param id
	 * @return ParseObject result of query or null if failed
	 * @throws ParseException
	 */
	public static ParseObject getValue(final String $, final String id) {
		try {
			return ParseQuery.getQuery($).get(id);
		} catch (ParseException ¢) {
			¢.printStackTrace();
		}
		return null;
	}

	/**
	 * Get item of class objectClass in parse server with id with callbackmethod
	 * get is done in background when item is retrieved function done in
	 * GetCallback is called if error occured ParseException!=null in done
	 * 
	 * @param objectClass
	 * @param id
	 * @param o
	 */
	public static void getValue(final String objectClass, final String id, GetCallback<ParseObject> o) {
		ParseQuery.getQuery(objectClass).getInBackground(id, o);
	}

	/**
	 * create a new object based on a mapping from strings to objects and put in
	 * server
	 * 
	 * @param objectClass
	 * @param fields
	 *            a mapping from a string to an object to be saved inside the
	 *            ParseObject
	 */
	public static ParseObject putValue(final String objectClass, Map<String, Object> fields) throws ParseException {
		final ParseObject $ = new ParseObject(objectClass);
		for (String key : fields.keySet())
			$.put(key, fields.get(key));
		$.save();
		return $;
	}

	/**
	 * create a new object based on a mapping from strings to objects and put in
	 * server
	 * 
	 * @param objectClass
	 * @param fields
	 *            a mapping from a string to an object to be saved inside the
	 *            ParseObject
	 * @param c
	 *            call back to be used for getting the result
	 */
	public static void putValue(final String objectClass, Map<String, Object> fields, SaveCallback c) {
		final ParseObject obj = new ParseObject(objectClass);
		for (String key : fields.keySet())
			obj.put(key, fields.get(key));
		obj.saveInBackground(c);
	}

	/**
	 * delete object in background from class @objectClass with id= @id when
	 * result is achieved c.done() is called
	 * 
	 * @param objectClass
	 * @param id
	 * @param c
	 *            callback method to get result
	 */
	public static void deleteById(final String objectClass, String id, DeleteCallback c) {
		final ParseObject obj = new ParseObject(objectClass);
		obj.setObjectId(id);
		obj.deleteInBackground(c);
	}

	/**
	 * deletes object in background while ignoring result
	 * 
	 * @param objectClass
	 * @param id
	 */
	public static void deleteById(final String objectClass, String id) {
		deleteById(objectClass, id, new DeleteCallback() {
			@Override
			public void done(ParseException arg0) {
				// do nothing
			}
		});
	}

	/**
	 * returns all the objects from @objectClass where the value for
	 * key @locationKeyName is a geo point and is within a @radius km distance
	 * from @center
	 * 
	 * @param objectClass
	 * @param center
	 *            The point from which the distance is calculated
	 * @param radius
	 *            distance in KM from the point for query
	 * @param locationKeyName
	 *            Key name that holds the geo point for objects
	 *            from @objectClass
	 * @param o
	 *            callback for result
	 * @return
	 * @throws ParseException
	 */
	public static void queryByLocation(final String objectClass, LatLng center, double radius, String locationKeyName,
			FindCallback<ParseObject> o) {
		ParseQuery<ParseObject> pq = ParseQuery.getQuery(objectClass);
		pq.whereWithinKilometers(locationKeyName, new ParseGeoPoint(center.getLat(), center.getLng()), radius);
		pq.findInBackground(o);
	}

	/**
	 * Calls queryByLocation(final String objectClass, LatLng center, double
	 * radius, String locationKeyName) with locationKeyName="location"
	 * 
	 * @param objectClass
	 * @param center
	 * @param radius
	 *            in KM
	 * @param o
	 *            callback for result
	 * @return
	 * @throws ParseException
	 */
	public static void queryByLocation(final String objectClass, LatLng center, double radius,
			FindCallback<ParseObject> o) {
		queryByLocation(objectClass, center, radius, "location", o);
	}

	/**
	 * returns objects in class where results fields match values mapping
	 * 
	 * @param objectClass
	 * @param values
	 * @param o
	 */
	public static void queryByFields(final String objectClass, Map<String, Object> values,
			FindCallback<ParseObject> o) {
		ParseQuery<ParseObject> pq = ParseQuery.getQuery(objectClass);
		for (String key : values.keySet())
			pq.whereEqualTo(key, values.get(key));
		pq.findInBackground(o);
	}

	/**
	 * returns objects in class where results fields match values mapping
	 * 
	 * @param objectClass
	 * @param values
	 */
	public static void getObjectByFields(final String objectClass, Map<String, Object> values,
			GetCallback<ParseObject> o) {
		ParseQuery<ParseObject> pq = ParseQuery.getQuery(objectClass);
		for (String key : values.keySet())
			pq.whereEqualTo(key, values.get(key));
		pq.limit(1);
		pq.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> arg0, ParseException arg1) {
				if (arg1 != null || arg0==null)
					o.done(null, arg1);
				else
					o.done(arg0.get(0), null);
			}
		});
	}

	/**
	 * Update the object from class @objectClass with id @id using the fields
	 * in @values fields that don't appear in values assume to be unchanged if
	 * object is not found on the server no action is taken
	 */
	public static void update(final String objectClass, final String id, Map<String, Object> values) {
		ParseQuery.getQuery(objectClass).getInBackground(id, new GetCallback<ParseObject>() {
			@Override
			public void done(ParseObject arg0, ParseException arg1) {
				if (arg0 == null || arg1 != null)
					return;
				for (String key : values.keySet())
					arg0.put(key, values.get(key));
				arg0.saveInBackground();
			}
		});
	}

}
