package smartcity.acessibility.jxMapsFunctionality;

import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.PlaceSearchRequest;
import com.teamdev.jxmaps.swing.MapView;

public class placeSearchAttempt extends MapView{
	 final Map map = getMap();
     // Creating places search request
     final PlaceSearchRequest request = new PlaceSearchRequest(map);
   
     /*
     // Setting start point for places search
     request.setLocation(map.getCenter());
     // Setting radius for places search
     request.setRadius(500.0);
     */
}
