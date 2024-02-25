package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.fxml.FXML;

public class InventoryController {
	@FXML
	private Button btnSignOut; // Ensure this matches the fx:id in FXML

    @FXML
    private void handleSignOff(ActionEvent event) {
        System.out.println("Signed off");
        // Any additional sign-off logic can go here
    }

    public void goToMenu(ActionEvent event) {
        application.Main.navigateTo("Menu.fxml");
    }

    public void goToStatistics(ActionEvent event) {
    	application.Main.navigateTo("Stats.fxml");
    }

    public void goToInventory(ActionEvent event) {
    	application.Main.navigateTo("Inventory.fxml");
    }
}
