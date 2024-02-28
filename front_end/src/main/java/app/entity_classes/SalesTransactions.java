package app.entity_classes;

public class SalesTransactions {
    private int id;
    private double cost;
    private int employeeId;
    private String timeStamp;

    public SalesTransactions(int id, double cost, int employeeId, String timeStamp){
        this.id = id;
        this.cost = cost;
        this.employeeId = employeeId;
        this.timeStamp = timeStamp;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public double getCost(){
        return cost;
    }
    
    public void addCost(double cost) {
        this.cost += cost;
    }
    
    public void setCost(double cost){
        this.cost = cost;
    }

    public int getEmployeeId(){
        return employeeId;
    }

    public void setEmployeeId(int employeeId){
        this.employeeId = employeeId;
    }

    public String getTimeStamp(){
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp){
        this.timeStamp = timeStamp;
    }
}
