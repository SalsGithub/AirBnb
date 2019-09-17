package application.componenthandlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.RentalService;
import application.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * The componenet handler for the property finder screen.
 * @author Sal Chousein, Mik Miller, Sam Paget, Mergana Sitepu
 */
public class PropertyFinderComponentHandler {

	/**
	 * The values stored inside the occasion selection drop down menu.
	 */
	private ObservableList<String> occasionValues = FXCollections.observableArrayList("Birthday", "Family Event", "Other");

	/**
	 * The values stored inside the sort selection drop down menu.
	 */
	private ObservableList<String> sortSelectionValues = FXCollections.observableArrayList("Price", "Minimum Required Stay");

	/**
	 * The drop down menu to select the occasion.
	 */
	@FXML
	private ComboBox<String> occasionSelection;

	/**
	 * The spinner to select the number of people that would be staying in the property.
	 */
	@FXML
	private Spinner<Integer> numberOfPeopleSpinner;

	/**
	 * The spinner to select the minimum nights they will be willing to stay.
	 */
	@FXML
	private Spinner<Integer> minimumNightsStaySpinner;

	/**
	 * The check box to refine the search to include properties near a tube.
	 */
	@FXML
	private CheckBox nearTube;

	/**
	 * The check box to refine the search to include properties with a no smoking rule.
	 */
	@FXML
	private CheckBox noSmoking;

	/**
	 * The check box to refine the search to include properties that allow pets.
	 */
	@FXML
	private CheckBox petsAllowed;

	/**
	 * The check box to refine the search to include properties with a balcony.
	 */
	@FXML
	private CheckBox balcony;

	/**
	 * The check box to refine the search to include properties with a garden.
	 */
	@FXML
	private CheckBox garden;

	/**
	 * The check box to refine the search to include properties with city views.
	 */
	@FXML
	private CheckBox niceViews;

	/**
	 * The text area for phrases.
	 */
	@FXML
	private TextArea phrases;

	/**
	 * The label to display the characters left for the phrases they can input.
	 */
	@FXML
	private Label charactersLeftLabel;

	/**
	 * The back button to go back one page in the software.
	 */
	@FXML
	private Button backButton;

	/**
	 * The search button to find the results for the search and display it in the table view.
	 */
	@FXML
	private Button searchButton;

	/**
	 * The label to tell the user aboout highlighted properties.
	 */
	@FXML
	private Label highlightLabel;

	/**
	 * The table view for the results.
	 */
	@FXML
	private TableView<Property> results;

	/**
	 * The column for the price.
	 */
	@FXML
	private TableColumn<Property, String> priceColumn;

	/**
	 * The column for the borough name.
	 */
	@FXML
	private TableColumn<Property, String> boroughColumn;

	/**
	 * The column for the description of a property.
	 */
	@FXML
	private TableColumn<Property, String> descriptionColumn;

	/**
	 * The sort label.
	 */
	@FXML
	private Label sortLabel;

	/**
	 * The drop down menu to select an option to sort the results by.
	 */
	@FXML
	private ComboBox<String> sortSelection;

	/**
	 * The label to tell the user that they can click on a property to view more details about it.
	 */
	@FXML
	private Label listingLabel;

	/**
	 * The label to state some results in words.
	 */
	@FXML
	private Label resultsLabel;

	/**
	 * The final list of phrases to look for in the property description.
	 */
	private List<String> finalPhrases;

	/**
	 * The character limit for the phrases box.
	 */
	private final int CHARACTER_LIMIT = 150;

	/**
	 * A map of a checkbox to a string array of the phrases to look for if that check box is selected.
	 * The key : CheckBox.
	 * The value : String[].
	 */
	private Map<CheckBox, String[]> checkBoxMap;

	/**
	 * Initialise the property finder.
	 */
	@FXML
	private void initialize() {
		this.finalPhrases = new ArrayList<>();
		this.checkBoxMap = new HashMap<>();
		populateCheckBoxMap(); //Populate the default values in the hash map.
		this.occasionSelection.setItems(occasionValues);
		this.sortSelection.setItems(sortSelectionValues);
		this.priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		this.boroughColumn.setCellValueFactory(new PropertyValueFactory<>("neighbourhood"));
		this.descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description")); 
		this.phrases.setTextFormatter(new TextFormatter<String>(text -> text.getControlNewText().length() <= 150 ? text : null));
		Label promptText = new Label("Your results will appear here once the form is filled and the search button is pressed.");
		promptText.setStyle("-fx-font-size: 11px");
		this.results.setPlaceholder(promptText);
		this.results.setDisable(true);
		this.sortSelection.setDisable(true);
		this.sortLabel.setDisable(true);
		this.listingLabel.setDisable(true);
		this.highlightLabel.setDisable(true);
	}

