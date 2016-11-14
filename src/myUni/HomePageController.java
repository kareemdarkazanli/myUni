package myUni;

import java.util.ArrayList;

public class HomePageController {
	
	//Have instance of model
	MySQLConnect newModel = new MySQLConnect();
	//Get instance of User singleton
	
	public HomePageController(){
		
	}
	
	//Create method that gets all states
	public ArrayList<String> getAllStates(){
		return newModel.getAllStates();
	}
	
	//Create method that gets all colleges from a specific state
	public ArrayList<String> getAllColleges(String state){
		return newModel.getAllColleges(state);
	}
	
	//Create method that gets all majors from a specific college
	public ArrayList<String> getAllMajors(String college){
		return newModel.getAllMajors(college);
	}
	
	//Create a method that applies for a specific major in a college
	public boolean apply(String college, String major){
		//Get student ID from User singleton
		int sID = 0;
		return newModel.apply(sID, college, major);
	}
	
}
