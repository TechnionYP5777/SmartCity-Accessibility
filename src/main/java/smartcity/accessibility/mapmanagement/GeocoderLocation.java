package smartcity.accessibility.mapmanagement;



import com.teamdev.jxmaps.GeocoderResult;
import com.teamdev.jxmaps.LatLng;

public class GeocoderLocation extends Location{
	
	private GeocoderResult geocoderResult;
	
	public GeocoderLocation(GeocoderResult gr){
		this.geocoderResult = gr;
	}
	
	@Override
	public LatLng getCoordinates(){
		return geocoderResult.getGeometry().getLocation();
	}
	
	@Override
	public String getAddress(){
		return geocoderResult.getFormattedAddress();
	}
}
