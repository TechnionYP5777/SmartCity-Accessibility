package smartcity.accessibility.navigation.mapquestcommunication;

/**
 * The class holds a collection of shape points in the route. shape points are
 * points in the route that represent turning point in the route
 * 
 * @author yael
 */
public class Shape {
	private Double[] shapePoints;

	public Shape() {

	}

	public Double[] getShapePoints() {
		return shapePoints;
	}

	public void setShapePoints(Double[] shapePoints) {
		this.shapePoints = shapePoints;
	}

}
