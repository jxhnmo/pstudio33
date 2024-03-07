package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The Main class is the entry point of the JavaFX application.
 * It sets up the primary stage and provides methods for navigating between
 * scenes.
 */
public class Main extends Application {

    private static Stage primaryStage; // Keep a reference to the primary stage
    private static boolean isManager = false;

    /**
     * This method is the entry point of the JavaFX application.
     * It is called when the application is launched and sets up the primary stage.
     *
     * The primary stage of the JavaFX application.
     */
    @Override
    public void start(Stage primaryStage) {
        Main.primaryStage = primaryStage; // Store the primary stage as a static variable
        navigateTo("Login");
        primaryStage.show();
    }

    // Central method to switch scenes

    /*
     * Navigates to the specified FXML file and sets it as the scene for the primary
     * stage.
     *
     * @param fxml The name of the FXML file to navigate to (without the file
     * extension).
     */
    public static void navigateTo(String fxml) {
        try {
            Parent root = FXMLLoader.load(Main.class.getResource(fxml + ".fxml"));
            Scene scene = new Scene(root, 1200, 900); // Adjusted size of screen
            primaryStage.setScene(scene);
        } catch (Exception e) {

            e.printStackTrace();

            // Handle the exception (e.g., FXML file not found)
        }
    }

    /**
     * Navigates to the specified FXML view and initializes it based on the user's role.
     * This method dynamically loads the FXML file, retrieves its controller, and calls the initialization method with the user's manager status.
     *
     * @param fxml      The name of the FXML file to navigate to (without the ".fxml" extension).
     * @param isManager A boolean indicating whether the current user is a manager. True if the user is a manager, false otherwise.
     */
    public static void navigateToMenuWithRole(String fxml, boolean isManager) {
        try {
            // Load the FXML file and get the loader
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxml));
            Parent root = loader.load();

            // Get the controller and pass the role
            MenuController controller = loader.getController();
            controller.initializeMenu(isManager); // Ensure your MenuController has this method

            // Show the scene
            Scene scene = new Scene(root, 1200, 900); // Adjusted size of screen
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception (e.g., controller method not found, FXML file not found)
        }
    }

    /**
     * The main entry point for the application.
     * This method launches the JavaFX application.
     *
     * @param args Command-line arguments passed to the application. Not used in this application.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Sets the manager status for the current session.
     * This method should be called with the appropriate manager status when a user logs in.
     *
     * @param b The manager status to set. True if the user is a manager, false otherwise.
     */
    public static void setIsManager(boolean b) {
        isManager = b;
    }

    /**
     * Retrieves the manager status for the current session.
     * Use this method to check if the current user is recognized as a manager in the application.
     *
     * @return True if the current user is a manager, false otherwise.
     */
    public static boolean getIsManager() {
        return isManager;
    }
}
