package app;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;

import java.sql.ResultSet;
import app.entity_classes.InventoryItems;
import app.entity_classes.MenuItems;
import app.entity_classes.SalesItems;
import app.database.*;

public class InventoryController {
    @FXML
    private Button btnSignOut;

    @FXML
    private TableView<InventoryItems> tableView;

    @FXML // columns 1 (item), 2 (stock), and 4 (price)
    private TableColumn<InventoryItems, String> c1, c2, c4;

    @FXML // column 3 is the order column
    private TableColumn<InventoryItems, Number> c3;

    @FXML
    private TextArea orderListTextArea;

    @FXML
    private Label price;

    private DbConnection dbConnection;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded. It sets up the table columns and
     * initializes the connection to the database.
     */
    @FXML
    private void initialize() {
        dbConnection = new DbConnection();
        populateTableFromDatabase();

        c1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItemName()));
        c2.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getStock())));
        c4.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrice())));

        c3.setCellFactory(column -> new OrderCell());

        tableView.setRowFactory(tv -> {
            TableRow<InventoryItems> row = new TableRow<>();
            row.setOnMouseEntered(event -> {
                updateOrderList();
            });
            row.setOnMouseClicked(event -> {
                updateOrderList();
            });
            row.setOnMouseExited(event -> {
                updateOrderList();
            });

            row.setOnMouseMoved(event -> {
                updateOrderList();
            });

            return row;
        });
    }

    /**
     * Updates the order list text area with the current orders from the table view.
     */
    public void updateOrderList() {
        StringBuilder orderList = new StringBuilder();
        for (InventoryItems item : tableView.getItems()) {
            if (item.getOrder() > 0) {
                orderList.append(item.getOrder()).append(" ").append(item.getItemName()).append("s\n");
            }
        }
        orderListTextArea.setText(orderList.toString());
        Double totalCost = 0.0;
        for (InventoryItems item : tableView.getItems()) {
            totalCost += item.getPrice();
        }
     
        price.setText(String.valueOf(totalCost));
    }

    /**
     * Populates the table view with inventory items retrieved from the database.
     */
    private void populateTableFromDatabase() {
        String query = "SELECT * FROM inventory_items ORDER BY item_name ASC";
        ResultSet result = dbConnection.runStatement(query);
        try {
            while (result.next()) {
                InventoryItems item = new InventoryItems(
                        result.getInt("id"),
                        result.getString("item_name"),
                        result.getInt("stock"),
                        result.getDouble("price"));
                addItemToTable(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while populating table from database.");
        }
    }

    /**
     * Handles the sign-off action. This method is called when the sign-out button is clicked.
     *
     * @param event The action event that triggered the method.
     */
    @FXML
    private void handleSignOff(ActionEvent event) {
        System.out.println("Signed off");
        // Any additional sign-off logic can go here

        // Signs out to login page
        app.Main.navigateTo("Login");
    }

    /**
     * Handles the confirmation of orders. This method is called when the confirm order button is clicked.
     *
     * @param event The action event that triggered the method.
     */
    @FXML
    private void handleConfirmOrder(ActionEvent event) {
        for (InventoryItems item : tableView.getItems()) {
            if (item.getOrder() > 0) {
                // Add the ordered quantity of the item to the database
                String updateQuery = "UPDATE inventory_items SET stock = stock + " + item.getOrder() + " WHERE id = "
                        + item.getID();
                dbConnection.runUpdate(updateQuery);
                item.setStock(item.getStock() + item.getOrder());
                item.setOrder(0);
            }
        }
        tableView.refresh();

        tableView.getItems().clear();
        populateTableFromDatabase();
        updateOrderList();
    }

    /**
     * Navigates to the menu view. This method is called when the menu button is clicked.
     *
     * @param event The action event that triggered the method.
     */
    @FXML
    public void goToMenu(ActionEvent event) {
        app.Main.navigateToMenuWithRole("Menu.fxml", app.Main.getIsManager());
    }

    /**
     * Navigates to the statistics view. This method is called when the statistics button is clicked.
     *
     * @param event The action event that triggered the method.
     */
    @FXML
    public void goToStatistics(ActionEvent event) {
        app.Main.navigateTo("Stats");
    }

    /**
     * Navigates back to the inventory view. This method is called when the inventory button is clicked.
     *
     * @param event The action event that triggered the method.
     */
    @FXML
    public void goToInventory(ActionEvent event) {
        app.Main.navigateTo("Inventory");
    }

    /**
     * @param item - the item whose information is added to the TableView
     */
    private void addItemToTable(InventoryItems item) {
        tableView.getItems().add(item);
    }
}
