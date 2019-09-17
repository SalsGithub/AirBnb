package application.componenthandlers;

import java.util.HashMap;

import application.RentalService;
import application.property.Property;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * A class to handle the components in the statistics screen handler.
 * @author Sal Chousein, Mik Miller, Sam Paget, Mergana Sitepu
 *
 */
public class StatisticsScreenHandler {

	/**
	 * The title of the first statistic being displayed in pane one.
	 */
	@FXML	
	private Label statTitle1;

	/**
	 * The label to display the actual statistic in pane one.
	 */
	@FXML
	private Label statLabel1;

	/**
	 * The button to view the next statistic in pane one.
	 */
	@FXML
	private Button nextStat1; 

	/**
	 * The button to view the previous statistic in pane one.
	 */
	@FXML
	private Button prevStat1; 

	/**
	 * The title of the second statistic being displayed in pane two.
	 */
	@FXML
	private Label statTitle2;

	/**
	 * The label to display the actual statistic in pane two.
	 */
	@FXML  
	private Label statLabel2;

	/**
	 * The button to view the next statistic in pane two.
	 */
	@FXML
	private Button nextStat2;

	/**
	 * The button to view the previous statistic in pane two.
	 */
	@FXML
	private Button prevStat2;

	/**
	 * The title of the third statistic being displayed in pane three.
	 */
	@FXML
	private Label statTitle3;

	/**
	 * The label to display the actual statistic in pane three.
	 */
	@FXML
	private Label statLabel3;

	/**
	 * The button to view the next statistic in pane three.
	 */
	@FXML
	private Button nextStat3; 

	/**
	 * The button to view the previous statistic in pane three.
	 */
	@FXML
	private Button prevStat3; 

	/**
	 * The title of the fourth statistic being displayed in pane four.
	 */
	@FXML
	private Label statTitle4;

	/**
	 * The label to display the actual statistic in pane four.
	 */
	@FXML 
	private Label statLabel4;

	/**
	 * The button to view the next statistic in pane four.
	 */
	@FXML
	private Button nextStat4;

	/**
	 *The button to view the previous statistic in pane four.
	 */
	@FXML
	private Button prevStat4; 

	/**
	 * The next button to go to the next page in the software.
	 */
	@FXML
	private Button nextButton;  

	/**
	 * The back button to go to the previous page in the software.
	 */
	@FXML 
	private Button backButton;

	/**
	 * A map which maps a button to an array of labels.
	 * The key : Button : the previous/next button.
	 * The value : Label array : the label held in the pane.
	 * The label is the labels that button will update when it is clicked on.
	 */
	private HashMap<Button, Label[]> buttonToLabel;

	/**
	 * Handles initialising the statistics screen.
	 */
	@FXML
	private void initialize() {
		this.buttonToLabel = new HashMap<>();
		//Setting unique ids to the buttons.
		prevStat1.setId("1");
		nextStat1.setId("2");
		prevStat2.setId("3");
		nextStat2.setId("4");
		prevStat3.setId("5");
		nextStat3.setId("6");
		prevStat4.setId("7");
		nextStat4.setId("8");
		//Display the default values shown on the screen.
		statTitle1.setText("Average Reviews");
		statLabel1.setText(Double.toString(RentalService.getDataStore().averageReviews()) + ".");
		statTitle2.setText("Priciest borough");
		String borough = RentalService.getDataStore().getPriciestBorough();
		int price = RentalService.getDataStore().getTotalBoroughPrice(borough);
		statLabel2.setText(borough + " is the priciest borough with a price of £" + price + ".");
		statTitle3.setText("Greatest Minimum Stay");
		Property greatestMinimumNights = RentalService.getDataStore().getLongestStay();
		String stat = "Property with id " + greatestMinimumNights.getId() + ", hosted by " + greatestMinimumNights.getHostName() +  " in "
				+ "" + greatestMinimumNights.getNeighbourhood() + ".";
		statLabel3.setText(stat);
		statTitle4.setText("Room Types");
		int[] roomTypes = RentalService.getDataStore().getNoOfRoomTypes();
		String roomType = "There are " + roomTypes[0] + " private properties, " + roomTypes[1] + " apartments, " + roomTypes[2] + " shared.";
		statLabel4.setText(roomType);
		//Register the buttons click event handler, and populating the button into the hashmap.
		registerButton(prevStat1, nextStat1, prevStat2, nextStat2, prevStat3, nextStat3, prevStat4, nextStat4);
	}

	/**
	 * Handles going to the next page in the software.
	 */
	@FXML
	private void next() {
		RentalService.viewPropertyFinderScreen();
	}

	/**
	 * Handles going back to the previous page in the software.
	 */
	@FXML	
	private void back() {
		RentalService.viewMapScreen();
	}

