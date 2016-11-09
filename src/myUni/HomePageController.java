package myUni;

import java.util.ArrayList;

public class HomePageController {
	
	//Have instance of model
	HomePageModel homePageModel = new HomePageModel();
	//Get instance of User singleton
	
	public HomePageController(){
		
	}
	
	//Create method that gets all states
	public ArrayList<String> getAllStates(){
		return homePageModel.getAllStates();
	}
	
	//Create method that gets all colleges from a specific state
	public ArrayList<String> getAllColleges(String state){
		return homePageModel.getAllColleges(state);
	}
	
	//Create method that gets all majors from a specific college
	public ArrayList<String> getAllMajors(String college){
		return homePageModel.getAllMajors(college);
	}
	
	//Create a method that applies for a specific major in a college
	public boolean apply(String college, String major){
		//Get student ID from User singleton
		int sID = 0;
		return homePageModel.apply(sID, college, major);
	}
	
	

}
