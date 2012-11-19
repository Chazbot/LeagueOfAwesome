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
        ResultSet[] rs = new ResultSet[8];
        int i = 0;
		for(String query : queries)
		{
			rs[i] = stat.executeQuery(query);
			i++;
		}
		
		for(int j = 0; j < rs.length; j++)
		{
			rs[j].next();
			switch(j)
			{
				case 0:
					features.put(rs[j].getInt("familynameID"), 
							rs[j].getString("familyname"));
					break;
				case 1:
					features.put(rs[j].getInt("SecondaryColorID"), 
							rs[j].getString("secondaryColor"));
					break;
				case 2:
					features.put(rs[j].getInt("PColorIDs"), 
							rs[j].getString("PrimaryColors"));
					break;
				case 3:
					features.put(rs[j].getInt("FeederFrequencyId"), 
							rs[j].getString("FeederFrequency"));
					break;
				case 4:
					features.put(rs[j].getInt("HabitatId"), 
							rs[j].getString("habitatName"));
					break;
				case 5:
					features.put(rs[j].getInt("ConservationStausID"), 
							rs[j].getString("ConservationStaus"));
					break;
				case 6:
					features.put(rs[j].getInt("SizeId"), 
							rs[j].getString("Size"));
					break;
				case 7:
					features.put(rs[j].getInt("LocationID"), 
							rs[j].getString("locationName"));
					break;
				default:
					break;
			}
		}
		moreQueries(ID);
		getBirdFeatures();
		for(String bird: descriptions)
		{
			description += bird;
		}
		return description;
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
	
	public int[] evenMoreQueries(String a)
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
	
	public void getBirdFeatures() throws SQLException
	{
		createQueries();
		Connection conn = SimpleDataSource.getconnection();
        Statement stat = null;
        stat = conn.createStatement();
        ResultSet[] rs = new ResultSet[8];
        int i = 0;
        for(String query : queries)
		{
			rs[i] = stat.executeQuery(query);
			i++;
		}
		i = 0;
		for(int j = 0; j < rs.length; j++)
		{
			String temp = "";
			switch(j)
			{
				case 0:
					temp += "Bird's Family: ";
					while(rs[j].next())
			        {
			            i++;
			        }
					rs[j] = stat.executeQuery(queries[j]);
				    rs[j].next();
				    for(int k= 0; k < i - 1; k++)//populate arraylist
				    {    
				    	temp += features.get(rs[j].getInt("uniqueFamilyId") + ", ");
				    	rs[j].next();
				    }
				    temp += features.get(rs[j].getInt("uniqueFamilyId") + "\n");
					descriptions.add(temp);
					break;
				case 1:
					temp += "Bird's Secondary Color: ";
					while(rs[j].next())
			        {
			            i++;
			        }
					rs[j] = stat.executeQuery(queries[j]);
				    rs[j].next();
				    for(int k= 0; k < i - 1; k++)//populate arraylist
				    {    
				    	temp += features.get(rs[j].getInt("BirdSecondaryColor") + ", ");
				    	rs[j].next();
				    }
				    temp += features.get(rs[j].getInt("BirdSecondaryColor") + "\n");
					descriptions.add(temp);
					break;
				case 2:
					temp += "Bird's Primary Color: ";
					while(rs[j].next())
			        {
			            i++;
			        }
					rs[j] = stat.executeQuery(queries[j]);
				    rs[j].next();
				    for(int k= 0; k < i - 1; k++)//populate arraylist
				    {    
				    	temp += features.get(rs[j].getInt("birdPrimaryColor") + ", ");
				    	rs[j].next();
				    }
				    temp += features.get(rs[j].getInt("birdPrimaryColor") + "\n");
					descriptions.add(temp);
					break;
				case 3:
					temp += "Bird's Backyard Feeder Frequency: ";
					while(rs[j].next())
			        {
			            i++;
			        }
					rs[j] = stat.executeQuery(queries[j]);
				    rs[j].next();
				    for(int k= 0; k < i - 1; k++)//populate arraylist
				    {    
				    	temp += features.get(rs[j].getInt("BirdFeederFrequency") + ", ");
				    	rs[j].next();
				    }
				    temp += features.get(rs[j].getInt("BirdFeederFrequency") + "\n");
					descriptions.add(temp);
					break;
				case 4:
					temp += "Bird's Habitat: ";
					while(rs[j].next())
			        {
			            i++;
			        }
					rs[j] = stat.executeQuery(queries[j]);
				    rs[j].next();
				    for(int k= 0; k < i - 1; k++)//populate arraylist
				    {    
				    	temp += features.get(rs[j].getInt("BirdHabitat") + ", ");
				    	rs[j].next();
				    }
				    temp += features.get(rs[j].getInt("BirdHabitat") + "\n");
					descriptions.add(temp);
					break;
				case 5:
					temp += "Bird's Conservation Status: ";
					while(rs[j].next())
			        {
			            i++;
			        }
					rs[j] = stat.executeQuery(queries[j]);
				    rs[j].next();
				    for(int k= 0; k < i - 1; k++)//populate arraylist
				    {    
				    	temp += features.get(rs[j].getInt("BirdConservationStatus") + ", ");
				    	rs[j].next();
				    }
				    temp += features.get(rs[j].getInt("BirdConservationStatus") + "\n");
					descriptions.add(temp);
					break;
				case 6:
					temp += "Bird's Size: ";
					while(rs[j].next())
			        {
			            i++;
			        }
					rs[j] = stat.executeQuery(queries[j]);
				    rs[j].next();
				    for(int k= 0; k < i - 1; k++)//populate arraylist
				    {    
				    	temp += features.get(rs[j].getInt("BirdSize") + ", ");
				    	rs[j].next();
				    }
				    temp += features.get(rs[j].getInt("BirdSize") + "\n");
					descriptions.add(temp);
					break;
				case 7:
					temp += "Bird's Location: ";
					while(rs[j].next())
			        {
			            i++;
			        }
					rs[j] = stat.executeQuery(queries[j]);
				    rs[j].next();
				    for(int k= 0; k < i - 1; k++)//populate arraylist
				    {    
				    	temp += features.get(rs[j].getInt("BirdLocation") + ", ");
				    	rs[j].next();
				    }
				    temp += features.get(rs[j].getInt("BirdLocation") + "\n");
					descriptions.add(temp);
					break;
				default:
					break;
			}
		}
	}
	
	public Map<String, ArrayList<String>> getFeatures() throws SQLException
	{
		createQueries();
		Map<String, ArrayList<String>> stuff = new HashMap<String, ArrayList<String>>();
		ArrayList<String> feature = new ArrayList<String>();
		Connection conn = SimpleDataSource.getconnection();
        Statement stat = null;
        stat = conn.createStatement();
        ResultSet[] rs = new ResultSet[8];
        int i = 0;
        for(String query : queries)
		{
			rs[i] = stat.executeQuery(query);
			i++;
		}
        for(int k= 0; k < i; k++)//populate arraylist
	    {
        	int j = 0;
        	switch(k)
			{
        		case 0:
        			while(rs[k].next())
			        {
			            j++;
			        }
					rs[k] = stat.executeQuery(queries[k]);
				    rs[k].next();
				    for(int l = 0; l < j; l++)//populate arraylist
				    {    
				    	feature.add(rs[k].getString("familyname"));
				    	feature.add(rs[k].getInt("familynameID") + "");
				    	rs[k].next();
				    }
				    stuff.put("Family", feature);
				    feature.clear();
        			break;
        		case 1:
        			while(rs[k].next())
			        {
			            j++;
			        }
					rs[k] = stat.executeQuery(queries[k]);
				    rs[k].next();
				    for(int l = 0; l < j; l++)//populate arraylist
				    {    
				    	feature.add(rs[k].getString("secondaryColor"));
				    	feature.add(rs[k].getInt("SecondaryColorID") + "");
				    	rs[k].next();
				    }
				    stuff.put("Secondary Color", feature);
				    feature.clear();
        			break;
        		case 2:
        			while(rs[k].next())
			        {
			            j++;
			        }
					rs[k] = stat.executeQuery(queries[k]);
				    rs[k].next();
				    for(int l= 0; l < j; l++)//populate arraylist
				    {    
				    	feature.add(rs[k].getString("PrimaryColors"));
				    	feature.add(rs[k].getInt("PColorsIDs") + "");
				    	rs[k].next();
				    }
				    stuff.put("Primary Color", feature);
				    feature.clear();
        			break;
        		case 3:
        			while(rs[k].next())
			        {
			            j++;
			        }
					rs[k] = stat.executeQuery(queries[k]);
				    rs[k].next();
				    for(int l= 0; l < j; l++)//populate arraylist
				    {    
				    	feature.add(rs[k].getString("FeederFrequency"));
				    	feature.add(rs[k].getInt("FeederFrequencyID") + "");
				    	rs[k].next();
				    }
				    stuff.put("Feeding Frequency", feature);
				    feature.clear();
        			break;
        		case 4:
        			while(rs[k].next())
			        {
			            j++;
			        }
					rs[k] = stat.executeQuery(queries[k]);
				    rs[k].next();
				    for(int l= 0; l < j; l++)//populate arraylist
				    {    
				    	feature.add(rs[k].getString("habitatName"));
				    	feature.add(rs[k].getInt("HabitatId") + "");
				    	rs[k].next();
				    }
				    stuff.put("Habitat", feature);
				    feature.clear();
        			break;
        		case 5:
        			while(rs[k].next())
			        {
			            j++;
			        }
					rs[k] = stat.executeQuery(queries[k]);
				    rs[k].next();
				    for(int l= 0; l < j; l++)//populate arraylist
				    {    
				    	feature.add(rs[k].getString("ConservationStatus"));
				    	feature.add(rs[k].getInt("ConservationStatusID") + "");
				    	rs[k].next();
				    }
				    stuff.put("Conservation Status", feature);
				    feature.clear();
        			break;
        		case 6:
        			while(rs[k].next())
			        {
			            j++;
			        }
					rs[k] = stat.executeQuery(queries[k]);
				    rs[k].next();
				    for(int l= 0; l < j; l++)//populate arraylist
				    {    
				    	feature.add(rs[k].getString("size"));
				    	feature.add(rs[k].getInt("SizeID") + "");
				    	rs[k].next();
				    }
				    stuff.put("Size", feature);
				    feature.clear();
        			break;
        		case 7:
        			while(rs[k].next())
			        {
			            j++;
			        }
					rs[k] = stat.executeQuery(queries[k]);
				    rs[k].next();
				    for(int l= 0; l < j; l++)//populate arraylist
				    {    
				    	feature.add(rs[k].getString("locationName"));
				    	feature.add(rs[k].getInt("LocationID") + "");
				    	rs[k].next();
				    }
				    stuff.put("Location", feature);
				    feature.clear();
        			break;
        		default:
        			break;
	    }
	  }
      return stuff;
	}
}
