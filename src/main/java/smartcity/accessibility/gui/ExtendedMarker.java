package smartcity.accessibility.gui;

import java.util.HashMap;

import com.teamdev.jxmaps.Icon;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.MapMouseEvent;
import com.teamdev.jxmaps.Marker;
import com.teamdev.jxmaps.MouseEvent;

import smartcity.accessibility.gui.components.location.LocationFrame;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.mapmanagement.Location.LocationType;

public class ExtendedMarker extends Marker {

	private static HashMap<LocationType, Icon> iconMap = new HashMap<LocationType, Icon>();
	private Location location;

	public ExtendedMarker(Map map, Location loc) {
		super(map);
		setIcon(iconMap.get(LocationType.Default));
		addEventListener("click", new MapMouseEvent() {

			@Override
			public void onEvent(MouseEvent arg0) {
				new LocationFrame(location);

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
		iconMap.put(LocationType.Default, icon);

		icon = new Icon();
		icon.loadFromFile("res/restaurants_for_map.png");
		iconMap.put(LocationType.Restaurant, icon);

		icon = new Icon();
		icon.loadFromFile("res/hotels_for_map.png");
		iconMap.put(LocationType.Hotel, icon);

		icon = new Icon();
		icon.loadFromFile("res/bars_and_pubs_for_map.png");
		iconMap.put(LocationType.Bar, icon);

	}

}
