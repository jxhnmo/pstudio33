package app.entity_classes;
/**
 * Represents an ingredient used in the menu items of Rev's.
 * Each ingredient is associated with a specific menu item through a menu ID and has properties
 * like an ID, name, and the quantity of the ingredient available or required.
 */
public class Ingredients {
    private int ID;
    private String itemName;
    private int menuID;
    private int num;
    /**
     * Constructs an ingredient with the specified details.
     *
     * @param ID the unique identifier for the ingredient
     * @param itemName the name of the ingredient
     * @param menuID the identifier of the menu item this ingredient belongs to
     * @param num the quantity or number of this ingredient available or required
     */
    public Ingredients(int ID, String itemName, int menuID, int num){
        this.ID = ID;
        this.itemName = itemName;
        this.menuID = menuID;
        this.num = num;
    }
    /**
     * Returns the ingredient's unique identifier.
     *
     * @return the ID of the ingredient
     */
    public int getID(){
        return ID;
    }
    /**
     * Sets the ingredient's unique identifier.
     *
     * @param ID the new ID to be set for the ingredient
     */
    public void setID(int ID){
        this.ID = ID;
    }
    /**
     * Returns the name of the ingredient.
     *
     * @return the ingredient's name
     */
    public String getItemName(){
        return itemName;
    }
    /**
     * Sets the name of the ingredient.
     *
     * @param itemName the new name to be set for the ingredient
     */
    public void setItemName(String itemName){
        this.itemName = itemName;
    }
    /**
     * Returns the menu item identifier this ingredient is associated with.
     *
     * @return the menu ID associated with this ingredient
     */
    public int getMenuID(){
        return menuID;
    }
    /**
     * Sets the menu item identifier this ingredient is associated with.
     *
     * @param menuID the new menu ID to be associated with the ingredient
     */
    public void setMenuID(int menuID){
        this.menuID = menuID;
    }
    /**
     * Returns the quantity or number of this ingredient required.
     *
     * @return the quantity of the ingredient
     */
    public int getNum(){
        return num;
    }
    /**
     * Sets the quantity or number of this ingredient required.
     *
     * @param num the new quantity to be set for the ingredient
     */
    public void setNum(int num){
        this.num = num;
    }
}
