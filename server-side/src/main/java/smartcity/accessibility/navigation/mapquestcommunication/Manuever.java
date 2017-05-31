package smartcity.accessibility.navigation.mapquestcommunication;

/**
 * This class include narratives of the route.
 * 
 * @author yael
 *
 */
public class Manuever {
	private String narrative;
	private String mapUrl;
	
	public String getMapUrl() {
		return mapUrl;
	}

	public void setMapUrl(String mapUrl) {
		this.mapUrl = mapUrl;
	}

	public String getNarrative() {
		return narrative;
	}

	public void setNarrative(String narrative) {
		this.narrative = narrative;
	}
}
