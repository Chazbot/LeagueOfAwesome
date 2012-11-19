package BothellBird;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * @return player
 * @author Bret Van Hof
 */
public class ReadFile 
{
        private int rowCount;
        private HashMap<String, String> names;
        private Bird bird;
        
        public ReadFile()
        {
        	
        }
        public ReadFile(Bird aBird)
        {
        	bird = new Bird();
        	bird.setName(aBird.getName());
        	System.out.println(bird.getName());
        }
        /**
         * reads names of birds from an s.q.l server and creates a HashMap of 
         * Bird names
         * @return HashMap of birdNames
         * @throws SQLException 
         */    
    public HashMap<String, String> readNames() throws SQLException
    {
            Connection conn = SimpleDataSource.getconnection();
            Statement stat = null;
            stat = conn.createStatement();
            String query = "SELECT [Name] FROM" 
                    + " BirdDatabase.dbo.Birds";  //get database table
            ResultSet rs = null;
            rs = stat.executeQuery(query);
            int i = 0; 
            while(rs.next())
            {
                i++;
            }
            rowCount = i;
            names = new HashMap<String, String>();
            rs = stat.executeQuery(query);
            rs.next();
            for(int j= 0; j < rowCount; j++)
            {       
                names.put
                (rs.getString("Name").trim().toLowerCase(), rs.getString("Name").trim().toLowerCase());
                rs.next();
            }
            conn.close();
         return names;
    }
    public String readLocation() throws SQLException
    {
    	Connection conn = SimpleDataSource.getconnection();
    	Statement stat = null;
    	stat = conn.createStatement();
    	String query = "SELECT [Location] FROM BirdDatabase.dbo.Birds Where name = '" 
    			+ bird.getName() + "'";
    	ResultSet rs = null;
    	System.out.println("LOC");
    	rs = stat.executeQuery(query);
    	rs.next();
    	String location = rs.getString("Location");
    	conn.close();
    	return location;
    }
    public String readDescription() throws SQLException
    {
    	Connection conn = SimpleDataSource.getconnection();
    	Statement stat = null;
    	stat = conn.createStatement();
    	String query = "SELECT [Description] FROM BirdDatabase.dbo.Birds Where name = '" 
    			+ bird.getName() + "'";
    	ResultSet rs = null;
    	System.out.println("desc");
    	rs = stat.executeQuery(query);
    	rs.next();
    	String descript = rs.getString("Description");
    	conn.close();
    	return descript;
    }
    public boolean hasMap() throws SQLException
    {
    	Connection conn = SimpleDataSource.getconnection();
    	Statement stat = null;
    	stat = conn.createStatement();
    	String query = "SELECT [Map] FROM BirdDatabase.dbo.Birds Where name = '" 
    			+ bird.getName() + "'";
    	ResultSet rs = null;
    	System.out.println("Map");
    	rs = stat.executeQuery(query);
    	rs.next();
    	String map = rs.getString("Map");
    	conn.close();
    	return map.substring(0, 4).equalsIgnoreCase("true"); 	
    }
    public boolean hasPhoto() throws SQLException
    {
    	Connection conn = SimpleDataSource.getconnection();
    	Statement stat = null;
    	stat = conn.createStatement();
    	String query = "SELECT [Photo] FROM BirdDatabase.dbo.Birds Where name = '" 
    			+ bird.getName() + "'";
    	ResultSet rs = null;
    	System.out.println("Photo");
    	rs = stat.executeQuery(query);
    	rs.next();
    	String photo = rs.getString("Photo");
    	conn.close();
    	return photo.substring(0, 4).equalsIgnoreCase("true"); 	
    }
    public boolean hasSound() throws SQLException
    {
    	Connection conn = SimpleDataSource.getconnection();
    	Statement stat = null;
    	stat = conn.createStatement();
    	String query = "SELECT [Sound] FROM BirdDatabase.dbo.Birds Where name = '" 
    			+ bird.getName() + "'";
    	ResultSet rs = null;
    	System.out.println("sound");
    	rs = stat.executeQuery(query);
    	rs.next();
    	int sound = rs.getInt("sound");
    	conn.close();
    	return sound == 1; 	
    }
    public File getImage() throws SQLException
    {
            Connection conn = SimpleDataSource.getconnection();
            Statement stat = null;
            stat = conn.createStatement();
            String imageFileName = bird.getName() + "Photo.jpg";
            String query = "SELECT * FROM" 
                    + " BirdsDatabase.dbo.MyBirdStore Where name = '" 
            		+ imageFileName + "'";  //get database table
            ResultSet rs = null;
            rs = stat.executeQuery(query);
            rs.next();
            String outputBinaryFileName2 = "myImage.jpg";
            File outputBinaryFile2 = new File(outputBinaryFileName2);
            FileOutputStream  outputFileOutputStream = null;
            try {
				outputFileOutputStream  = new FileOutputStream(outputBinaryFile2);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            Blob image = rs.getBlob("file_stream");
            InputStream blobInputStream  = image.getBinaryStream();
            int                 bytesRead                   = 0;
            int                 bytesWritten                = 0;
            int                 totBytesRead                = 0;
            int                 totBytesWritten             = 0;

            int chunkSize = (int)image.length();
            byte[] binaryBuffer = new byte[chunkSize];
            
            try {
				while ((bytesRead = blobInputStream.read(binaryBuffer)) != -1) {
				    
				    // Loop through while reading a chunk of data from the BLOB
				    // column using an InputStream. This data will be stored
				    // in a temporary buffer that will be written to disk.
				    outputFileOutputStream.write(binaryBuffer, 0, bytesRead);
				    
				    totBytesRead += bytesRead;
				    totBytesWritten += bytesRead;

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            try {
				outputFileOutputStream.close();
            blobInputStream.close();
            
            } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
         conn.close();
         return outputBinaryFile2;
    }
    public File getMap() throws SQLException
    {
            Connection conn = SimpleDataSource.getconnection();
            Statement stat = null;
            stat = conn.createStatement();
            String imageFileName = bird.getName() + "Map.jpg";
            String query = "SELECT * FROM" 
                    + " BirdsDatabase.dbo.MyBirdStore Where name = '" 
            		+ imageFileName + "'";  //get database table
            ResultSet rs = null;
            rs = stat.executeQuery(query);
            rs.next();
            String outputBinaryFileName2 = "myMap.jpg";
            File outputBinaryFile2 = new File(outputBinaryFileName2);
            FileOutputStream  outputFileOutputStream = null;
            try {
				outputFileOutputStream  = new FileOutputStream(outputBinaryFile2);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            Blob image = rs.getBlob("file_stream");
            InputStream blobInputStream  = image.getBinaryStream();
            int                 bytesRead                   = 0;
            int                 bytesWritten                = 0;
            int                 totBytesRead                = 0;
            int                 totBytesWritten             = 0;

            int chunkSize = (int)image.length();
            byte[] binaryBuffer = new byte[chunkSize];
            
            try {
				while ((bytesRead = blobInputStream.read(binaryBuffer)) != -1) {
				    
				    // Loop through while reading a chunk of data from the BLOB
				    // column using an InputStream. This data will be stored
				    // in a temporary buffer that will be written to disk.
				    outputFileOutputStream.write(binaryBuffer, 0, bytesRead);
				    
				    totBytesRead += bytesRead;
				    totBytesWritten += bytesRead;

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            try {
				outputFileOutputStream.close();
            blobInputStream.close();
            
            } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
         conn.close();
         return outputBinaryFile2;
    }
    public File getSound() throws SQLException
    {
            Connection conn = SimpleDataSource.getconnection();
            Statement stat = null;
            stat = conn.createStatement();
            String imageFileName = bird.getName() + "Sound.wav";
            String query = "SELECT * FROM" 
                    + " BirdsDatabase.dbo.MyBirdStore Where name = '" 
            		+ imageFileName + "'";  //get database table
            ResultSet rs = null;
            rs = stat.executeQuery(query);
            rs.next();
            String outputBinaryFileName2 = "mySound.wav";
            File outputBinaryFile2 = new File(outputBinaryFileName2);
            FileOutputStream  outputFileOutputStream = null;
            try {
				outputFileOutputStream  = new FileOutputStream(outputBinaryFile2);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            Blob sound = rs.getBlob("file_stream");
            InputStream blobInputStream  = sound.getBinaryStream();
            int                 bytesRead                   = 0;
            int                 bytesWritten                = 0;
            int                 totBytesRead                = 0;
            int                 totBytesWritten             = 0;

            int chunkSize = (int)sound.length();
            byte[] binaryBuffer = new byte[chunkSize];
            
            try {
				while ((bytesRead = blobInputStream.read(binaryBuffer)) != -1) {
				    
				    // Loop through while reading a chunk of data from the BLOB
				    // column using an InputStream. This data will be stored
				    // in a temporary buffer that will be written to disk.
				    outputFileOutputStream.write(binaryBuffer, 0, bytesRead);
				    
				    totBytesRead += bytesRead;
				    totBytesWritten += bytesRead;

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            try {
				outputFileOutputStream.close();
            blobInputStream.close();
            
            } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
         conn.close();
         return outputBinaryFile2;
    }
  
}

