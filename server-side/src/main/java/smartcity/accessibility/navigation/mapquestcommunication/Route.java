package smartcity.accessibility.navigation.mapquestcommunication;

/**
 * This class represent a route between 2 locations in the map.
 * 
 * @author yael
 */
public class Route {
	private Shape shape;
	private Integer realTime;

	public Route() {
		// empty constructor needed for JSON
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape s) {
		this.shape = s;
	}

	public Integer getRealTime() {
		return realTime;
	}

	public void setRealTime(Integer realTime) {
		this.realTime = realTime;
	}

}
