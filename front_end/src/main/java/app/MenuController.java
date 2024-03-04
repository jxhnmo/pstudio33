package app;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Parent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

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

    private final int BTNS_PER_ROW = 4;

    /**
     * @param isManager
     */
    public void initializeMenu(boolean isManager) {
        // Initialize the role
        if (!isManager) {
            btnMenu.setDisable(true); // Disable for employees
            btnStatistics.setDisable(true); // Disable for employees
            btnInventory.setDisable(true); // Disable for employees
            // Disable any other manager-specific buttons
        }
        // If isManager is true, no need to disable buttons, assuming all buttons are
        // enabled by default
        else {
            taskbar.setTranslateX(-79.2);
            Button newbutton = new Button("Edit Menu");
            newbutton.setMnemonicParsing(false);
            newbutton.setMinHeight(70);
            newbutton.setMinWidth(150);
            newbutton.setOnAction(this::handleEditMenu);
            taskbar.getChildren().add(2, newbutton);
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
        currTransaction = new SalesTransactions(-1, 0, employeeId, "");
    }

    private void getCategories() {
        String query = "SELECT category FROM menu_items GROUP BY category";
        try {
            ResultSet result = dbConnection.runStatement(query);
            while (result.next()) {
                menuCategories.add(result.getString("category"));
            }
            categories.getChildren().clear();
        } catch (Exception e) {
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
            while (result.next()) {
                MenuItems item = new MenuItems(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getBoolean("available"),
                        result.getDouble("price"),
                        result.getString("category"));
                allItems.add(item);
            }
        } catch (Exception e) {
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
            if (item.getCategory().equals(currCategory.getText())) {
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
                } catch (Exception e) {
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
                if (c >= BTNS_PER_ROW) { // 4 columns for the buttons
                    c = 0;
                    ++r;
                }
            }
        }
    }

    private void updateSalesInfo() {
        String salesText = "";
        for (SalesItems sitem : currSalesItems) {
            MenuItems item = allItems.get(sitem.getItemId() - 1);
            salesText += item.getName() + "\n" + item.getPrice() + "\n\n";
        }
        salesTextbox.setText(salesText);
        String totalCost = String.format("%.2f", currTransaction.getCost());
        total.setText(totalCost);
    }

    private String updateIngredients(int menu_id) {
        // String query = "UPDATE inventory_items SET stock = stock - (SELECT num FROM
        // ingredients WHERE ingredients.menu_id = "
        // + menu_id +"AND ingredients.item_id = inventory_items.id);";

        String query = "UPDATE inventory_items SET stock = stock - (SELECT num FROM ingredients WHERE ingredients.menu_id = "
                + menu_id
                + "AND ingredients.item_id = inventory_items.id) WHERE id IN (SELECT item_id FROM ingredients WHERE ingredients.menu_id="
                + menu_id + " GROUP BY item_id);";
        return query;
    }

    /**
     * @param event
     */
    @FXML
    private void handleSignOff(ActionEvent event) {
        System.out.println("Signed off");
        // Any additional sign-off logic can go here

        // Signs out to login page
        app.Main.navigateTo("Login");
    }

    /**
     * @param event
     */
    public void goToMenu(ActionEvent event) {
        // app.Main.navigateTo("Menu");
    }

    /**
     * @param event
     */
    public void goToStatistics(ActionEvent event) {
        app.Main.navigateTo("Stats");
    }

    /**
     * @param event
     */
    public void goToInventory(ActionEvent event) {
        app.Main.navigateTo("Inventory");
    }

    /**
     * @param event
     */
    private void handleCategorySelection(ActionEvent event) {
        Button button = (Button) event.getSource();
        currCategory.setDisable(false);
        currCategory = button;
        currCategory.setDisable(true);
        updateMenuItems();
    }

    /**
     * @param event
     */
    private void handleItemSelection(ActionEvent event) {
        if (editmode) {
            handleItemPriceEdit(event);
            return;
        }
        Button button = (Button) event.getSource();
        int index = itemButtons.indexOf(button);
        MenuItems item = currItems.get(index);
        SalesItems saleItem = new SalesItems(-1, -1, item.getID());
        currSalesItems.add(saleItem);
        currTransaction.addCost(item.getPrice());
        updateSalesInfo();
        // System.out.println(item.getName());
    }

    /**
     * @param event
     */
    private void handleItemPriceEdit(ActionEvent event) {
        Button button = (Button) event.getSource();
        int index = itemButtons.indexOf(button);
        currMenuItem = currItems.get(index);
    }

    /**
     * @param item
     * @param price
     */
    private void changeItemPrice(MenuItems item, double price) {
        item.setPrice(price);
        String query = "UPDATE menu_items SET price=" + item.getPrice() + " WHERE id=" + item.getID() + ";";
        try {
            dbConnection.runUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while populating table from database.");
        }
    }

    /**
     * @return int
     */
    private int getCurrTransactionId() {
        String query = "SELECT MAX(id) AS max_id FROM sales_transactions;";
        try {
            ResultSet result = dbConnection.runStatement(query);
            result.next();
            return result.getInt("max_id") + 1;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while populating table from database.");
        }
        return -1;
    }

    /**
     * @return int
     */
    private int getCurrSaleItemId() {
        String query = "SELECT MAX(id) AS max_id FROM sales_items;";
        try {
            ResultSet result = dbConnection.runStatement(query);
            result.next();
            return result.getInt("max_id") + 1;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while populating table from database.");
        }
        return -1;
    }

    /**
     * @param event
     */
    @FXML
    public void handleOrderConfirm(ActionEvent event) {
        if (editmode)
            return;
        if (currSalesItems.size() != 0) {
            int transactionId = getCurrTransactionId();
            int salesItemID = getCurrSaleItemId();
            if (transactionId == -1 || salesItemID == -1)
                return;
            currTransaction.setId(transactionId);
            LocalDateTime currTime = LocalDateTime.now();
            currTime = currTime.withNano(0);
            currTransaction.setTimeStamp(currTime.toString());
            String query = "INSERT INTO sales_transactions VALUES " + currTransaction.toString() + ";\n";
            query += "INSERT INTO sales_items VALUES\n";
            int index = 0;
            int lastIndex = currSalesItems.size() - 1;
            for (SalesItems sitem : currSalesItems) {
                sitem.setID(salesItemID);
                sitem.setSalesID(transactionId);
                query += sitem.toString();
                if (index != lastIndex)
                    query += ",\n";
                ++salesItemID;
                ++index;
            }
            query += ";\n";

            // Updates ingredientes inventory from transaction
            for (SalesItems sitem : currSalesItems) {
                int menu_id = sitem.getItemId();
                query += updateIngredients(menu_id);
                query += "\n";
            }
            System.out.println(query);
            // End update

            try {
                dbConnection.runUpdate(query);
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Error while populating table from database.");
            }
            // System.out.println(query);
            // System.out.println(currTransaction.toString());
            currSalesItems.clear();
            currTransaction = new SalesTransactions(-1, 0, employeeId, "");
            updateSalesInfo();
        }
    }

    /**
     * @param event
     */
    @FXML
    public void handleEditMenu(ActionEvent event) {
        // TODO: Don't need to change the text on the Edit Menu Button.
        if (editmode) {
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
            menuItems.add(addMenuItem, numChildren % BTNS_PER_ROW, numChildren / BTNS_PER_ROW);
            menuItems.setMargin(addMenuItem, new Insets(10));
        }
        editmode = !editmode;
    }

    /**
     * @param event
     */
    @FXML
    public void handleCategoryAdd(ActionEvent event) {
        System.out.println("Handle Category Addd called.");
    }

    /**
     * @param event
     */
    @FXML
    public void handleMenuItemAdd(ActionEvent event) {
        // TODO: Add a popup window to add an item
        System.out.println("Handle Menu Item Add called.");
        createPopUpWindow();
    }

    /**
     * Loads the FXML file for the new popup window,
     * and disables parent window until popup is closed.
     */
    private void createPopUpWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuPopup.fxml"));
            Parent root = loader.load();

            // Pass data to the popup window:
            MenuPopupController popupController = loader.getController();
            popupController.loadCategories(/* */); // Pass data to the popup

            Stage popupStage = new Stage();
            popupStage.setTitle("Add New Menu Item");
            popupStage.initModality(Modality.WINDOW_MODAL);

            Window primaryStage = btnSignOut.getScene().getWindow();
            popupStage.initOwner(primaryStage);
            popupStage.setScene(new Scene(root, 800, 600));
            popupStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
