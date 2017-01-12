package smartcity.accessibility.search;

import java.util.ArrayList;
import java.util.List;

import com.teamdev.jxmaps.Geocoder;
import com.teamdev.jxmaps.GeocoderCallback;
import com.teamdev.jxmaps.GeocoderRequest;
import com.teamdev.jxmaps.GeocoderResult;
import com.teamdev.jxmaps.GeocoderStatus;
import com.teamdev.jxmaps.swing.MapView;

import smartcity.accessibility.search.SearchQuery.SearchStage;

public class ElaborateSearchQuery extends SearchQuery{

	protected ElaborateSearchQuery(String parsedQuery) {
		super(parsedQuery);
	}

	@Override
	protected SearchQueryResult Search(GeocoderRequest r, MapView v) {
		SetSearchStatus(SearchStage.Running);
		List<GeocoderResult> results = new ArrayList<GeocoderResult>();
		Geocoder g = v.getServices().getGeocoder();
		g.geocode(r, new GeocoderCallback(v.getMap()) {
			@Override
			public void onComplete(GeocoderResult[] rs, GeocoderStatus s) {
				System.out.println("arrived to search on complete");
				if (s != GeocoderStatus.OK){
					SetSearchStatus(SearchStage.Failed);
					wakeTheWaiters();
					return;	
				}
				results.add(rs[0]);
				SetSearchStatus(SearchStage.Done);
				wakeTheWaiters();
			}

		});
		return new SearchQueryResult(results, v.getMap());		
	}
}
