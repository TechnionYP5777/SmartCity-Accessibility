package smartcity.accessibility.mapmanagement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.parse4j.ParseException;

import com.teamdev.jxmaps.LatLng;
import com.teamdev.jxmaps.swing.MapView;

import smartcity.accessibility.database.ReviewManager;
import smartcity.accessibility.exceptions.UnauthorizedAccessException;
import smartcity.accessibility.mapmanagement.JxMapsFunctionality.ExtendedMapView;
import smartcity.accessibility.search.SearchQuery;
import smartcity.accessibility.search.SearchQueryResult;
import smartcity.accessibility.socialnetwork.User;
import smartcity.accessibility.socialnetwork.User.Privilege;
import smartcity.accessibility.socialnetwork.BestReviews;
import smartcity.accessibility.socialnetwork.Review;
import smartcity.accessibility.socialnetwork.Score;

/**
 * @author Koral Chapnik
 */

public class Location {

	public enum LocationSubTypes {
		Restaurant, Hotel, Bar, Default
	}

	public enum LocationTypes {
		Coordinate(LocationSubTypes.Default), Facility(LocationSubTypes.Restaurant, LocationSubTypes.Hotel,
				LocationSubTypes.Bar, LocationSubTypes.Default), Street(LocationSubTypes.Default);

		private List<LocationSubTypes> subTypes = new ArrayList<LocationSubTypes>();

		LocationTypes(LocationSubTypes... s) {
			for (LocationSubTypes st : s)
				subTypes.add(st);
		}

		public List<LocationSubTypes> getSubTypes() {
			return Collections.unmodifiableList(subTypes);
		}
	}

	private static final long serialVersionUID = -9204783865281694652L;

	private ArrayList<Review> reviews;
	private LatLng coordinates;
	private String name = "";
	private LocationTypes locationType;
	private LocationSubTypes locationSubType;

	// ArthurSap
	public ArrayList<Review> getReviews() {
		return reviews;
	}

	// ArthurSap
	public List<Review> getPinnedReviews() {
		return reviews.stream().filter(r -> r.isPinned()).collect(Collectors.toList());
	}
	
	public List<Review> getNotPinnedReviews() {
		return reviews.stream().filter(r -> !r.isPinned()).collect(Collectors.toList());
	}

	public Location() {
		initiateArrays();
		this.coordinates = null;
	}

	public Location(LatLng c) {
		initiateArrays();
		this.coordinates = c;
	}

	public Location(LatLng c, LocationTypes lt) {
		initiateArrays();
		this.coordinates = c;
		this.locationType = lt;
	}
	
	public Location(LatLng c, LocationTypes lt, LocationSubTypes lst){
		this(c,lt);
		this.locationSubType = lst;
	}
	
	/**
	 * Added in order to create location when loading them from the DB 
	 * @author assaflu
	 * @param c
	 * @param lt
	 * @param lst
	 * @param r
	 */
	public Location(LatLng c, LocationTypes lt, LocationSubTypes lst,ArrayList<Review> r){
		this(c,lt);
		this.reviews = r;
		this.locationSubType = lst;
	}

	public void setName(String n) {
		this.name = n;
	}

	public String getName() {
		return this.name;
	}

	public Location(ArrayList<Review> r, LatLng c) {
		this.reviews = r;
		this.coordinates = c;
	}

	public Location(ArrayList<Review> pinned,ArrayList<Review> unPinned, LatLng c) {
		initiateArrays();
		this.reviews.addAll(pinned);
		this.reviews.addAll(unPinned);
		this.coordinates = c;
	}
	
	private void initiateArrays() {
		this.reviews = new ArrayList<Review>();
	}

