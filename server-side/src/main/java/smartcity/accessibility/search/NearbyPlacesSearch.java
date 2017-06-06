package smartcity.accessibility.search;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.maps.GeoApiContext;
import com.google.maps.NearbySearchRequest;
import com.google.maps.PendingResult.Callback;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlaceType;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;

import smartcity.accessibility.database.callbacks.ICallback;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.mapmanagement.LocationBuilder;

/**
 * @author Koral Chapnik
 */
public class NearbyPlacesSearch {

	private static Logger logger = LoggerFactory.getLogger(NearbyPlacesSearch.class);

	private NearbyPlacesSearch(){
		
	}
	
	/**
	 * This method returns the nearby places according to the following
	 * parameters:
	 * 
	 * @param v
	 *            - the mapView
	 * @param initLocation
	 *            - the location which will be the center of our search
	 * @param radius
	 *            - the radius in meters we want to search by
	 * @param kindsOfLocations
	 *            - which kinds of nearby places we want to search ? for example
	 *            cafe, restaurant, pub etc
	 * @param c
	 *            - a callback which gets a list of nearby places and does a
	 *            desired operation with it in the "done" method
	 */
	public static void findNearbyPlaces(Location initLocation, int radius, List<String> kindsOfLocations,
			ICallback<List<Location>> c) {
		GeoApiContext context = SearchQuery.getContext();
		List<PlaceType> l = kindsOfLocations.stream().map(PlaceType::valueOf).collect(Collectors.toList());
		NearbySearchRequest nsr = new NearbySearchRequest(context).radius( radius)
				.location(initLocation.getCoordinates()).type(l.toArray(new PlaceType[l.size()]));
		nsr.setCallback(new Callback<PlacesSearchResponse>() {
			@Override
			public void onResult(PlacesSearchResponse arg0) {
				ArrayList<Location> ret = new ArrayList<>();
				PlacesSearchResult[] rs = arg0.results;
				logger.info("got {} results", rs.length);
				for (int i = 0; i < rs.length; ++i) {
					PlacesSearchResult result = rs[i];
					LatLng l = result.geometry.location;
					Location f = new LocationBuilder().setCoordinates(l).setName(result.name).build();
					ret.add(f);
				}
				logger.debug("called to callback done");
				c.onFinish(ret);
			}

			@Override
			public void onFailure(Throwable arg0) {
				logger.error("Nearby Search failed with error {} ", arg0);
			}
		});
	}

}