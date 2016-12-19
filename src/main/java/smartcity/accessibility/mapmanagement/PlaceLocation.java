package smartcity.accessibility.mapmanagement;

import com.teamdev.jxmaps.PlacePhoto;
import com.teamdev.jxmaps.PlaceResult;

public class PlaceLocation extends Location{
	
	private PlaceResult placeResult;
	
	public PlaceLocation(PlaceResult pr){
		super(pr.getGeometry().getLocation());
		this.placeResult = pr;
	}
	
	
	public PlacePhoto[] getPhotos(){
		return placeResult.getPhotos();
	}
}