	// n is the number of reviews we want to calculate the Location's rating by
	public Score getRating(int n) {
		int rating = -1;
		List<Review> pinnedReviews = getPinnedReviews();
		List<Review> notPinnedReviews = getNotPinnedReviews();
		if (pinnedReviews.isEmpty() && notPinnedReviews.isEmpty())
			return new Score(Score.getMinScore());
		BestReviews br = !pinnedReviews.isEmpty() ? new BestReviews(n, pinnedReviews) : new BestReviews(n, notPinnedReviews);
		rating = br.getTotalRating();
		return new Score(rating);
	}

/*
 * due to changes of search results, implementation for this should change
 * 
 * 	public String getAddress(MapView mapView) {
		SearchQuery sq = SearchQuery.adressSearch("_");
		JxMapsFunctionality.waitForMapReady((ExtendedMapView) mapView);
		SearchQueryResult result = sq.searchByCoordinates(mapView, coordinates);
		try {
			sq.waitOnSearch();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result.getCoordinations().get(0).getFormattedAddress();
	}*/

	public LatLng getCoordinates() {
		return this.coordinates;
	}

	/**
	 * @author Kolikant
	 * @throws ParseException
	 */
	public void addReview(User u, int rating, String review) throws ParseException {
		actuallyAddReview((new Review(this, rating, review, u)));
	}

	/**
	 * @author ArthurSap
	 * @throws ParseException
	 */
	public void addReview(Review r) throws ParseException {
		actuallyAddReview(r);
	}

	/**
	 * @author ArthurSap
	 * @throws ParseException
	 */
	private void actuallyAddReview(Review r) throws ParseException {
		reviews.add(r);
		ReviewManager.uploadReview(r);
	}

	
	private Review getReview(Review r) {
		for (Review rev : reviews)
			if (rev.equals(r))
				return rev;
		return null;
	}
	
	/**
	 * Marks a review as important - whilst calculating the location's
	 * accessibility level always takes this review in the calculation. Also,
	 * always show this review in the top reviews.
	 * 
	 * @throws UnauthorizedAccessException
	 **/
	public void pinReview(User u, Review r) throws UnauthorizedAccessException {
		Review review = getReview(r);
		if (review == null) {
			System.out.print("ERROR! This review doesn't exist in current location!");
			System.out.println("\tCurrent Location: " + this.coordinates);
			return;
		}

		if (getPinnedReviews().contains(r)) {
			System.out.println("Review is already pinned.");
			return;
		}

		review.pin(u);
	}

	/**
	 * Reverts the effects of pinReview.
	 * 
	 * @throws UnauthorizedAccessException
	 */
	public void unpinReview(User u, Review r) throws UnauthorizedAccessException {	
		Review review = getReview(r);
		if (review == null) {
			System.out.print("ERROR! This review doesn't exist in current location!");
			System.out.println("\tCurrent Location: " + this.coordinates);
			return;
		}
		
		if (!review.isPinned()) {
			System.out.println("Review is already un-pinned.");
			return;
		}
		review.unPin(u);
	}

	
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;

		if (!(o instanceof Location))
			return false;

		return ((Location) o).coordinates.equals(this.coordinates);
	}

	public LocationTypes getLocationType() {
		return locationType;
	}

	public LocationSubTypes getLocationSubType() {
		return locationSubType;
	}
	
	/**
	 * turns string to enum LocationTypes
	 * @author assaflu
	 * @param s
	 * @return
	 */
	public static LocationTypes stringToEnumTypes(String s){
		switch(s){
			case "Coordinate":
				return LocationTypes.Coordinate;
			case "Facility":
				return LocationTypes.Facility;
			case "Street":
				return LocationTypes.Street;
		}
		return LocationTypes.Street; // default return
	}
	
	/**
	 * trun string to enum LocationSubTypes
	 * @author assaflu
	 * @param s
	 * @return
	 */
	public static LocationSubTypes stringToEnumSubTypes(String s){
		switch(s){
		case "Restaurant":
			return LocationSubTypes.Restaurant;
		case "Hotel":
			return LocationSubTypes.Hotel;
		case "Bar":
			return LocationSubTypes.Bar;
		}
		return LocationSubTypes.Default; // default return
	}

}