package smartcity.accessibility.gui;

import java.io.File;
import java.util.HashMap;

import com.teamdev.jxmaps.Icon;
import com.teamdev.jxmaps.LatLng;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.MapMouseEvent;
import com.teamdev.jxmaps.Marker;
import com.teamdev.jxmaps.MouseEvent;

import smartcity.accessibility.gui.components.location.LocationFrame;
import smartcity.accessibility.mapmanagement.Coordinates;
import smartcity.accessibility.mapmanagement.Location;

public class ExtendedMarker extends Marker {
	
	public enum MarkerType{
		Restaurant,Hotel,Bar,Default;
	}

	private static HashMap<MarkerType,Icon> iconMap = new HashMap<MarkerType,Icon>();
	private Location location;
	
	
	
	public ExtendedMarker(Map map, MarkerType type) {
		super(map);
		setIcon(iconMap.get(type));
		addEventListener("click", new MapMouseEvent() {

			@Override
			public void onEvent(MouseEvent arg0) {
				new LocationFrame(location);
				
			}
			
		});
	}
	
	@Override
	public void setPosition(LatLng l){
		super.setPosition(l);
		location = new Coordinates(l);
	}
	
	static {
		Icon icon = new Icon();
		icon.loadFromFile("res/map-marker.png");
		iconMap.put(MarkerType.Default,icon);
		
		icon = new Icon();
		icon.loadFromFile("res/restaurants_for_map.png");
		iconMap.put(MarkerType.Restaurant,icon);
		
		icon = new Icon();
		icon.loadFromFile("res/hotels_for_map.png");
		iconMap.put(MarkerType.Hotel,icon);
		
		icon = new Icon();
		icon.loadFromFile("res/bars_and_pubs_for_map.png");
		iconMap.put(MarkerType.Bar,icon);
		
	}

}
