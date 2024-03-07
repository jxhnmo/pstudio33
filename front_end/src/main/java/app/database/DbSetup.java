package app.database;

/*
 * This class represents the setup for the database.
 * It should provide the URL, username, and password for the database connection.
 */
public  final class DbSetup {
   public static final String name = System.getenv("DB_NAME");
   public static final String user = System.getenv("DB_USER");
   public static final String password = System.getenv("DB_PASSWORD");
   public static final String url = String.format("jdbc:postgresql://%s/%s", System.getenv("DB_LINK"), name);
}//end class