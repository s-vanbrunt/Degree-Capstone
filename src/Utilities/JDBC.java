package Utilities;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Obtain a sql database connection and close it.
 *
 * @author Scott VanBrunt
 */
public abstract class JDBC {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String dbname = "client_schedule";
    private static final String connUrl = protocol + vendor + location + dbname + "?connectionTimeZone = SERVER";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String userName = "sqlUser";
    private static final String password = "Passw0rd!";
    public static Connection conn;

    /**
     * Opens a connection with specified database.
     */
    public static void openConnection(){
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(connUrl, userName, password);
            System.out.println("Connection established.");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Closes database connection.
     */
    public static void closeConnection(){
        try{
            conn.close();
            System.out.println("Connection closed.");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
