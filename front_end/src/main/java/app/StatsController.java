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
import javafx.scene.layout.HBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;

import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;

import java.sql.ResultSet;
import java.sql.Timestamp;
import app.database.*;

import app.entity_classes.MenuItems;
import app.entity_classes.InventoryItems;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import java.time.LocalDate;
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
    
    private DatePicker startDateTime,endDateTime;
    private LocalDateTime firstSale,lastSale;
    private LocalDateTime lastRestock;
    
    @FXML
    private AnchorPane chartArea;
    private TableView<Map<String, Object>> salesTable;
    private TableView<Map<String, Object>> excessTable;
    private TableView<InventoryItems> restockTable;
    private TableView<Map<String, Object>> pairSalesTable;

    @FXML
    public void initialize() {
        setupButtons();
        dbConnection = new DbConnection();
        getMenuItems();
        getInventory();
        getSaleTimeBorders();
        setupSalesTable();
        setupExcessTable();
        setupRestockTable();
        setupPairTable();
        lineChart.setVisible(false);
        productUsage();
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
    
    private void setupSalesTable() {
        salesTable = new TableView<>();
        // menuid menuitemname sales
        TableColumn<Map<String, Object>, Integer> menuID = new TableColumn<>("Number of Sales");
        menuID.setCellValueFactory(data -> 
            new SimpleIntegerProperty(
                Integer.parseInt(
                    data.getValue().get("menu_id").toString())).asObject()
        );
        
        TableColumn<Map<String, Object>, String> itemName = new TableColumn<>("Item Name");
        itemName.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().get("item_name").toString())
        );
        
        TableColumn<Map<String, Object>, String> category = new TableColumn<>("Category");
        category.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().get("category").toString())
        );
        
        
        TableColumn<Map<String, Object>, Integer> numSales = new TableColumn<>("Number of Sales");
        numSales.setCellValueFactory(data -> 
            new SimpleIntegerProperty(
                Integer.parseInt(
                    data.getValue().get("num_sales").toString())).asObject()
        );
        
        salesTable.getColumns().addAll(menuID, itemName, category, numSales);
    }
    
    private void setupExcessTable() {
        excessTable = new TableView<>();
        // menuid menuitemname sales
        TableColumn<Map<String, Object>, Integer> itemID = new TableColumn<>("Number of Sales");
        itemID.setCellValueFactory(data -> 
            new SimpleIntegerProperty(
                Integer.parseInt(
                    data.getValue().get("item_id").toString())).asObject()
        );
        
        TableColumn<Map<String, Object>, String> itemName = new TableColumn<>("Item Name");
        itemName.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().get("item_name").toString())
        );
        
        TableColumn<Map<String, Object>, Integer> stockSold = new TableColumn<>("Stock Sold");
        stockSold.setCellValueFactory(data -> 
            new SimpleIntegerProperty(
                Integer.parseInt(
                    data.getValue().get("stock_sold").toString())).asObject()
        );
        
        TableColumn<Map<String, Object>, Integer> maxStock = new TableColumn<>("Max Stock");
        maxStock.setCellValueFactory(data -> 
            new SimpleIntegerProperty(
                Integer.parseInt(
                    data.getValue().get("max_stock").toString())).asObject()
        );
        
        TableColumn<Map<String, Object>, String> percent = new TableColumn<>("Percent Sold");
        percent.setCellValueFactory(data -> 
            new SimpleStringProperty(
                data.getValue().get("percent_sold").toString())
        );
        
        excessTable.getColumns().addAll(itemID, itemName, stockSold, maxStock, percent);
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
        
        TableColumn<InventoryItems, Integer> deficit = new TableColumn<>("Deficit");
        deficit.setCellValueFactory(data->new SimpleIntegerProperty(data.getValue().getDeficit()).asObject());
        
        TableColumn<InventoryItems, Double> price = new TableColumn<>("Unit Cost");
        price.setCellValueFactory(data->new SimpleDoubleProperty(data.getValue().getPrice()).asObject());
        
        TableColumn<InventoryItems, Double> totalprice = new TableColumn<>("Total Cost");
        totalprice.setCellValueFactory(data->new SimpleDoubleProperty(data.getValue().getRestockCost()).asObject());
        
        restockTable.getColumns().addAll(id,itemName,stock,maxStock,deficit,price,totalprice);
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
    
    private void getSaleTimeBorders() {
        String query = "SELECT purchase_time FROM sales_transactions ORDER BY purchase_time LIMIT 1;";
        ResultSet result = dbConnection.runStatement(query);
        try {
            if(result.next())
                firstSale = result.getTimestamp("purchase_time").toLocalDateTime();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while populating table from database.");
        }
        String query2 = "SELECT purchase_time FROM sales_transactions ORDER BY purchase_time DESC LIMIT 1;";
        result = dbConnection.runStatement(query2);
        try {
            if(result.next())
                lastSale = result.getTimestamp("purchase_time").toLocalDateTime();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while populating table from database.");
        }
        
        String query3 = "SELECT transaction_date FROM inventory_transactions ORDER BY transaction_date DESC LIMIT 1;";
        result = dbConnection.runStatement(query3);
        try {
            if(result.next())
                lastRestock = result.getTimestamp("transaction_date").toLocalDateTime();
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
        
        startDateTime = new DatePicker(firstSale.toLocalDate());
        Label startLabel = new Label("Start Date:  ");
        HBox startBox = new HBox(startLabel,startDateTime);
        startBox.setLayoutX(50);
        startBox.setLayoutY(700);
        
        endDateTime = new DatePicker(lastSale.toLocalDate());
        Label endLabel = new Label("End Date:  ");
        HBox endBox = new HBox(endLabel,endDateTime);
        endBox.setLayoutX(57);
        endBox.setLayoutY(730);
        chartArea.getChildren().add(startBox);
        chartArea.getChildren().add(endBox);
        
        //startDateTime.valueProperty().addListener(this::handleDateChange);
        //endDateTime.valueProperty().addListener(this::handleDateChange);
        chartArea.setLeftAnchor(lineChart,0.0);
        chartArea.setRightAnchor(lineChart,0.0);
        chartArea.setTopAnchor(lineChart,0.0);
        chartArea.setBottomAnchor(lineChart,200.0);
    }
    
    public void salesReport() {
        System.out.println("Selected salesReport");
        startDateTime = new DatePicker(firstSale.toLocalDate());
        Label startLabel = new Label("Start Date:  ");
        startLabel.getStyleClass().add("custom-label");
        HBox startBox = new HBox(startLabel,startDateTime);
        
        endDateTime = new DatePicker(lastSale.toLocalDate());
        Label endLabel = new Label("End Date:  ");
        endLabel.getStyleClass().add("custom-label");
        HBox endBox = new HBox(endLabel,endDateTime);
        
        startDateTime.valueProperty().addListener(this::handleDateChange);
        endDateTime.valueProperty().addListener(this::handleDateChange);
        chartArea.getChildren().add(startBox);
        chartArea.getChildren().add(endBox);
        startBox.setLayoutX(50);
        startBox.setLayoutY(700);
        endBox.setLayoutX(57);
        endBox.setLayoutY(730);
        
        populateSalesTable();
        chartArea.getChildren().add(salesTable);
        chartArea.setLeftAnchor(salesTable,0.0);
        chartArea.setRightAnchor(salesTable,0.0);
        chartArea.setTopAnchor(salesTable,0.0);
        chartArea.setBottomAnchor(salesTable,200.0);
        
    }
    
    private void handleDateChange(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
        populateSalesTable();
    }
    
    private void populateSalesTable() {
        Timestamp start = Timestamp.valueOf(startDateTime.getValue().atStartOfDay());
        Timestamp end = Timestamp.valueOf(endDateTime.getValue().atStartOfDay().plusDays(1));
        // System.out.println(start);
        // System.out.println(end);
        salesTable.getItems().clear();
        
        String front = """
        SELECT menu_items.id,name,category,COUNT(*) AS frequency FROM menu_items
        JOIN sales_items ON sales_items.menu_id = menu_items.id
        JOIN sales_transactions ON sales_items.sales_id = sales_transactions.id
        """;
        String time_portion = "WHERE purchase_time > '"+ start + "' AND purchase_time < '" + end + "'\n";
        String end_statement = """
        GROUP BY menu_items.id
        ORDER BY menu_items.id;
        """;
        String query = front + time_portion + end_statement;
        ResultSet result = dbConnection.runStatement(query);
        ArrayList<Map<String, Object>> data = new ArrayList<>();
        try {
            while (result.next()) {
                data.add(new HashMap<>() {{
                    put("menu_id",result.getInt("id"));
                    put("item_name",result.getString("name"));
                    put("category",result.getString("category"));
                    put("num_sales",result.getInt("frequency"));
                }});
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while populating table from database.");
        }
        salesTable.getItems().addAll(data);
        
    }
    
    public void excessReport() {
        System.out.println("Selected excessReport");
        startDateTime = new DatePicker(lastRestock.toLocalDate());
        Label startLabel = new Label("Timestamp:  ");
        startLabel.getStyleClass().add("custom-label");
        HBox startBox = new HBox(startLabel,startDateTime);
        startBox.setLayoutX(50);
        startBox.setLayoutY(700);
        startDateTime.valueProperty().addListener(this::handleExcessUpdate);

        chartArea.getChildren().add(startBox);
        
        populateExcessTable();
        chartArea.getChildren().add(excessTable);
        chartArea.setLeftAnchor(excessTable,0.0);
        chartArea.setRightAnchor(excessTable,0.0);
        chartArea.setTopAnchor(excessTable,0.0);
        chartArea.setBottomAnchor(excessTable,200.0);
        
    }
    
    private void handleExcessUpdate(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
        populateExcessTable();
    }
    
    private void populateExcessTable() {
        Timestamp start = Timestamp.valueOf(startDateTime.getValue().atStartOfDay());
        Timestamp end = Timestamp.valueOf(LocalDateTime.now());
        
        String front = """
        SELECT inventory_items.id,item_name,SUM(ingredients.num) AS stock_sold,max_stock FROM inventory_items
        JOIN ingredients ON inventory_items.id = ingredients.item_id
        JOIN menu_items ON ingredients.menu_id = menu_items.id
        JOIN sales_items ON menu_items.id = sales_items.menu_id
        JOIN sales_transactions ON sales_items.sales_id = sales_transactions.id
        """;
        String time_portion = "WHERE sales_transactions.purchase_time > '" + start + "'\n";
        String end_part = """
        GROUP BY inventory_items.id;
        """;
        String query = front + time_portion + end_part;
        
        // System.out.println(query);
        ResultSet result = dbConnection.runStatement(query);
        ArrayList<Map<String, Object>> data = new ArrayList<>();
        try {
            while (result.next()) {
                if(result.getInt("stock_sold")*5 < result.getInt("max_stock"))
                    data.add(new HashMap<>() {{
                        put("item_id",result.getInt("id"));
                        put("item_name",result.getString("item_name"));
                        put("stock_sold",result.getInt("stock_sold"));
                        put("max_stock",result.getInt("max_stock"));
                        put("percent_sold",getPercent(result.getInt("stock_sold"),result.getInt("max_stock")));
                    }});
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error while populating table from database.");
        }
        excessTable.getItems().clear();
        excessTable.getItems().addAll(data);
        
        
    }
    
    private String getPercent(int a, int b) {
        double ans = a * 100.0;
        ans /= b;
        return String.format("%.2f",ans)+"%";
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
