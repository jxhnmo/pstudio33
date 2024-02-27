package app.entity_classes;

public class Ingredients {
    private int ID;
    private String itemName;
    private int menuID;
    private int num;

    public Ingredients(int ID, String itemName, int menuID, int num){
        this.ID = ID;
        this.itemName = itemName;
        this.menuID = menuID;
        this.num = num;
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

    public int getMenuID(){
        return menuID;
    }

    public void setMenuID(int menuID){
        this.menuID = menuID;
    }

    public int getNum(){
        return num;
    }

    public void setNum(int num){
        this.num = num;
    }
}
