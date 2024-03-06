package app;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;

import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.sql.ResultSet;
import app.database.*;

import app.entity_classes.MenuItems;
import app.entity_classes.InventoryItems;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import java.time.LocalDateTime;

public class StatsController {
    private DbConnection dbConnection;
    
    @FXML
    private Button btnSignOut; // Ensure this matches the fx:id in FXML

    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private BarChart<String, Number> salesReportChart;
    
    @FXML
    private VBox stats_options;
    private Button product_usage;
    private Button sales_report;
    private Button excess_report;
    private Button restock_report;
    private Button sells_together;
    private Button currSelected;
    
    private ArrayList<MenuItems> menu_items = new ArrayList<>();
    private ArrayList<InventoryItems> inventory = new ArrayList<>();
    
    @FXML
    private AnchorPane chartArea;
    private TableView<InventoryItems> restockTable;
    private TableView<Map<String, Object>> pairSalesTable;

    @FXML
    public void initialize() {
        setupButtons();
        dbConnection = new DbConnection();
        getMenuItems();
        getInventory();
        setupRestockTable();
        setupPairTable();
        lineChart.setVisible(false);
    }
    private void setupButtons() {
        stats_options.getChildren().clear();
        product_usage = addButton("Product Usage");
        sales_report = addButton("Sales Report");
        excess_report = addButton("Excess Report");
        restock_report = addButton("Restock Report");
        sells_together = addButton("Paired Menu Items");
        
        currSelected = product_usage;
        currSelected.setDisable(true);
    }
    private Button addButton(String name) {
        Button new_button = new Button(name);
        new_button.setMnemonicParsing(false);
        new_button.setPrefHeight(77.0);
        new_button.setPrefWidth(192.0);
        new_button.setOnAction(this::handleButtonSelect);
        stats_options.getChildren().add(new_button);
        return new_button;
    }
    
    private void getMenuItems() {
        String query = "SELECT id,name,available,price,category FROM menu_items;";
        ResultSet result = dbConnection.runStatement(query);

        try {
            while (result.next()) {
                MenuItems item = new MenuItems(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getBoolean("available"),
                        result.getDouble("price"),
                        result.getString("category"));
                menu_items.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while populating table from database.");
        }
    }
    
    private void setupRestockTable() {
        restockTable = new TableView<>();
        TableColumn<InventoryItems, Integer> id = new TableColumn<>("Item ID");
        id.setCellValueFactory(data->new SimpleIntegerProperty(data.getValue().getID()).asObject());
        
        TableColumn<InventoryItems, String> itemName = new TableColumn<>("Item Name");
        itemName.setCellValueFactory(data->new SimpleStringProperty(data.getValue().getItemName()));
        
        TableColumn<InventoryItems, Integer> stock = new TableColumn<>("Stock");
        stock.setCellValueFactory(data->new SimpleIntegerProperty(data.getValue().getStock()).asObject());
        
        TableColumn<InventoryItems, Integer> maxStock = new TableColumn<>("Max Stock");
        maxStock.setCellValueFactory(data->new SimpleIntegerProperty(data.getValue().getMaxStock()).asObject());
        
        TableColumn<InventoryItems, Double> price = new TableColumn<>("Unit Cost");
        price.setCellValueFactory(data->new SimpleDoubleProperty(data.getValue().getPrice()).asObject());
        
        restockTable.getColumns().addAll(id,itemName,stock,maxStock,price);
        for(InventoryItems ii : inventory) {
            if(ii.getStock() < ii.getMaxStock()) // will change to half
                restockTable.getItems().add(ii);
        }
    }
    
    private void setupPairTable() {
        pairSalesTable = new TableView<>();
        TableColumn<Map<String, Object>, String> menuItem1 = new TableColumn<>("Item 1");
        menuItem1.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().get("item1").toString())
        );
        
