package smartcity.accessibility.database;

import java.util.List;

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
	
	public abstract String getId(LatLng coordinates, ICallback<String> callback);
	
	public abstract String uploadLocation(Location l, ICallback<String> callback);
	
	public abstract List<Location> getLocation(LatLng coordinates, ICallback<List<Location>> locationListCallback);
	
	/**
	 * 
	 * @param l
	 * @param distance in KM
	 * @param locationListCallback
	 */
	public abstract List<Location> getLocationsAround(LatLng l, double distance, ICallback<List<Location>> locationListCallback);
	
	public abstract Location getLocation(LatLng coordinates, LocationTypes locType, LocationSubTypes locSubType, ICallback<Location> callback);
	
	public abstract Boolean updateLocation(Location loc, ICallback<Boolean> callback);
	
	public abstract List<LatLng> getNonAccessibleLocationsInRadius(Location source, Location destination,
			Integer accessibilityThreshold, ICallback<List<LatLng>> locationListCallback);
}
