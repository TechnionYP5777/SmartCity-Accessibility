package smartcity.accessibility.navigation.mapquestcommunication;

/**
 * This class represent a route between 2 locations in the map.
 * 
 * @author yael
 */
public class Route {
	private Shape shape;
	private Integer time;
	private Leg[] legs;
	private Double distance;

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

	public Leg[] getLegs() {
		return legs;
	}

	public void setLegs(Leg[] legs) {
		this.legs = legs;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

}
