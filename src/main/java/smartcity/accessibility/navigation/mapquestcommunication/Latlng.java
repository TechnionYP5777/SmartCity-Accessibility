package smartcity.accessibility.navigation.mapquestcommunication;

/**
 * This class represent lat and lng of a location.
 * 
 * @author yael
 */
public class Latlng {
	private Double lat;
	private Double lng;

	public Latlng() {
	}

	public Latlng(Double lat, Double lng) {
		this.lat = lat;
		this.lng = lng;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

}
