package smartcity.accessibility.mapmanagement;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.maps.model.LatLng;

import smartcity.accessibility.mapmanagement.Location.LocationSubTypes;
import smartcity.accessibility.mapmanagement.Location.LocationTypes;
import smartcity.accessibility.socialnetwork.Review;

/**
 * @author KaplanAlexander
 *
 */
public class LocationBuilder {
	
	private static Logger logger = LoggerFactory.getLogger(LocationBuilder.class);
	private Location l;

	public LocationBuilder(){
		l = new Location();
		l.setLocationType(LocationTypes.Coordinate);
		l.setLocationSubType(LocationSubTypes.DEFAULT);
	}
	
	public Location build(){
		if (l.getLocationType().equals(LocationTypes.Street) && l.getSegmentId() == null) {
			logger.error("cannot build locaiton of type street with no segmentId");
			return null;
		}
		return l;
	}
	
	public LocationBuilder addReviews(Collection<Review> reviews){
		l.addReviews(reviews);
		return this;
	}
	
	public LocationBuilder setCoordinates(double lat, double lng){
		l.setCoordinates(new LatLng(lat, lng));
		return this;
	}
	
	public LocationBuilder setCoordinates(LatLng lt){
		l.setCoordinates(lt);
		return this;
	}
	
	public LocationBuilder setName(String name){
		l.setName(name);
		return this;
	}
	
	public LocationBuilder setType(LocationTypes lt){
		l.setLocationType(lt);
		return this;
	}
	
	public LocationBuilder setSegmentId(String id){
		l.setSegmentId(id);
		return this;
	}
	
	public LocationBuilder setSubType(LocationSubTypes lt){
		l.setLocationSubType(lt);
		return this;
	}
	
	
	
}
