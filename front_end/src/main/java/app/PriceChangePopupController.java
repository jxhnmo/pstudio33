package app;

import app.database.DbConnection;
import app.entity_classes.MenuItems;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller class for the price change popup in a JavaFX application.
 * <p>
 * This class is responsible for handling the price change functionality, allowing the user
 * to update the price of a menu item. It includes methods to initialize the popup, handle
 * confirm and cancel actions, and update the item's price in the database.
 */
public class PriceChangePopupController {
    @FXML
    TextField oldPriceField;
    @FXML
    Spinner<Double> newPriceSpinner;

    private DbConnection dbConnection;
    private MenuItems item;

    /**
     * Initializes the controller class. Sets up the spinner control and prepares it for user interaction.
     */
    @FXML
    private void initialize() {
        TextField spinnerEditor = newPriceSpinner.getEditor();
        spinnerEditor.setStyle("-fx-font-size: 20;");
        // Initialized more in the loadDatabaseAndMenuItems function
    }

    /**
     * Handles the confirmation action when the "Confirm" button is clicked.
     * Updates the item's price if the new value is valid and different from the old price.
     *
     * @param event The action event that triggered the method.
     */
    @FXML
    public void handleConfirmClicked(ActionEvent event) {
        Double newValue = newPriceSpinner.getValue();
        if (newValue != null && newValue > 0.0 && newValue != item.getPrice()) {
            Double newPrice = newPriceSpinner.getValue();
            updatePriceInDatabase(newPrice);
            closeWindow();
        }
        else {
            // Nothing to do, unless we want to add an error message
        }
    }

    /**
     * Loads the database connection and the menu item to be updated.
     * Sets up the old price field and the new price spinner with the current price of the item.
     *
     * @param db   The database connection object.
     * @param item The menu item whose price is to be changed.
     */
    public void loadDatabaseAndMenuItems(DbConnection db, MenuItems item) {
        this.dbConnection = db;
        this.item = item;
        oldPriceField.setText(String.valueOf(item.getPrice()));

        SpinnerValueFactory<Double> valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(
            0.00, // minimum value of Spinner
            Double.MAX_VALUE, // max value of Spinner
            this.item.getPrice(), // initial value of Spinner
            0.01 // how much Spinner increments/decrements
        );
        newPriceSpinner.setValueFactory(valueFactory);
    }

    /**
     * Handles the cancellation action when the "Cancel" button is clicked.
     * Closes the popup window without making any changes.
     *
     * @param event The action event that triggered the method.
     */
    @FXML
    public void handleCancelClicked(ActionEvent event) {
        closeWindow();
    }

    /**
     * Closes the popup window. This method is used after the confirm or cancel action is completed.
     */
    private void closeWindow() {
        Stage stage = (Stage) oldPriceField.getScene().getWindow();
        stage.close();
    }

    /**
     * Updates the price of the menu item in the database and the local MenuItems object.
     *
     * @param newPrice The new price to be set for the menu item.
     */
    private void updatePriceInDatabase(Double newPrice) {
        String query = "UPDATE menu_items\n" +
                        "SET price = " + newPrice.toString() + "\n" + 
                        "WHERE name = '" + item.getName() + "';\n";
        dbConnection.runUpdate(query);
        item.setPrice(newPrice);
    }
}
