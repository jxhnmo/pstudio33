package app.entity_classes;

import java.time.LocalDateTime;

public class InventoryTransactions {
    private int ID;
    private int managerID;
    private LocalDateTime transactionDate;
    private double price;
    private int[] items;

    public InventoryTransactions(int ID, int managerID, LocalDateTime transactionDate, double price, int[] items) {
        this.ID = ID;
        this.managerID = managerID;
        this.transactionDate = transactionDate;
        this.price = price;
        this.items = items;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getManagerID() {
        return managerID;
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int[] getItems() {
        return items;
    }

    public void setItems(int[] items) {
        this.items = items;
    }
}
