package BothellBird;
//Albert Lee
//10/21/2012
//FileInput.java
// How to store birds and search for birds
// Don't know how to use SQL, so used text file as dummy
//
// * -Will read birds from text file ( line by line ) 
//   -Put them into a Vector
//   -Sort them
//   -User able to search for bird by name

import java.io.FileInputStream; // file input stream
import java.io.FileNotFoundException; //filenotfoundexception
import java.util.Scanner; //input stream


//file reader class
//testing with text file
public class FileInput 
{
	public Scanner readData( String fileName )
	{
		Scanner fileData = null;
		
		try //try opening textfile. File name inputted by user without the .txt
		{
			fileData = new Scanner (new FileInputStream( fileName + ".txt" ));
		}
		catch( FileNotFoundException e)
		{
			//if file not found exit
			System.out.println( "file not found" );
			System.exit(0);
		}
		
		//confirm filename
		System.out.println( "File opened is: " + fileName );
		if( fileData.hasNext() )
		{
			//check if file is empty
			System.out.println( "InputFile is not Empty" );
		}
		
		//returns an input stream
		return fileData;
	}
}