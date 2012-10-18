

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author Bret Van Hof
 */
public class WriteFile {
    /**
     * writes user supplied data to a table of values in an s.q.l server
     * @param name
     * @param description
     * @param map
     * @param photo
     * @param location
     * @param index
     * @throws SQLException 
     */
    public void writeData(String name, String description, String map, String photo, 
            String location, int index) throws SQLException
    {
            Connection conn = SimpleDataSource.getconnection();
            Statement stat = null;
            stat = conn.createStatement();
            stat.executeUpdate("INSERT INTO BirdDatabase." + 
                 "dbo.Birds VALUES (" + "'" + name + "'" + "," +"'" + description +
                    "'" + "," + "'" + map + "'" + "," + "'" + photo + "'" + 
                    "," + "'" + location + "'" + "," + "'" + index + "'" +")");
            //write to database
            conn.close();
    }
    /**
     * used to edit a data table entry/ edits info about a player
     * @param name
     * @param description
     * @param map
     * @param photo
     * @param location
     * @param index
     * @throws SQLException 
     */
    public void editData(String name, String description, String map, 
            String photo, String location, int index) throws SQLException
    {
        Connection conn = SimpleDataSource.getconnection();
        Statement stat = null;
        stat = conn.createStatement();
        stat.executeUpdate("UPDATE BirdDatabase.dbo.Birds Set [Name]"
                + " = '" + name + "'," + " [Description] = '" + description 
                + "'" + ",[Map] = '" + map + "'" + 
                ",[Photo] = '" + photo + "'" +
                ",[Location] = '" + location + "'" + ", this = " + 
                index + "Where this = '"  + index + "'");//update database
        conn.close();
    }
    /**
     * deletes a bird from the database
     * @param index
     * @param birds 
     */
    public void deleteData(int index, ArrayList<Bird> birds)
    {
        if(index >= 0)
        {        
            try
            {
                Connection conn = SimpleDataSource.getconnection();
                Statement stat = null;
                stat = conn.createStatement();
                stat.executeUpdate("Delete From BirdDatabase.dbo.Birds Where" 
                        + " " + "this = " + index);//delete member
                String query = "UPDATE BirdDatabase.dbo.Birds Set this = ";
                int rowCount = birds.size() - 1;
                if(rowCount > 0)
                {       
                    for(int j= index; j < rowCount; j++)
                    {
                        stat.executeUpdate(query + j + " Where this = " 
                                + (j + 1));//reorder database
                    }    
                    conn.close();
                }
                else if(rowCount == 0)
                {
                    stat.executeUpdate(query + 0);
                }
                   
             }
            catch(SQLException ex)
          {
            System.out.println(ex);
          }
       }   
   }
    /**
     * deletes all data in the database
     */
    public void deleteAll()
    {
        try
        {
            Connection conn = SimpleDataSource.getconnection();
            Statement stat = null;
            stat = conn.createStatement();
            stat.executeUpdate("Delete From BirdDatabase.dbo.Birds");
            //remove all data from table
        }
        catch(SQLException ex)
        {
            System.out.println(ex);
        }
    }
}
