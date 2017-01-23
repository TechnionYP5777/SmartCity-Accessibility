package smartcity.accessibility.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

//import org.eclipse.persistence.internal.sessions.remote.SequencingFunctionCall.GetNextValue;
import org.parse4j.ParseException;
import org.parse4j.ParseGeoPoint;
import org.parse4j.ParseObject;
import org.parse4j.callback.FindCallback;
import org.parse4j.callback.GetCallback;
import org.parse4j.callback.SaveCallback;

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.exceptions.UnauthorizedAccessException;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.socialnetwork.Review;
import smartcity.accessibility.socialnetwork.User;
import smartcity.accessibility.socialnetwork.UserImpl;

/**
 * @author assaflu
 *
 */
public class LocationManager {

	/**
	 * If the location saved return it to the callback function If it's not
	 * saved notify the callback function
	 * 
	 * @param l
	 * @param o
	 */
	static void checkLocationInDB(Location l, GetCallback<ParseObject> o) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("coordinates", new ParseGeoPoint(l.getCoordinates().getLat(), l.getCoordinates().getLng()));
		m.put("subtype", (l.getLocationSubType() != null ? l.getLocationSubType() : Location.LocationSubTypes.Default) + "");
		m.put("type", (l.getLocationType() != null ? l.getLocationType() : Location.LocationTypes.Coordinate) + "");
		DatabaseManager.getObjectByFields("Location", m, (new GetCallback<ParseObject>() {
			@Override
			public void done(ParseObject arg0, ParseException arg1) {
				o.done(arg1 == null && arg0 != null ? arg0 : null, null);
			}
		}));
	}

	/**
	 * calculate the distance between the source point and destination point in
	 * meters
	 * 
	 * @param source
	 * @param destination
	 * @return
	 */
	private static double distanceBtween(LatLng source, LatLng destination) {
		double lat1 = source.getLat();
		double lat2 = destination.getLat();
		double lon1 = source.getLng();
		double lon2 = destination.getLng();
		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);
		return 12732000 * Math.asin(Math.sqrt((Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2)
				* Math.sin(dLon / 2) * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)))));
	}

	/**
	 * return list of LatLng that qualify the radius and threshold (not yet know
	 * if to implement in background or not - opened issue on it)
	 * 
	 * @param source
	 * @param destination
	 * @param accessibilityThreshold
	 * @return
	 */
	public static List<LatLng> getNonAccessibleLocationsInRadius(Location source, Location destination,
			Integer accessibilityThreshold) {
		StringBuilder mutex = new StringBuilder();
		final AtomicInteger secondM = new AtomicInteger(0);
		double radius = distanceBtween(source.getCoordinates(), destination.getCoordinates());
		LatLng center = new LatLng((source.getCoordinates().getLat() + destination.getCoordinates().getLat()) / 2,
				(source.getCoordinates().getLng() + destination.getCoordinates().getLng()) / 2);
		ArrayList<LatLng> $ = new ArrayList<LatLng>();
		FindCallback<ParseObject> callBack = new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> arg0, ParseException arg1) {
				if (arg1 == null && arg0 != null)
					for (ParseObject obj : arg0) {
						ParseGeoPoint point = (ParseGeoPoint) obj.get("coordinates");
						$.add(new LatLng(point.getLatitude(), point.getLongitude()));
					}
				synchronized (mutex) {
					mutex.append("done");
					mutex.notifyAll();
				}

			}
		};
		DatabaseManager.queryByLocation("Location", center, radius / 1000, "coordinates", callBack);
		synchronized (mutex) {
			if (!"done".equals(mutex))
				try {
					mutex.wait();
				} catch (InterruptedException e) {
				}
		}
		final List<Location> loc = new ArrayList<Location>();
		LocationListCallback list = new LocationListCallback() {

			@Override
			public void done(List<Location> ls) {
				for (Location ¢ : ls)
					loc.add(¢);
				synchronized (secondM) {
					secondM.set(1);
					secondM.notifyAll();
				}
			}
		};

		for (LatLng l : $) {
			getLocation(l, list);
			synchronized (secondM) {
				if (secondM.get() == 0)
					try {
						secondM.wait();
					} catch (InterruptedException e) {
					}
				secondM.set(0);
			}
		}

		List<Location> returnMe = FilterToAllAbove(loc, 1, accessibilityThreshold); // '1' will be changed in the future

		for (Location ¢ : returnMe)
			$.remove(¢.getCoordinates());

		$.remove(destination.getCoordinates());
		$.remove(source.getCoordinates());
		return $;
	}

	/**
	 * this function will return a specific location, not happened in the
	 * background
	 * 
	 * @param point
	 * @param t
	 * @param subtype
	 * @return
	 */
	public static Location getLocation(LatLng point, Location.LocationTypes t, Location.LocationSubTypes subtype) {
		// StringBuilder mutex = new StringBuilder();
		final AtomicInteger mutI = new AtomicInteger(0);
		ArrayList<Review> reviews = new ArrayList<Review>();
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("location", new ParseGeoPoint(point.getLat(), point.getLng()));
		FindCallback<ParseObject> callBackR = new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> arg0, ParseException arg1) {
				if (arg1 == null && arg0 != null)
					for (ParseObject ¢ : arg0)
						reviews.add(new Review(new Location(point, t, subtype), ¢.getInt("rating"),
								¢.getString("comment"), ¢.getString("user")));
				synchronized (mutI) {
					mutI.set(1);
					mutI.notifyAll();
				}
			}
		};

		DatabaseManager.queryByFields("Review", values, callBackR);
		synchronized (mutI) {
			if ((mutI.get() == 0))
				try {
					mutI.wait();
				} catch (InterruptedException e) {
				}
		}
		return new Location(point, t, subtype, reviews);
	}

	/**
	 * return list of location that belong to the point, each location with it's
	 * own reviews
	 * 
	 * @param point
	 * @param c
	 */
	public static void getLocation(LatLng point, LocationListCallback c) {
		ArrayList<Review> reviews = new ArrayList<Review>();
		ArrayList<Location> ls = new ArrayList<Location>();
		Map<String, Object> valuesR = new HashMap<String, Object>();
		Map<String, Object> valuesL = new HashMap<String, Object>();

		valuesL.put("coordinates", new ParseGeoPoint(point.getLat(), point.getLng()));
		valuesR.put("location", new ParseGeoPoint(point.getLat(), point.getLng()));

		FindCallback<ParseObject> callBackL = new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> arg0, ParseException arg1) {
				if (arg0 != null) {
					int index = 0;
					for (ParseObject obj : arg0) { // get all the locations  belong to the latlng and their id's
						ls.add(new Location(point, Location.stringToEnumTypes(obj.getString("type")),
								Location.stringToEnumSubTypes(obj.getString("subtype"))));
						for (Review r : reviews)
							if (r.getLocationID().equals(obj.getObjectId())) {
								try {
									r.locationSet(new UserImpl("db", "123123", User.Privilege.Admin), ls.get(index));
								} catch (UnauthorizedAccessException e) {
									System.out.println("someting went worng loading reviews to location");
								}
								ls.get(index).addReviewNoSave(r);
							}
					}
				}
				c.done(ls);
			}
		};

		DatabaseManager.queryByFields("Review", valuesR, (new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> arg0, ParseException arg1) {
				if (arg1 == null && arg0 != null)
					for (ParseObject obj : arg0) {
						Review r = new Review(obj.getString("locationID"), obj.getInt("rating"),
								obj.getString("comment"), obj.getString("user"));
						if (obj.getInt("pined") == 1)
							try {
								r.pin(new UserImpl("db", "123123", User.Privilege.Admin));
							} catch (UnauthorizedAccessException e) {
								System.out.println("someting went worng loading reviews to location");
							}
						reviews.add(r);
					}
				DatabaseManager.queryByFields("Location", valuesL, callBackL);
			}
		}));
	}

	/**
	 * Save the location in the DB happen in the background
	 * 
	 * @param l
	 */
	public static void saveLocation(Location l, SaveCallback o) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("subtype", (l.getLocationSubType() != null ? l.getLocationSubType() : Location.LocationSubTypes.Default) + "");
		m.put("type", (l.getLocationType() != null ? l.getLocationType() : Location.LocationTypes.Coordinate) + "");
		m.put("coordinates", new ParseGeoPoint(l.getCoordinates().getLat(), l.getCoordinates().getLng()));
		checkLocationInDB(l, new GetCallback<ParseObject>() {

			@Override
			public void done(ParseObject arg0, ParseException arg1) {
				if (arg0 == null)
					DatabaseManager.putValue("Location", m, new SaveCallback() {
						@Override
						public void done(ParseException arg0) {
							o.done(arg0);
							for (Review ¢ : l.getReviews())
								ReviewManager.updateReview(¢);
						}
					});
			}
		});
	}

	/**
	 * If the location is not in the DB save it else update it (include updating
	 * the reviews belong to this location)
	 * 
	 * @param l
	 */
	public static void updateLocation(Location l) {
		checkLocationInDB(l, (new GetCallback<ParseObject>() {
			@Override
			public void done(ParseObject arg0, ParseException arg1) {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("coordinates", new ParseGeoPoint(l.getCoordinates().getLat(), l.getCoordinates().getLng()));
				if (arg0 == null)
					saveLocation(l, new SaveCallback() {
						@Override
						public void done(ParseException arg0) {
						}
					});
				else {
					DatabaseManager.update("Location", arg0.getObjectId(), m);
					for (Review ¢ : l.getReviews())
						ReviewManager.updateReview(¢);
				}
			}
		}));

	}

	/*
	 * @Author Kolikant
	 */
	public static List<Location> FilterToAllBellow(List<Location> totalLocation, int ReviewsTakenToAccount,
			int accessibilityLevel) {
		return totalLocation.stream().filter(p -> p.getRating(ReviewsTakenToAccount).getScore() < accessibilityLevel)
				.collect(Collectors.toList());
	}

	/*
	 * @Author Kolikant
	 */
	public static List<Location> FilterToAllAbove(List<Location> totalLocation, int ReviewsTakenToAccount,
			int accessibilityLevel) {
		return totalLocation.stream().filter(p -> p.getRating(ReviewsTakenToAccount).getScore() >= accessibilityLevel)
				.collect(Collectors.toList());
	}

	/**
	 * return the location into the call back (without their reviews)
	 * 
	 * @param point
	 * @param c
	 * @param radius
	 */
	public static void getLocationsNearPoint(LatLng point, LocationListCallback c, double radius) {
		ArrayList<Location> ls = new ArrayList<Location>();
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("location", new ParseGeoPoint(point.getLat(), point.getLng()));
		DatabaseManager.queryByLocation("Location", point, radius, "coordinates", (new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> arg0, ParseException arg1) {
				if (arg0 != null)
					for (ParseObject ¢ : arg0)
						ls.add(new Location(point, Location.stringToEnumTypes(¢.getString("type")),
								Location.stringToEnumSubTypes(¢.getString("subtype"))));
				c.done(ls);
			}
		}));
	}

}
