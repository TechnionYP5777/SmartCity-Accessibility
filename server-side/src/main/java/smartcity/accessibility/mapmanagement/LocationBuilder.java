package smartcity.accessibility.mapmanagement;

import java.util.Collection;

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.mapmanagement.Location.LocationSubTypes;
import smartcity.accessibility.mapmanagement.Location.LocationTypes;
import smartcity.accessibility.socialnetwork.Review;

/**
 * @author KaplanAlexander
 *
 */
public class LocationBuilder {
	
	private Location l;

	public LocationBuilder(){
		l = new Location();
	}
	
	public Location build(){
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
	
	public LocationBuilder setSubType(LocationSubTypes lt){
		l.setLocationSubType(lt);
		return this;
	}
	
	
	
}
