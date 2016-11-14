package myUni;

import java.util.ArrayList;


//DUE FOR DELETION
public class HomePageModel {
	
	//Have instance of MySQLConnect
	
	public HomePageModel(){
		
	}
	
	//Create method that gets all states
	public ArrayList<String> getAllStates(){
		//Create query that gets all states
		ArrayList<String> states = new ArrayList<String>();
		return states;
	}
	
	//Create method that gets all colleges from a specific state
	public ArrayList<String> getAllColleges(String state){
		//Create query that gets all colleges from state
		ArrayList<String> colleges = new ArrayList<String>();
		return colleges;
	}
	
	//Create method that gets all majors from a specific college
	public ArrayList<String> getAllMajors(String college){
		
		//Create query that gets all colleges from state
		ArrayList<String> majors = new ArrayList<String>();
		return majors;
	}
	
	//Create a method that applies for a specific major in a college
	public boolean apply(int sID, String college, String major){
		//Create a query that inserts into Apply
		return false;
	}
	

}
