

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.*;
import java.util.ArrayList;
/**
 * @return player
 * @author Bret Van Hof
 */
public class ReadFile 
{
        private int rowCount;
        private ArrayList<Bird> bird;
        /**
         * reads data from an s.q.l server and creates an array list of 
         * Bird objects
         * @return list of birds
         * @throws SQLException 
         */    
    public ArrayList readData() throws SQLException
    {
            Connection conn = SimpleDataSource.getconnection();
            Statement stat = null;
            stat = conn.createStatement();
            String query = "SELECT [Name], [Description], " + 
                   "[Map], [Photo]," 
                    + "[Location], this FROM" 
                    + " BirdDatabase.dbo.Birds";  //get database table
            ResultSet rs = null;
            rs = stat.executeQuery(query);
            int i = 0; 
            while(rs.next())
            {
                i++;
            }
            rowCount = i;
            bird = new ArrayList<Bird>();
            rs = stat.executeQuery(query);
            rs.next();
            for(int j= 0; j < rowCount; j++)//populate arraylist
            {    
                bird.add(new Bird());       
                bird.get(j).setName(rs.getString("Name"));
                bird.get(j).setDescription
                        (rs.getString("Description"));
                bird.get(j).setMap(rs.getString
                        ("Map"));
                bird.get(j).setPhoto(rs.getString("Photo"));
                bird.get(j).setLocation(rs.getString("Location"));
                bird.get(j).setNumber(rs.getInt("this"));
                rs.next();
            }
            conn.close();
         return bird;
    }
   /**
     * returns the number of rows of data in the Birds database
     * @return rowCount
     */
    public int rowCountReturn()
    {
        return rowCount;
    }
}

