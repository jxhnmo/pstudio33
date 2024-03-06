package app;

import java.sql.ResultSet;
import java.util.ArrayList;

import app.database.DbConnection;
import app.entity_classes.Ingredients;
import app.entity_classes.InventoryItems;
import app.entity_classes.MenuItems;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
/**
 * Controller class for the menu item popup in the application.
 * This class is responsible for handling all interactions within the popup window,
 * including initializing the window components, handling button clicks, and managing the menu item's ingredients.
 */
public class MenuPopupController {
    @FXML
    TextField nameField;
    @FXML
    ChoiceBox<String> categoryBox;
    @FXML 
    TextField priceField; 
    @FXML 
    TextArea ingredientsArea;
    @FXML
    private TableView<InventoryItems> tableView;
    @FXML 
    private TableColumn<InventoryItems, String> tableColumn;

    private DbConnection dbConnection;

    private String ingredientsAdded = "";
    private ArrayList<String> inventoryIds = new ArrayList<String>();
    private ArrayList<InventoryItems> inventoryItems = new ArrayList<InventoryItems>();
    /**
     * Initializes the controller class. This method is automatically called after the FXML file has been loaded.
     * It sets up the table view with database data and event listeners for the UI components.
     */
    @FXML
    private void initialize() {
        dbConnection = new DbConnection();
        populateTableFromDatabase();
        tableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItemName()));
        tableView.setOnMouseClicked(event ->{
            if (tableView.getSelectionModel().getSelectedItem() != null) {
                InventoryItems selectedItem = tableView.getSelectionModel().getSelectedItem();
                ingredientsAdded += selectedItem.getItemName() + "\n";
                ingredientsArea.setText(ingredientsAdded);
                inventoryItems.add(selectedItem);
                inventoryIds.add(Integer.toString(selectedItem.getID()));
            }
        }
        );
    }
    /**
     * Handles the action when the cancel button is clicked.
     * Closes the current window.
     *
     * @param event The action event triggered by clicking the button.
     */
    @FXML
    public void handleCancelClicked(ActionEvent event) {
        closeWindow();
    }
    
    
    /**
     * Handles the action when the confirm button is clicked.
     * Validates the input fields and adds a new menu item to the database along with its ingredients if valid.
     *
     * @param event The action event triggered by clicking the button.
     */
    @FXML
    public void handleConfirmClicked(ActionEvent event) {
        String category = categoryBox.getValue();
        String name = nameField.getText();
        String price = priceField.getText();
        if (category == null || name == null || price == null) {
            // invalid request
        }
        else {
            try {
            int menuId = dbConnection.getNextAvailableId("menu_items");
            int ingredientId = dbConnection.getNextAvailableId("ingredients");
            MenuItems menuItem = new MenuItems(menuId, name, true, Double.parseDouble(priceField.getText()), category);
            dbConnection.runUpdate("INSERT INTO menu_items (id, name, available, price, category) VALUES ('" + Integer.toString(menuId) + "', '" + name + "', '1', '" + price + "', '" + category + "')");
            for (String inventoryId: inventoryIds) {
                dbConnection.runUpdate("INSERT INTO ingredients (id, item_id, menu_id, num) VALUES ('" + Integer.toString(ingredientId) + "', '" + inventoryId+ "', '" + Integer.toString(menuId) + "', '1')");
                ingredientId++;
            }
            }   
            catch (Exception e) {
                e.printStackTrace();
            }
            closeWindow();
        }
    }
    /**
     * Populates the category choice box with available categories.
     *
     * @param categories A list of categories to populate the choice box.
     */

    public void loadCategories(ArrayList<String> categories) {
        ObservableList<String> observableList = FXCollections.observableArrayList(categories);
        categoryBox.setItems(observableList);
        categoryBox.setStyle("-fx-font-size: 18px;");
    }
    
    /**
     * Populates the table view with inventory items from the database.
     * This method queries the database for inventory items and adds them to the table view.
     */
    private void populateTableFromDatabase() {
        String query = "SELECT * FROM inventory_items";
        ResultSet result = dbConnection.runStatement(query);
        try {
            while (result.next()){
                InventoryItems item = new InventoryItems(
                    result.getInt("id"),
                    result.getString("item_name"),
                    result.getInt("stock"),
                    result.getDouble("price")
                );
                addItemToTable(item);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while populating table from database.");
        }
    }
    /**
     * Adds an inventory item to the table view.
     *
     * @param item The inventory item to be added to the table.
     */
    private void addItemToTable(InventoryItems item)
    {
        tableView.getItems().add(item);
    }
    /**
     * Loads the database connection into this controller.
     *
     * @param dbConnection The database connection to be used by this controller.
     */

    public void loadDatabase(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }
    /**
     * Closes the current window.
     * This method retrieves the current stage from the name field's scene and closes it.
     */
    private void closeWindow() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}
