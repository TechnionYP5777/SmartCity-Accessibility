package smartcity.accessibility.database;

import java.util.List;
import java.util.Optional;

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.database.callbacks.ICallback;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.mapmanagement.Location.LocationSubTypes;
import smartcity.accessibility.mapmanagement.Location.LocationTypes;

/**
 * All functions can either run in the background and return with a callback method
 * Or block the operation until complete (not advised) and return the value
 * Blocking is done when callback method given is null
 * @author KaplanAlexander
 *
 */
public abstract class AbstractLocationManager {
	protected static AbstractLocationManager instance;
	
	public static void initialize(AbstractLocationManager m) {
		instance = m;
	}
	
	public static AbstractLocationManager instance() {
		return instance;
	}
	
	public abstract Optional<String> getId(LatLng coordinates, LocationTypes locType, LocationSubTypes locSubType, ICallback<Optional<String>> callback);
	
	public abstract String uploadLocation(Location l, ICallback<String> callback);
	
	public abstract List<Location> getLocation(LatLng coordinates, ICallback<List<Location>> locationListCallback);
	
	/**
	 * 
	 * @param l
	 * @param distance in KM
	 * @param locationListCallback
	 */
	public abstract List<Location> getLocationsAround(LatLng l, double distance, ICallback<List<Location>> locationListCallback);
	
	public abstract Optional<Location> getLocation(LatLng coordinates, LocationTypes locType, LocationSubTypes locSubType, ICallback<Optional<Location>> callback);
	
	public abstract Boolean updateLocation(Location loc, ICallback<Boolean> callback);
	
	public abstract List<LatLng> getNonAccessibleLocationsInRadius(LatLng source, LatLng destination,
			Integer accessibilityThreshold, ICallback<List<LatLng>> locationListCallback);
	
	public abstract List<Location> getTopRated(LatLng l, double radius, int n, ICallback<List<Location>> callback);
	
}
