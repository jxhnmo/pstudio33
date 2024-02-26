import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import app.entity_classes.DbSetup;

public class DbConnection{
    //want constructor 
    DbSetup setup;
    public DbConnection(){ 
        Connection conn = null;
        DbSetup setup = new DbSetup();
       
        try {
            conn = DriverManager.getConnection(setup.url, setup.user, setup.password);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
      JOptionPane.showMessageDialog(null,"Opened database successfully");
        String name = "";
      try{
        //create a statement object
        Statement stmt = conn.createStatement();
        //create a SQL statement
        //TODO Step 2 (see line 8)
        String sqlStatement = "SELECT * FROM menu_items LIMIT 5;";
        //send statement to DBMS
        ResultSet result = stmt.executeQuery(sqlStatement);
      }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,"Error accessing Database.");
          }
        //connect to databas
        
    }
    public void closeConnection(){
        //close connection
    }

}