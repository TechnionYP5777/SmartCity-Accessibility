package smartcity.accessibility.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import smartcity.accessibility.database.AbstractLocationManager;
import smartcity.accessibility.exceptions.EmptySearchQuery;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.socialnetwork.BestReviews;

/**
 * @author Kolikant
 *
 */
public class SearchQueryResult {

	private List<Location> locations;

	SearchQueryResult(List<Location> c) {
		this.locations = c;
	}

	public List<Location> getLocations() {
		return locations;
	}

	public boolean gotResults() {
		return !locations.isEmpty();
	}

	public Location get(int ¢) {
		return locations.size() <= ¢ ? null : locations.get(¢);
	}

	/***
	 * Iterate over the locations of the search query. If they exist in the DB
	 * convert them to the real locations if they do no exist leave them as they
	 * are (dummies).
	 * 
	 * @throws EmptySearchQuery
	 *             - If the query is empty.
	 */
	public void convertDummiesToReal() throws EmptySearchQuery {
		if (locations.isEmpty())
			throw new EmptySearchQuery();

		ArrayList<Location> arr = new ArrayList<>();
		for (Location ¢ : locations){
			Optional<Location> loc = AbstractLocationManager.instance().getLocation(¢.getCoordinates(), ¢.getLocationType(), ¢.getLocationSubType(), null);
			if (loc.isPresent())
				arr.add(loc.get());
		}
		
		locations.clear();
		locations.addAll(arr);
	}
	

	/***
	 * Iterate over the locations of the search query, remove all those that do
	 * not match the accessibility level. If the location is a dummy (i.e
	 * doesn't exist in the DB) treat them as if their accessibility level is
	 * maximal.
	 * 
	 * @param tresh
	 *            - minimal accessibility level.
	 * @throws EmptySearchQuery
	 *             - If the query is empty or no location meets the criteria
	 */
	public void filterLocations(int tresh) throws EmptySearchQuery {
		List<Location> clone = new ArrayList<>(locations);
		if (locations.isEmpty())
			throw new EmptySearchQuery();

		long rating;
		for (Location ¢ : clone) {
			rating = Integer.parseInt(Long.toUnsignedString(¢.getRating(BestReviews.DEFAULT).getScore()));
			if (rating < tresh)
				locations.remove(¢);
		}

		if (locations.isEmpty())
			throw new EmptySearchQuery();
	}
}
