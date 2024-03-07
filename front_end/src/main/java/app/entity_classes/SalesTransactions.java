package app.entity_classes;

/**
 * Represents a sales transaction.
 * <p>
 * This class encapsulates the properties of a sales transaction, such as its ID, total cost, the ID of the employee
 * who processed the sale, and the timestamp of the transaction.
 */
public class SalesTransactions {
    private int id;
    private double cost;
    private int employeeId;
    private String timeStamp;

    /**
     * Constructs a new SalesTransactions instance with the specified properties.
     *
     * @param id         The unique identifier for the sales transaction.
     * @param cost       The total cost of the sales transaction.
     * @param employeeId The ID of the employee who processed the sale.
     * @param timeStamp  The timestamp when the transaction was processed.
     */
    public SalesTransactions(int id, double cost, int employeeId, String timeStamp){
        this.id = id;
        this.cost = cost;
        this.employeeId = employeeId;
        this.timeStamp = timeStamp;
    }

    // Getter and setter methods

    /**
     * Retrieves the ID of the sales transaction.
     *
     * @return The ID of the sales transaction.
     */
    public int getId(){
        return id;
    }

    /**
     * Sets the ID of the sales transaction.
     *
     * @param id The ID of the sales transaction.
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Retrieves the total cost of the sales transaction.
     *
     * @return The total cost of the sales transaction.
     */
    public double getCost(){
        return cost;
    }
    
    /**
     * Adds the specified cost to the total cost of the transaction.
     *
     * @param cost The cost to add.
     */
    public void addCost(double cost) {
        this.cost += cost;
    }
    
    /**
     * Sets the total cost of the sales transaction.
     *
     * @param cost The total cost of the sales transaction.
     */
    public void setCost(double cost){
        this.cost = cost;
    }

    /**
     * Retrieves the ID of the employee who processed the sale.
     *
     * @return The ID of the employee who processed the sale.
     */
    public int getEmployeeId(){
        return employeeId;
    }

    /**
     * Sets the ID of the employee who processed the sale.
     *
     * @param employeeId The ID of the employee who processed the sale.
     */
    public void setEmployeeId(int employeeId){
        this.employeeId = employeeId;
    }

    /**
     * Retrieves the timestamp when the transaction was processed.
     *
     * @return The timestamp when the transaction was processed.
     */
    public String getTimeStamp(){
        return timeStamp;
    }

    /**
     * Sets the timestamp when the transaction was processed.
     *
     * @param timeStamp The timestamp when the transaction was processed.
     */
    public void setTimeStamp(String timeStamp){
        this.timeStamp = timeStamp;
    }

    /**
     * Returns a string representation of the sales transaction.
     *
     * @return A string representing the sales transaction.
     */
    public String toString() {
        return "("+id+","+String.format("%.2f",cost)+","+employeeId+",'"+timeStamp+"')";
    }
}
