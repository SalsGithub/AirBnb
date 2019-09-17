package application.componenthandlers;

import java.io.File; 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.RentalService;
import application.property.Property;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * The map screen handler.
 * Handles the components inside the map screen.
 * @author Sal Chousein, Mik Miller, Sam Paget, Mergana Sitepu
 */
public class MapScreenHandler {

	/**
	 * The house icon image.
	 */
	private final Image HOUSE_IMAGE_ICON = new Image(new File("./data/house.png").toURI().toString());
	
	/**
	 * The maximum size (in pixels) for a house icon.
	 */
	private final int MAX_HOUSE_ICON_SIZE = 15;

	/** 
	 * The image view for the map of london.
	 */
	@FXML
	private ImageView mapOfLondon;

	/**
	 * The image view for the house icon for hillingdon.
	 */
	@FXML
	private ImageView hillingdon;

	/**
	 * The image view for the house icon for harrow.
	 */
	@FXML
	private ImageView harrow; 

	/**
	 * The image view for the house icon for barnet.
	 */
	@FXML
	private ImageView barnet;

	/**
	 * The image view for the house icon for enfield.
	 */
	@FXML
	private ImageView enfield;

	/**
	 * The image view for the house icon for hounslow.
	 */
	@FXML
	private ImageView hounslow;

	/**
	 * The image view for the house icon for ealing.
	 */
	@FXML
	private ImageView ealing;

	/**
	 * The image view for the house icon for brent.
	 */
	@FXML
	private ImageView brent;

	/**
	 * The image view for the house icon for richmond.
	 */
	@FXML
	private ImageView richmondUponThames;

	/**
	 * The image view for the house icon for hammersmith and fulham.
	 */
	@FXML
	private ImageView hammersmithAndFulham; 

	/**
	 * The image view for the house icon for kensington and chelsea.
	 */
	@FXML
	private ImageView kensingtonAndChelsea;

	/**
	 * The image view for the house icon for westminster.
	 */
	@FXML
	private ImageView westminster;

	/**
	 * The image view for the house icon for camden.
	 */
	@FXML
	private ImageView camden;

	/**
	 * The image view for the house icon for islington.
	 */
	@FXML
	private ImageView islington;
	
	/**
	 * The image view for the house icon for the city of london.
	 */
	@FXML
	private ImageView cityOfLondon;

	/**
	 * The image view for the house icon for hackney.
	 */
	@FXML
	private ImageView hackney;

	/**
	 * The image view for the house icon for haringey.
	 */
	@FXML
	private ImageView haringey;

	/**
	 * The image view for the house icon for waltham forest.
	 */
	@FXML
	private ImageView walthamForest;

	/**
	 * The image view for the house icon for redbridge.
	 */
	@FXML
	private ImageView redbridge;

	/**
	 * The image view for the house icon for kingston.
	 */
	@FXML
	private ImageView kingstonUponThames;

	/**
	 * The image view for the house icon for wandsworth.
	 */
	@FXML
	private ImageView wandsworth;

	/**
	 * The image view for the house icon for lambeth.
	 */
	@FXML
	private ImageView lambeth;

	/**
	 * The image view for the house icon for southwark.
	 */
	@FXML
	private ImageView southwark;

	/**
	 * The image view for the house icon for howerHamlets.
	 */
	@FXML
	private ImageView towerHamlets;

	/**
	 * The image view for the house icon for newham.
	 */
	@FXML
	private ImageView newham;

	/**
	 * The image view for the house icon for barking and dagenham.
	 */
	@FXML
	private ImageView barkingAndDagenham;

	/**
	 * The image view for the house icon for havering.
	 */
	@FXML
	private ImageView havering;

	/**
	 * The image view for the house icon for merton.
	 */
	@FXML
	private ImageView merton;

	/**
	 * The image view for the house icon for sutton.
	 */
	@FXML
	private ImageView sutton;

	/**
	 * The image view for the house icon for croydon.
	 */
	@FXML
	private ImageView croydon;

	/**
	 * The image view for the house icon for bromley.
	 */
	@FXML
	private ImageView bromley;

	/**
	 * The image view for the house icon for lewisham.
	 */
	@FXML
	private ImageView lewisham;

	/**
	 * The image view for the house icon for greenwich.
	 */
	@FXML
	private ImageView greenwich;

	/**
	 * The image view for the house icon for bexley.
	 */
	@FXML
	private ImageView bexley;

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
	 * A map to store the borough name with its house icon.
	 * This is used to resize the house icons, display certain ones etc.
	 */
	private Map<String, ImageView> houseIcons;

