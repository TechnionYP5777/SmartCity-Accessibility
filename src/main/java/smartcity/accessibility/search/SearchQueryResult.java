package smartcity.accessibility.search;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.teamdev.jxmaps.Geocoder;
import com.teamdev.jxmaps.GeocoderCallback;
import com.teamdev.jxmaps.GeocoderRequest;
import com.teamdev.jxmaps.GeocoderResult;
import com.teamdev.jxmaps.GeocoderStatus;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.MapViewOptions;
import com.teamdev.jxmaps.Marker;
import com.teamdev.jxmaps.swing.MapView;

import smartcity.acessibility.jxMapsExamples.HelloWorld;

/**
 * @author Kolikant
 *
 */
public class SearchQueryResult extends MapView{

	Map map;
	private List<GeocoderResult> coordinations;

	SearchQueryResult(List<GeocoderResult> c, Map m){
		this.coordinations = c;
		this.map = m;
	}
	
	public List<GeocoderResult> getCoordinations(){
		return coordinations;
	}
	
	public void showResults(){
		for(GeocoderResult gr: coordinations){
                    map.setCenter(gr.getGeometry().getLocation());
                    Marker marker = new Marker(map);
                    marker.setPosition(gr.getGeometry().getLocation());
		}
    }
}
	

