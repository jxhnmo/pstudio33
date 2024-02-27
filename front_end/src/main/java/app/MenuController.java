package app;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.fxml.FXML;

public class MenuController {
    @FXML
    private Button btnSignOut; // Ensure this matches the fx:id in FXML

    @FXML
    private void handleSignOff(ActionEvent event) {
        System.out.println("Signed off");
        // Any additional sign-off logic can go here

        // Quit the JavaFX application
        Platform.exit();
    }

    public void goToMenu(ActionEvent event) {
        app.Main.navigateTo("Menu");
    }

    public void goToStatistics(ActionEvent event) {
        app.Main.navigateTo("Stats");
    }

    public void goToInventory(ActionEvent event) {
        app.Main.navigateTo("Inventory");
    }
}
