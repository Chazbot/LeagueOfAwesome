package BothellBirder;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Bret Van Hof
 */
public class SimpleDataSource 
{
    private static String url = 
            "jdbc:sqlserver://24.22.248.54 ";
    private static String username = "IBirdUsers";
    private static String password = "guest";
    
    /**
      Gets a connection to the database.
     * @return the database connection
     */            
    public static Connection getconnection() throws SQLException
    {
        return DriverManager.getConnection(url, username, password);
    }
}
