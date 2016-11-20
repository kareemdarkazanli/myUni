package myUni;

import java.util.ArrayList;

public class CollegeApplicationsController {
	//Have instance of model
		MySQLConnect newModel = new MySQLConnect();
		//Get instance of User singleton
		
		public CollegeApplicationsController(){
			
		}
		
		public ArrayList<String> getCollegeApplications(int sID){
			return newModel.getCollegeApplications(sID);
		}
		
		public void deleteCollegeApplication(int sID, String cName, String major){
			newModel.deleteCollegeApplication(sID, cName, major);
		}

}
