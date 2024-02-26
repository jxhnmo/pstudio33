package app.entity_classes;

public class SalesItems {
    private int ID;
    private int salesID;
    private int itemID;
    private int itemCounter;

    public SalesItems(int ID, int salesID, int itemID, int itemCounter){
        this.ID = ID;
        this.salesID = salesID;
        this.itemID = itemID;
        this.itemCounter = itemCounter;
    }

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

    public int getItemCounter(){
        return itemCounter;
    }

    public void setItemCounter(int itemCounter){
        this.itemCounter = itemCounter;
    }
}
