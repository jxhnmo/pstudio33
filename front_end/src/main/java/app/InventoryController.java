package app;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import app.entity_classes.InventoryItems;
import app.database.*;

public class InventoryController {
    @FXML
    private Button btnSignOut;

    @FXML
    private TableView<InventoryItems> tableView;

    @FXML // columns 1  (item), 2 (stock), and 4 (price)
    private TableColumn<InventoryItems, String> c1, c2, c4;

    @FXML // column 3 is the order column
    private TableColumn<InventoryItems, Number> c3;

    private DbConnection dbConnection;
    
    @FXML
    private void initialize() {    
        c3.setCellFactory(column -> new OrderCell());
        addItemToTable(new InventoryItems(0, "Default Item", 0, 0.0));
    }

    @FXML
    private void handleSignOff(ActionEvent event) {
        System.out.println("Signed off");
        // Any additional sign-off logic can go here

        // Signs out to login page
        app.Main.navigateTo("Login");
    }

    @FXML
    public void goToMenu(ActionEvent event) {
        app.Main.navigateTo("Menu");
    }

    @FXML
    public void goToStatistics(ActionEvent event) {
        app.Main.navigateTo("Stats");
    }

    @FXML
    public void goToInventory(ActionEvent event) {
        app.Main.navigateTo("Inventory");
    }

    
    /** 
     * @param item - the item whose information is added to the TableView
     */
    private void addItemToTable(InventoryItems item)
    {
        tableView.getItems().add(item);

        c1.setCellValueFactory(data -> new SimpleStringProperty(item.getItemName()));
        c2.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(item.getStock())));
        c4.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(item.getPrice())));
    }
}
