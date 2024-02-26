package app.entity_classes;

public class Employee {
    private int id;
    private String name;
    private double salary;
    private int shiftTimes;
    private Boolean isManager;

    public Employee(int id, String name, double salary, int shiftTimes, boolean isManager) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.shiftTimes = shiftTimes;
        this.isManager = isManager;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getShiftTimes() {
        return shiftTimes;
    }

    public void setShiftTimes(int shiftTimes){
        this.shiftTimes = shiftTimes;
    }

    public boolean isManager() {
        return isManager;
    }

    public void setManager(boolean manager) {
        isManager = manager;
    }
}
