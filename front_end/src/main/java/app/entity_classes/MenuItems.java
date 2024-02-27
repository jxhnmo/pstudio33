package app.entity_classes;

public class MenuItems {
    private int ID;
    private String name;
    private String description;
    //? private  image
    private Boolean available;
    private int price;
    private String category;

    public MenuItems(int ID, String name, String description, /*Image image*/ Boolean available, int price, String category){
        this.ID = ID;
        this.name = name;
        this.description = description;
        /*this.image = image */
        this.available = available;
        this.price = price;
        this.category = category;
    }

    public int getID(){
        return ID;
    }

    public void setID(int ID){
        this.ID = ID;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    /*
    public image getImage(){
        return Image;
    }

    public void setImage(Image image){
        this.image = image;
    }
     */

    public Boolean getAvailable(){
        return available;
    }

    public void setAvailable(Boolean available){
        this.available = available;
    }

    public int getPrice(){
        return price;
    }

    public void setPrice(int price){
        this.price = price;
    }

    public String getCategory(){
        return category;
    }

    public void setCategory(String category){
        this.category = category;
    }

}
