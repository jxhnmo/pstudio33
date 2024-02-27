package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * The Main class is the entry point of the JavaFX application.
 * It sets up the primary stage and provides methods for navigating between scenes.
 */
public class Main extends Application {

    private static Stage primaryStage; // Keep a reference to the primary stage

    /**
     * This method is the entry point of the JavaFX application.
     * It is called when the application is launched and sets up the primary stage.
     *
     * @param primaryStage The primary stage of the JavaFX application.
     */
    @Override
    public void start(Stage primaryStage) {
        Main.primaryStage = primaryStage; // Store the primary stage as a static variable
        navigateTo("Menu");
        primaryStage.show();
    }

    // Central method to switch scenes
         
    /* 
     * Navigates to the specified FXML file and sets it as the scene for the primary stage.
     *
     * @param fxml The name of the FXML file to navigate to (without the file extension).
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

    public static void main(String[] args) {
        launch(args);
    }
}