        TableColumn<Map<String, Object>, String> menuItem2 = new TableColumn<>("Item 2");
        menuItem2.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().get("item2").toString())
        );
        
        TableColumn<Map<String, Object>, Integer> pairCount = new TableColumn<>("Frequency");
        pairCount.setCellValueFactory(data -> 
            new SimpleIntegerProperty(
                Integer.parseInt(
                    data.getValue().get("pairCount").toString())).asObject()
        );
        
        pairSalesTable.getColumns().addAll(menuItem1, menuItem2,pairCount);
        
        ArrayList<Map<String, Object>> pairData = getPairData();
        pairSalesTable.getItems().addAll(pairData);
    }
    
    private void getInventory() {
        String query = "SELECT id,item_name,stock,price,max_stock FROM inventory_items;";
        ResultSet result = dbConnection.runStatement(query);
        
        try {
            while (result.next()) {
                InventoryItems item = new InventoryItems(
                        result.getInt("id"),
                        result.getString("item_name"),
                        result.getInt("stock"),
                        result.getDouble("price"),
                        result.getInt("max_stock"));
                inventory.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while populating table from database.");
        }
    }
    
    private ArrayList<Map<String, Object>> getPairData() {
        ArrayList<Map<String, Object>> data = new ArrayList<>();
        String front = """
        SELECT si1.menu_id as item1, si2.menu_id as item2, COUNT(*) AS pair_count
        FROM sales_items si1
        JOIN sales_items si2 ON si1.sales_id = si2.sales_id AND si1.menu_id < si2.menu_id
        """;
        String end = """
        GROUP BY si1.menu_id, si2.menu_id
        ORDER BY COUNT(*) DESC;
        """;
        
        String query = front + end;
        ResultSet result = dbConnection.runStatement(query);
        
        try {
            while (result.next()) {
                data.add(new HashMap<>() {{
                    put("item1",getMenuName(result.getInt("item1")));
                    put("item2",getMenuName(result.getInt("item2")));
                    put("pairCount",result.getInt("pair_count"));    
                }});
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while populating table from database.");
        }
        
        return data;
    }
    private String getMenuName(int id) {
        for(MenuItems mi : menu_items)
            if(mi.getID() == id)
                return mi.getName();
        return "N/A";
    }
    
    public void handleButtonSelect(ActionEvent e) {
        chartArea.getChildren().clear();
        Button source = (Button) e.getSource();
        currSelected.setDisable(false);
        currSelected = source;
        currSelected.setDisable(true);
        if(source == product_usage)
            productUsage();
        else if(source == sales_report)
            salesReport();
        else if(source == restock_report)
            restockReport();
        else if(source == excess_report)
            excessReport();
        else if(source == sells_together)
            sellsTogether();
    }
    
    public void productUsage() {
        System.out.println("Selected productUsage");
        chartArea.getChildren().add(lineChart);
        lineChart.setVisible(true);
    }
    
    public void salesReport() {
        System.out.println("Selected salesReport");
    }
    
    public void excessReport() {
        System.out.println("Selected excessReport");
    }
    
    public void restockReport() {
        System.out.println("Selected restockReport");
        chartArea.getChildren().add(restockTable);
        chartArea.setTopAnchor(restockTable,0.0);
        chartArea.setBottomAnchor(restockTable,0.0);
        chartArea.setLeftAnchor(restockTable,0.0);
        chartArea.setRightAnchor(restockTable,0.0);
    }
    
    public void sellsTogether() {
        System.out.println("Selected sellsTogether");
        chartArea.getChildren().add(pairSalesTable);
        chartArea.setTopAnchor(pairSalesTable,0.0);
        chartArea.setBottomAnchor(pairSalesTable,0.0);
        chartArea.setLeftAnchor(pairSalesTable,0.0);
        chartArea.setRightAnchor(pairSalesTable,0.0);
    }
    
    @FXML
    public void initializeLineChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Monthly Sales");
    
        // Sample data
        series.getData().add(new XYChart.Data<>("Jan", 200));
        series.getData().add(new XYChart.Data<>("Feb", 300));
        series.getData().add(new XYChart.Data<>("Mar", 250));
    
        lineChart.getData().add(series);
    }

    @FXML
    public void initializeBarChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Sales Report");

        // Sample data
        series.getData().add(new XYChart.Data<>("Product A", 150));
        series.getData().add(new XYChart.Data<>("Product B", 200));
        series.getData().add(new XYChart.Data<>("Product C", 100));
    
        salesReportChart.getData().add(series);
    }

    @FXML
    private void handleSignOff(ActionEvent event) {
        System.out.println("Signed off");
        // Any additional sign-off logic can go here

        // Signs out to login page
        app.Main.navigateTo("Login");
    }

    public void goToMenu(ActionEvent event) {
        app.Main.navigateToMenuWithRole("Menu.fxml", app.Main.getIsManager());
    }

    public void goToStatistics(ActionEvent event) {
        app.Main.navigateTo("Stats");
    }

    public void goToInventory(ActionEvent event) {
        app.Main.navigateTo("Inventory");
    }


}
