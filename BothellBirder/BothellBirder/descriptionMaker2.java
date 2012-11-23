package BothellBirder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class descriptionMaker
{
	private Map<Integer, String> features = new HashMap<Integer, String>();
	String[] queries = new String[8];
	private ArrayList<String> descriptions = new ArrayList<String>();
	
	public descriptionMaker() 
	{
		
	}
	
	public String getDescription(int ID) throws SQLException
	{
		String description = "";
		createQueries();
		Connection conn = SimpleDataSource.getconnection();
        Statement stat = null;
        stat = conn.createStatement();
        ResultSet rs = null;
		for(int i = 0; i < queries.length; i++)
		{
			rs = stat.executeQuery(queries[i]);
			rs.next();
			process(i, rs);
		}
		moreQueries(ID);
		getBirdFeatures(ID);
		for(String bird: descriptions)
		{
			description += bird;
		}
		return description;
	}
	
	public void processAgain(int j, ResultSet rs, Statement stat) throws SQLException
	{
		int i = 0;
			String temp = "";
			switch(j)
			{
				case 0:
					temp += "Bird's Family: ";
					while(rs.next())
			        {
			            i++;
			        }
					rs = stat.executeQuery(queries[j]);
				    rs.next();
				    for(int k= 0; k < i - 1; k++)//populate arraylist
				    {    
				    	temp += features.get(rs.getInt("uniqueFamilyId") + ", ");
				    	rs.next();
				    }
				    temp += features.get(rs.getInt("uniqueFamilyId") + "\n");
					descriptions.add(temp);
					break;
				case 1:
					temp += "Bird's Secondary Color: ";
					while(rs.next())
			        {
			            i++;
			        }
					rs = stat.executeQuery(queries[j]);
				    rs.next();
				    for(int k= 0; k < i - 1; k++)//populate arraylist
				    {    
				    	temp += features.get(rs.getInt("BirdSecondaryColor") + ", ");
				    	rs.next();
				    }
				    temp += features.get(rs.getInt("BirdSecondaryColor") + "\n");
					descriptions.add(temp);
					break;
				case 2:
					temp += "Bird's Primary Color: ";
					while(rs.next())
			        {
			            i++;
			        }
					rs = stat.executeQuery(queries[j]);
				    rs.next();
				    for(int k= 0; k < i - 1; k++)//populate arraylist
				    {    
				    	temp += features.get(rs.getInt("birdPrimaryColor") + ", ");
				    	rs.next();
				    }
				    temp += features.get(rs.getInt("birdPrimaryColor") + "\n");
					descriptions.add(temp);
					break;
				case 3:
					temp += "Bird's Backyard Feeder Frequency: ";
					while(rs.next())
			        {
			            i++;
			        }
					rs = stat.executeQuery(queries[j]);
				    rs.next();
				    for(int k= 0; k < i - 1; k++)//populate arraylist
				    {    
				    	temp += features.get(rs.getInt("BirdFeederFrequency") + ", ");
				    	rs.next();
				    }
				    temp += features.get(rs.getInt("BirdFeederFrequency") + "\n");
					descriptions.add(temp);
					break;
				case 4:
					temp += "Bird's Habitat: ";
					while(rs.next())
			        {
			            i++;
			        }
					rs = stat.executeQuery(queries[j]);
				    rs.next();
				    for(int k= 0; k < i - 1; k++)//populate arraylist
				    {    
				    	temp += features.get(rs.getInt("BirdHabitat") + ", ");
				    	rs.next();
				    }
				    temp += features.get(rs.getInt("BirdHabitat") + "\n");
					descriptions.add(temp);
					break;
				case 5:
					temp += "Bird's Conservation Status: ";
					while(rs.next())
			        {
			            i++;
			        }
					rs = stat.executeQuery(queries[j]);
				    rs.next();
				    for(int k= 0; k < i - 1; k++)//populate arraylist
				    {    
				    	temp += features.get(rs.getInt("BirdConservationStatus") + ", ");
				    	rs.next();
				    }
				    temp += features.get(rs.getInt("BirdConservationStatus") + "\n");
					descriptions.add(temp);
					break;
				case 6:
					temp += "Bird's Size: ";
					while(rs.next())
			        {
			            i++;
			        }
					rs = stat.executeQuery(queries[j]);
				    rs.next();
				    for(int k= 0; k < i - 1; k++)//populate arraylist
				    {    
				    	temp += features.get(rs.getInt("BirdSize") + ", ");
				    	rs.next();
				    }
				    temp += features.get(rs.getInt("BirdSize") + "\n");
					descriptions.add(temp);
					break;
				case 7:
					temp += "Bird's Location: ";
					while(rs.next())
			        {
			            i++;
			        }
					rs = stat.executeQuery(queries[j]);
				    rs.next();
				    for(int k= 0; k < i - 1; k++)//populate arraylist
				    {    
				    	temp += features.get(rs.getInt("BirdLocation") + ", ");
				    	rs.next();
				    }
				    temp += features.get(rs.getInt("BirdLocation") + "\n");
					descriptions.add(temp);
					break;
				default:
					break;
			}
		}
	
	public void process(int j, ResultSet rs) throws SQLException
	{
		switch(j)
		{
			case 0:
				features.put(rs.getInt("familynameID"), 
						rs.getString("familyname"));
				break;
			case 1:
				features.put(rs.getInt("SecondaryColorID"), 
						rs.getString("secondaryColor"));
				break;
			case 2:
				features.put(rs.getInt("PColorIDs"), 
						rs.getString("PrimaryColors"));
				break;
			case 3:
				features.put(rs.getInt("FeederFrequencyId"), 
						rs.getString("FeederFrequency"));
				break;
			case 4:
				features.put(rs.getInt("HabitatId"), 
						rs.getString("habitatName"));
				break;
			case 5:
				features.put(rs.getInt("ConservationStatusID"), 
						rs.getString("ConservationStatus"));
				break;
			case 6:
				features.put(rs.getInt("SizeId"), 
						rs.getString("Size"));
				break;
			case 7:
				features.put(rs.getInt("LocationID"), 
						rs.getString("locationName"));
				break;
			default:
				break;
		}
	}
	
	public void createQueries()
	{
		queries[0] = "SELECT [familynameID], [familyname] FROM" 
                + " BirdDatabase.dbo.Family";
		queries[1] = "SELECT [SecondaryColorID], [secondaryColor] FROM" 
                + " BirdDatabase.dbo.SecondaryColor";
		queries[2] = "SELECT [PColorIDs], [PrimaryColors] FROM" 
                + " BirdDatabase.dbo.PrimaryColors";
		queries[3] = "SELECT [FeederFrequencyId], [FeederFrequency] FROM" 
                + " BirdDatabase.dbo.FeederFrequency";
		queries[4] = "SELECT [HabitatId], [habitatName] FROM" 
                + " BirdDatabase.dbo.Habitats";
		queries[5] = "SELECT [ConservationStatusID], [ConservationStatus] FROM" 
                + " BirdDatabase.dbo.ConservationStatus";
		queries[6] = "SELECT [SizeId], [Size] FROM" 
                + " BirdDatabase.dbo.size";
		queries[7] = "SELECT [LocationID], [locationName] FROM" 
                + " BirdDatabase.dbo.Locations";
	}
	
	public void moreQueries(int id)
	{
		queries[0] = "SELECT [uniqueFamilyId] FROM" 
                + " BirdDatabase.dbo.BirdFamilies where uniqueBirdId = " + id;
		queries[1] = "SELECT [BirdSecondaryColor] FROM" 
                + " BirdDatabase.dbo.BirdSecondaryColors where UniqueBirdID = " + id;
		queries[2] = "SELECT [birdPrimaryColor] FROM" 
                + " BirdDatabase.dbo.BirdPrimaryColor where UniqueBirdID = " + id;
		queries[3] = "SELECT [BirdFeederFrequency] FROM" 
                + " BirdDatabase.dbo.BirdFeederFrequency where UniqueBirdID = " + id;
		queries[4] = "SELECT [birdHabitat] FROM" 
                + " BirdDatabase.dbo.BirdHabitat where uniqueBirdID = " + id;
		queries[5] = "SELECT [BirdConservationStatus] FROM" 
                + " BirdDatabase.dbo.BirdConservationStatus where UniqueBirdId = " + id;
		queries[6] = "SELECT [BirdSize] FROM" 
                + " BirdDatabase.dbo.BirdSize where UniqueBirdID = " + id;
		queries[7] = "SELECT [BirdLocation] FROM" 
                + " BirdDatabase.dbo.BirdLocations where UniqueBirdId = " + id;
	}
	
	public void evenMoreQueries(String id)
	{
		queries[0] = "SELECT [uniqueFamilyId] FROM" 
                + " BirdDatabase.dbo.BirdFamilies where uniqueBirdId = '" + id +"'";
		queries[1] = "SELECT [BirdSecondaryColor] FROM" 
                + " BirdDatabase.dbo.BirdSecondaryColors where UniqueBirdID = '" + id +"'";
		queries[2] = "SELECT [birdPrimaryColor] FROM" 
                + " BirdDatabase.dbo.BirdPrimaryColor where UniqueBirdID = '" + id +"'";
		queries[3] = "SELECT [BirdFeederFrequency] FROM" 
                + " BirdDatabase.dbo.BirdFeederFrequency where UniqueBirdID = '" + id +"'";
		queries[4] = "SELECT [birdHabitat] FROM" 
                + " BirdDatabase.dbo.BirdHabitat where uniqueBirdID = '" + id +"'";
		queries[5] = "SELECT [BirdConservationStatus] FROM" 
                + " BirdDatabase.dbo.BirdConservationStatus where UniqueBirdId = '" + id +"'";
		queries[6] = "SELECT [BirdSize] FROM" 
                + " BirdDatabase.dbo.BirdSize where UniqueBirdID = '" + id +"'";
		queries[7] = "SELECT [BirdLocation] FROM" 
                + " BirdDatabase.dbo.BirdLocations where UniqueBirdId = '" + id +"'";

	}
	
	public void getBirdFeatures(int ID) throws SQLException
	{
		createQueries();
		Connection conn = SimpleDataSource.getconnection();
        Statement stat = null;
        stat = conn.createStatement();
        ResultSet rs = null;
        moreQueries(ID);
        for(int i = 0; i < 8; i++)
		{
			rs = stat.executeQuery(queries[i]);
			rs.next();
			processAgain(i, rs, stat);
		}
		
	}
	
	public Map<String, ArrayList<String>> getFeatures() throws SQLException
	{
		createQueries();
		Map<String, ArrayList<String>> stuff = new HashMap<String, ArrayList<String>>();
		Connection conn = SimpleDataSource.getconnection();
        Statement stat = null;
        stat = conn.createStatement();
        ResultSet rs = null;
        for(int i = 0; i < 8; i++)
		{
			rs = stat.executeQuery(queries[i]);
			rs.next();
			stuff = aProcess(i, rs, stat, stuff);
		}
      return stuff;
	}
	
	
	
	public Map<String, ArrayList<String>> aProcess
		(int i, ResultSet rs, Statement stat, Map<String, ArrayList<String>> stuff) throws SQLException
	{
        	int j = 0;
        	switch(i)
			{
        		case 0:
        			rs = stat.executeQuery(queries[i]);
        			ArrayList<String> feature = new ArrayList<String>();
        			while(rs.next())
			        {
			            j++;
			        }
					rs = stat.executeQuery(queries[i]);
				    rs.next();
				    for(int l = 0; l < j; l++)//populate arraylist
				    {    
				    	feature.add(rs.getString("familyname"));
				    	feature.add(rs.getInt("familynameID") + "");
				    	rs.next();
				    }
				    stuff.put("Family", feature);
        			break;
        		case 1:
        			rs = stat.executeQuery(queries[i]);
        			ArrayList<String> featureA = new ArrayList<String>();
        			while(rs.next())
			        {
			            j++;
			        }
					rs = stat.executeQuery(queries[i]);
				    rs.next();
				    for(int l = 0; l < j; l++)//populate arraylist
				    {    
				    	featureA.add(rs.getString("secondaryColor"));
				    	featureA.add(rs.getInt("SecondaryColorID") + "");
				    	rs.next();
				    }
				    stuff.put("Secondary Color", featureA);
        			break;
        		case 2:
        			rs = stat.executeQuery(queries[i]);
        			ArrayList<String> featureB = new ArrayList<String>();
        			while(rs.next())
			        {
			            j++;
			        }
        			rs = stat.executeQuery(queries[i]);
				    rs.next();
				    for(int l= 0; l < j; l++)//populate arraylist
				    {    
				    	featureB.add(rs.getString("PrimaryColors"));
				    	featureB.add(rs.getInt("PColorIDs") + "");
				    	rs.next();
				    }
				    stuff.put("Primary Color", featureB);
        			break;
        		case 3:
        			rs = stat.executeQuery(queries[i]);
        			ArrayList<String> featureC = new ArrayList<String>();
        			while(rs.next())
			        {
			            j++;
			        }
					rs = stat.executeQuery(queries[i]);
				    rs.next();
				    for(int l= 0; l < j; l++)//populate arraylist
				    {    
				    	featureC.add(rs.getString("FeederFrequency"));
				    	featureC.add(rs.getInt("FeederFrequencyID") + "");
				    	rs.next();
				    }
				    stuff.put("Feeding Frequency", featureC);
        			break;
        		case 4:
        			rs = stat.executeQuery(queries[i]);
        			ArrayList<String> featureD = new ArrayList<String>();
        			while(rs.next())
			        {
			            j++;
			        }
					rs = stat.executeQuery(queries[i]);
				    rs.next();
				    for(int l= 0; l < j; l++)//populate arraylist
				    {    
				    	featureD.add(rs.getString("habitatName"));
				    	featureD.add(rs.getInt("HabitatId") + "");
				    	rs.next();
				    }
				    stuff.put("Habitat", featureD);
        			break;
        		case 5:
        			rs = stat.executeQuery(queries[i]);
        			while(rs.next())
			        {
			            j++;
			        }
					rs = stat.executeQuery(queries[i]);
					ArrayList<String> featureE = new ArrayList<String>();
				    rs.next();
				    for(int l= 0; l < j; l++)//populate arraylist
				    {    
				    	featureE.add(rs.getString("ConservationStatus"));
				    	featureE.add(rs.getInt("ConservationStatusID") + "");
				    	rs.next();
				    }
				    stuff.put("Conservation Status", featureE);
        			break;
        		case 6:
        			rs = stat.executeQuery(queries[i]);
        			ArrayList<String> featureF = new ArrayList<String>();
        			while(rs.next())
			        {
			            j++;
			        }
					rs = stat.executeQuery(queries[i]);
				    rs.next();
				    for(int l= 0; l < j; l++)//populate arraylist
				    {    
				    	featureF.add(rs.getString("size"));
				    	featureF.add(rs.getInt("SizeID") + "");
				    	rs.next();
				    }
				    stuff.put("Size", featureF);
        			break;
        		case 7:
        			rs = stat.executeQuery(queries[i]);
        			ArrayList<String> featureG = new ArrayList<String>();
        			while(rs.next())
			        {
			            j++;
			        }
					rs = stat.executeQuery(queries[i]);
				    rs.next();
				    for(int l= 0; l < j; l++)//populate arraylist
				    {    
				    	featureG.add(rs.getString("locationName"));
				    	featureG.add(rs.getInt("LocationID") + "");
				    	System.out.println(rs.getString("locationName"));
				    	rs.next();
				    }
				    stuff.put("Location", featureG);
        			break;
        		default:
        			break;
	    }
        	return stuff;
	}
	
}
