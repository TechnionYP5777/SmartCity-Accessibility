package smartcity.accessibility.navigation.mapquestcommunication;

/**
 * 
 * @author yael This class represent a route between 2 locations in the map.
 */
public class Route {
	private Leg[] legs;
	private Shape shape;

	public Route() {

	}

	public Leg[] getLegs() {
		return legs;
	}

	public void setLegs(Leg[] ls) {
		this.legs = ls;
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape s) {
		this.shape = s;
	}

}
