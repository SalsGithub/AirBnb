package application.componenthandlers;

import application.RentalService;
import application.property.Property;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 * Handles the components in the single property screen.
 * @author Sal Chousein, Mik Miller, Sam Paget, Mergana Sitepu
 */
public class SinglePropertyScreenHandler {
	
	/**
	 * The label to display the neighbourhood.
	 */
	@FXML
	private Label neighbourhoodLabel;
	
	/**
	 * The label to display the host name.
	 */
	@FXML
	private Label hostNameLabel;
	
	/**
	 * The label for the property id.
	 */
	@FXML
	private Label propertyIdLabel;
	
	/**
	 * The text area to hold the information about the property.
	 */
	@FXML
	private TextArea textArea;

	/**
	 * The close button.
	 */
	@FXML
	private Button closeButton;
	
	@FXML
	private void initialize() {
		Property property = RentalService.SELECTED_PROPERTY;
		StringBuilder sb = new StringBuilder();
		boolean singleNight = property.getMinimumNights() == 1;
		boolean unavailable = property.getAvailability365() < 1;
		boolean noReviewsMonthly = property.getReviewsPerMonth() < 1;
		String prefixType = property.getRoomType().equalsIgnoreCase("private room") ? "a" : "an";
		neighbourhoodLabel.setText("Borough : " + property.getNeighbourhood());
		hostNameLabel.setText("Hosted by " + property.getHostName());
		propertyIdLabel.setText("Property id : " + property.getId() + ".");
		sb.append("Described as '" + property.getDescription() + "' by the owner. \n");
		sb.append("The property must be rented for a minimum of " + property.getMinimumNights() + " night" + (singleNight ? "" : "s") +  ". \n");
		sb.append("The property is " + prefixType + " " + property.getRoomType() + " ");
		sb.append("and is " + (unavailable ? "not " : "") + "available");
		sb.append(unavailable ? " during the year. \n" : " for " + property.getAvailability365() + " days during the year. \n");
		sb.append(property.isMissingReview() ? "No reviews have been left for this property." : "The last review for this property was on " + property.getLastReview() + ". \n");
		sb.append(noReviewsMonthly ? "The property has not received any reviews in the past month." : "The property received " + property.getReviewsPerMonth() + " reviews per month.");
		textArea.setText(sb.toString());
	}

	/**
	 * Handles closing the pop up.
	 */
	@FXML
	private void close() {
		RentalService.SELECTED_PROPERTY = null;
		textArea.getScene().getWindow().hide();
	}
}
