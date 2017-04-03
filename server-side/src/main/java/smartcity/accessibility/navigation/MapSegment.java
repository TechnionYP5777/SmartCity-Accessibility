package smartcity.accessibility.navigation;

/**
 * This class represent the minimal segment of the map that can be declared as
 * not accessible. The street hold the name of the street that the mapSegment is
 * in. and the linkId is the identification of the segment.
 * 
 * @author yael
 *
 */
public class MapSegment {
	private Integer linkId;
	private String street;

	public Integer getLinkId() {
		return linkId;
	}

	public void setLinkId(Integer linkId) {
		this.linkId = linkId;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
}
