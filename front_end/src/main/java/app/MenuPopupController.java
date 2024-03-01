package app;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.ChoiceBox;

public class MenuPopupController {
    @FXML
    TextField newItemName;
    @FXML
    ChoiceBox<String> choiceBox;

    
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

    public void loadCategories(ArrayList<String> categories) {
        ObservableList<String> observableList = FXCollections.observableArrayList(categories);
        choiceBox.setItems(observableList);
        choiceBox.setStyle("-fx-font-size: 32px;");
    }

    private void closeWindow() {
        Stage stage = (Stage) newItemName.getScene().getWindow();
        stage.close();
    }
}
