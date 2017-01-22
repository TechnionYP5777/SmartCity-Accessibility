package smartcity.accessibility.gui;

import java.util.HashMap;
import java.util.List;

import com.teamdev.jxmaps.Icon;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.MapMouseEvent;
import com.teamdev.jxmaps.Marker;
import com.teamdev.jxmaps.MouseEvent;

import smartcity.accessibility.database.LocationListCallback;
import smartcity.accessibility.database.LocationManager;
import smartcity.accessibility.gui.components.location.LocationFrame;
import smartcity.accessibility.mapmanagement.JxMapsFunctionality;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.mapmanagement.Location.LocationSubTypes;

public class ExtendedMarker extends Marker {

	private static HashMap<LocationSubTypes, Icon> iconMap = new HashMap<LocationSubTypes, Icon>();
	private Location location;
	private Location subLocation;

	public ExtendedMarker(Map map, Location loc) {
		super(map);
		setIcon(iconMap.get(
				iconMap.containsKey(loc.getLocationSubType()) ? loc.getLocationSubType() : LocationSubTypes.Default));

		addEventListener("click", new MapMouseEvent() {

			@Override
			public void onEvent(MouseEvent arg0) {
				JxMapsFunctionality.onClick(arg0.latLng());

			}

		});

		location = loc;
		this.subLocation = null;
	}

	public ExtendedMarker(Map map, Location loc, Location subLocation) {
		super(map);
		setIcon(iconMap.get(!iconMap.containsKey(subLocation.getLocationSubType()) ? LocationSubTypes.Default
				: subLocation.getLocationSubType()));

		addEventListener("click", new MapMouseEvent() {

			@Override
			public void onEvent(MouseEvent arg0) {
				LocationManager.getLocation(loc.getCoordinates(),new LocationListCallback() {
					
					@Override
					public void done(List<Location> ls) {
						new LocationFrame(ls.get(0));
						
					}
				});
				

			}

		});
		location = loc;
		this.subLocation = subLocation;
	}

	public Location getLocation() {
		return location;
	}

	public Location getSubLocation() {
		return subLocation;
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
