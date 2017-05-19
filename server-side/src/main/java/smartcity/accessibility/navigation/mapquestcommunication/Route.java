package smartcity.accessibility.navigation.mapquestcommunication;

/**
 * This class represent a route between 2 locations in the map.
 * 
 * @author yael
 */
public class Route {
	private Shape shape;
	private Integer time;

	public Route() {
		// empty constructor needed for JSON
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape s) {
		this.shape = s;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

}
