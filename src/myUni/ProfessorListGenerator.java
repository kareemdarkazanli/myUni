package myUni;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.PrintWriter;
/**
 * populates the professors.txt file with the database
 * uses random names generated into the names.txt file from
 * https://www.randomlists.com/last-names
 * @author JonathanNeel
 *
 */
public class ProfessorListGenerator {
	public static void main(String[] args) throws IOException
	{
		ArrayList<String> randomNames = new ArrayList<String>();
		String[] majors = {"Art","Biology","Business", "Communications",
				"CS","EE", "English", "History","Kinesiology",
				"Marketing","Math","ME","Physics","SE"};
		String sCurrentLine = ""; 
		BufferedReader br = new BufferedReader(new FileReader("names.txt"));

		while ((sCurrentLine = br.readLine()) != null) {
		  randomNames.add(sCurrentLine);
		}
		
		try{
		    PrintWriter writer = new PrintWriter("professors.txt", "UTF-8");
		    String currentSchool = "";
		    int namesIncr = 0;
		    for(int i = 0; i < 5; i++)
		    {
		    	if(i == 0)
		    		currentSchool = "SJSU";
		    	else if(i == 1)
		    		currentSchool = "SFSU";
		    	else if(i == 2)
		    		currentSchool = "CSUEB";
		    	else if(i == 3)
		    		currentSchool = "OSU";
		    	else
		    		currentSchool = "WSU";
		    	for(int j = 0; j < 14; j++)
		    	{
		    		for(int k = 0; k < 3; k++)
		    		{
		    			writer.println(currentSchool + "\t" + randomNames.get(namesIncr) + "\t" + majors[j]);
		    			namesIncr++;
		    		}
		    	}
		    }

		    writer.close();
		} catch (Exception e) {
		   // do something
		}

	}
}
