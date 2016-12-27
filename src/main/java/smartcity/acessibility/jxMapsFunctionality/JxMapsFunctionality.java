package smartcity.acessibility.jxMapsFunctionality;

import com.teamdev.jxmaps.InfoWindow;
import com.teamdev.jxmaps.LatLng;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.Marker;

public abstract class JxMapsFunctionality {
	public static void putMarker(Map m, LatLng l, String name) {
		Marker m1 = new Marker(m);
		m1.setPosition(l);
		m.setCenter(l);
		final InfoWindow window = new InfoWindow(m);
		window.setContent(name);
		window.open(m, m1);
	}
}
