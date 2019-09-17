package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import application.property.Property;
import application.property.PropertyLoader;

/**
 * Represents the dataStore class.
 * A class used to store properties loaded from CSV file.
 * Also stores list of properties within a price range selected by the user on the first page of the software.
 * Handles everything to do with properties within the application.
 * @author Sal Chousein, Mik Miller, Sam Paget, Mergana Sitepu
 */
public class DataStore {

	/**
	 * The list of properties.
	 */
	private List<Property> properties;

	/**
	 * The list of properties within the price range selected by the user.
	 */
	private List<Property> propertiesWithinPriceRange;

	/**
	 * A map of the neighbourhood prices.
	 * The key -> String : Neighbourhood name.
	 * The value -> Integer : Total price of neighbourhood properties.
	 */
	private Map<String, Integer> neighbourhoodPrices;

	/**
	 * Constructs the data store.
	 */
	public DataStore() {
		this.properties = new PropertyLoader().load();
		this.neighbourhoodPrices = new HashMap<>();
		this.propertiesWithinPriceRange = new ArrayList<Property>();
	}

	/**
	 * Method used to initalise the properties within the price range.
	 * Also handles populating the price map which maps a borough name to the total price of the neighbourhood properties.
	 */
	public void prepareProperties() {
		this.propertiesWithinPriceRange = getProperties(RentalService.MINIMUM_PRICE_SELECTED, RentalService.MAXIMUM_PRICE_SELECTED);
		populatePriceMap();
	}

	/**
	 * Gets properties within a price range. 
	 * @param minimumPrice - the minimum price.
	 * @param maximumPrice - the maximum price.
	 * @return - the list of properties within the given price range.
	 */
	private List<Property> getProperties(int minimumPrice, int maximumPrice) {
		return properties.stream().filter(property -> property.getPrice() >= minimumPrice && property.getPrice() <= maximumPrice).distinct().collect(Collectors.toList());
	}

	/**
	 * Creates a list of all properties within a given neighbourhood/borough.
	 * Takes into account the formatted borough name as well as the normal borough name.
	 * @param - the neighbourHood/borough.
	 * @return - list of properties in given borough.
	 */
	public List<Property> getProperties(String neighbourHood) {
		return propertiesWithinPriceRange.stream().filter(property -> property.getNeighbourhood().equalsIgnoreCase(neighbourHood)
				|| property.getFormattedBoroughName().equalsIgnoreCase(neighbourHood)).distinct().collect(Collectors.toList());
	}

	/**
	 * Creates an empty list of boroughs.
	 * Loops through all properties within the price range and if the borough has not been put in the list, it inserts it.
	 * @returns a list of all the boroughs.
	 */
	public List<String> getBoroughs() {
		List<String> boroughs = new ArrayList<String>();
		propertiesWithinPriceRange.forEach(property -> {
			if (!boroughs.contains(property.getNeighbourhood())) {
				boroughs.add(property.getNeighbourhood());
			}
		});
		return boroughs;
	}

