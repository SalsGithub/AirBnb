package application.componenthandlers;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import application.RentalService;
import application.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * A class to handle the components on the borough information screen.
 * @author Sal Chousein, Mik Miller, Sam Paget, Mergana Sitepu
 */
public class BoroughInfoScreenHandler {

	/**
	 * The values stored inside the drop-down combobox selectors.
	 */
	private ObservableList<String> sortOptions = FXCollections.observableArrayList("Number of reviews", "Price", "Host name");

	/**
	 * A tableview which holds property information.
	 */
	@FXML
	private TableView<Property> boroughInfo;

	/**
	 * The column for the host name.
	 */
	@FXML
	private TableColumn<Property, String> hostNameColumn;

	/**
	 * The column for the price.
	 */
	@FXML
	private TableColumn<Property, String> priceColumn;

	/**
	 * The column for the number of reviews.
	 */
	@FXML
	private TableColumn<Property, String> numberOfReviewsColumn;

	/**
	 * The column for the minimum nights you have to stay in the property.
	 */
	@FXML
	private TableColumn<Property, String> minimumNightsColumn;

	/**
	 * The label to display the number of properties found in the borough.
	 */
	@FXML
	private Label numberOfPropertiesLabel;

	/**
	 * The label to display the cheapest price of a property in the borough.
	 */
	@FXML
	private Label cheapestPriceLabel;

	/**
	 * The label to display the dearest price of a property in the borough.
	 */
	@FXML
	private Label dearestPriceLabel;

	/**
	 * The sort drop down menu.
	 */
	@FXML
	private ComboBox<String> sortOption;

	/**
	 * The close button.
	 */
	@FXML
	private Button closeButton;

	/**
	 * Handles initialising the table view.
	 */
	@FXML
	private void initialize() {
		ObservableList<Property> properties = getProperties();
		this.hostNameColumn.setCellValueFactory(new PropertyValueFactory<>("hostName"));
		this.priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		this.numberOfReviewsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfReviews"));
		this.minimumNightsColumn.setCellValueFactory(new PropertyValueFactory<>("minimumNights"));
		updateBoroughInfo(properties);
		String borough = properties.get(0).getNeighbourhood();
		this.numberOfPropertiesLabel.setText("There are " + boroughInfo.getItems().size() + " properties in " + borough + ".");
		int cheapestPrice = RentalService.getDataStore().getCheapestPropertyPrice(borough);
		int dearestPrice = RentalService.getDataStore().getDearestPropertyPrice(borough);
		this.cheapestPriceLabel.setText("Cheapest price : £" + cheapestPrice + ".");
		this.dearestPriceLabel.setText("Dearest price : £" + dearestPrice + ".");
		this.sortOption.setItems(sortOptions);
	}

	/**
	 * Creates an oberservable list of properties from the selected borough.
	 * @return - the observable list of properties.
	 */
	private ObservableList<Property> getProperties() {
		ObservableList<Property> observablePropertyList = FXCollections.observableArrayList();
		List<Property> properties = RentalService.getDataStore().getProperties(RentalService.SELECTED_BOROUGH);
		for (Property property : properties) {
			observablePropertyList.add(property);
		}
		return observablePropertyList;
	}

	/**
	 * Sorts the list in order of the option selected.
	 */
	@FXML
	private void sort() {
		if (sortOption.getValue() != null) {
			ObservableList<Property> sortedList = boroughInfo.getItems();
			switch (sortOption.getValue().replaceAll(" ", "").toLowerCase()) {
			case "numberofreviews":
				sortedList.sort(new Comparator<Property>() {
					@Override
					public int compare(Property one, Property two) {
						return one.getNumberOfReviews() - two.getNumberOfReviews();
					}
				});
				Collections.reverse(sortedList);
				updateBoroughInfo(sortedList);
				break;
			case "price":
				sortedList.sort(new Comparator<Property>() {
					@Override
					public int compare(Property one, Property two) {
						return one.getPrice() - two.getPrice();
					}
				});
				Collections.reverse(sortedList);
				updateBoroughInfo(sortedList);
				break;
			case "hostname":
				sortedList.sort(new Comparator<Property>() {
					@Override
					public int compare(Property one, Property two) {
						return one.getHostName().compareTo(two.getHostName());
					}
				});
				updateBoroughInfo(sortedList);
				break;
			}
		}
	}

	/**
	 * Closes the current window.
	 */
	@FXML
	private void close() {
		boroughInfo.getScene().getWindow().hide();
	}

	/**
	 * Updates the borough info table view to show the list of the properties.
	 * @param properties - the list of the properties.
	 */
	private final void updateBoroughInfo(ObservableList<Property> properties) {
		if (properties != null) {
			boroughInfo.setItems(properties);
		}
	}

	/**
	 * Handles clicking on a property inside the list of properties for that borough.
	 */
	@FXML
	private void onPropertySelected() {
		Property property = boroughInfo.getSelectionModel().getSelectedItem();
		if (property != null) {
			RentalService.SELECTED_PROPERTY = property;
			RentalService.viewSinglePropertyInformation();
		}
	}
}
