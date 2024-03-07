package app.entity_classes;

/**
 * Represents an item within a sales transaction.
 * <p>
 * This class encapsulates the details of a sales item, including its own ID, the ID of the sale it belongs to,
 * and the ID of the item being sold.
 */
public class SalesItems {
    private int ID;
    private int salesID;
    private int itemID;

    /**
     * Constructs a new SalesItems instance with the specified properties.
     *
     * @param ID      The unique identifier for the sales item.
     * @param salesID The ID of the sales transaction this item belongs to.
     * @param itemID  The ID of the item being sold.
     */
    public SalesItems(int ID, int salesID, int itemID/*, int itemCounter*/){
        this.ID = ID;
        this.salesID = salesID;
        this.itemID = itemID;
        // this.itemCounter = itemCounter;
    }

    // Getter and setter methods

    public int getID(){
        return ID;
    }

    public void setID(int ID){
        this.ID = ID;
    }

    public int getSalesID(){
        return salesID;
    }

    public void setSalesID(int salesID){
        this.salesID = salesID;
    }

    public int getItemId(){
        return itemID;
    }

    public void setItemID(int itemID){
        this.itemID = itemID;
    }

    /**
     * Returns a string representation of the sales item.
     *
     * @return A string representing the sales item.
     */
    public String toString() {
        return "("+ID+","+salesID+","+itemID+")";
    }
}
