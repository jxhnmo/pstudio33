package app;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

public class StatsController {
    @FXML
    private Button btnSignOut; // Ensure this matches the fx:id in FXML

    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private BarChart<String, Number> salesReportChart;

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
