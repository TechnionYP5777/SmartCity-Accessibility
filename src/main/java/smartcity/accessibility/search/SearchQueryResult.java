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
	
	public void convertDummiesToReal() throws EmptySearchQuery{
		if(locations.isEmpty()) throw new EmptySearchQuery();
		
		for(Location loc : locations){
			loc = LocationManager.getLocation(loc.getCoordinates(),
					loc.getLocationType(), loc.getLocationSubType());
		}
	}
}
	

