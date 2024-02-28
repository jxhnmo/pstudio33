package app;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField userIdField;

    @FXML
    protected void handleLogin() {
        String userId = userIdField.getText().trim();
        boolean isManager = false;
        // TODO implement if manager or employee, currently just is temp values
        if ("0".equals(userId)) {
            isManager = true; // User is a manager
        } 
        else if ("1".equals(userId)) {
            isManager = false; // User is an employee
        }
        else {
            System.out.println("Invalid ID"); // Implement proper error handling
            return;
        }
        Main.navigateToMenuWithRole("Menu.fxml", isManager);
    }
}
