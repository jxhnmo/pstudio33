package app;

import java.sql.ResultSet;
import java.util.ArrayList;

import app.database.DbConnection;
import app.entity_classes.InventoryItems;
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
    private ArrayList<InventoryItems> inventoryItems = new ArrayList<InventoryItems>();
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
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
            }
        }
        );
    }
    
    /** 
     * @param event
     */
    @FXML
    public void handleCancelClicked(ActionEvent event) {
        closeWindow();
    }
    
    /** 
     * @param event
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
            
            // int menuId = ??? 
            //    MenuItems menuItem = new MenuItems(0, name, true, Double.parseDouble(priceField.getText()), category);
            //     public Ingredients(int ID, String itemName, int menuID, int num)
            //    Ingredients ingredient = new Ingredients( , name, )
            // dbConnection.runUpdate("INSERT INTO menu_items (id, name, available, price, category) VALUES ('" + + "', " + price + ", '" + category + "')"
            }   
            catch (Exception e) {
                e.printStackTrace();
            }
            closeWindow();
        }
    }

    public void loadCategories(ArrayList<String> categories) {
        ObservableList<String> observableList = FXCollections.observableArrayList(categories);
        categoryBox.setItems(observableList);
        categoryBox.setStyle("-fx-font-size: 18px;");
    }
    public void updateDatabase(ChoiceBox categoryBox, TextField name, TextArea ingredientsArea){

    }
    public void addToIngredients(){

        //TODO: INSERT the ingredients of the new menu item to the ingredients table
    }
    public void addToMenuItems(){
        //TODO: Add the menu item to menu_items table
    }

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
    private void addItemToTable(InventoryItems item)
    {
        tableView.getItems().add(item);
    }
    public void loadDatabase(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }
    private void closeWindow() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}
