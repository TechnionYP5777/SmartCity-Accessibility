package smartcity.accessibility.services;

import java.io.IOException;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

import smartcity.accessibility.exceptions.illigalString;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.search.SearchQuery;
import smartcity.accessibility.search.SearchQueryResult;
import smartcity.accessibility.services.exceptions.NoAdressFound;

/**
 * @author ariel
 */

@RestController
public class AdressSearchService {
	
	
	private static final GeoApiContext context;
	private static final String API_KEY = "AIzaSyAxFHT3dheK_oTyQu6lERytdi83uaqg5m8";
	static {
		context = new GeoApiContext().setApiKey(API_KEY);
	}
	@RequestMapping(value="/simpleSearch/{search}")
	@ResponseBody
    public Location searchService(@RequestHeader("authToken") String token, @PathVariable("search") String search) {	
		
		
		try {
				SearchQuery $ = SearchQuery.adressSearch(search);
		        SearchQueryResult sqr1 = $.SearchByAddress();
		        $.waitOnSearch();
		        Location location2 = sqr1.getLocations().get(0);
		        return location2;	
		} catch (illigalString | InterruptedException e) {
			return null;
		}
    }
	
	@RequestMapping(value="/getAdress")
	@ResponseBody
	public wrapper getAdress (@RequestHeader("authToken") String token,
			@RequestParam("srcLat") Double srcLat, @RequestParam("srcLng") Double srcLng){
		GeocodingApiRequest req = GeocodingApi.newRequest(context);
		req = req.latlng(new LatLng(srcLat,srcLng));
		GeocodingResult[] a;
		try {
			a = req.await();
			if(a.length == 0){
				throw new NoAdressFound();
			}else{
				System.out.println(a[0].formattedAddress);
				return new wrapper(a[0].formattedAddress);
			}
		} catch (ApiException | InterruptedException | IOException | NoAdressFound e) {
			System.out.println("shit fuck");
			throw new NoAdressFound();
		}
		
	}
	
	@RequestMapping(value="/getAdressExample")
	@ResponseBody
	public wrapper getAdressExample (
			@RequestParam("srcLat") Double srcLat, @RequestParam("srcLng") Double srcLng){
		GeocodingApiRequest req = GeocodingApi.newRequest(context);
		req = req.latlng(new LatLng(srcLat,srcLng));
		GeocodingResult[] a;
		try {
			a = req.await();
			if(a.length == 0){
				throw new NoAdressFound();
			}else{
				System.out.println(a[0].formattedAddress);
				return new wrapper(a[0].formattedAddress);
			}
		} catch (ApiException | InterruptedException | IOException | NoAdressFound e) {
			System.out.println("shit fuck");
			throw new NoAdressFound();
		}
		
	}
}