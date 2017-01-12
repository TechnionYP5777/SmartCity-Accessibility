package smartcity.accessibility.mapmanagement;

import com.teamdev.jxmaps.LatLng;

/**
 * 
 * @author Koral Chapnik
 * This class encapsulates JxMaps LatLng
 *
 */
@Deprecated
public class Coordinates extends Location {
	private static final long serialVersionUID = -5647589570659349011L;

	public Coordinates(LatLng c) {
		super(c);
	}
}
