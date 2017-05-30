package smartcity.accessibility.navigation.mapquestcommunication;

/**
 * This class include narratives of the route.
 * 
 * @author yael
 *
 */
public class Manuever {
	private String narrative;
	private String mapURL;
	
	public String getMapURL() {
		return mapURL;
	}

	public void setMapURL(String mapURL) {
		this.mapURL = mapURL;
	}

	public String getNarrative() {
		return narrative;
	}

	public void setNarrative(String narrative) {
		this.narrative = narrative;
	}
}
