package application.property;

/**
 * Represents one listing of a property for rental on Airbnb.
 * This is essentially one row in the data table. Each column
 * has a corresponding field.
 */
public class Property {

	/**
	 * The id of an a single property.
	 */
	private String id;

	/**
	 * The name of a single property.
	 */ 
	private String description; 

	/**
	 * The id and name of the host for this listing.
	 * Each listing has only one host, but one host may
	 * list many properties.
	 */
	private String hostId;
	private String hostName;

	/**
	 * The grouped location to where the listed property is situated.
	 * For this data set, it is a london borough.
	 */
	private String neighbourhood;

	/**
	 * The location on a map where the property is situated.
	 */
	private double latitude;
	private double longitude;

	/**
	 * The type of property, either "Private room" or "Entire Home/apt".
	 */
	private String roomType;

	/**
	 * The price per night's stay
	 */
	private int price;

	/**
	 * The minimum number of nights the listed property must be booked for.
	 */
	private int minimumNights;
	private int numberOfReviews;

	/**
	 * The date of the last review, but as a String
	 */
	private String lastReview;
	private double reviewsPerMonth;

	/**
	 * The total number of listings the host holds across AirBnB
	 */
	private int calculatedHostListingsCount;
	/**
	 * The total number of days in the year that the property is available for
	 */
	private int availability365;

	/**
	 * The formatted borough name.
	 * This is the formatted borough assigned the way the components are assigned.
	 * This allows for no name confusions between the variable names and the borough name.
	 * Each image view has its own id which is the variable name, so this will be the same as that for ease of use.
	 */
	private String formattedBoroughName;

	/**
	 * Constructs a property.
	 * @param id - the property id.
	 * @param description - the description.
	 * @param hostId - the host id.
	 * @param hostName - the host name.
	 * @param neighbourhood - the neighbourhood.
	 * @param latitude - the latitude.
	 * @param longitude - the longitude.
	 * @param roomType - the room type.
	 * @param price - price per night.
	 * @param minimumNights - the minimum nights.
	 * @param numberOfReviews - the number of reviews.
	 * @param lastReview - the last review.
	 * @param reviewsPerMonth - the reviews per month.
	 * @param calculatedHostListingsCount - the calculated host listings count.
	 * @param availability365 - the availability during the year.
	 */
	public Property(String id, String description, String hostId,
			String hostName, String neighbourhood, double latitude,
			double longitude, String roomType, int price,
			int minimumNights, int numberOfReviews, String lastReview,
			double reviewsPerMonth, int calculatedHostListingsCount, int availability365) {
		this.id = id;
		this.description = description;
		this.hostId = hostId;
		this.hostName = hostName;
		this.neighbourhood = neighbourhood;
		this.latitude = latitude;
		this.longitude = longitude;
		this.roomType = roomType;
		this.price = price;
		this.minimumNights = minimumNights;
		this.numberOfReviews = numberOfReviews;
		this.lastReview = lastReview;
		this.reviewsPerMonth = reviewsPerMonth;
		this.calculatedHostListingsCount = calculatedHostListingsCount;
		this.availability365 = availability365;
		setFormattedBoroughName(formatBoroughName(neighbourhood));
	}

	/**
	 * Get the id of the property.
	 * @return - the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Get the description of the property.
	 * @return - the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Get the host id. 
	 * @return - the host id.
	 */
	public String getHostId() {
		return hostId;
	}

	/**
	 * Get the host name.
	 * @return - the host name.
	 */
	public String getHostName() {
		return hostName;
	}

	/**
	 * Get the heighbourhood/borough.
	 * @return - the neighbourhood/borough.
	 */
	public String getNeighbourhood() {
		return neighbourhood;
	}

	/**
	 * Get the latitude.
	 * @return - the latitude.
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * Get the longitude.
	 * @return - the longitude.
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * Get the room type.
	 * @return - the room type.
	 */
	public String getRoomType() {
		return roomType;
	}

	/**
	 * Get the price per night.
	 * @return - the price per night.
	 */ 
	public int getPrice() {
		return price;
	}

	/**
	 * Get the minimum nights.
	 * @return - the minimum nights.
	 */
	public int getMinimumNights() {
		return minimumNights;
	}

