package smartcity.accessibility.mapmanagement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.parse4j.ParseException;

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.database.ReviewManager;
import smartcity.accessibility.exceptions.UnauthorizedAccessException;
import smartcity.accessibility.socialnetwork.BestReviews;
import smartcity.accessibility.socialnetwork.Review;
import smartcity.accessibility.socialnetwork.Score;
import smartcity.accessibility.socialnetwork.User;
import smartcity.accessibility.socialnetwork.User.Privilege;
import smartcity.accessibility.socialnetwork.UserProfile;

/**
 * @author Koral Chapnik
 */

public class Location {

	

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

	public ArrayList<Review> getReviews() {
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

	/**
	 * use for adding reviews in from the db
	 * 
	 * @param ¢
	 */
	public void addReviewNoSave(Review ¢) {
		reviews.add(¢);
	}

	public LatLng getCoordinates() {
		return this.coordinates;
	}
	
	public void addReviews(Collection<Review> reviews){
		reviews.addAll(reviews);
	}

	/**
	 * @author Kolikant
	 * @throws ParseException
	 */
	public void addReview(UserProfile u, int rating, String review) throws ParseException {
		actuallyAddReview(new Review(this, rating, review, u));
	}

	/**
	 * @author ArthurSap
	 * @throws ParseException
	 */
	public void addReview(Review ¢) throws ParseException {
		actuallyAddReview(¢);
	}

	/**
	 * @author ArthurSap
	 * @throws ParseException
	 */
	private void actuallyAddReview(Review ¢) throws ParseException {
		reviews.add(¢);
		ReviewManager.uploadReview(¢);
	}

	private Review getReview(Review ¢) {
		for (Review $ : reviews)
			if ($.equals(¢))
				return $;
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
		Review review = checkExistence(r);
		if (review != null)
			if (!getPinnedReviews().contains(r))
				review.pin(u);
			else
				System.out.println("Review is already pinned.");
	}

	/**
	 * Reverts the effects of pinReview.
	 * 
	 * @throws UnauthorizedAccessException
	 */
	public void unpinReview(User u, Review r) throws UnauthorizedAccessException {
		Review review = checkExistence(r);
		if (review != null)
			if (review.isPinned())
				review.unPin(u);
			else
				System.out.println("Review is already un-pinned.");
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
	public void deleteReview(User u, Review r) throws UnauthorizedAccessException {
		if (checkExistence(r) == null)
			return;

		if (!Privilege.deletePrivilegeLevel(u) && !u.equals(r.getUser()))
			throw (new UnauthorizedAccessException(Privilege.minDeleteLevel()));

		reviews.remove(r);
		ReviewManager.deleteReview(r);
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
		System.out.print("ERROR! This review doesn't exist in current location!");
		System.out.println("\tCurrent Location: " + this.coordinates);
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

	/**
	 * turns string to enum LocationTypes
	 * 
	 * @author assaflu
	 * @param ¢
	 * @return
	 */
	public static LocationTypes stringToEnumTypes(String ¢) {
		if (¢ == null)
			return LocationTypes.Street;
		switch (¢) {
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
	 * 
	 * @author assaflu
	 * @param ¢
	 * @return
	 */
	public static LocationSubTypes stringToEnumSubTypes(String ¢) {
		switch (¢) {
		case "Bar":
			return LocationSubTypes.Bar;
		case "Hotel":
			return LocationSubTypes.Hotel;
		case "Restaurant":
			return LocationSubTypes.Restaurant;
		}
		return LocationSubTypes.Default; // default return
	}
	
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

}