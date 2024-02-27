package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage primaryStage; // Keep a reference to the primary stage

    @Override
    public void start(Stage primaryStage) {
        Main.primaryStage = primaryStage; // Store the primary stage as a static variable
        navigateTo("Login");
        primaryStage.show();
    }

    // Central method to switch scenes
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

    // New method to navigate to Menu.fxml with role information
    public static void navigateToMenuWithRole(String fxml, boolean isManager) {
        try {
            // Load the FXML file and get the loader
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxml));
            Parent root = loader.load();

            // Get the controller and pass the role
            MenuController controller = loader.getController();
            controller.initializeRole(isManager); // Ensure your MenuController has this method

            // Show the scene
            Scene scene = new Scene(root, 1200, 900); // Adjusted size of screen
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception (e.g., controller method not found, FXML file not found)
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
