package BothellBirder;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageMaker 
{
	private FileMaker maker;
	private ImageIcon myIcon;
	public ImageMaker()
	{
		maker = new FileMaker();
	}
	public ImageIcon readData(int ID, int nameId) throws SQLException
	{
		Connection conn = SimpleDataSource.getconnection();
		Statement stat = null;
		stat = conn.createStatement();
		//get database table
		String query = "SELECT [hasMaleImage], [hasFemaleImage], [hasAmbiguousImage] FROM" 
				+ " BirdDatabase.dbo.Files where uniqueBirdID = '" + ID + "'";  
		ResultSet rs = null;
		rs = stat.executeQuery(query);
		String query2 = "SELECT [gender] FROM BirdDatabase.dbo.gender where uniqueBirdName"
				+ "= '" + nameId + "'";
		ResultSet rs2 = null;
		rs2 = stat.executeQuery(query2);
		rs = stat.executeQuery(query);
		rs.next();
		rs2.next();
		String gender = rs2.getString("gender");
		if(gender.charAt(0) == 'm' || gender.charAt(0) == 'M')
		{
			if(rs.getInt("hasMaleImage") > 0)
				try {
					myIcon = new ImageIcon(ImageIO.read(maker.make(ID, 'm', 1, "Jpg")));
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		else if(gender.charAt(0) == 'f' || gender.charAt(0) == 'F')
		{
			if(rs.getInt("hasFemaleImage") > 0)
				try {
					myIcon = new ImageIcon(ImageIO.read(maker.make(ID, 'f', 1, "Jpg")));
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		else if(rs.getInt("hasAmbiguousImage") > 0)
			try {
				myIcon = new ImageIcon(ImageIO.read(maker.make(ID, 'a', 1, "Jpg")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		else
			try {
					myIcon = new ImageIcon(ImageIO.read(maker.make(0, 'a' , 0, "Jpg")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		conn.close();
		return myIcon;
	}
	public ImageIcon bigImage(int ID, int nameId) throws SQLException
	{
		Connection conn = SimpleDataSource.getconnection();
		Statement stat = null;
		stat = conn.createStatement();
		//get database table
		String query = "SELECT [hasMaleImage], [hasFemaleImage], [hasAmbiguousImage] FROM" 
				+ " BirdDatabase.dbo.Files where uniqueBirdID = '" + ID + "'";  
		ResultSet rs = null;
		rs = stat.executeQuery(query);
		String query2 = "SELECT [gender] FROM BirdDatabase.dbo.gender where uniqueBirdName"
				+ "= '" + nameId + "'";
		ResultSet rs2 = null;
		rs2 = stat.executeQuery(query2);
		rs = stat.executeQuery(query);
		rs.next();
		rs2.next();
		String gender = rs2.getString("gender");
		if(gender.charAt(0) == 'm' || gender.charAt(0) == 'M')
		{
			if(rs.getInt("hasMaleImage") > 1)
				try {
					myIcon = new ImageIcon(ImageIO.read(maker.make(ID, 'm', 2, "Jpg")));
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		else if(gender.charAt(0) == 'f' || gender.charAt(0) == 'F')
		{
			if(rs.getInt("hasFemaleImage") > 1)
				try {
					myIcon = new ImageIcon(ImageIO.read(maker.make(ID, 'f', 2, "Jpg")));
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		else if(rs.getInt("hasAmbiguousImage") > 2)
			try {
				myIcon = new ImageIcon(ImageIO.read(maker.make(ID, 'a', 2, "Jpg")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		else
			try {
					myIcon = new ImageIcon(ImageIO.read(maker.make(0, 'a' , 0, "Jpg")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		conn.close();
		return myIcon;
	}
}
