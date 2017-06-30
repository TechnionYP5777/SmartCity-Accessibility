package smartcity.accessibility.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.parse4j.ParseGeoPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.maps.model.LatLng;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import smartcity.accessibility.database.callbacks.ICallback;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.mapmanagement.Location.LocationSubTypes;
import smartcity.accessibility.mapmanagement.Location.LocationTypes;
import smartcity.accessibility.navigation.Navigation;
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
	public static final String SEGMENT_ID_FIELD_NAME = "segmentId";

	private static Logger logger = LoggerFactory.getLogger(LocationManager.class);

	@Inject
	public LocationManager(Database db) {
		this.db = db;
	}

	public static Map<String, Object> toMap(Location l) {
		Map<String, Object> map = new HashMap<>();
		map.put(LOCATION_FIELD_NAME, new ParseGeoPoint(l.getCoordinates().lat, l.getCoordinates().lng));
		map.put(TYPE_FIELD_NAME, l.getLocationType().toString());
		map.put(SUB_TYPE_FIELD_NAME, l.getLocationSubType().toString());
		map.put(NAME_FIELD_NAME, l.getName());
		map.put(SEGMENT_ID_FIELD_NAME, l.getSegmentId() == null ? "" : l.getSegmentId());
		return map;
	}

	public static Location fromMap(Map<String, Object> m) {
		LocationBuilder lb = new LocationBuilder();
		lb.setName(m.get(NAME_FIELD_NAME).toString());
		lb.setType(LocationTypes.valueOf(m.get(TYPE_FIELD_NAME).toString().toUpperCase()));
		lb.setSubType(LocationSubTypes.valueOf(m.get(SUB_TYPE_FIELD_NAME).toString().toUpperCase()));
		ParseGeoPoint pgp = (ParseGeoPoint) m.get(LOCATION_FIELD_NAME);
		lb.setCoordinates(pgp.getLatitude(), pgp.getLongitude());
		if (!"".equals(m.get(SEGMENT_ID_FIELD_NAME)))
			lb.setSegmentId(m.get(SEGMENT_ID_FIELD_NAME).toString());
		return lb.build();
	}

	@Override
	public Optional<String> getId(LatLng coordinates, LocationTypes locType, LocationSubTypes locSubType,
			ICallback<Optional<String>> callback) {
		logger.info("getting id of location {} {} {}", coordinates, locType, locSubType);
		Flowable<Optional<String>> res = Flowable.fromCallable(() -> {
			Map<String, Object> m = new HashMap<>();
			m.put(LOCATION_FIELD_NAME, new ParseGeoPoint(coordinates.lat, coordinates.lng));
			m.put(TYPE_FIELD_NAME, locType.toString());
			m.put(SUB_TYPE_FIELD_NAME, locSubType.toString());
			List<Map<String, Object>> locs = db.get(DATABASE_CLASS, m);
			if (locs.isEmpty()) {
				logger.info("no locations found for {}, {}, {}", coordinates, locType, locSubType);
				String s = null;
				return Optional.ofNullable(s);
			}
			if (locs.size() > 1)
				logger.error("Multiple locations found with same id (coord, loctype and loc subtype) for {} , {} , {}",
						coordinates, locType, locSubType);
			return Optional.of(locs.get(0).get(ID_FIELD_NAME).toString());
		}).subscribeOn(Schedulers.io()).observeOn(Schedulers.single());
		if (callback == null)
			return res.blockingFirst();
		res.subscribe(callback::onFinish, Throwable::printStackTrace);
		return Optional.empty();
	}

	@Override
	public String uploadLocation(Location l, ICallback<String> callback) {
		logger.info("upload location {}", l);
		Flowable<String> res = Flowable.fromCallable(() -> {
			Optional<String> oid = getId(l.getCoordinates(), l.getLocationType(), l.getLocationSubType(), null);
			if (oid.isPresent()) {
				logger.debug("location already in db");
				return oid.get();
			}
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
			m.put(LOCATION_FIELD_NAME, new ParseGeoPoint(coordinates.lat, coordinates.lng));
			List<Map<String, Object>> locsMap = db.get(DATABASE_CLASS, m);
			logger.debug("db.get returned {}", locsMap.toString());
			List<Location> locs = new ArrayList<>();
			Flowable.fromIterable(locsMap).flatMap(m1 -> Flowable.just(m1).subscribeOn(Schedulers.io()).map(m2 -> {
				Location l = fromMap(m2);
				return getLocation(l.getCoordinates(), l.getLocationType(), l.getLocationSubType(), null).get();
			})).blockingSubscribe(locs::add);
			return locs;
		}).subscribeOn(Schedulers.io()).observeOn(Schedulers.single());

		if (locationListCallback == null)
			return res.blockingFirst();
		res.subscribe(locationListCallback::onFinish, Throwable::printStackTrace);
		return new ArrayList<>();
	}

	@Override
	public Optional<Location> getLocation(LatLng coordinates, LocationTypes locType, LocationSubTypes locSubType,
			ICallback<Optional<Location>> callback) {
		Flowable<Optional<Location>> res = Flowable.fromCallable(() -> {
			Optional<String> id = getId(coordinates, locType, locSubType, null);
			if (!id.isPresent()) {
				if (locType == LocationTypes.STREET) {
					String segId = String
							.valueOf(Navigation.getMapSegmentOfLatLng(coordinates.lat, coordinates.lng).getLinkId());
					Location sLoc = new LocationBuilder().setCoordinates(coordinates).setSegmentId(segId)
							.setType(locType).setSubType(locSubType).build();
					id = Optional.ofNullable(uploadLocation(sLoc, null));
				}
				if (!id.isPresent()) {
					Location temp = null;
					return Optional.ofNullable(temp);
				}
			}
			Location l = fromMap(db.get(DATABASE_CLASS, id.get()));
			if (l.getSegmentId() != null) {
				logger.debug("location has segment id, getting others with same field name");
				Map<String, Object> m = new HashMap<>();
				m.put(SEGMENT_ID_FIELD_NAME, l.getSegmentId());
				l.addReviews(db.get(DATABASE_CLASS, m)
						.stream().map(locMap -> AbstractReviewManager.instance()
								.getReviews(locMap.get(ID_FIELD_NAME).toString(), null))
						.flatMap(List::stream).collect(Collectors.toList()));
			} else {
				logger.debug("location has no segment id, getting others with same field name");
				l.addReviews(AbstractReviewManager.instance().getReviews(id.get(), null));
			}
			return Optional.of(l);
		}).subscribeOn(Schedulers.io()).observeOn(Schedulers.single());
		if (callback == null)
			return res.blockingFirst();
		res.subscribe(callback::onFinish, Throwable::printStackTrace);
		Location temp = null;
		return Optional.ofNullable(temp);
	}

	@Override
	public List<Location> getLocationsAround(LatLng l, double distance, ICallback<List<Location>> callback) {
		Flowable<List<Location>> res = Flowable.fromCallable(() -> {
			List<Map<String, Object>> mapList = db.get(DATABASE_CLASS, LOCATION_FIELD_NAME, l.lat, l.lng, distance);
			return mapList.stream().map(m -> {
				Location ml = fromMap(m);
				return getLocation(ml.getCoordinates(), ml.getLocationType(), ml.getLocationSubType(), null).get();
			}).collect(Collectors.toList());
		}).subscribeOn(Schedulers.io()).observeOn(Schedulers.single());
		if (callback == null)
			return res.blockingFirst();
		res.subscribe(callback::onFinish, Throwable::printStackTrace);
		return new ArrayList<>();
	}

	@Override
	public Boolean updateLocation(Location loc, ICallback<Boolean> callback) {
		logger.info("updateLocation for loc {},{},{}", loc.getCoordinates(), loc.getLocationType(),
				loc.getLocationSubType());
		Flowable<Boolean> res = Flowable.fromCallable(() -> {
			Optional<String> id = getId(loc.getCoordinates(), loc.getLocationType(), loc.getLocationSubType(), null);
			if (!id.isPresent()) {
				logger.error("Location not found {},{},{}", loc.getCoordinates(), loc.getLocationType(),
						loc.getLocationSubType());
				return false;
			}
			return db.update(DATABASE_CLASS, id.get(), toMap(loc));
		}).subscribeOn(Schedulers.io()).observeOn(Schedulers.single());
		if (callback == null)
			return res.blockingFirst();
		res.subscribe(callback::onFinish, Throwable::printStackTrace);
		return false;
	}

	@Override
	public List<LatLng> getNonAccessibleLocationsInRadius(LatLng source, LatLng destination,
			Integer accessibilityThreshold, ICallback<List<LatLng>> callback) {
		Flowable<List<LatLng>> res = Flowable.fromCallable(() -> {
			List<Location> locList = getLocationsAround(getCenter(source, destination), distance(source, destination),
					null);
			return locList.stream()
					.filter(l -> l.getLocationType().equals(Location.LocationTypes.STREET))
					.map(BestReviews::new)
					.filter(br -> br.getTotalRatingByAvg() < accessibilityThreshold)
					.map(br -> br.getLocation().getCoordinates()).distinct().collect(Collectors.toList());
		}).subscribeOn(Schedulers.io()).observeOn(Schedulers.single());
		if (callback == null)
			return res.blockingFirst();
		res.subscribe(callback::onFinish, Throwable::printStackTrace);
		return new ArrayList<>();
	}

	private LatLng getCenter(LatLng p1, LatLng p2) {
		double dLon = Math.toRadians(p2.lng - p1.lng);

		double lat1 = Math.toRadians(p1.lat);
		double lat2 = Math.toRadians(p2.lat);
		double lon1 = Math.toRadians(p1.lng);

		double bx = Math.cos(lat2) * Math.cos(dLon);
		double by = Math.cos(lat2) * Math.sin(dLon);
		double lat3 = Math.atan2(Math.sin(lat1) + Math.sin(lat2),
				Math.sqrt((Math.cos(lat1) + bx) * (Math.cos(lat1) + bx) + by * by));
		double lon3 = lon1 + Math.atan2(by, Math.cos(lat1) + bx);

		return new LatLng(Math.toDegrees(lat3), Math.toDegrees(lon3));

	}

	private double distance(LatLng p1, LatLng p2) {
		double lat1 = p1.lat;
		double lat2 = p2.lat;
		double lng1 = p1.lng;
		double lng2 = p2.lng;
		double earthRadius = 6371; // in km
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2) * Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return earthRadius * c;
	}

	@Override
	public List<Location> getTopRated(LatLng l, double radius, int n, ICallback<List<Location>> callback) {
		Flowable<List<Location>> res = Flowable.fromCallable(() -> {
			List<Location> locsList = getLocationsAround(l, radius, null);
			return locsList.stream().map(BestReviews::new)
					.sorted((br1, br2) -> br1.getTotalRatingByAvg() - br2.getTotalRatingByAvg()).limit(n)
					.map(BestReviews::getLocation).collect(Collectors.toList());
		}).subscribeOn(Schedulers.io()).observeOn(Schedulers.single());
		if (callback == null)
			return res.blockingFirst();
		res.subscribe(callback::onFinish, Throwable::printStackTrace);
		return new ArrayList<>();
	}

}
