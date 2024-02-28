package app.database;

/*
 * This class represents the setup for the database.
 * It should provide the URL, username, and password for the database connection.
 */
public  final class DbSetup {
   public static final String name = "csce315_903_03_db";
   public static final String user = "csce315_903_03_user";
   public static final String password = "PIJ5JlKq";
   public static final String url = String.format("jdbc:postgresql://csce-315-db.engr.tamu.edu/%s", name);
}//end class
  