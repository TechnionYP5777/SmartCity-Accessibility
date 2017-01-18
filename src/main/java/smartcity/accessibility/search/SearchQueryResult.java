package smartcity.accessibility.search;

import java.util.List;

import com.teamdev.jxmaps.GeocoderResult;
import com.teamdev.jxmaps.Map;

import smartcity.accessibility.database.LocationManager;
import smartcity.accessibility.exceptions.EmptySearchQuery;
import smartcity.accessibility.mapmanagement.Location;

/**
 * @author Kolikant
 *
 */
public class SearchQueryResult{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Map map;
	private List<Location> locations;

	SearchQueryResult(List<Location> c){
		this.locations = c;
	}
	
	public List<Location> getLocations(){
		return locations;
	}
	
	public boolean gotResults(){
		return !locations.isEmpty();
	}
	
	public Location get(int i){
		return locations.size() < i + 1 ? null : locations.get(i);
	}
	
	
	/***
	 * Iterate over the locations of the search query.
	 * If they exist in the DB convert them to the real locations
	 * if they do no exist leave them as they are (dummies).
	 * @throws EmptySearchQuery - If the query is empty.
	 */
	public void convertDummiesToReal() throws EmptySearchQuery{
		if(locations.isEmpty()) throw new EmptySearchQuery();
		
		for(Location loc : locations){
			loc = LocationManager.getLocation(loc.getCoordinates(),
					loc.getLocationType(), loc.getLocationSubType());
		}
	}
	
	/***
	 * Iterate over the locations of the search query, remove all
	 * those that do not match the accessibility level.
	 * If the location is a dummy (i.e doesn't exist in the DB) treat
	 * them as if their accessibility level is maximal.
	 * @param tresh - minimal accessibility level.
	 * @throws EmptySearchQuery - If the query is empty or no location
	 * meets the criteria
	 */
	public void filterLocations(int tresh) throws EmptySearchQuery{
		if(locations.isEmpty()) throw new EmptySearchQuery();
		
		long rating;
		for(Location loc : locations){
			rating = Integer.parseInt(
					Long.toUnsignedString(
					loc.getRating(loc.getReviews().size()).getScore()));
			if(rating < tresh) locations.remove(loc);
		}
		
		if(locations.isEmpty()) throw new EmptySearchQuery();
	}
}
	

