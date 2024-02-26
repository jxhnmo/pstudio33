import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import app.entity_classes.DbSetup;

public class DbConnection{
    //want constructor 
    DbSetup setup;
    Connection conn;
    public DbConnection(){ 
        conn = null;
        DbSetup setup = new DbSetup();
       
        try {
            conn = DriverManager.getConnection(setup.url, setup.user, setup.password);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
     
        //connect to databas
        
    }
    public ResultSet runStatement(String sqlStatement){
    
        try{
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(sqlStatement);
            return result;
          }
            catch (Exception e){
                System.err.println("Error accessingg database");
              }

        
    }
    public void closeConnection(){
        //close connection
    }

}