	/**
	 * Handles when a phrase is typed into the text area.
	 * This is limited to 150 characters and will display the remaining characters as well for the user.
	 */
	@FXML
	private void onPhraseType() {
		int remainingCharacterLimit = 150;
		if (phrases.getText() != null && !phrases.getText().isEmpty()) {
			remainingCharacterLimit = CHARACTER_LIMIT - phrases.getText().length();
			if (remainingCharacterLimit < 1) {
				remainingCharacterLimit = 0;
			}
		} else {
			remainingCharacterLimit = CHARACTER_LIMIT;
		}
		this.charactersLeftLabel.setText("Characters left : " + remainingCharacterLimit);
	}

	/**
	 * The back button to go one page back in the software.
	 */
	@FXML
	private void back() {
		RentalService.viewStatisticsScreen();
	}

	/**
	 * Handles when a user clicks on the search button.
	 * If required fields are missing, an error message will be popped up to the user.
	 */
	@FXML
	private void search() {
		this.finalPhrases.clear(); //Clear the final phrases, will populate depending on the input by the user which can change every search.
		int minimumNightsStaying = minimumNightsStaySpinner.getValue().intValue();
		boolean displayError = occasionSelection.getValue() == null || minimumNightsStaying == 0;
		if (displayError) {
			RentalService.displayError("Required fields missing", 
					"Some or all of the required fields are missing. A red asterisk will denote a compulsory field.", 
					"Ensure fields with a red asterisk are filled out.");
			return;
		}
		String occasion = occasionSelection.getValue();
		//Loops through each check box.
		for (Map.Entry<CheckBox, String[]> entrySet : checkBoxMap.entrySet()) {
			CheckBox checkBox = entrySet.getKey();
			String[] phrases = entrySet.getValue();
			if (checkBox.isSelected()) { //If the check box is selected, add the phrases associated with this check box.
				for (String s : phrases) {
					if (!finalPhrases.contains(s)) { //Making sure its not in the list already, no need to duplicate the phrases.
						finalPhrases.add(s);
					}
				} 
			} else {
				for (String s : phrases) { //If the check box is not selected, remove the phrases.
					finalPhrases.remove(s);
				}
			}
		}
		if (phrases.getText() != null && !phrases.getText().isEmpty()) {
			String[] userPhrases = phrases.getText().split(",");
			if (userPhrases == null) {
				RentalService.displayError("Missing comma", "When adding phrases of your choice, ensure to seperate each phrase with a comma.", 
						"Add commas between phrases e.g tube, balcony.");
				return;
			} else {
				for (String s : userPhrases) {
					s = s.toLowerCase().trim();
					if (!finalPhrases.contains(s)) {
						finalPhrases.add(s);
					}
				}
			}
		}
		List<Property> propertiesWithinPriceRange = RentalService.getDataStore().getPropertiesWithinPriceRange();
		List<Property> filteredList = new ArrayList<>();
		for (Property property : propertiesWithinPriceRange) {
			if (property.getMinimumNights() >= minimumNightsStaying) { //This has to match before anything else can.
				if (!finalPhrases.isEmpty()) {
					for (String phrase : finalPhrases) {
						if (phrase != null) {
							if (property.getDescription().toLowerCase().contains(phrase.toLowerCase())) {
								filteredList.add(property);
							}
						}
					}
				} else {
					filteredList.add(property);
				}
			}
		}
		//If the filtered list is empty.
		if (filteredList.isEmpty()) {
			RentalService.displayError("No properties found", 
					"There were no properties found according to your liking.", 
					"Reconsider your preferences and try again.\n If there are still no properties found, you may need to change your price range.");
			return;
		}
		ObservableList<Property> resultantList = FXCollections.observableArrayList();
		for (Property property : filteredList) {
			resultantList.add(property);
		}
		//Enable the components to view the properties.
		this.highlightLabel.setDisable(false);
		this.results.setDisable(false);
		this.results.setItems(resultantList);
		this.results.refresh(); //Refresh the results table.
		//Set the row factory to highlight specific properties based on the occasion.
		this.results.setRowFactory(rowFactory -> new TableRow<Property>(){
			@Override
			public void updateItem(Property property, boolean empty) {
				super.updateItem(property, empty) ;
				String pinkHighlight = "-fx-background-color: #fc90f9;";
				String orangeHighlight = "-fx-background-color: #ffa500;";
				if (property != null) {
					switch(occasion.toLowerCase()) {
					case "birthday":
						if (property.getRoomType().equalsIgnoreCase("entire home/apt")) {
							setStyle(orangeHighlight);
							super.updateItem(property, empty);
						} else {
							setStyle("");
						}
						break;
					case "family event":
						if (property.getRoomType().equalsIgnoreCase("private room")) {
							setStyle(pinkHighlight);
							super.updateItem(property, empty);
						} else {
							setStyle("");
						}
						break;
					}
				} else {
					setStyle("");
				}
			}
		}); 
		this.sortSelection.setDisable(false);
		this.sortLabel.setDisable(false);
		this.listingLabel.setDisable(false);
		int totalProperties = resultantList.size();
		int cheapestPrice = 8000;
		int dearestPrice = 0;
		for (Property property : filteredList) { //Calculating cheapest and dearest price.
			if (property.getPrice() < cheapestPrice) {
				cheapestPrice = property.getPrice();
			} 
			if (property.getPrice() > dearestPrice) {
				dearestPrice = property.getPrice();
			}
		}
		StringBuilder sb = new StringBuilder();
		sb.append("There are " + totalProperties + " properties found according to your preferences. ");
		sb.append("The cheapest price found was £" + cheapestPrice + ", the dearest price found is £" + dearestPrice + ".");
		this.resultsLabel.setText(sb.toString());
	} 

