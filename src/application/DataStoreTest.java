package application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import application.property.Property;

class DataStoreTest {

	/**
	 * The date store.
	 */
	private DataStore dataStore; 

	/**
	 * The list of all the properties in the software.
	 */
	private List<Property> properties;	

	/**
	 * Default constructor for test class DataStoreTest
	 */
	public DataStoreTest()  {
		this.dataStore = new DataStore();
		this.dataStore.prepareProperties();
		this.properties = dataStore.getAllProperties();
		RentalService.MAXIMUM_PRICE_SELECTED = 100000;
		RentalService.MINIMUM_PRICE_SELECTED = 0;
	}

	/**
	 * Empty.
	 */
	@Before
	public void setUp() {

	}


	/**
	 * Testing the get properties method.
	 */
	@Test
	public void testGetProperties() {
		assertNotNull(properties.stream().filter(property -> property.getPrice() >= 0 && property.getPrice() <= 10000).distinct().collect(Collectors.toList()));
		assertEquals(properties.stream().filter(property -> property.getPrice() >= 5 && property.getPrice() <= 5).distinct().collect(Collectors.toList()).size(), 0);
	}


	/**
	 * Testing the property count for a given borough.
	 */
	@Test
	public void testGetPropertiesByBorough() {
		assertEquals(dataStore.getProperties("Croydon").size(), 553);

	}

	/**
	 * Tests if the method returns correct number of boroughs.
	 */
	@Test
	public void testGetBoroughs() {
		assertEquals(dataStore.getBoroughs().size(),33);

	}

	/**
	 * Tests if the method returns correct number of properties in a given borough.
	 */
	@Test
	public void testGetNumberOfProperties() { 
		assertEquals(dataStore.getNumberOfProperties("Bromley"), 391);
	}

	/**
	 * Tests if the method returns correct price for the cheapest property.
	 */
	@Test
	public void testGetCheapestPropertyPrice() {
		assertEquals(dataStore.getCheapestPropertyPrice("Croydon"), 8);		
	}

	/**
	 * Tests if the method returns correct price of the dearest property.
	 */
	@Test
	public void testDearestPropertyPrice() {
		assertEquals(dataStore.getDearestPropertyPrice("Croydon"), 405);
	}

	/**
	 * Tests if the method returns correct number of available properties.
	 */
	@Test
	public void testGetAvailableProperties() {
		assertEquals(dataStore.getAvailableProperties(), 41941);
	}

	/** 
	 * Tests if the method returns correct number of homes.
	 */
	@Test
	public void testGetNumberOfHomes() {
		assertEquals(dataStore.getNumberOfHomes(),27175);
	}

	/**
	 * Tests if the method correctly returns the priciest borough.
	 */
	@Test
	public void testGetPriciestBorough() {
		assertEquals(dataStore.getPriciestBorough(), "Westminster");	
	}

	/**
	 * Tests if the method correctly returns the average of total reviews.
	 */
	@Test
	public void testAverageReviews() {
		assertEquals(dataStore.averageReviews(), 12.0);
	}

	/**
	 * Tests if the method correctly returns the property that is closest to the North Pole.
	 */
	@Test
	public void testClosestNorthPole() {
		Property temp = new Property("14361646","Home from Home Enfield Area","88067875","Richard","Enfield",51.68310107,-0.110834286,"Private room",50,1,0,"",-1.0,1,365);
		assertEquals(dataStore.getClosestPropertyToNorthPole().getId(),temp.getId());
	}

	/**
	 * Tests if the method correctly returns the property with the biggest minimum number of nights.
	 */
	@Test
	public void testLongestStay() {
		Property temp = new Property("13261420","Large double room in East Sheen","74651323","Inese","Richmond upon Thames",51.46427403,-0.275216508,"Private room",400,5000,3,"31/07/2016",0.33,1,89);
		assertEquals(dataStore.getLongestStay().getId(), temp.getId());
	} 

	/**
	 * Tests if the method correctly returns the array with numbers of rooms of given type.
	 */
	@Test
	public void testGetNoOfRoomTypes() {
		int[] x = new int[] {26019,27175,710};
		Assert.assertArrayEquals(x, dataStore.getNoOfRoomTypes());
	}

	/**
	 * Tests if the method correctly returns the property that has the latest review.
	 */
	@Test
	public void testGetLatestReview() {
		Property temp = new Property("17212442","Lovely double Room @ CITY AIRPORT & CANARY WHARF","14983028","Sk","Newham",51.51818901,0.006913357,"Private room",28,1,1,"05/03/2017",1.0,3,24);
		assertEquals(dataStore.getLatestReview().getId(), temp.getId());	
	}

	/**
	 * Tests if the method correctly returns the list with properties within the range.
	 */
	@Test
	public void testGetPropertiesWithInPriceRange() {
		assertFalse(dataStore.getPropertiesWithinPriceRange().size() == 0);
	}
}