	/**
	 * Loops through all properties within price range and checks that if that property is in the one we want.
	 * If so, adds it to the count. If not, does not add it to count.
	 * Includes the check that if the formatted borough name matches the borough (used for images).
	 * @param borough - the borough we want to check.
	 * @return - the number of properties in that borough.
	 */
	public int getNumberOfProperties(String borough) {
		int count = 0; 
		for (Property property : propertiesWithinPriceRange) {
			if (property.getNeighbourhood().equalsIgnoreCase(borough) || property.getFormattedBoroughName().equalsIgnoreCase(borough)) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Return the cheapest property for properties within the price range.
	 * @return - the cheapest price within the collection.
	 */
	public int getCheapestPropertyPrice(String borough) {
		int cheapestPrice = 8000; //Highest cost is 7000, using a larger value to filter down.
		for (Property property : getProperties(borough)) {
			if (property.getPrice() < cheapestPrice) {
				cheapestPrice = property.getPrice();
			}
		}
		return cheapestPrice;
	}

	/**
	 * Return the dearest property for properties within the price range.
	 * @return - the priciest property within the collection.
	 */
	public int getDearestPropertyPrice(String borough) {
		int dearestPrice = 0; //Start from zero.
		for (Property property : getProperties(borough)) {
			if (property.getPrice() > dearestPrice) {
				dearestPrice = property.getPrice();
			}
		}
		return dearestPrice;
	}

	/**
	 * Loops through all properties within the price range and checks if it is available at least once in a year.
	 * If so, adds it to the count. if not, does not add it to count.
	 * @return - the number of available properties in a year.
	 */
	public int getAvailableProperties() {
		int count = 0;
		for (Property property : propertiesWithinPriceRange) {
			if(property.getAvailability365() > 0) {
				count ++;
			}
		}
		return count;
	}

	/**
	 * Loops through all properties within the price range and checks that the property is either an entire home or an apartment.
	 * If so, adds it to the count. if not does not add it to count.
	 * @return - the number of properties that is an entire home or an apartment (not private rooms).
	 */
	public int getNumberOfHomes() {
		int count = 0;
		for (Property property : propertiesWithinPriceRange) {
			if (property.getRoomType().equalsIgnoreCase("Entire home/apt")) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Maps the boroughs to a value of the total price of renting properties in each borough in accordance to the minimum 
	 * number of nights stayed.
	 * Uses a for loop to calculate the price of staying at each property for the minimum number of nights and 
	 * sums up these prices for each borough taking into account the given price range.
	 * @Key - the borough
	 * @Value - the total sum of prices of properties in accordance to minimum number of nights.
	 */
	private void populatePriceMap() {
		int totalPrice = 0;
		for (Property p : propertiesWithinPriceRange) {
			totalPrice = p.getMinimumNights() * p.getPrice();
			if (neighbourhoodPrices.containsKey(p.getNeighbourhood())) {
				totalPrice += neighbourhoodPrices.get(p.getNeighbourhood());
				neighbourhoodPrices.put(p.getNeighbourhood(), totalPrice); 
			} else {
				neighbourhoodPrices.put(p.getNeighbourhood(), totalPrice);
			}
			totalPrice = 0;
		}
	}

	/**
	 * Iterates the map implemented and returns the name of the borough with the greatest price value.
	 * @return - the borough with the greatest total price.
	 */
	public String getPriciestBorough() {
		String priciestNeighbourhood = "";
		int price = 0;
		for (Map.Entry<String, Integer> entry : neighbourhoodPrices.entrySet()) {
			String neighbourhood = entry.getKey();
			int priceOfNeighbourhood = entry.getValue();
			if (priceOfNeighbourhood > price) {
				price = priceOfNeighbourhood;
				priciestNeighbourhood = neighbourhood;
			}
		}
		return priciestNeighbourhood;
	}
	
	/**
	 * Get the total price of a given borough.
	 * Price is calculated by the price per night multiplied by the minimum nights it is available for.
	 * @param borough - the borough.
	 * @return - the total borough price.
	 */
	public int getTotalBoroughPrice(String borough) {
		return neighbourhoodPrices.get(borough);
	}

	/**
	 * loops through all properties within the given price range summing up the number of reviews.
	 * Then divides it by number of properties within the price range.
	 * @return - average number of reviews for all properties
	 */
	public double averageReviews() {
		int sum = 0;
		for (Property property: propertiesWithinPriceRange) {
			sum += property.getNumberOfReviews();
		}
		return sum / (propertiesWithinPriceRange.size());
	}

	/**
	 * ADDTIONAL STATISTIC 1:
	 * Loops through the latitudes of all properties within the price range and returns the one with the greatest value.
	 * This is the closest to the north Pole as the latitude there is 90.
	 * Longitude can be ignored as it is 0 at the north pole.
	 * @return - the property with greatest latitude.
	 */
	public Property getClosestPropertyToNorthPole() {
		double latitude = 0;
		Property tempProperty = propertiesWithinPriceRange.get(0);
		for (Property property : propertiesWithinPriceRange) {
			if (property.getLatitude() > latitude) {
				latitude = property.getLatitude();
				tempProperty = property;
			}
		}
		return tempProperty;
	}

	/**
	 * ADDITIONAL STATISTIC 2:
	 * Iterates the properties within the price range and returns the property with the longest 
	 * minimum number of nights.
	 * @return - the property within the data set with the longest minimum number of nights.
	 */
	public Property getLongestStay() {
		int minNights = 0;
		Property tempProperty = propertiesWithinPriceRange.get(0);
		for (Property property: propertiesWithinPriceRange) {
			if (property.getMinimumNights() > minNights) {
				minNights = property.getMinimumNights();
				tempProperty = property;
			}
		}
		return tempProperty;
	}

	/**
	 * ADDITIONAL STATISTIC 3
	 * Iterates over properties within price range collection and returns and array with number of rooms of the given type.	
	 * @return - number of rooms of the given type
	 */
	public int[] getNoOfRoomTypes() {
		int numberOfPrivate = 0;
		int numberOfApt = 0;
		int numberOfShared = 0;
		for(Property x: propertiesWithinPriceRange) {
			if(x.getRoomType().equalsIgnoreCase("Private room")){
				numberOfPrivate += 1;			
			} 
			else if(x.getRoomType().equalsIgnoreCase("Shared room")) {
				numberOfShared += 1;
			} else {
				numberOfApt += 1;
			}		
		}
		return new int[] {numberOfPrivate, numberOfApt, numberOfShared};
	}

	/**
	 * ADDITIONAL STATISTIC 4
	 * Iterates through the list of all properties within the price range and utilises 
	 * the method getLatesetReviewed Property to decide between which property has the latest date 
	 * of review with reference to the given price range/dataset..
	 * @param - the borough we are looking at
	 * @return - property with latest review
	 */
	public Property getLatestReview() {
		Property temp = propertiesWithinPriceRange.get(0);
		for (Property p : propertiesWithinPriceRange) {
			temp = getLatestReviewedProperty(temp, p);
			if (temp == null) {
				temp = p;
			}
		}
		return temp;
	}

	/**
	 * ADDITIONAL STATISTIC 4 LOGIC
	 * Compares the dates of two properties and returns the one with latest date.
	 * @param - first property.
	 * @param - second property.
	 * @return - property with the latest date
	 */
	private Property getLatestReviewedProperty(Property one, Property two) {
		if (one.getLastReviewYear() == two.getLastReviewYear()) {
			if (one.getLastReviewMonth() == two.getLastReviewMonth()) {
				return one.getLastReviewDay() > two.getLastReviewDay() ? one : two;
			} else if (one.getLastReviewMonth() > two.getLastReviewMonth()) {
				return one;
			}
		} else if (one.getLastReviewYear() > two.getLastReviewYear()) {
			return one;
		} else {
			return two;
		}
		return null;
	}

	/**
	 * Get the properties within the price range selected by the user.
	 * @return - the list of properties within the price range.
	 */
	public List<Property> getPropertiesWithinPriceRange() {
		return propertiesWithinPriceRange;
	}
	
	/**
	 * Get all the properties within the software.
	 * Used in the JUNIT test class.
	 * @return - a list of all the properties.
	 */
	public List<Property> getAllProperties() {
		return properties;
	}
}