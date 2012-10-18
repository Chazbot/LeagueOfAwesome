

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Bret Van Hof
 */
public class Bird 
{
    String name;
    String description;
    String map;
    String photo;
    String location;
    int rowNumber;
     /**
     * Default TBird Constructor
     */   
    public Bird()
    {
        
    }
    
    
    /**
     * sets the name of Bird object
     * @param first 
     */
    public void setName(String bird)
    {
        name = bird;
    }
    /**
     * returns the name of a Bird object
     * @return name
     */
    public String getName()
    {
        return name;
    }
    /**
     * sets the description of a Bird object
     * @param describe
     */
    public void setDescription(String describe)
    {
       description = describe;
    }
    /**
     * returns the description of a Bird object
     * @return description
     */
    public String getDescription()
    {
        return description;
    }
    /**
     * sets the map Url of a Bird object
     * @param _Map 
     */
    public void setMap(String _Map)
    {
        map = _Map;
    }
    /**
     * returns the Map URL of a Bird object
     * @return map
     */
    public String getMap()
    {
        return map;
    }
    /**
     * Sets the photo URL of a Bird object 
     * @param _Photo 
     */
    public void setPhoto(String _Photo)
    {        
        photo = _Photo;
    }
    /**
     * returns the photo URL of a Bird object 
     * @return phoneNumber 
     */
    public String getPhoto()
    {
        return photo;
    }
    /**
     * returns the location of Bird object
     * @return location
     */
    public String getLocation()
    {
        return location;
    }
    /**
     * sets the location of Bird object
     * @param _location
     */
    public void setLocation(String _location)
    {
        location = _location;
    }
    /**
     * sets the row index of a 0 based row index of a Bird object
     * @param number 
     */
    public void setNumber (int number)
    {
        rowNumber = number;
    }
    /**
     * returns the row index of a 0 based row index of a Bird object
     * @return rowNumber
     */
    public int getNumber()
    {
        return rowNumber;
    }
    
}