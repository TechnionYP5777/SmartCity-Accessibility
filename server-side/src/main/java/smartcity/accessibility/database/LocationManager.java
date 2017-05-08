package smartcity.accessibility.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.parse4j.ParseGeoPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.teamdev.jxmaps.LatLng;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import smartcity.accessibility.database.callbacks.ICallback;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.mapmanagement.Location.LocationSubTypes;
import smartcity.accessibility.mapmanagement.Location.LocationTypes;
import smartcity.accessibility.mapmanagement.LocationBuilder;
import smartcity.accessibility.socialnetwork.BestReviews;

/**
 * @author KaplanAlexander
 *
 */
public class LocationManager extends AbstractLocationManager {

	public static final String DATABASE_CLASS = "Location";
	private Database db;

	public static final String NAME_FIELD_NAME = "name";
	public static final String SUB_TYPE_FIELD_NAME = "subType";
	public static final String TYPE_FIELD_NAME = "type";
	public static final String LOCATION_FIELD_NAME = "location";
	public static final String ID_FIELD_NAME = "objectId";

	private static Logger logger = LoggerFactory.getLogger(LocationManager.class);

	@Inject
	public LocationManager(Database db) {
		this.db = db;
	}

	public static Map<String, Object> toMap(Location l) {
		Map<String, Object> map = new HashMap<>();
		map.put(LOCATION_FIELD_NAME, new ParseGeoPoint(l.getCoordinates().getLat(), l.getCoordinates().getLng()));
		map.put(TYPE_FIELD_NAME, l.getLocationType().toString());
		map.put(SUB_TYPE_FIELD_NAME, l.getLocationSubType().toString());
		map.put(NAME_FIELD_NAME, l.getName());
		return map;
	}

	public static Location fromMap(Map<String, Object> m) {
		LocationBuilder lb = new LocationBuilder();
		lb.setName(m.get(NAME_FIELD_NAME).toString());
		lb.setType(LocationTypes.valueOf(m.get(TYPE_FIELD_NAME).toString()));
		lb.setSubType(LocationSubTypes.valueOf(m.get(SUB_TYPE_FIELD_NAME).toString()));
		ParseGeoPoint pgp = (ParseGeoPoint) m.get(LOCATION_FIELD_NAME);
		lb.setCoordinates(pgp.getLatitude(), pgp.getLongitude());
		return lb.build();
	}

	@Override
	public String getId(LatLng coordinates, LocationTypes locType, LocationSubTypes locSubType,
			ICallback<String> callback) {
		logger.info("getting id of location {} {} {}", coordinates, locType, locSubType);
		Flowable<String> res = Flowable.fromCallable(() -> {
			Map<String, Object> m = new HashMap<>();
			m.put(LOCATION_FIELD_NAME, new ParseGeoPoint(coordinates.getLat(), coordinates.getLng()));
			m.put(TYPE_FIELD_NAME, locType);
			m.put(SUB_TYPE_FIELD_NAME, locSubType);
			List<Map<String, Object>> locs = db.get(DATABASE_CLASS, m);
			if (locs.isEmpty()) {
				logger.info("no locations found for {}, {}, {}", coordinates, locType, locSubType);
				return null;
			}
			if (locs.size() > 1)
				logger.error("Multiple locations found with same id (coord, loctype and loc subtype) for {} , {} , {}",
						coordinates, locType, locSubType);
			return locs.get(0).get(ID_FIELD_NAME).toString();
		}).subscribeOn(Schedulers.io()).observeOn(Schedulers.single());
		if (callback == null)
			return res.blockingFirst();
		res.subscribe(callback::onFinish, Throwable::printStackTrace);
		return null;
	}

	@Override
	public String uploadLocation(Location l, ICallback<String> callback) {
		Flowable<String> res = Flowable.fromCallable(() -> {
			if (getId(l.getCoordinates(), l.getLocationType(), l.getLocationSubType(), null) == null)
				return null;
			return db.put(DATABASE_CLASS, toMap(l));
		}).subscribeOn(Schedulers.io()).observeOn(Schedulers.single());
		if (callback == null)
			return res.blockingFirst();
		res.subscribe(callback::onFinish, Throwable::printStackTrace);
		return null;
	}

