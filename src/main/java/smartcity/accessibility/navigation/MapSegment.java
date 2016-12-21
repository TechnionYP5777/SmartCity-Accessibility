package smartcity.accessibility.navigation;

/**
 * 
 * @author yael This class represent the minimal segment of the map that can be
 *         declared as not accessible. This class might be changed to "Street"
 *         or something like that,
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