	/**
	 * Initalises the map screen.
	 */
	@FXML
	private void initialize() {
		this.houseIcons = new HashMap<>();
		RentalService.getDataStore().prepareProperties();
		//Register the house icons. See @registerHouseIcon to see what the registration includes.
		registerHouseIcon(hillingdon, harrow, barnet, enfield, hounslow, ealing, brent, richmondUponThames, hammersmithAndFulham, kensingtonAndChelsea, 
				westminster, camden, islington, cityOfLondon, hackney, haringey, walthamForest, redbridge, kingstonUponThames, wandsworth, lambeth, 
				southwark, towerHamlets, newham, barkingAndDagenham, havering, merton, sutton, croydon, bromley, lewisham, greenwich, bexley);
		List<Property> visibleProperties = RentalService.getDataStore().getPropertiesWithinPriceRange();
		List<String> visibleBoroughs = new ArrayList<>(); //Store visible boroughs according to price range.
		visibleProperties.forEach(property -> { //Store the borough names for each visible property.
			if (!visibleBoroughs.contains(property.getFormattedBoroughName())) {
				visibleBoroughs.add(property.getFormattedBoroughName());
			}
		});
		for (String boroughs : visibleBoroughs) { //From each borough name that has a property, display the marker.
			ImageView imageView = houseIcons.get(boroughs);
			applySizeDistortion(imageView);
			if (imageView != null) {
				imageView.setImage(HOUSE_IMAGE_ICON);
			}
		}
	}

	/**
	 * The back button goes back to the welcome screen.
	 */
	@FXML
	private void back() {
		RentalService.MINIMUM_PRICE_SELECTED = -1; //Reset price range.
		RentalService.MAXIMUM_PRICE_SELECTED = -1;
		RentalService.viewWelcomeScreen();
	}

	/**
	 * The next button goes to the next page in the software.
	 */
	@FXML
	private void next() {
		RentalService.viewStatisticsScreen();
	}

	/**
	 * Views the borough stats.
	 */
	private void viewBoroughStatistics() {
		if (RentalService.SELECTED_BOROUGH != null) {
			RentalService.viewBoroughStatics();
		}
	}

	/**
	 * Registers an array of image views (each house icon is an image view).
	 * Each house icon will have a mouse event handler to handle clicking on the house which views the statistics in that borough.
	 * Each house icon will have the 'hand' mouse icon displayed when hovering.
	 * @param imageViews - the array of image views.
	 */
	private void registerHouseIcon(ImageView...imageViews) { 
		EventHandler<MouseEvent> event = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent)  {
				Object object = mouseEvent.getSource(); 
				if (object instanceof ImageView) {
					ImageView image = (ImageView) object;
					RentalService.SELECTED_BOROUGH = image.getId(); 
					viewBoroughStatistics();
				}
			}
		};
		for (ImageView imageView : imageViews) {
			imageView.setFitHeight(8);
			imageView.setFitWidth(8);
			houseIcons.put(imageView.getId(), imageView);
			imageView.setOnMouseClicked(event);
			imageView.setStyle("-fx-cursor: hand");
		}
	}

	/**
	 * Applies a size distortion to a given image view.
	 * @param imageView - the image view to apply the size distortion to.
	 */
	private void applySizeDistortion(ImageView imageView) {
		double percentageIncrease = 1.0;
		String borough = imageView.getId();
		int numberOfPropertiesWithinBorough = RentalService.getDataStore().getNumberOfProperties(borough);
		if (numberOfPropertiesWithinBorough >= 5 && numberOfPropertiesWithinBorough <= 500) {
			percentageIncrease *= 1.3;
		} else if (numberOfPropertiesWithinBorough > 500 && numberOfPropertiesWithinBorough <= 1000) {
			percentageIncrease *= 1.35;
		} else if (numberOfPropertiesWithinBorough > 1000 && numberOfPropertiesWithinBorough <= 2000) {
			percentageIncrease *= 1.65;
		} else if (numberOfPropertiesWithinBorough > 2000  && numberOfPropertiesWithinBorough <= 3000) {
			percentageIncrease *= 1.85;
		} else if (numberOfPropertiesWithinBorough > 3000) {
			percentageIncrease *= 2.0;
		}
		double newSize = imageView.getFitHeight() * percentageIncrease;
		if (newSize > MAX_HOUSE_ICON_SIZE) {
			newSize = MAX_HOUSE_ICON_SIZE;
		}
		imageView.setFitHeight(newSize);
		imageView.setFitWidth(newSize);
	}
}