package app.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * The DbConnection class represents a connection to a database.
 * It provides methods to execute SQL statements and retrieve results.
 */
public class DbConnection {
    DbSetup setup;
    Connection conn;

    /**
     * Constructs a new DbConnection object and establishes a connection to the database.
     */

    public DbConnection() {
        conn = null;
        DbSetup setup = new DbSetup();
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(setup.url, setup.user, setup.password);
        } 
        catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
    /**
     * Executes the given SQL statement and returns the result set.
     *
     * @param sqlStatement the SQL statement to execute
     * @return the result set of the executed SQL statement
     */
    public ResultSet runStatement(String sqlStatement) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(sqlStatement);
            return result;
        } catch (Exception e) {
            System.err.println("Error accessing database");
        }
        return null;
    }

    public int runUpdate(String sqlStatement) {
        int rowsChanged = 0;
        try {
            Statement stmt = conn.createStatement();
            rowsChanged = stmt.executeUpdate(sqlStatement);
        } catch (Exception e) {
            System.err.println("Error accessing database");
        }
        return rowsChanged;
    }

    /**
     * Retrieves a string representation of the result set for the specified column label.
     *
     * @param result      the result set to retrieve the string representation from
     * @param columnLabel the label of the column to retrieve the string representation from
     * @return a string representation of the result set for the specified column label
     */
    public String getResultString(ResultSet result, String columnLabel) {
        String res = "";
        try {
            while (result.next()) {
                res += result.getString(columnLabel);
            }
        } catch (Exception e) {
            System.err.println("Error with result");
        }
        return res;
    }
    /** 
     * @param result
     * @param columnLabel the label of the column to retrieve the string representation from
     * @return ArrayList<String> an array representation of the result set for the specified column label
     */
    public ArrayList<String> getResultArray(ResultSet result, String columnLabel) {
        ArrayList<String> list = new ArrayList<>();
        try {
            while (result.next()) {
                list.add(result.getString(columnLabel));
            }
        } catch (Exception e) {
            System.err.println("Error with result");
        }
        return list;
    }

    /**
     * Closes the database connection.
     */
    public void closeConnection() {
        try {
            conn.close();
        } catch (Exception e) {
            System.err.println("Connection NOT closed.");
        }
    }
}
        //close connection
    

