package app.entity_classes;

/**
 * Represents a menu item in a restaurant or food service application.
 * <p>
 * This class encapsulates the properties of a menu item, such as its ID, name, availability, price, and category.
 * Additional properties like description and image can be included if needed.
 */
public class MenuItems {
    private int ID;
    private String name;
    private String description;
    //? private  image
    private Boolean available;
    private double price;
    private String category;

    /**
     * Constructs a new MenuItems instance with the specified properties.
     *
     * @param ID        The unique identifier for the menu item.
     * @param name      The name of the menu item.
     * @param available The availability status of the menu item.
     * @param price     The price of the menu item.
     * @param category  The category to which the menu item belongs.
     */
    public MenuItems(int ID, String name, /*String description,*/ /*Image image,*/ Boolean available, double price, String category){
        this.ID = ID;
        this.name = name;
        /*this.description = description;*/
        /*this.image = image */
        this.available = available;
        this.price = price;
        this.category = category;
    }

    // Getter and setter methods

    /**
     * Returns the ID of the menu item.
     *
     * @return The ID of the menu item.
     */
    public int getID(){
        return ID;
    }

    /**
     * Sets the ID of the menu item.
     *
     * @param ID The new ID of the menu item.
     */
    public void setID(int ID){
        this.ID = ID;
    }

    /**
     * Returns the name of the menu item.
     *
     * @return The name of the menu item.
     */
    public String getName(){
        return name;
    }

    /**
     * Sets the name of the menu item.
     *
     * @param name The new name of the menu item.
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Returns the availability status of the menu item.
     *
     * @return The availability status of the menu item.
     */
    public Boolean getAvailable(){
        return available;
    }

    /**
     * Sets the availability status of the menu item.
     *
     * @param available The new availability status of the menu item.
     */
    public void setAvailable(Boolean available){
        this.available = available;
    }

    /**
     * Returns the price of the menu item.
     *
     * @return The price of the menu item.
     */
    public double getPrice(){
        return price;
    }

    /**
     * Sets the price of the menu item.
     *
     * @param price The new price of the menu item.
     */
    public void setPrice(double price){
        this.price = price;
    }

    /**
     * Returns the category of the menu item.
     *
     * @return The category of the menu item.
     */
    public String getCategory(){
        return category;
    }

    /**
     * Sets the category of the menu item.
     *
     * @param category The new category of the menu item.
     */
    public void setCategory(String category){
        this.category = category;
    }

}
