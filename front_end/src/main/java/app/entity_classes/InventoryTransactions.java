package app.entity_classes;

import java.time.LocalDateTime;
/**
 * Represents a transaction in the inventory management system. This class holds information
 * about each transaction, including the transaction ID, manager ID responsible for the transaction,
 * the date and time of the transaction, the total price of the transaction, and an array of item IDs
 * involved in the transaction.
 */
public class InventoryTransactions {
    private int ID;
    private int managerID;
    private LocalDateTime transactionDate;
    private double price;
    private int[] items;
    /**
     * Constructs an InventoryTransaction with specified details.
     *
     * @param ID               the unique identifier for the transaction
     * @param managerID        the ID of the manager responsible for the transaction
     * @param transactionDate  the date and time when the transaction occurred
     * @param price            the total price of the transaction
     * @param items            an array of item IDs involved in the transaction
     */
    public InventoryTransactions(int ID, int managerID, LocalDateTime transactionDate, double price, int[] items) {
        this.ID = ID;
        this.managerID = managerID;
        this.transactionDate = transactionDate;
        this.price = price;
        this.items = items;
    }

    /**
     * Returns the ID of the transaction.
     *
     * @return the transaction ID
     */
    public int getID() {
        return ID;
    }
    /**
     * Sets the ID for the transaction.
     *
     * @param ID the new ID for the transaction
     */
    public void setID(int ID) {
        this.ID = ID;
    }
    /**
     * Returns the ID of the manager responsible for the transaction.
     *
     * @return the manager ID
     */
    public int getManagerID() {
        return managerID;
    }
    /**
     * Sets the ID of the manager responsible for the transaction.
     *
     * @param managerID the new manager ID
     */
    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }
    /**
     * Returns the date and time when the transaction occurred.
     *
     * @return the transaction date and time
     */
    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }
    /**
     * Sets the date and time for the transaction.
     *
     * @param transactionDate the new date and time for the transaction
     */
    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
    /**
     * Returns the total price of the transaction.
     *
     * @return the transaction price
     */
    public double getPrice() {
        return price;
    }
    /**
     * Sets the total price for the transaction.
     *
     * @param price the new total price for the transaction
     */
    public void setPrice(double price) {
        this.price = price;
    }
    /**
     * Returns an array of item IDs involved in the transaction.
     *
     * @return an array of item IDs
     */
    public int[] getItems() {
        return items;
    }
    /**
     * Sets the array of item IDs involved in the transaction.
     *
     * @param items the new array of item IDs for the transaction
     */
    public void setItems(int[] items) {
        this.items = items;
    }
}
