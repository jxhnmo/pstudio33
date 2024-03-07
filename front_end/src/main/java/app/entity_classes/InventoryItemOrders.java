package app.entity_classes;
/**
 * Represents an inventory item order within Rev's inventory management system.
 * This class holds information related to individual orders of inventory items, including
 * the order ID, transaction ID associated with the purchase or sale, item ID for inventory tracking,
 * stock quantity, and price of the item.
 */
public class InventoryItemOrders {
    public int ID;
    public int transactionID;
    public int itemID;
    public int stock;
    public double price;
    /**
     * Constructs an InventoryItemOrder with specified details.
     *
     * @param ID            the unique identifier for the order
     * @param transactionID the transaction ID associated with this order
     * @param itemID        the unique identifier of the item ordered
     * @param stock         the quantity of the item ordered
     * @param price         the price of the item ordered
     */
    public InventoryItemOrders(int ID, int transactionID, int itemID, int stock, double price){
        this.ID = ID;
        this.transactionID = transactionID;
        this. itemID = itemID;
        this.stock = stock;
        this.price = price;
    }
    /**
     * Returns the order's unique identifier.
     *
     * @return the order ID
     */
    public int getID(){
        return ID;
    }
    /**
     * Sets the order's unique identifier.
     *
     * @param ID the new ID to set
     */
    public void setID(int ID){
        this.ID = ID;
    }
    /**
     * Returns the transaction ID associated with this order.
     *
     * @return the transaction ID
     */
    public int getTransactionID(){
        return transactionID;
    }
    /**
     * Sets the transaction ID for this order.
     *
     * @param transactionID the new transaction ID to set
     */
    public void setTransactionID(int transactionID){
        this.transactionID = transactionID;
    }
    /**
     * Returns the item ID of the ordered item.
     *
     * @return the item ID
     */
    public int getItemID(){
        return itemID;
    }
    /**
     * Sets the item ID of the order.
     *
     * @param itemID the new item ID to set
     */
    public void setItemID(int itemID){
        this.itemID = itemID;
    }
    /**
     * Returns the stock quantity of the order.
     *
     * @return the stock quantity
     */
    public int getStock(){
        return stock;
    }
    /**
     * Sets the stock quantity for this order.
     *
     * @param stock the new stock quantity to set
     */
    public void setStock(int stock){
        this.stock = stock;
    }
    /**
     * Returns the price of the ordered item.
     *
     * @return the price
     */
    public double getPrice(){
        return price;
    }
    /**
     * Sets the price of the ordered item.
     *
     * @param price the new price to set
     */
    public void setPrice(double price){
        this.price = price;
    }
}
