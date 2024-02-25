package application;

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
        navigateTo("Menu.fxml");
        primaryStage.show();
    }

    // Central method to switch scenes
    public static void navigateTo(String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(Main.class.getResource("/view/" + fxmlFile));
            Scene scene = new Scene(root, 800, 600); // Adjusted size of screen
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