	/**
	 * Get the number of reviews.
	 * @return - the number of reviews.
	 */
	public int getNumberOfReviews() {
		return numberOfReviews;
	}

	/**
	 * Get the last review.
	 * @return - the last review.
	 */
	public String getLastReview() {
		return lastReview;
	}

	/**
	 * Get the reviews per month.
	 * @return - the reviews per month.
	 */
	public double getReviewsPerMonth() {
		return reviewsPerMonth;
	}

	/**
	 * The calculated host listings count.
	 * @return - the calculated host listings count.
	 */
	public int getCalculatedHostListingsCount() {
		return calculatedHostListingsCount;
	}

	/**
	 * Get the days available during the year.
	 * @return - the days available during the year.
	 */
	public int getAvailability365() {
		return availability365;
	}

	/**
	 * Get the year of the last review.
	 * @return - the year of the last review.
	 */ 
	public int getLastReviewYear() {
		return isMissingReview() ? 0 : Integer.parseInt(lastReview.substring(6, 10));
	}

	/**
	 * Get the month of the last review.
	 * @return - the month of the last review.
	 */
	public int getLastReviewMonth() {
		return isMissingReview() ? 0 : Integer.parseInt(lastReview.substring(3, 5));
	}

	/**
	 * Get the day of the last review.
	 * @return - the day of the last review.
	 */
	public int getLastReviewDay() {
		return isMissingReview() ? 0 : Integer.parseInt(lastReview.substring(0, 2));
	}

	/**
	 * If a review is missing.
	 * @return true if a review is missing, false otherwise.
	 */
	public boolean isMissingReview() {
		return lastReview == null || lastReview.isEmpty();
	}

	/**
	 * Sets the formatted borough name.
	 * If it has been set once, it will not be set again once the data is loaded.
	 * @param formattedBoroughName - the formatted borough name.
	 */
	private void setFormattedBoroughName(String formattedBoroughName) {
		if (this.formattedBoroughName == null) {
			this.formattedBoroughName = formattedBoroughName;
		}
	}

	/**
	 * Get the formatted borough name.
	 * @return - the formatted borough name.
	 */
	public String getFormattedBoroughName() {
		return formattedBoroughName;
	}

	/**
	 * Converts a given borough name in a variable naming format.
	 * @param borough - the borough.
	 * @return - the formatted borough name.
	 */
	private String formatBoroughName(String borough) {
		borough.replaceAll(" ", "");
		borough = borough.toLowerCase();
		StringBuilder stringBuilder = new StringBuilder();
		boolean spaceExists = true;
		for (int i = 0; i < borough.length(); i++) {
			if (spaceExists) {
				stringBuilder.append(("" + borough.charAt(i)).toUpperCase());
				spaceExists = false;
			} else {
				stringBuilder.append(borough.charAt(i));
			}
			if (borough.charAt(i) == ' ') {
				spaceExists = true;
			}
		}
		String finalString = stringBuilder.toString();
		finalString = finalString.substring(0, 1).toLowerCase() + finalString.substring(1);
		return finalString.replaceAll(" ", "");
	}

	/**
	 * Overriding the toString method to print details out cleaner.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Property id = " + id + " \n");
		sb.append("Description = " + description + "\n");
		sb.append("Host id = " + hostId + "\n");
		sb.append("Host name = " + hostName + "\n");
		sb.append("Neighbourhood = " + neighbourhood + "\n");
		sb.append("Latitude = " + latitude + "\n");
		sb.append("Longitude = " + longitude + "\n");
		sb.append("Room type = " + roomType + "\n");
		sb.append("Price = " + price + "\n");
		sb.append("Minimum nights = " + minimumNights + "\n");
		sb.append("Number of reviews = " + numberOfReviews + "\n");
		sb.append("Last review = " + lastReview + "\n");
		sb.append("Reviews per month = " + reviewsPerMonth + "\n");
		sb.append("Calculated host listings count = " + calculatedHostListingsCount + "\n");
		sb.append("Availability during the year = " + availability365 + "\n");
		sb.append("\n");
		return sb.toString();
	}
}
