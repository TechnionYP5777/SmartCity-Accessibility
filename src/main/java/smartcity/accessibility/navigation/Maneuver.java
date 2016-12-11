package smartcity.accessibility.navigation;

import java.util.List;

/**
 * 
 * @author yael This class represent a step in the route. This class is needed
 *         for communication with the servers.
 */
public class Maneuver {
	List<Integer> linkIds;
	List<String> street_names;
	Direction direction;
}
