package myUni;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
	
	public static void main(String[] args) throws SQLException{
		MySQLConnect newModel = new MySQLConnect();
		MySQLConnect.setUpData();
		LoginPageView loginPage = new LoginPageView(newModel);
	}

}
