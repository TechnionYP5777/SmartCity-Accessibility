package smartcity.accessibility.mapmanagement;

import com.teamdev.jxmaps.LatLng;
import com.teamdev.jxmaps.PlacePhoto;
import com.teamdev.jxmaps.PlaceResult;

public class PlaceLocation extends Location{
	
	private PlaceResult placeResult;
	
	public PlaceLocation(PlaceResult pr){
		this.placeResult = pr;
	}
	
	@Override
	public LatLng getCoordinates(){
		return placeResult.getGeometry().getLocation();
	}
	
	@Override
	public String getAddress(){
		return placeResult.getFormattedAddress();
	}
	
	public PlacePhoto[] getPhotos(){
		return placeResult.getPhotos();
	}
}