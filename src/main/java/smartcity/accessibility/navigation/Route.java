package smartcity.accessibility.navigation;

import java.util.List;

/**
 * 
 * @author yael This class represent a route between 2 locations in the map.
 */
public class Route {
	private List<Leg> legs;

	public Route() {

	}

	public List<Leg> getLegs() {
		return legs;
	}

	public void setLegs(List<Leg> ls) {
		this.legs = ls;
	}

}
