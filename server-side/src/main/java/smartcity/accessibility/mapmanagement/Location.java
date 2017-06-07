package smartcity.accessibility.mapmanagement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.parse4j.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.maps.model.LatLng;
import com.google.maps.model.PlaceType;

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
	private String segmentId;
	private int rating;

	public Location() {
		reviews = new ArrayList<>();
		this.coordinates = null;
		this.rating = -1;
	}

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
		return reviews.stream().filter(Review::isPinned).collect(Collectors.toList());
	}

	public List<Review> getNotPinnedReviews() {
		return reviews.stream().filter(λ -> !λ.isPinned()).collect(Collectors.toList());
	}

	public void setName(String s) {
		this.name = s;
	}

	public String getName() {
		return this.name;
	}

	public void setRating() {
		this.rating = this.getTotalRating();
	}

	public int getRating() {
		return this.rating;
	}

	public String getSegmentId() {
		return segmentId;
	}

	public void setSegmentId(String segmentId) {
		this.segmentId = segmentId;
	}

	/**
	 * @param i
	 *            - the number of reviews we want to calculate the Location's
	 *            rating by
	 * @return the rating of the specified location.
	 */
	public Score getRating(int i) {
		int score = Score.getMaxScore();
		if (reviews.isEmpty())
			return new Score(score);
		score = (new BestReviews(i, this)).getTotalRatingByAvg();
		return new Score(score);
	}

	/*
	 * This is the method we should use to check the total rating of a location.
	 */
	public int getTotalRating() {
		if (reviews.isEmpty())
			return Score.getMaxScore();
		return (new BestReviews(this)).getTotalRatingByAvg();
	}

	public LatLng getCoordinates() {
		return this.coordinates;
	}

	public void addReviews(Collection<Review> revs) {
		reviews.addAll(revs);
	}

	/**
	 * @author ArthurSap
	 * @throws ParseException
	 */
	public void addReview(Review r) {
		reviews.add(r);
	}

	private Review getReview(Review r) {
		for (Review $ : reviews)
			if ($.equals(r))
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
	 * @param r
	 *            - the review to be checked
	 * @return - the review if it exists or null otherwise
	 */
	private Review checkExistence(Review r) {
		Review review = getReview(r);
		if (review != null)
			return review;
		logger.error("This review doesn't exist in current location! {}", this.coordinates);
		return null;

	}

	@Override
	public boolean equals(Object o) {
		return o instanceof Location && ((Location) o).coordinates.equals(this.coordinates)
				&& ((Location) o).locationSubType.equals(locationSubType)
				&& ((Location) o).locationType.equals(locationType);
	}

	@Override
	public int hashCode() {
		return Double.hashCode(coordinates.lat) + Double.hashCode(coordinates.lng);
	}

	public LocationTypes getLocationType() {
		return locationType;
	}

	public LocationSubTypes getLocationSubType() {
		return locationSubType;
	}

	public enum LocationSubTypes {
		RESTAURANT, HOTEL, CAFE, DEFAULT;

		public String getSearchType() {
			switch (this) {
			case DEFAULT:
				return PlaceType.STORE.toString().toUpperCase();
			case HOTEL:
				return PlaceType.LODGING.toString().toUpperCase();
			case RESTAURANT:
				return PlaceType.RESTAURANT.toString().toUpperCase();
			case CAFE:
				return PlaceType.CAFE.toString().toUpperCase();
			default:
				break;
			}
			return null;
		}

		public LocationTypes getParentype() {
			for (LocationTypes lt : LocationTypes.values()) {
				if (lt.subTypes.contains(this)) {
					return lt;
				}
			}
			return null;
		}
	}

	public enum LocationTypes {
		COORDINATE(LocationSubTypes.DEFAULT), FACILITY(LocationSubTypes.RESTAURANT, LocationSubTypes.HOTEL,
				LocationSubTypes.CAFE, LocationSubTypes.DEFAULT), STREET(LocationSubTypes.DEFAULT);

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