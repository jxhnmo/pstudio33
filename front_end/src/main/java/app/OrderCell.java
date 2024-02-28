package app;

import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import app.entity_classes.InventoryItems;

public class OrderCell extends TableCell<InventoryItems, Number> {
    private final HBox container = new HBox();
    private final Button minusTenButton = new Button("-10");
    private final Button minusOneButton = new Button("-1");
    private final TextField quantityField = new TextField();
    private final Button plusOneButton = new Button("+1");
    private final Button plusTenButton = new Button("+10");

    public OrderCell() {
        container.getChildren().addAll(minusTenButton, minusOneButton, quantityField, plusOneButton, plusTenButton);
        
        minusTenButton.setOnAction(e -> adjustQuantity(-10));
        minusOneButton.setOnAction(e -> adjustQuantity(-1));
        plusOneButton.setOnAction(e -> adjustQuantity(1));
        plusTenButton.setOnAction(e -> adjustQuantity(10));

        quantityField.setEditable(false);
        quantityField.setPrefWidth(100.0);

        System.out.println("OrderCell constructor called!");
    }

    @Override
    protected void updateItem(Number item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setGraphic(container);
            quantityField.setText("0");
        } else {
            setGraphic(container);
            quantityField.setText(item.toString());
        }
    }

    private void adjustQuantity(int delta) {
        InventoryItems item = getTableView().getItems().get(getIndex());
        int newQuantity = item.getOrder() + delta;
        item.setOrder(Math.max(0, newQuantity));
        quantityField.setText(String.valueOf(item.getOrder()));
    }
}
