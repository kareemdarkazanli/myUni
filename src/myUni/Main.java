package myUni;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
	
	public static void main(String[] args){
		LoginPageModel newModel = new LoginPageModel();
		LoginPageView loginPage = new LoginPageView(newModel);
		newModel.attach(loginPage);
	}

}
