package smartcity.accessibility.gui;

import java.util.HashMap;

import com.teamdev.jxmaps.Icon;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.MapMouseEvent;
import com.teamdev.jxmaps.Marker;
import com.teamdev.jxmaps.MouseEvent;

import smartcity.accessibility.database.LocationManager;
import smartcity.accessibility.gui.components.location.LocationFrame;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.mapmanagement.Location.LocationSubTypes;

public class ExtendedMarker extends Marker {

	private static HashMap<LocationSubTypes, Icon> iconMap = new HashMap<LocationSubTypes, Icon>();
	private Location location;

	public ExtendedMarker(Map map, Location loc) {
		super(map);
		setIcon(iconMap.get(iconMap.containsKey(loc.getLocationSubType()) ? loc.getLocationSubType() : LocationSubTypes.Default));
		
		addEventListener("click", new MapMouseEvent() {

			@Override
			public void onEvent(MouseEvent arg0) {
				new LocationFrame(LocationManager.getLocation(location.getCoordinates()));

			}

		});
		/*
		 * not under extended markers jurisdiction
		 * 
		 * super.setPosition(loc.getCoordinates());
		 */
		location = loc;
	}

	static {
		Icon icon = new Icon();
		icon.loadFromFile("res/map-marker.png");
		iconMap.put(LocationSubTypes.Default, icon);

		icon = new Icon();
		icon.loadFromFile("res/restaurants_for_map.png");
		iconMap.put(LocationSubTypes.Restaurant, icon);

		icon = new Icon();
		icon.loadFromFile("res/hotels_for_map.png");
		iconMap.put(LocationSubTypes.Hotel, icon);

		icon = new Icon();
		icon.loadFromFile("res/bars_and_pubs_for_map.png");
		iconMap.put(LocationSubTypes.Bar, icon);

	}

}
