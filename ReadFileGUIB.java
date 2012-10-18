

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;

import javax.imageio.stream.FileImageInputStream;
/**
 * @return player
 * @author Bret Van Hof
 */
public class ReadFile 
{
        private int rowCount;
        private ArrayList<String> bird;
        /**
         * reads data from an s.q.l server and creates an array list of 
         * Bird objects
         * @return list of birds
         * @throws SQLException 
         */    
    public File readData() throws SQLException
    {
            Connection conn = SimpleDataSource.getconnection();
            Statement stat = null;
            stat = conn.createStatement();
            String query = "SELECT * FROM" 
                    + " BirdsDatabase.dbo.MyBirdStore Where name = 'eaglePhoto.jpg'";  //get database table
            ResultSet rs = null;
            rs = stat.executeQuery(query);
            int i = 0; 
            while(rs.next())
            {
                i++;
            }
            rowCount = i;
            bird = new ArrayList<String>();
            rs = stat.executeQuery(query);
            rs.next();
            for(int j= 0; j < rowCount; j++)//populate arraylist
            {    
            	bird.add(new String());       
                bird.set(j, rs.getString("name"));
                System.out.println(bird.get(j));
                rs.next();
            }
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
   /**
     * returns the number of rows of data in the Birds database
     * @return rowCount
     */
    public int rowCountReturn()
    {
        return rowCount;
    }
}