	/**
	 * Registers a button.
	 * @param buttons - the array of buttons to register.
	 */
	private void registerButton(Button...buttons) {
		EventHandler<MouseEvent> event = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent)  {
				Object object = mouseEvent.getSource(); 
				if (object instanceof Button) {
					Button button = (Button) object;
					changeStatistic(button);
				}
			}
		};
		for (Button button : buttons) {
			buttonToLabel.put(button, getLabels(button.getId()));
			button.setOnMouseClicked(event);
		}
	}

	/**
	 * Gets an array of labels for a given button id.
	 * @param buttonId - the button id.
	 * @return - the array of labels.
	 */
	private Label[] getLabels(String buttonId) {
		switch(buttonId) {
		case "1":
			return new Label[] {statTitle1, statLabel1};
		case "2":
			return new Label[] {statTitle1, statLabel1};
		case "3":
			return new Label[] {statTitle2, statLabel2};
		case "4":
			return new Label[] {statTitle2, statLabel2};
		case "5":
			return new Label[] {statTitle3, statLabel3};
		case "6":
			return new Label[] {statTitle3, statLabel3};
		case "7":
			return new Label[] {statTitle4, statLabel4};
		case "8":
			return new Label[] {statTitle4, statLabel4};
		}
		return null;
	}	

	/**
	 * The currently viewed statistic.
	 */
	private String currentlyViewed = null;

	/**
	 * Updates a statistic for a given button.
	 * @param button - the button.
	 */
	private void changeStatistic(Button button) {	
		int buttonId = Integer.parseInt(button.getId());
		if (buttonId == 1 || buttonId == 2) { //First panel holds average number of reviews and total number of available properties.
			currentlyViewed = getLabels(button.getId())[0].getText();
			int availableProperties = RentalService.getDataStore().getAvailableProperties();
			double averageReviews = RentalService.getDataStore().averageReviews();
			if (currentlyViewed.startsWith("Average")) {
				getLabels(button.getId())[0].setText("Available properties");
				getLabels(button.getId())[1].setText("There are " + availableProperties + " properties available.");
			} else {
				getLabels(button.getId())[0].setText("Average Reviews");
				getLabels(button.getId())[1].setText("" + averageReviews + ".");
			}
		} else if (buttonId == 3 || buttonId == 4) { //Second panel holds number of Homes/apartments, priciest borough.
			currentlyViewed = getLabels(button.getId())[0].getText();
			int homesAndApartments = RentalService.getDataStore().getNumberOfHomes();
			String priciestBorough = RentalService.getDataStore().getPriciestBorough();
			int price = RentalService.getDataStore().getTotalBoroughPrice(priciestBorough);
			if (currentlyViewed.startsWith("Priciest")) {
				getLabels(button.getId())[0].setText("Number of Homes/Apartments");
				getLabels(button.getId())[1].setText("There are " + homesAndApartments + " homes/apartments.");
			} else {
				getLabels(button.getId())[0].setText("Priciest borough");
				getLabels(button.getId())[1].setText(priciestBorough + " is the priciest borough with a price of £" + price + ".");
			}
		} else if (buttonId == 5 || buttonId == 6) { //Third panel holds the property closest to north pole and greatest minimum nights.
			currentlyViewed = getLabels(button.getId())[0].getText();
			Property closestNorthPole = RentalService.getDataStore().getClosestPropertyToNorthPole();
			Property greatestMinimumNights = RentalService.getDataStore().getLongestStay();
			if (currentlyViewed.startsWith("Closest")) {
				getLabels(button.getId())[0].setText("Greatest Minimum Stay");
				getLabels(button.getId())[1].setText("Property with id " + greatestMinimumNights.getId() + " hosted by " + greatestMinimumNights.getHostName() + 
						" in " + greatestMinimumNights.getNeighbourhood() + ".");
			} else {
				getLabels(button.getId())[0].setText("Closest Property To North Pole");
				getLabels(button.getId())[1].setText("Property with id " + closestNorthPole.getId() + ", hosted by " + closestNorthPole.getHostName() + 
						" in " + closestNorthPole.getNeighbourhood() + ".");
			}
		} else if (buttonId == 7 || buttonId == 8) { //Fourth panel holds the latest review and number of room types.
			currentlyViewed = getLabels(button.getId())[0].getText();
			int[] roomTypes = RentalService.getDataStore().getNoOfRoomTypes();
			Property latestReviewed = RentalService.getDataStore().getLatestReview();
			if (currentlyViewed.startsWith("Room")) {
				getLabels(button.getId())[0].setText("Latest Review");
				getLabels(button.getId())[1].setText("Latest review was made in " + latestReviewed.getNeighbourhood() + " on " + latestReviewed.getLastReview() + ".");
			} else {
				getLabels(button.getId())[0].setText("Room Types");
				getLabels(button.getId())[1].setText("There are " + roomTypes[0] + " private properties, " + roomTypes[1] + " apartments, " + roomTypes[2] + " shared.");
			}
		}
	}
}