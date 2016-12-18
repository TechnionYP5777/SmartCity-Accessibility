package smartcity.accessibility.navigation.mapquestcommunication;


/**
 * 
 * @author yael This class represent a step in the route. This class is needed
 *         for communication with the servers.
 */
public class Maneuver {
	private Long[] linkIds;
	private String[] streets;
	private Direction direction;
	private Integer index;

	public Maneuver() {

	}
	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public Long[] getLinkIds() {
		return linkIds;
	}

	public void setLinkIds(Long[] linkIds) {
		this.linkIds = linkIds;
	}

	
	public String[] getStreets() {
		return streets;
	}

	public void setStreets(String[] streets) {
		this.streets = streets;
	}

	
	
	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction d) {
		this.direction = d;
	}
}
