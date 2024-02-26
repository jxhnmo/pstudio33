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
    public String getResultString(ResultSet result, String columnLabel){
        String res = "";
        try{
            while (result.next()) {
              res += result.getString("name")+"\t"+result.getString("price")+"\n";
            }
          }
          catch (Exception e){
            System.err.println("error with result");
          }
          return res;
    }
    public void closeConnection(){
        try {
            conn.close();
            System.err.println("Connection closed");
          } catch(Exception e) {
            System.err.println("Connection NOT Closed.");
          }
        //close connection
    }

}