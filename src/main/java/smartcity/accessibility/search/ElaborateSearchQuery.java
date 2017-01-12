package smartcity.accessibility.search;

import java.util.ArrayList;
import java.util.List;

import com.teamdev.jxmaps.Geocoder;
import com.teamdev.jxmaps.GeocoderCallback;
import com.teamdev.jxmaps.GeocoderRequest;
import com.teamdev.jxmaps.GeocoderResult;
import com.teamdev.jxmaps.GeocoderStatus;
import com.teamdev.jxmaps.MapViewOptions;
import com.teamdev.jxmaps.swing.MapView;

import smartcity.accessibility.mapmanagement.Facility;
import smartcity.accessibility.mapmanagement.JxMapsFunctionality;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.search.SearchQuery.SearchStage;

public class ElaborateSearchQuery extends SearchQuery{

	protected ElaborateSearchQuery(String parsedQuery) {
		super(parsedQuery);
	}

	@Override
	protected SearchQueryResult Search(GeocoderRequest r, MapView v) {
		return null;
	}
	protected SearchQueryResult Search(Location initLocation, double radius, List<String> kindsOfLocations) {
		SetSearchStatus(SearchStage.Running);
		kindsOfLocations.add(queryString);
		MapViewOptions options = new MapViewOptions();
		options.importPlaces();
		NearbyPlacesAttempt n = new NearbyPlacesAttempt(options);
		ArrayList<Location> places = n.findNearbyPlaces(initLocation, radius, kindsOfLocations);
		if(!places.isEmpty()){
			SetSearchStatus(SearchStage.Failed);
			wakeTheWaiters();
		}else{
			SetSearchStatus(SearchStage.Done);
			wakeTheWaiters();
		}

		//return new SearchQueryResult(places);
		return null;
	}
}
