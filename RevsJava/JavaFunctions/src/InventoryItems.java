public class InventoryItems {
    private int ID;
    private String itemName;
    private int stock;
    private double price;

    public InventoryItems(int ID, String itemName, int stock, double price){
        this.ID = ID;
        this.itemName = itemName;
        this.stock = stock;
        this.price = price;
    }

    public int getID(){
        return ID;
    }

    public void setID(int ID){
        this.ID = ID;
    }

    public String getItemName(){
        return itemName;
    }

    public void setItemName(String itemName){
        this.itemName = itemName;
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
