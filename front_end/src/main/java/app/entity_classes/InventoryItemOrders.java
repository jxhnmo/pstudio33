package app.entity_classes;

public class InventoryItemOrders {
    public int ID;
    public int transactionID;
    public int itemID;
    public int stock;
    public double price;

    public InventoryItemOrders(int ID, int transactionID, int itemID, int stock, double price){
        this.ID = ID;
        this.transactionID = transactionID;
        this. itemID = itemID;
        this.stock = stock;
        this.price = price;
    }

    public int getID(){
        return ID;
    }

    public void setID(int ID){
        this.ID = ID;
    }

    public int getTransactionID(){
        return transactionID;
    }

    public void setTransactionID(int transactionID){
        this.transactionID = transactionID;
    }

    public int getItemID(){
        return itemID;
    }

    public void setItemID(int itemID){
        this.itemID = itemID;
    }

    public int getStock(){
        return stock;
    }

    public void setStock(int stock){
        this.stock = stock;
    }

    public double getPrice(){
        return price;
    }

    public void setPrice(double price){
        this.price = price;
    }
}
