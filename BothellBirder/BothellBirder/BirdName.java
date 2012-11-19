package BothellBirder;

public class BirdName 
{
	private String name;
	private int nameId;
	private int birdId;
	
	public BirdName(String aName, int aBirdId, int aNameId) 
	{
		name = aName;
		nameId = aBirdId;
	    birdId = aNameId;
	}
	
	public int getNameId()
	{
		return nameId;
	}
	
	public int getBirdId()
	{
		return birdId;
	}

	public String getName()
	{
		return name;
	}
}
