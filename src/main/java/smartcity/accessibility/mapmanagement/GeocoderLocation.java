package smartcity.accessibility.mapmanagement;



import com.teamdev.jxmaps.GeocoderResult;

public class GeocoderLocation extends Location{
	
	private GeocoderResult geocoderResult;
	
	public GeocoderLocation(GeocoderResult gr){
		super(gr.getGeometry().getLocation());
		this.geocoderResult = gr;
	}
	
}
