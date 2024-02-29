package app;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.FXML;

import java.sql.ResultSet;
import app.entity_classes.MenuItems;
import app.entity_classes.SalesTransactions;
import app.entity_classes.SalesItems;
import app.database.*;

import java.util.ArrayList;
import java.time.LocalDateTime;

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
    @FXML
    private HBox taskbar;
    @FXML
    private Button addCategory;
    @FXML
    private Button addMenuItem;
    
    boolean editmode = false;
    private MenuItems currMenuItem;
    
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
        else {
            taskbar.setTranslateX(-79.2);
            Button newbutton = new Button("Edit Menu");
            newbutton.setMnemonicParsing(false);
            newbutton.setMinHeight(70);
            newbutton.setMinWidth(150);
            newbutton.setOnAction(this::handleEditMenu);
            taskbar.getChildren().add(2,newbutton);
        }
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

                // Apply image to button:
                String filePath = "/app/image/" + item.getName().replace(" ", "") + ".png";
                Image image;
                try {
                    image = new Image(getClass().getResource(filePath).toString());
                }
                catch (Exception e) {
                    image = new Image(getClass().getResource("/app/image/default.png").toString());
                }
                // Set dimensions of button's image:
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(100);
                imageView.setFitWidth(100);
                imageView.setPreserveRatio(true);

                newbutton.setGraphic(imageView);
                newbutton.setContentDisplay(ContentDisplay.TOP);
                itemButtons.add(newbutton);
                menuItems.add(newbutton, c, r);
                menuItems.setMargin(newbutton, new Insets(10));
                ++c;
                if (c >= 4) { // 4 columns for the buttons
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
        // app.Main.navigateTo("Menu");
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
        if(editmode) {
            handleItemPriceEdit(event);
            return;
        }
        Button button = (Button) event.getSource();
        int index = itemButtons.indexOf(button);
        MenuItems item = currItems.get(index);
        SalesItems saleItem = new SalesItems(-1,-1,item.getID());
        currSalesItems.add(saleItem);
        currTransaction.addCost(item.getPrice());
        updateSalesInfo();
        // System.out.println(item.getName());
    }
    
    private void handleItemPriceEdit(ActionEvent event) {
        Button button = (Button) event.getSource();
        int index = itemButtons.indexOf(button);
        currMenuItem = currItems.get(index);
    }

    private void changeItemPrice(MenuItems item,double price) {
        item.setPrice(price);
        String query = "UPDATE menu_items SET price="+item.getPrice()+" WHERE id="+item.getID()+";";
        try {
            dbConnection.runUpdate(query);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while populating table from database.");
        }
    }
    
    private int getCurrTransactionId() {
        String query = "SELECT MAX(id) AS max_id FROM sales_transactions;";
        try {
            ResultSet result = dbConnection.runStatement(query);
            result.next();
            return result.getInt("max_id")+1;
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while populating table from database.");
        }
        return -1;
    }
    private int getCurrSaleItemId() {
        String query = "SELECT MAX(id) AS max_id FROM sales_items;";
        try {
            ResultSet result = dbConnection.runStatement(query);
            result.next();
            return result.getInt("max_id")+1;
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while populating table from database.");
        }
        return -1;
    }
    
    @FXML
    public void handleOrderConfirm(ActionEvent event) {
        if(editmode)
            return;
        if(currSalesItems.size() != 0) {
            int transactionId = getCurrTransactionId();
            int salesItemID = getCurrSaleItemId();
            if (transactionId == -1 || salesItemID == -1)
                return;
            currTransaction.setId(transactionId);
            LocalDateTime currTime = LocalDateTime.now();
            currTime = currTime.withNano(0);
            currTransaction.setTimeStamp(currTime.toString());
            String query = "INSERT INTO sales_transactions VALUES "+currTransaction.toString()+";\n";
            query += "INSERT INTO sales_items VALUES\n";
            int index = 0;
            int lastIndex = currSalesItems.size()-1;
            for (SalesItems sitem : currSalesItems) {
                sitem.setID(salesItemID);
                sitem.setSalesID(transactionId);
                query += sitem.toString();
                if(index != lastIndex)
                    query += ",\n";
                ++salesItemID;
                ++index;
            }
            query += ";\n";
            try {
                dbConnection.runUpdate(query);
            }
            catch (Exception e) {
                e.printStackTrace();
                System.err.println("Error while populating table from database.");
            }
            // System.out.println(query);
            // System.out.println(currTransaction.toString());
            currSalesItems.clear();
            currTransaction = new SalesTransactions(-1,0,employeeId,"");
            updateSalesInfo();
        }
    }
    
    @FXML
    public void handleEditMenu(ActionEvent event) {
        if(editmode) {
            categories.getChildren().remove(addCategory);
            menuItems.getChildren().remove(addMenuItem);
            ((Button) taskbar.getChildren().get(2)).setText("Edit Menu");
        } else {
            ((Button) taskbar.getChildren().get(2)).setText("Exit Editor");
            addCategory = new Button("Add Category");
            addCategory.setMnemonicParsing(false);
            addCategory.setPrefHeight(77.0);
            addCategory.setPrefWidth(192.0);
            addCategory.setOnAction(this::handleCategoryAdd);
            categories.getChildren().add(addCategory);
            
            int numChildren = menuItems.getChildren().size();
            addMenuItem = new Button("Add new item");
            addMenuItem.setMnemonicParsing(false);
            addMenuItem.setPrefHeight(211.0);
            addMenuItem.setPrefWidth(280.0);
            addMenuItem.setWrapText(true);
            addMenuItem.setOnAction(this::handleMenuItemAdd);    
            menuItems.add(addMenuItem, numChildren % 3, numChildren / 3);
            menuItems.setMargin(addMenuItem, new Insets(10));
        }
        editmode = !editmode;
    }
    
    @FXML
    public void handleCategoryAdd(ActionEvent event) {
        
    }
    @FXML
    public void handleMenuItemAdd(ActionEvent event) {
        
    }
}
