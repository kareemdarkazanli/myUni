package myUni;

import java.util.ArrayList;

public class HomePageController {
	
	//Have instance of model
	private MySQLConnect newModel;
	//Get instance of User singleton
	
	public HomePageController(MySQLConnect theModel){
		newModel = theModel;
	}
	
	/**
	 * getter for the model
	 * @return an instance of the model
	 */
	public MySQLConnect getModel()
	{
		return newModel;
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
	public int apply(int sID, String college, String major){
		//Get student ID from User singleton
		return newModel.apply(sID, college, major);
	}
	
	
	
}
