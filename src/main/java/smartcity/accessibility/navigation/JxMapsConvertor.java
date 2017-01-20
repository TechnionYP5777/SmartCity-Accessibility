package smartcity.accessibility.navigation;

import com.teamdev.jxmaps.LatLng;
import com.teamdev.jxmaps.Map;

import com.teamdev.jxmaps.Polyline;
import com.teamdev.jxmaps.PolylineOptions;
import com.teamdev.jxmaps.swing.MapView;

/**
 * This class convert the route from mapquest to JxMap
 * 
 * @author yael
 *
 */
public class JxMapsConvertor {
	private static Polyline prevRoute;
	private static Polyline prevStartLine; // line for the start of the route
	private static Polyline prevEndLine; // line for the end of the route
	private static final String colorOfRoute = "#FF0000";
	private static final String colorOfExternalLines = "#0066ff";
	private static final double thicknessOfLine = 2.0;

	public static void displayRoute(MapView v, LatLng[] shapeLatlng) {
		removePrevPolyline();
		prevRoute = addLine(v, shapeLatlng, colorOfRoute, thicknessOfLine);
	}

	public static void removePrevPolyline() {
		if (prevRoute != null)
			prevRoute.setVisible(false);
		if (prevStartLine != null)
			prevStartLine.setVisible(false);
		if (prevEndLine != null)
			prevEndLine.setVisible(false);
	}

	private static Polyline addLine(MapView v, LatLng[] latLngArr, String color, double thickness) {
		final Map map = v.getMap();
		Polyline $ = new Polyline(map);
		$.setPath(latLngArr);
		PolylineOptions options = new PolylineOptions();
		options.setGeodesic(true);
		options.setStrokeColor(color);
		options.setStrokeWeight(thickness);
		$.setOptions(options);
		return $;
	}

	public static void addStartLine(MapView v, LatLng src, LatLng dst) {
		prevStartLine = addLine(v, (new LatLng[] { src, dst }), colorOfExternalLines, thicknessOfLine);
	}

	public static void addEndLine(MapView v, LatLng src, LatLng dst) {
		prevEndLine = addLine(v, (new LatLng[] { src, dst }), colorOfExternalLines, thicknessOfLine);
	}
}
