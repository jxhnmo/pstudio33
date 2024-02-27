package app;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.fxml.FXML;

public class MenuController {
    @FXML
    private Button btnSignOut;
    @FXML
    private Button btnMenu;
    @FXML
    private Button btnStatistics;
    @FXML
    private Button btnInventory;

    // Method to initialize the role
    public void initializeRole(boolean isManager) {
        if (!isManager) {
            btnMenu.setDisable(true); // Disable for employees
            btnStatistics.setDisable(true); // Disable for employees
            btnInventory.setDisable(true); // Disable for employees
            // Disable any other manager-specific buttons
        }
        // If isManager is true, no need to disable buttons, assuming all buttons are enabled by default
    }

    @FXML
    private void handleSignOff(ActionEvent event) {
        System.out.println("Signed off");
        // Any additional sign-off logic can go here

        // Signs out to login page
        app.Main.navigateTo("Login");
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
