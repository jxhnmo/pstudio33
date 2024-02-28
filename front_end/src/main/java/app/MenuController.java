package app;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.fxml.FXML;

import java.sql.ResultSet;
import app.entity_classes.MenuItems;
import app.entity_classes.SalesTransactions;
import app.entity_classes.SalesItems;
import app.database.*;

import java.util.ArrayList;

public class MenuController {
    @FXML
    private Button btnSignOut;
    @FXML
    private Button btnMenu;
    @FXML
    private Button btnStatistics;
    @FXML
    private Button btnInventory;
    @FXML
    private VBox categories;
    @FXML
    private GridPane menuItems;
    @FXML
    private TextArea salesTextbox;
    @FXML
    private Label total;
    
    private DbConnection dbConnection;
    
    private ArrayList<String> menuCategories = new ArrayList<>();
    private ArrayList<Button> categoryButtons = new ArrayList<>();
    private Button currCategory;
    
    private ArrayList<MenuItems> allItems = new ArrayList<>();
    private ArrayList<MenuItems> currItems = new ArrayList<>();
    private ArrayList<Button> itemButtons = new ArrayList<>();
    
    private int employeeId = 1;
    private SalesTransactions currTransaction;
    private ArrayList<SalesItems> currSalesItems = new ArrayList<>();
    
    public void initializeMenu(boolean isManager) {
        // Initialize the role
        if (!isManager) {
            btnMenu.setDisable(true); // Disable for employees
            btnStatistics.setDisable(true); // Disable for employees
            btnInventory.setDisable(true); // Disable for employees
            // Disable any other manager-specific buttons
        }
        // If isManager is true, no need to disable buttons, assuming all buttons are enabled by default
    }
    
    public void initialize() {
        // Initialize dbConnection
        dbConnection = new DbConnection();
        getCategories();
        currCategory = categoryButtons.get(0);
        currCategory.setDisable(true);
        getMenuItems();
        updateMenuItems();
        currTransaction = new SalesTransactions(-1,0,employeeId,"");
    }
    
    private void getCategories() {
        String query = "SELECT category FROM menu_items GROUP BY category";
        try {
            ResultSet result = dbConnection.runStatement(query);
            while (result.next()){
                menuCategories.add(result.getString("category"));
            }
            categories.getChildren().clear();
        }
        catch(Exception e) {
            e.printStackTrace();
            System.err.println("Error while populating table from database.");
        }
        menuCategories.sort(null);
        for (String category : menuCategories) {
            // System.out.println(category);
            Button newbutton = new Button(category);
            newbutton.setMnemonicParsing(false);
            newbutton.setPrefHeight(77.0);
            newbutton.setPrefWidth(192.0);
            newbutton.setOnAction(this::handleCategorySelection);
            categoryButtons.add(newbutton);
            categories.getChildren().add(newbutton);
        }
    }
    
    private void getMenuItems() {
        String query = "SELECT * FROM menu_items ORDER BY id;";
        ResultSet result = dbConnection.runStatement(query);
        
        try {
            while (result.next()){
                MenuItems item = new MenuItems(
                    result.getInt("id"),
                    result.getString("name"),
                    result.getBoolean("available"),
                    result.getDouble("price"),
                    result.getString("category")
                );
                allItems.add(item);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while populating table from database.");
        }
    }
    
    private void updateMenuItems() {
        currItems.clear();
        menuItems.getChildren().clear();
        itemButtons.clear();
        int r = 0;
        int c = 0;
        for (MenuItems item : allItems) {
            if(item.getCategory().equals(currCategory.getText())) {
                currItems.add(item);
                Button newbutton = new Button(item.getName());
                newbutton.setMnemonicParsing(false);
                newbutton.setPrefHeight(211.0);
                newbutton.setPrefWidth(280.0);
                newbutton.setWrapText(true);
                newbutton.setOnAction(this::handleItemSelection);
                itemButtons.add(newbutton);
                menuItems.add(newbutton, c, r);
                menuItems.setMargin(newbutton, new Insets(10));
                ++c;
                if (c == 3) {
                    c = 0;
                    ++r;
                }
            }
        }
    }
    
    private void updateSalesInfo() {
        String salesText = "";
        for(SalesItems sitem : currSalesItems) {
            MenuItems item = allItems.get(sitem.getItemId()-1);
            salesText += item.getName() + "\n" + item.getPrice() + "\n\n";
        }
        salesTextbox.setText(salesText);
        String totalCost = String.format("%.2f",currTransaction.getCost());
        total.setText(totalCost);
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
    
    private void handleCategorySelection(ActionEvent event) {
        Button button = (Button) event.getSource();
        currCategory.setDisable(false);
        currCategory = button;
        currCategory.setDisable(true);
        updateMenuItems();
    }
    private void handleItemSelection(ActionEvent event) {
        Button button = (Button) event.getSource();
        int index = itemButtons.indexOf(button);
        MenuItems item = currItems.get(index);
        SalesItems saleItem = new SalesItems(-1,-1,item.getID());
        currSalesItems.add(saleItem);
        currTransaction.addCost(item.getPrice());
        updateSalesInfo();
        // System.out.println(item.getName());
    }
    @FXML
    public void handleOrderConfirm(ActionEvent event) {
        if(currSalesItems.size() != 0) {
            currSalesItems.clear();
            currTransaction = new SalesTransactions(-1,0,employeeId,"");
            updateSalesInfo();
        }
    }
}
