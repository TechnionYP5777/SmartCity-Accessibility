package smartcity.accessibility.mapmanagement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.parse4j.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.exceptions.UnauthorizedAccessException;
import smartcity.accessibility.socialnetwork.BestReviews;
import smartcity.accessibility.socialnetwork.Review;
import smartcity.accessibility.socialnetwork.Score;

/**
 * @author Koral Chapnik
 */

public class Location {

	private static Logger logger = LoggerFactory.getLogger(Location.class);

	private ArrayList<Review> reviews;
	private LatLng coordinates;
	private String name = "";
	private LocationTypes locationType;
	private LocationSubTypes locationSubType;

	public void setCoordinates(LatLng coordinates) {
		this.coordinates = coordinates;
	}

	public void setLocationType(LocationTypes locationType) {
		this.locationType = locationType;
	}

	public void setLocationSubType(LocationSubTypes locationSubType) {
		this.locationSubType = locationSubType;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public List<Review> getPinnedReviews() {
		return reviews.stream().filter(λ -> λ.isPinned()).collect(Collectors.toList());
	}

	public List<Review> getNotPinnedReviews() {
		return reviews.stream().filter(λ -> !λ.isPinned()).collect(Collectors.toList());
	}

	public Location() {
		reviews = new ArrayList<>();
		this.coordinates = null;
	}

	public void setName(String ¢) {
		this.name = ¢;
	}

	public String getName() {
		return this.name;
	}


	/**
	 * The calculation of the rating works as follows: if there are no
	 * 
	 * @param ¢
	 *            - the number of reviews we want to calculate the Location's
	 *            rating by
	 * @return the rating of the specified location.
	 */
	public Score getRating(int ¢) {
		int $ = Score.getMaxScore();
		if (reviews.isEmpty())
			return new Score($);
		$ = (new BestReviews(¢, this)).getTotalRatingByAvg();
		return new Score($);
	}

	public LatLng getCoordinates() {
		return this.coordinates;
	}
	
	public void addReviews(Collection<Review> revs){
		reviews.addAll(revs);
	}

	/**
	 * @author ArthurSap
	 * @throws ParseException
	 */
	public void addReview(Review ¢) {
		reviews.add(¢);
	}

	private Review getReview(Review ¢) {
		for (Review $ : reviews)
			if ($.equals(¢))
				return $;
		return null;
	}

	/**
	 * Deletes a review from this location
	 * 
	 * @param u
	 *            - the user that wishes to delete a review
	 * @param r
	 *            - the review to be deleted
	 * @throws UnauthorizedAccessException
	 *             - if the user isn't an admin or higher
	 */
	public void deleteReview(Review r) {
		if (checkExistence(r) == null)
			return;
		reviews.remove(r);
	}

	/**
	 * Checks whether a given review belongs to this location
	 * 
	 * @param ¢
	 *            - the review to be checked
	 * @return - the review if it exists or null otherwise
	 */
	private Review checkExistence(Review ¢) {
		Review $ = getReview(¢);
		if ($ != null)
			return $;
		logger.error("This review doesn't exist in current location! {}", this.coordinates);
		return null;
	}

	@Override
	public boolean equals(Object ¢) {
		return ¢ == this || (¢ instanceof Location && ((Location) ¢).coordinates.equals(this.coordinates)
				&& ((Location) ¢).locationSubType.equals(locationSubType)
				&& ((Location) ¢).locationType.equals(locationType));
	}

	public LocationTypes getLocationType() {
		return locationType;
	}

	public LocationSubTypes getLocationSubType() {
		return locationSubType;
	}
	
	public enum LocationSubTypes {
		Restaurant, Hotel, Cafe, Default;
		
		public String toString(){
				switch(this){
				case Default:
					return "";
				case Hotel:
					return "Hotel";
				case Restaurant:
					return "Restaurant";
				case Cafe:
					return "Cafe";
				default:
					break;
			}
				return null;
		}
	}

	public enum LocationTypes {
		Coordinate(LocationSubTypes.Default), Facility(LocationSubTypes.Restaurant, LocationSubTypes.Hotel,
				LocationSubTypes.Cafe, LocationSubTypes.Default), Street(LocationSubTypes.Default);

		private List<LocationSubTypes> subTypes = new ArrayList<>();

		LocationTypes(LocationSubTypes... s) {
			for (LocationSubTypes st : s)
				subTypes.add(st);
		}
		
		

		public List<LocationSubTypes> getSubTypes() {
			return Collections.unmodifiableList(subTypes);
		}
	}

}