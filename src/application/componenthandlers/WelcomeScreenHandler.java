package application.componenthandlers;

import application.RentalService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

/**
 * Handles the components on the welcome screen.
 * @author Sal Chousein, Mik Miller, Sam Paget, Mergana Sitepu
 */
public class WelcomeScreenHandler {

	/**
	 * The values stored inside the drop-down combobox selectors.
	 */
	private ObservableList<String> values = FXCollections.observableArrayList("£5", "£50","£100", "£200", "£500", "£1000", "£2000", "£3000", "£5000", 
			"£7000");
	
	/**
	 * The 'from' drop-down selector.
	 */
	@FXML 
	private ComboBox<String> minimumPriceSelector;

	/**
	 * The 'to' drop-down selector.
	 */
	@FXML
	private ComboBox<String> maximumPriceSelector;

	/** 
	 * The back button.
	 */
	@FXML
	private Button backButton;

	/**
	 * The next button.
	 */
	@FXML
	private Button nextButton;

	/**
	 * Initialise method.
	 * Runs upon starting to initialise a few components.
	 */
	@FXML
	private void initialize() { 
		minimumPriceSelector.setItems(values);
		maximumPriceSelector.setItems(values);
		nextButton.setDisable(true);
		backButton.setDisable(true);
	}

	/**
	 * Handle selecting the minimum price range.
	 */
	@FXML
	private void selectMinimumPrice() {
		RentalService.MINIMUM_PRICE_SELECTED = Integer.parseInt(minimumPriceSelector.getValue().substring(1));
		if (RentalService.MINIMUM_PRICE_SELECTED != -1 && RentalService.MAXIMUM_PRICE_SELECTED != -1) {
			nextButton.setDisable(false);
			backButton.setDisable(false);
		}
	}

	/**
	 * Handle selecting the maximum price range.
	 */
	@FXML
	private void selectMaximumPrice() {
		RentalService.MAXIMUM_PRICE_SELECTED = Integer.parseInt(maximumPriceSelector.getValue().substring(1));
		if (RentalService.MINIMUM_PRICE_SELECTED != -1 && RentalService.MAXIMUM_PRICE_SELECTED != -1) {
			nextButton.setDisable(false);
			backButton.setDisable(false);
		}
	}

	/**
	 * Handle the back button.
	 * Since this is the welcome screen, the back button does not lead anywhere, so display a message showing so.
	 */
	@FXML
	private void back() {
		RentalService.displayInformation("No further action", "This is the first screen, there are no previous screens.");
	}

	/**
	 * Handle then next page button.
	 * If a valid price range was selected, view the next screen which is the map screen.
	 * Otherwise display an error message.
	 */
	@FXML
	private void next() {
		if (!isValidPriceRange(RentalService.MINIMUM_PRICE_SELECTED, RentalService.MAXIMUM_PRICE_SELECTED)) {
			RentalService.displayError("Invalid price range", "Price range selected is invalid.",
					"The 'From' price must be smaller than or equal to the 'To' price.");
		} else {
			RentalService.viewMapScreen();
		}
	}

	/**
	 * Checks if a given minimum and maximum value is a valid price range.
	 * @param minimum - the minimum value.
	 * @param maximum - the maximum value.
	 * @return true if the minimum value is not the same as the maximum value and is smaller than the maximum, false otherwise.
	 */
	private boolean isValidPriceRange(int minimum, int maximum) {
		return (minimum <= maximum) && (minimum > 0 && maximum > 0);
	}
}