	/**
	 * Handles clicking on a property inside the list of properties for that borough.
	 */
	@FXML
	private void onPropertySelected() {
		Property property = results.getSelectionModel().getSelectedItem();
		if (property != null) {
			RentalService.SELECTED_PROPERTY = property;
			RentalService.viewSinglePropertyInformation();
		}
	}

	/**
	 * Handles sorting the results based on a given sort selection.
	 */
	@FXML
	private void sort() {
		if (sortSelection.getValue() != null) {
			ObservableList<Property> sortedList = results.getItems();
			switch(sortSelection.getValue().replace(" ", "").toLowerCase()) {
			case "price":
				sortedList.sort(new Comparator<Property>() {
					@Override
					public int compare(Property one, Property two) {
						return one.getPrice() - two.getPrice();
					}
				});
				Collections.reverse(sortedList);
				results.setItems(sortedList);
				break;
			case "minimumrequiredstay":
				sortedList.sort(new Comparator<Property>() {
					@Override
					public int compare(Property one, Property two) {
						return one.getMinimumNights() - two.getMinimumNights();
					}
				});
				results.setItems(sortedList);
				break;
			}
		}
	}

	/**
	 * Populate the check box map with the default values.
	 */
	private void populateCheckBoxMap() {
		String[] tubePhrases = new String[] {"tube", "tube station", "train"};
		String[] noSmokingPhrases = new String[]{"no smoking", "non-smoking", "non smoking"};
		String[] petsAllowedPhrases = new String[]{"pets ok", "pets welcome", "pets allowed", "pets considered"};
		String[] balconyPhrases = new String[]{"with balcony", "balcony"};
		String[] gardenPhrases = new String[]{"garden", "with garden"};
		String[] niceViewsPhrases = new String[]{"city views", "views", "london view", "view"};
		checkBoxMap.put(nearTube, tubePhrases);
		checkBoxMap.put(noSmoking, noSmokingPhrases);
		checkBoxMap.put(petsAllowed, petsAllowedPhrases);
		checkBoxMap.put(balcony, balconyPhrases);
		checkBoxMap.put(garden, gardenPhrases);
		checkBoxMap.put(niceViews, niceViewsPhrases);
	}
}
