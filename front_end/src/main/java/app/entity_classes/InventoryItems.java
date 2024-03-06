package app.entity_classes;
/**
 * Represents an inventory item within Rev's. 
 * This class stores details about the item, including its ID, name, current stock level, price,
 * maximum stock level, and the quantity on order. It provides methods to manipulate and retrieve
 * these properties, as well as to calculate the deficit between the current and maximum stock levels
 * and the cost to restock the item to its maximum level.
 */
public class InventoryItems {
    private int ID;
    private String itemName;
    private int stock;
    private double price;
    private int order;
    private int max_stock;
    /**
     * Constructs an InventoryItem with specified ID, name, stock level, and price. 
     * The maximum stock level is set to the initial stock level, and the order quantity is initialized to 0.
     *
     * @param ID the unique identifier for the inventory item
     * @param itemName the name of the inventory item
     * @param stock the current stock level of the item
     * @param price the price of the item
     */
    public InventoryItems(int ID, String itemName, int stock, double price){
        this.ID = ID;
        this.itemName = itemName;
        this.stock = stock;
        this.max_stock = stock;
        this.price = price;
        this.order = 0;
    }
    /**
     * Constructs an InventoryItem with specified ID, name, stock level, price, and maximum stock level. 
     * The order quantity is initialized to 0.
     *
     * @param ID the unique identifier for the inventory item
     * @param itemName the name of the inventory item
     * @param stock the current stock level of the item
     * @param price the price of the item
     * @param max_stock the maximum stock level of the item
     */
    public InventoryItems(int ID, String itemName, int stock, double price,int max_stock) {
        this.ID = ID;
        this.itemName = itemName;
        this.stock = stock;
        this.max_stock = max_stock;
        this.price = price;
        this.order = 0;
    }
    /**
     * Returns the ID of the inventory item.
     *
     * @return the ID of the item
     */
    public int getID(){
        return ID;
    }
    /**
     * Sets the ID of the inventory item.
     *
     * @param ID the new ID for the item
     */
    public void setID(int ID){
        this.ID = ID;
    }
    /**
     * Returns the name of the inventory item.
     *
     * @return the name of the item
     */
    public String getItemName(){
        return itemName;
    }
   /**
     * Sets the name of the inventory item.
     *
     * @param itemName the new name for the item
     */
    public void setItemName(String itemName){
        this.itemName = itemName;
    }
   /**
     * Returns the current stock level of the inventory item.
     *
     * @return the current stock level
     */
    public int getStock(){
        return stock;
    }
    /**
     * Sets the stock level of the inventory item.
     *
     * @param stock the new stock level for the item
     */
    public void setStock(int stock){
        this.stock = stock;
    }
    /**
     * Returns the maximum stock level of the inventory item.
     *
     * @return the maximum stock level
     */
    public int getMaxStock(){
        return max_stock;
    }
    /**
     * Sets the maximum stock level of the inventory item.
     *
     * @param max_stock the new maximum stock level for the item
     */
    public void setMaxStock(int max_stock){
        this.max_stock = max_stock;
    }
    /**
     * Calculates and returns the deficit in stock level relative to the maximum stock level.
     *
     * @return the deficit in stock level
     */
    public int getDeficit(){
        return max_stock-stock;
    }
    /**
     * Returns the price of the inventory item.
     *
     * @return the price of the item
     */
    public double getPrice(){
        return price;
    }
    /**
     * Sets the price of the inventory item.
     *
     * @param price the new price for the item
     */

    public void setPrice(double price){
        this.price = price;
    }
    /**
     * Calculates and returns the cost to restock the item to its maximum stock level.
     *
     * @return the cost to restock the item
     */
    public double getRestockCost() {
        return price*getDeficit();
    }
    /**
     * Returns the order quantity of the inventory item.
     *
     * @return the order quantity
     */
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
