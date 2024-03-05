package app;

import app.database.DbConnection;
import app.entity_classes.MenuItems;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PriceChangePopupController {
    @FXML
    TextField oldPriceField;
    @FXML
    Spinner<Double> newPriceSpinner;

    private DbConnection dbConnection;
    private MenuItems item;

    @FXML
    private void initialize() {
        TextField spinnerEditor = newPriceSpinner.getEditor();
        spinnerEditor.setStyle("-fx-font-size: 20;");
        // Initialized more in the loadDatabaseAndMenuItems function
    }

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

    @FXML
    public void handleCancelClicked(ActionEvent event) {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) oldPriceField.getScene().getWindow();
        stage.close();
    }

    private void updatePriceInDatabase(Double newPrice) {
        String query = "UPDATE menu_items\n" +
                        "SET price = " + newPrice.toString() + "\n" + 
                        "WHERE name = '" + item.getName() + "';\n";
        dbConnection.runUpdate(query);
        item.setPrice(newPrice);
    }
}
