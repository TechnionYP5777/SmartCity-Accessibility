package smartcity.accessibility.database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.parse4j.ParseGeoPoint;

import com.google.inject.Inject;
import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.mapmanagement.Location.LocationSubTypes;
import smartcity.accessibility.mapmanagement.Location.LocationTypes;
import smartcity.accessibility.mapmanagement.LocationBuilder;

public class LocationManager {

	private Database db;
	private static final String DATABASE_CLASS = "Location";
	private static final String ID_FIELD_NAME = "objectId";

	private static LocationManager instance;

	@Inject
	public LocationManager(Database db) {
		this.db = db;
	}

	public static void initialize(LocationManager m) {
		instance = m;
	}

	public static LocationManager instance() {
		return instance;
	}

	private static Map<String, Object> toMap(Location l) {
		Map<String, Object> map = new HashMap<>();
		map.put("location", new ParseGeoPoint(l.getCoordinates().getLat(), l.getCoordinates().getLng()));
		map.put("type", l.getLocationType().toString());
		map.put("subType", l.getLocationSubType().toString());
		map.put("name", l.getName());
		return map;
	}

	private static Location fromMap(Map<String, Object> m) {
		LocationBuilder lb = new LocationBuilder();
		if (m.containsKey(ID_FIELD_NAME)) {
			Map<String, Object> fields = new HashMap<>();
			fields.put(ID_FIELD_NAME, m.get(ID_FIELD_NAME));
			lb.addReviews(ReviewManager.instance().getReviews(fields));
		}
		lb.setName(m.get("name").toString());
		lb.setType(LocationTypes.valueOf(m.get("type").toString()));
		lb.setSubType(LocationSubTypes.valueOf(m.get("subType").toString()));
		ParseGeoPoint pgp = (ParseGeoPoint) m.get("location");
		lb.setCoordinates(pgp.getLatitude(), pgp.getLongitude());
		return lb.build();
	}

	public String uploadLocation(Location l) {
		return db.put(DATABASE_CLASS, toMap(l));
	}

	public static void getLocation(LatLng coordinates, LocationListCallback locationListCallback) {
		// TODO Auto-generated method stub
	}

	public static void getLocationsNearPoint(LatLng l, LocationListCallback locationListCallback, int i) {
		// TODO Auto-generated method stub

	}

	public static Location getLocation(LatLng coordinates, LocationTypes street, LocationSubTypes default1) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void updateLocation(Location loc) {
		// TODO Auto-generated method stub

	}

	public static List<LatLng> getNonAccessibleLocationsInRadius(Location source, Location destination,
			Integer accessibilityThreshold) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getId(LatLng coordinates) {
		// TODO : Try with db.get with coordinates and radius 0.0
		return null;
	}

}
