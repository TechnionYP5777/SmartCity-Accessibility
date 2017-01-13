package smartcity.accessibility.navigation;

import com.teamdev.jxmaps.ControlPosition;
import com.teamdev.jxmaps.LatLng;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.MapOptions;
import com.teamdev.jxmaps.MapTypeControlOptions;
import com.teamdev.jxmaps.Polyline;
import com.teamdev.jxmaps.PolylineOptions;
import com.teamdev.jxmaps.swing.MapView;

import javax.swing.*;
import java.awt.*;

/**
 * This class convert the route from mapquest to JxMap
 * 
 * @author yael
 *
 */
public class JxMapsConvertor{
	private static Polyline prevRoute;
	private static Polyline prevStartLine; //line for the start and the end of the route
	private static Polyline prevEndLine;
	public static void displayRoute(MapView mapView, LatLng[] shapeLatlng) {
		removePrevPolyline();
		final Map map = mapView.getMap();
		Polyline polyline = new Polyline(map);
		polyline.setPath(shapeLatlng);
		PolylineOptions options = new PolylineOptions();
		options.setGeodesic(true);
		options.setStrokeColor("#FF0000");
		options.setStrokeWeight(2.0);
		polyline.setOptions(options);
		prevRoute = polyline;
	}
	
	public static void removePrevPolyline(){
		if(prevRoute != null)
			prevRoute.setVisible(false);
		if(prevStartLine != null)
			prevStartLine.setVisible(false);
		if(prevEndLine != null)
			prevEndLine.setVisible(false);
	}
	
	private static Polyline addLine(MapView mapView,LatLng src,LatLng dst){
		final Map map = mapView.getMap();
		Polyline $ = new Polyline(map);
		LatLng[] latLngArr = {src , dst};
		$.setPath(latLngArr);
		PolylineOptions options = new PolylineOptions();
		options.setGeodesic(true);
		options.setStrokeColor("#0066ff");
		options.setStrokeWeight(2.0);
		$.setOptions(options);
		return $;
	}
	
	public static void addStartLine(MapView mapView,LatLng src,LatLng dst){
		prevStartLine = addLine(mapView,src,dst);
	}

	public static void addEndLine(MapView mapView,LatLng src,LatLng dst){
		prevEndLine = addLine(mapView,src,dst);
	}
}
