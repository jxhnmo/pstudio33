package app;

import java.util.ArrayList;

import app.database.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.ChoiceBox;

public class MenuPopupController {
    @FXML
    TextField newItemName;
    @FXML
    ChoiceBox<String> categoryBox;

    ArrayList<String> ingredients;



    private DbConnection dbConnection;
    
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
        if (category == null) {
        }
        else {
            try {
                // TODO: Send a message to the database adding a menu item with newItemName.toString() for the name,
                // category for the category, and default values for everything else (Except increment id by 1 of course).
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
        categoryBox.setStyle("-fx-font-size: 32px;");
    }
    public void addToIngredients(){
        //TODO: INSERT the ingredients of the new menu item to the ingredients table
    }
    public void addToMenuItems(){
        //TODO: Add the menu item to menu_items table
    }

    public void loadIngredients(){
        ingredients = dbConnection.getResultArray(dbConnection.runStatement("SELECT * FROM inventory_items;"), "item_name");
    }
    public void loadDatabase(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }


    private void closeWindow() {
        Stage stage = (Stage) newItemName.getScene().getWindow();
        stage.close();
    }
}
