package smartcity.accessibility.navigation;

/**
 * 
 * @author yael This class represent the minimal segment of the map that can be
 *         declared as not accessible. This class might be changed to "Street"
 *         or something like that,
 *
 */
public class MapSegment {
	private Long linkId;
	private String street;

	public Long getLinkId() {
		return linkId;
	}

	public void setLinkId(Long linkId) {
		this.linkId = linkId;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
}
