package app.entity_classes;
/**
 * The Employee class represents an employee within Rev's. It holds information about the employee,
 * including their ID, name, salary, shift times, and managerial status.
 */
public class Employee {
    private int id;
    private String name;
    private double salary;
    private int shiftTimes;
    private Boolean isManager;
    /**
     * Constructs a new Employee object with the specified details.
     *
     * @param id         the unique identifier for the employee
     * @param name       the name of the employee
     * @param salary     the salary of the employee
     * @param shiftTimes the number of shift times for the employee
     * @param isManager  a Boolean indicating if the employee is a manager (true) or not (false)
     */
    public Employee(int id, String name, double salary, int shiftTimes, boolean isManager) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.shiftTimes = shiftTimes;
        this.isManager = isManager;
    }
    /**
     * Gets the employee's ID.
     *
     * @return the employee's ID
     */
    public int getId(){
        return id;
    }
    /**
     * Sets the employee's ID.
     *
     * @param id the new ID for the employee
     */
    public void setId(int id){
        this.id = id;
    }
    /**
     * Gets the employee's name.
     *
     * @return the employee's name
     */
    public String getName() {
        return name;
    }
    /**
     * Sets the employee's name.
     *
     * @param name the new name for the employee
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Gets the employee's salary.
     *
     * @return the employee's salary
     */
    public double getSalary() {
        return salary;
    }
    /**
     * Sets the employee's salary.
     *
     * @param salary the new salary for the employee
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }
    /**
     * Gets the number of shift times for the employee.
     *
     * @return the number of shift times for the employee
     */
    public int getShiftTimes() {
        return shiftTimes;
    }
    /**
     * Sets the number of shift times for the employee.
     *
     * @param shiftTimes the new number of shift times for the employee
     */
    public void setShiftTimes(int shiftTimes){
        this.shiftTimes = shiftTimes;
    }
    /**
     * Determines if the employee is a manager.
     *
     * @return true if the employee is a manager, false otherwise
     */
    public boolean isManager() {
        return isManager;
    }
    /**
     * Sets the managerial status of the employee.
     *
     * @param manager the new managerial status for the employee, true for manager, false otherwise
     */
    public void setManager(boolean manager) {
        isManager = manager;
    }
}