	@Override
	public List<Location> getLocation(LatLng coordinates, ICallback<List<Location>> locationListCallback) {
		logger.info("getting locations with coordinates {}", coordinates);
		Flowable<List<Location>> res = Flowable.fromCallable(() -> {

			Map<String, Object> m = new HashMap<>();
			m.put(LOCATION_FIELD_NAME, new ParseGeoPoint(coordinates.getLat(), coordinates.getLng()));
			List<Map<String, Object>> locsMap = db.get(DATABASE_CLASS, m);
			logger.debug("db.get returned {}", locsMap.toString());
			List<Location> locs = new ArrayList<>();
			Flowable.fromIterable(locsMap).flatMap(m1 -> Flowable.just(m1).subscribeOn(Schedulers.io()).map(m2 -> {
				Location l = fromMap(m2);
				logger.debug("AbstractReviewManager is null : {}", AbstractReviewManager.instance() == null);
				logger.debug("getReviews returned : {} ",
						AbstractReviewManager.instance().getReviews(m2.get(ID_FIELD_NAME).toString(), null).toString());
				l.addReviews(AbstractReviewManager.instance().getReviews(m2.get(ID_FIELD_NAME).toString(), null));
				return l;
			})).blockingSubscribe(l -> locs.add(l));
			return locs;
		}).subscribeOn(Schedulers.io()).observeOn(Schedulers.single());

		if (locationListCallback == null)
			return res.blockingFirst();
		res.subscribe(locationListCallback::onFinish, Throwable::printStackTrace);
		return new ArrayList<>();
	}

	@Override
	public List<Location> getLocationsAround(LatLng l, double distance, ICallback<List<Location>> callback) {
		Flowable<List<Location>> res = Flowable.fromCallable(() -> {
			List<Map<String, Object>> mapList = db.get(DATABASE_CLASS, LOCATION_FIELD_NAME, l.getLat(), l.getLng(),
					distance);
			return mapList.stream().map(m -> fromMap(m)).collect(Collectors.toList());
		}).subscribeOn(Schedulers.io()).observeOn(Schedulers.single());
		if (callback == null)
			return res.blockingFirst();
		res.subscribe(callback::onFinish, Throwable::printStackTrace);
		return null;
	}

	@Override
	public Location getLocation(LatLng coordinates, LocationTypes locType, LocationSubTypes locSubType,
			ICallback<Location> callback) {
		Flowable<Location> res = Flowable.fromCallable(() -> {
			String id = getId(coordinates, locType, locSubType, null);
			if (id == null)
				return null;
			return fromMap(db.get(DATABASE_CLASS, id));
		}).subscribeOn(Schedulers.io()).observeOn(Schedulers.single());
		if (callback == null)
			return res.blockingFirst();
		res.subscribe(callback::onFinish, Throwable::printStackTrace);
		return null;
	}

	@Override
	public Boolean updateLocation(Location loc, ICallback<Boolean> callback) {
		logger.info("updateLocation for loc {},{},{}", loc.getCoordinates(), loc.getLocationType(),
				loc.getLocationSubType());
		Flowable<Boolean> res = Flowable.fromCallable(() -> {
			String id = getId(loc.getCoordinates(), loc.getLocationType(), loc.getLocationSubType(), null);
			if (id == null) {
				logger.error("Location not found {},{},{}", loc.getCoordinates(), loc.getLocationType(),
						loc.getLocationSubType());
				return false;
			}
			return db.update(DATABASE_CLASS, id, toMap(loc));
		}).subscribeOn(Schedulers.io()).observeOn(Schedulers.single());
		if (callback == null)
			return res.blockingFirst();
		res.subscribe(callback::onFinish, Throwable::printStackTrace);
		return null;
	}

	@Override
	public List<LatLng> getNonAccessibleLocationsInRadius(LatLng source, LatLng destination,
			Integer accessibilityThreshold, ICallback<List<LatLng>> callback) {
		Flowable<List<LatLng>> res = Flowable.fromCallable(() -> {
			List<Location> locList = getLocationsAround(getCenter(source, destination),
														distance(source, destination),
														null);
			
			return locList.stream()
							.map(l -> new BestReviews(l))
							.filter(br -> br.getTotalRatingByAvg() >= accessibilityThreshold)
							.map(br -> br.getLocation().getCoordinates())
							.distinct()
							.collect(Collectors.toList());
		}).subscribeOn(Schedulers.io()).observeOn(Schedulers.single());
		if (callback == null)
			return res.blockingFirst();
		res.subscribe(callback::onFinish, Throwable::printStackTrace);
		return null;
	}

	private LatLng getCenter(LatLng p1, LatLng p2) {
		double dLon = Math.toRadians(p2.getLng() - p1.getLng());

		double lat1 = Math.toRadians(p1.getLat());
		double lat2 = Math.toRadians(p2.getLat());
		double lon1 = Math.toRadians(p1.getLng());

		double Bx = Math.cos(lat2) * Math.cos(dLon);
		double By = Math.cos(lat2) * Math.sin(dLon);
		double lat3 = Math.atan2(Math.sin(lat1) + Math.sin(lat2),
				Math.sqrt((Math.cos(lat1) + Bx) * (Math.cos(lat1) + Bx) + By * By));
		double lon3 = lon1 + Math.atan2(By, Math.cos(lat1) + Bx);

		return new LatLng(Math.toDegrees(lat3), Math.toDegrees(lon3));

	}

	private double distance(LatLng p1, LatLng p2) {
		double lat1 = p1.getLat(); 
		double lat2 = p2.getLat();
		double lng1 = p1.getLng();
		double lng2 = p2.getLng();
		double earthRadius = 6371; // in km
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2) * Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return earthRadius * c;
	}

}
