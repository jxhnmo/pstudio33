 
//import RevsJava.JavaFunctions.src.DbSetup;
import java.sql.*;
import java.awt.event.*;
import javax.swing.*;


public class App {
    public static void main(String[] args) throws Exception {
        Connection conn = null;
        DbSetup setup = new DbSetup();
    //   String db_name = setup.name;
       
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
        name += "menu item name \t price\n";
        while (result.next()) {
          // TODO you probably need to change the column name tat you are retrieving
          //      this command gets the data from the "name" attribute
          name += result.getString("name")+"\t"+result.getString("price")+"\n";
        }
      } catch (Exception e){
        JOptionPane.showMessageDialog(null,"Error accessing Database.");
      }
      // create a new frame
     // f = new JFrame("DB GUI");

      // create a object
     // GUI s = new GUI();

      // create a panel
      JPanel p = new JPanel();

      JButton b = new JButton("Close");

      // add actionlistener to button
    //  b.addActionListener(s);

      //TODO Step 3 (see line 9)
      JTextArea textArea = new JTextArea(name);
      textArea.setEditable(false);
      p.add(textArea);


      //TODO Step 4 (see line 10)


      // add button to panel
      p.add(b);

      // add panel to frame
      //f.add(p);

      // set the size of frame
      //f.setSize(400, 400);

     // f.setVisible(true);

      //closing the connection
      try {
        conn.close();
        JOptionPane.showMessageDialog(null,"Connection Closed.");
      } catch(Exception e) {
        JOptionPane.showMessageDialog(null,"Connection NOT Closed.");
      }
    }

 

    }

