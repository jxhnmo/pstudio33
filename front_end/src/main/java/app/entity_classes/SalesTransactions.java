package app.entity_classes;

public class SalesTransactions {
    private int id;
    private double cost;
    private String employeeId;
    private String timeStamp;

    public SalesTransactions(int id, double cost, String employeeId, String timeStamp){
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

    public void setCost(double cost){
        this.cost = cost;
    }

    public String getEmployeeId(){
        return employeeId;
    }

    public void setEmployeeId(String employeeId){
        this.employeeId = employeeId;
    }

    public String getTimeStamp(){
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp){
        this.timeStamp = timeStamp;
    }
}
