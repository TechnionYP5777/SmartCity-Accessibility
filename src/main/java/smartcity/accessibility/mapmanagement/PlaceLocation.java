package smartcity.accessibility.mapmanagement;

import com.teamdev.jxmaps.PlacePhoto;
import com.teamdev.jxmaps.PlaceResult;

public class PlaceLocation extends Location{
	
	private PlaceResult placeResult;
	
	public PlaceLocation(PlaceResult pr){
		super(pr.getGeometry().getLocation());
		this.placeResult = pr;
	}
	
	@Override
	public String getAddress(){
		return placeResult.getFormattedAddress();
	}
	
	public PlacePhoto[] getPhotos(){
		return placeResult.getPhotos();
	}
}