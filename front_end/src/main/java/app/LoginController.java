package app;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import app.database.*;

/**
 * Controller for handling user login operations.
 * This class manages user authentication and redirection based on user roles.
 */
public class LoginController {

    // User ID field for input
    @FXML
    private TextField userIdField;
    
    // Error message label for the event of invalid input
    @FXML
    private Label errorMessageLabel;

    // Create an instance of DbConnection for use in the class
    private DbConnection dbConnection = new DbConnection();

    // Makes a hash map of id with the manager boolean
    private Map<Integer, Boolean> employeeManagerStatus = new HashMap<>();

    // Initialization of the login controller
    @FXML
    private void initialize() {
        preloadEmployeeData();
    }

    /**
     * Preloads employee data from the database and stores it in a map.
     * This method populates {@code employeeManagerStatus} with employee IDs and their manager status.
     */
    public void preloadEmployeeData() {
        String query = "SELECT id, manager FROM employees";
        try {
            ResultSet resultSet = dbConnection.runStatement(query);
    
            while (resultSet != null && resultSet.next()) {
                int id = resultSet.getInt("id");
                boolean isManager = resultSet.getBoolean("manager");
                employeeManagerStatus.put(id, isManager);
            }
    
            // Don't forget to close ResultSet and Statement
            resultSet.close();
        } 
        catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors
        }
    }

    /**
     * Handles the login process based on the entered user ID.
     * Validates the user ID, checks if the user is a manager or employee, and navigates to the appropriate view.
     */
    @FXML
    protected void handleLogin() {
        String userIdText = userIdField.getText().trim();
    
        if (!userIdText.isEmpty() && userIdText.matches("\\d+")) {
            int userId = Integer.parseInt(userIdText);
    
            // Check if the ID exists in the map and retrieve the manager status
            Boolean isManager = employeeManagerStatus.get(userId);
    
            if (isManager != null) { // ID found in the map
                app.Main.setIsManager(isManager);
                app.Main.navigateToMenuWithRole("Menu.fxml", isManager);
            } 
            else { // ID not found
                errorMessageLabel.setText("Invalid ID, please try again.");
            }
        } 
        else {
            errorMessageLabel.setText("Invalid ID format, please try again.");
        }
    }

}
    