package application;

import application.property.Property;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * The main class to run the RentalService application.
 * @author Sal Chousein, Mik Miller, Sam Paget, Mergana Sitepu
 */
public class RentalService extends Application {
  
	/** 
	 * The main stage.
	 * This is the stage for the main application.
	 */
	private static Stage mainStage = null;

	/**
	 * The popup stage. 
	 * This is the new stage that will be used for the popup window.
	 */
	private static Stage popUpStage = null;

	/**
	 * The current scene.
	 * This current scene is attached to the main stage, used to load new scenes onto the main stage.
	 */
	private static Scene currentScene = null;

	/**
	 * The data store handling all the properties within the software.
	 */
	private static DataStore dataStore;

	/**
	 * The minimum price selected by the user.
	 * Default value is -1.
	 */
	public static int MINIMUM_PRICE_SELECTED = -1;

	/**
	 * The maximum price selected by the user.
	 * Default value is -1.
	 */
	public static int MAXIMUM_PRICE_SELECTED = -1;

	/**
	 * The selected borough by the user from the map screen.
	 * Default value is null.
	 */
	public static String SELECTED_BOROUGH = null;

	/**
	 * The property selected by the user from a list of properties displayed in a table view.
	 */
	public static Property SELECTED_PROPERTY = null;

	/**
	 * The main method.
	 * @param args - the arguments.
	 */
	public static void main(String[] args) { 
		System.out.println("Launching rental service...");
		dataStore = new DataStore();
		launch(args);
	}

	/**
	 * Handle starting the application.
	 * @param primaryStage - the primary stage.
	 */
	@Override
	public void start(Stage primaryStage) {
		System.out.println("Starting the primary stage...");
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("WelcomeScreen.fxml"));
			Scene scene = new Scene(parent);
			primaryStage.setTitle("Rental Service AirBnB For London 2018");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();   
			mainStage = primaryStage;  
			currentScene = scene;
			addCSS(currentScene);
			System.out.println("Primary stage displayed.");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * View the welcome screen.
	 */
	public static void viewWelcomeScreen() {
		try {
			currentScene = new Scene(FXMLLoader.load(RentalService.class.getResource("WelcomeScreen.fxml")));
			mainStage.setScene(currentScene);
			mainStage.show();
			addCSS(currentScene);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Views the map screen.
	 */
	public static void viewMapScreen() {
		try {
			currentScene = new Scene(FXMLLoader.load(RentalService.class.getResource("MapScreen.fxml")));
			mainStage.setScene(currentScene);
			mainStage.show();
			addCSS(currentScene);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Views the borough statistics for a given borough.
	 */
	public static void viewBoroughStatics() {
		try {
			popUpStage = new Stage();
			popUpStage.initStyle(StageStyle.UTILITY);
			popUpStage.setResizable(false);
			popUpStage.setX(mainStage.getX() + 250);
			popUpStage.setY(mainStage.getY() - 50);
			popUpStage.setTitle(dataStore.getProperties(SELECTED_BOROUGH).get(0).getNeighbourhood());
			popUpStage.setScene(new Scene(FXMLLoader.load(RentalService.class.getResource("BoroughInfo.fxml"))));
			popUpStage.show();
			addCSS(popUpStage.getScene());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Views the statistics screen.
	 */
	public static void viewStatisticsScreen() {
		try {
			currentScene = new Scene(FXMLLoader.load(RentalService.class.getResource("StatisticsScreen.fxml")));
			mainStage.setScene(currentScene);
			mainStage.show();
			addCSS(currentScene);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Views the single property information screen.
	 */
	public static void viewSinglePropertyInformation() {
		try {
			popUpStage = new Stage();
			popUpStage.initStyle(StageStyle.UTILITY);
			popUpStage.setResizable(false);
			popUpStage.setX(mainStage.getX() - 250);
			popUpStage.setY(mainStage.getY() - 50);
			popUpStage.setTitle("Property Description");
			popUpStage.setScene(new Scene(FXMLLoader.load(RentalService.class.getResource("SinglePropertyScreen.fxml"))));
			popUpStage.show();
			addCSS(popUpStage.getScene());
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	 
	/**
	 * Views the property finder screen.
	 */
	public static void viewPropertyFinderScreen() {
		try {
			currentScene = new Scene(FXMLLoader.load(RentalService.class.getResource("PropertyFinderScreen.fxml")));
			mainStage.setScene(currentScene);
			mainStage.show();
			addCSS(currentScene);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 
	 * Updates the CSS file for a given scene.
	 * All scenes will most probably share the same CSS file since there is a generalised theme for the entire application.
	 * As long as the current scene is not nulled, the CSS file will be added to the current scene.
	 */
	private static void addCSS(Scene scene) {
		if (scene != null) {
			scene.getStylesheets().add(RentalService.class.getResource("Style.css").toExternalForm());
		}
	}

	/**
	 * Displays a pop-up menu to display an error.
	 * @param title - The title of the error.
	 * @param errorMessage - The error message. 
	 * @param fixMethod - A method to fix this error.
	 */
	public static void displayError(String title, String errorMessage, String fixMethod) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(errorMessage);
		if (fixMethod != null) {
			alert.setContentText("A possible fix has been found.\n" + fixMethod);
		} else {
			alert.setContentText("A possible fix was not found.");
		}
		alert.showAndWait();
	}

	/**
	 * Displays some information on an alert screen.
	 * @param title - the title of the information.
	 * @param information - the information to display.
	 */
	public static void displayInformation(String title, String information) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(information);
		alert.showAndWait();
	}

	/**
	 * Get the data store for this rental service.
	 * @return - the data store.
	 */
	public static DataStore getDataStore() {
		return dataStore;
	}
} 
