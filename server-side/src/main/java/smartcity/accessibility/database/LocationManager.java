package smartcity.accessibility.database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.parse4j.ParseGeoPoint;

import com.google.inject.Inject;
import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.database.callbacks.ICallback;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.mapmanagement.Location.LocationSubTypes;
import smartcity.accessibility.mapmanagement.Location.LocationTypes;
import smartcity.accessibility.mapmanagement.LocationBuilder;

/**
 * @author KaplanAlexander
 *
 */
public class LocationManager extends AbstractLocationManager {

	private Database db;
	private static final String DATABASE_CLASS = "Location";
	private static final String ID_FIELD_NAME = "objectId";

	@Inject
	public LocationManager(Database db) {
		this.db = db;
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
			lb.addReviews(AbstractReviewManager.instance().getReviews(m.get(ID_FIELD_NAME).toString(), null));
		}
		lb.setName(m.get("name").toString());
		lb.setType(LocationTypes.valueOf(m.get("type").toString()));
		lb.setSubType(LocationSubTypes.valueOf(m.get("subType").toString()));
		ParseGeoPoint pgp = (ParseGeoPoint) m.get("location");
		lb.setCoordinates(pgp.getLatitude(), pgp.getLongitude());
		return lb.build();
	}

	@Override
	public String getId(LatLng coordinates, LocationTypes locType, LocationSubTypes locSubType,
			ICallback<String> callback) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String uploadLocation(Location l, ICallback<String> callback) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Location> getLocation(LatLng coordinates, ICallback<List<Location>> locationListCallback) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Location> getLocationsAround(LatLng l, double distance,
			ICallback<List<Location>> locationListCallback) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Location getLocation(LatLng coordinates, LocationTypes locType, LocationSubTypes locSubType,
			ICallback<Location> callback) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean updateLocation(Location loc, ICallback<Boolean> callback) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LatLng> getNonAccessibleLocationsInRadius(LatLng source, LatLng destination,
			Integer accessibilityThreshold, ICallback<List<LatLng>> locationListCallback) {
		// TODO Auto-generated method stub
		return null;
	}

}
