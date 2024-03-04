package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class MenuPopupController {
    @FXML
    TextField newItemName;
    @FXML
    TextField newItemCategory;

    
    /** 
     * @param event
     */
    @FXML
    public void handleCancelClicked(ActionEvent event) {
        closeWindow();
    }
    
    /** 
     * @param event
     */
    @FXML
    public void handleConfirmClicked(ActionEvent event) {
        closeWindow();
    }

    public void loadCategories() {
    }

    private void closeWindow() {
        Stage stage = (Stage) newItemName.getScene().getWindow();
        stage.close();
    }
}
