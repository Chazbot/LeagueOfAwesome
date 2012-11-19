package BothellBirder;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SoundMaker 
{
	private FileMaker maker;
	private File myFile;
	
	public SoundMaker() 
	{
		maker = new FileMaker();
	}
	
	public File getSound(int ID, int nameId) throws SQLException
	{
		Connection conn = SimpleDataSource.getconnection();
		Statement stat = null;
		stat = conn.createStatement();
		//get database table
		String query = "SELECT [hasMaleSound], [hasFemaleSound], [hasAmbiguousSound] FROM" 
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
			if(rs.getInt("hasMaleSound") > 0)
				myFile = maker.make(ID, 'm', 1, "wav");
		}
		else if(gender.charAt(0) == 'f' || gender.charAt(0) == 'F')
		{
			if(rs.getInt("hasFemaleSound") > 0)
				myFile = maker.make(ID, 'f', 1, "wav");
		}
		else if(rs.getInt("hasAmbiguousImage") > 0)
			myFile = maker.make(ID, 'a', 1, "wav");
		else
			myFile = maker.make(0, 'a' , 0, "wav");
		conn.close();
		return myFile;
	}

}
