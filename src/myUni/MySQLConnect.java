package myUni;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Sets up the SQL connection and supplies various methods to fetch data from the database
 * code template modified from JDBCCallableStatement class example
 *
 */
public class MySQLConnect {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql:///127.0.0.1/";

	//  Database credentials
	static final String USER = "root";
	static final String PASS = ""; //enter your root password here...
	private static Connection conn = null;
	private static Statement statement = null;
	private static PreparedStatement preparedStatement = null;
	
	/**
	 * Creates the database
	 * @throws SQLException
	 */
	private static void createDatabase() 
	{

		// Open a connection
		System.out.println("Connecting to database...");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ApplyToCollege", "root", "");
			String queryDrop = "DROP SCHEMA IF EXISTS ApplyToCollege";
			Statement stmtDrop = conn.createStatement();
			stmtDrop.execute(queryDrop);



			// Create the database
			System.out.println("Creating database...");
			statement = conn.createStatement();

			String sql = "CREATE SCHEMA ApplyToCollege";
			statement.executeUpdate(sql);
			System.out.println("Database created successfully...");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			if(conn != null)
			{
				try {conn.close();} catch (SQLException e) {e.printStackTrace();}
			}
			if(statement != null)
			{
				try {statement.close();} catch (SQLException e) {e.printStackTrace();}
			}
		}
	}
	/**
	 * Populates the database with college, major, professor, and student tables
	 * @throws SQLException
	 */
	private static void createTables() 
	{
		// Open a connection and select the database 
		try {
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ApplyToCollege", "root", "");
			statement = conn.createStatement();

			String queryDrop = "DROP TABLE IF EXISTS ApplyToCollege.College";
			Statement stmtDrop = conn.createStatement();
			stmtDrop.execute(queryDrop);


			String createTableSQL = "CREATE TABLE `ApplyToCollege`.`College` (" 
					+"`cName` VARCHAR(30) NOT NULL," 
					+"`state` VARCHAR(30),"
					+ " `enrollment` INT," +
					"PRIMARY KEY (`cName`))";

			statement.execute(createTableSQL); 
			System.out.println("Table called College created successfully...");


			queryDrop = "DROP TABLE IF EXISTS ApplyToCollege.Student";
			stmtDrop.execute(queryDrop);

			createTableSQL = "CREATE TABLE `ApplyToCollege`.`Student` (" 
					+ " `sID` INT NOT NULL AUTO_INCREMENT,"
					+ "`password` VARCHAR(30),"
					+ "`sName` VARCHAR(30) UNIQUE,"
					+ "`GPA` FLOAT,"
					+ "PRIMARY KEY (`sID`))";
			statement.execute(createTableSQL);
			String alterSQL = "ALTER TABLE student AUTO_INCREMENT = 1000";
			PreparedStatement pState = conn.prepareStatement(alterSQL);
			pState.executeUpdate();
			System.out.println("Table called Student created successfully...");

			queryDrop = "DROP TABLE IF EXISTS ApplyToCollege.Major";
			stmtDrop.execute(queryDrop);

			createTableSQL = "CREATE TABLE `ApplyToCollege`.`Major` (" 
					+ "`cName` VARCHAR(30) NOT NULL,"
					+ "`major` VARCHAR(30) NOT NULL,"
					+ "`GPAREQ` FLOAT,"
					+ "PRIMARY KEY (`cName`,`major`),"
					+ "FOREIGN KEY cName_foreign_key(cName) REFERENCES `ApplyToCollege`.`College` (cName) ON DELETE CASCADE ON UPDATE CASCADE)";
			statement.execute(createTableSQL); 
			System.out.println("Table called Major created successfully...");

			queryDrop = "DROP TABLE IF EXISTS ApplyToCollege.Professor";
			stmtDrop.execute(queryDrop);

			createTableSQL = "CREATE TABLE `ApplyToCollege`.`Professor` (" 
					+ "`cName` VARCHAR(30) NOT NULL, "
					+ "`pName` VARCHAR(30), "
					+ "`major` VARCHAR(30) NOT NULL, "
					+ "`pID` INT NOT NULL AUTO_INCREMENT, "
					+ "PRIMARY KEY (`pID`), "
					+ "FOREIGN KEY major_foreign_key(cName, major) "
					+ "REFERENCES `ApplyToCollege`.`Major` (cName, major) "
					+ "ON DELETE CASCADE ON UPDATE CASCADE)";
			statement.execute(createTableSQL); 
			alterSQL = "ALTER TABLE professor AUTO_INCREMENT = 2000";
			pState = conn.prepareStatement(alterSQL);
			pState.executeUpdate();
			System.out.println("Table called Professor created successfully...");

			queryDrop = "DROP TABLE IF EXISTS ApplyToCollege.Apply";
			stmtDrop.execute(queryDrop);

			createTableSQL = "CREATE TABLE `ApplyToCollege`.`Apply` (" 
					+ "`sID` INT NOT NULL,"
					+ "`cName` VARCHAR(30),"
					+ "`major` VARCHAR(30),"
					+ "`accept` BOOLEAN,"
					+ "PRIMARY KEY (`sID`,`cName`,`major`),"
					+ "FOREIGN KEY sID_foreign_key(sID) REFERENCES `ApplyToCollege`.`Student` (sID) ON DELETE CASCADE ON UPDATE CASCADE,"
					+ "FOREIGN KEY cName_foreign_key(cName, major) REFERENCES `ApplyToCollege`.`Major` (cName, major) ON DELETE CASCADE ON UPDATE CASCADE)";
			statement.execute(createTableSQL); 
			System.out.println("Table called Apply created successfully...");

			String dropTrigger = "DROP TRIGGER IF EXISTS acceptance";
			stmtDrop.execute(dropTrigger);
			String acceptTrigger = "CREATE TRIGGER acceptance "
					+ "BEFORE INSERT ON Apply "
					+ "FOR EACH ROW "
					+ "BEGIN "
					+ "IF ( (select gpa from student where student.sID = NEW.sID) "
					+ "    >= (select GPAREQ from major "
					+ "where major.cName = NEW.cName "
					+ "AND major.major = NEW.major "
					+ "        ) "
					+ "    ) "
					+ "THEN "
					+ "set NEW.accept = TRUE; "
					+ "ELSE "
					+ "set NEW.accept = FALSE; "
					+ "END IF; "
					+ "END; ";

			pState = conn.prepareStatement(acceptTrigger);
			pState.executeUpdate();
			System.out.println("Accept trigger created successfully");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(conn != null)
			{
				try {conn.close();} catch (SQLException e) {e.printStackTrace();}
			}
			if(statement != null)
			{
				try {statement.close();} catch (SQLException e) {e.printStackTrace();}
			}
		}


	}
	/**
	 * Loads the data from the text files into the database
	 * @throws SQLException
	 */
	private static void loadDataIntoTables() throws SQLException
	{
		try{

			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ApplyToCollege", "root", "");
			statement = conn.createStatement();
			System.out.println("Load data from a file colleges.txt");
			String loadDataSQL = "LOAD DATA LOCAL INFILE 'colleges.txt' INTO TABLE COLLEGE";
			statement.execute(loadDataSQL); 

			System.out.println("Load data from a file students.txt");
			loadDataSQL = "LOAD DATA LOCAL INFILE 'students.txt' INTO TABLE STUDENT (password, sName, GPA)";
			statement.execute(loadDataSQL); 

			System.out.println("Load data from a file majors.txt");
			loadDataSQL = "LOAD DATA LOCAL INFILE 'majors.txt' INTO TABLE MAJOR";
			statement.execute(loadDataSQL); 

			System.out.println("Load data from a file professors.txt");
			loadDataSQL = "LOAD DATA LOCAL INFILE 'professors.txt' INTO TABLE PROFESSOR (cName, pName, Major)";
			statement.execute(loadDataSQL); 
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(conn != null)
			{
				try {conn.close();} catch (SQLException e) {e.printStackTrace();}
			}
			if(statement != null)
			{
				try {statement.close();} catch (SQLException e) {e.printStackTrace();}
			}
		}
		

	}

	/**
	 * Checks if the input username and password are correct
	 * @param inputPassword
	 * @param inputName
	 * @return
	 * @throws SQLException 
	 */
	public boolean areEmailAndPasswordCorrect(String inputName, String inputPassword) {
		String sql = null;
		ResultSet rs = null;
		int count = 0;
		try{

			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ApplyToCollege", "root", "");

			sql = "SELECT count(*) from student where sName = ? and password = ?";
			preparedStatement= conn.prepareStatement(sql);
			preparedStatement.setString(1, inputName);
			preparedStatement.setString(2,inputPassword );
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				count = rs.getInt("count(*)");
			}
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(conn != null)
			{
				try {conn.close();} catch (SQLException e) {e.printStackTrace();}
			}
			if(preparedStatement != null)
			{
				try {preparedStatement.close();} catch (SQLException e) {e.printStackTrace();}
			}
		}
		if(count == 1 )
			return true;
		else
			return false;
		
	}

	/**
	 * Gets the student ID from the name and password. This method gets called after a successful login to pass the sID of
	 * the logged in user to the main page
	 * @throws SQLException
	 */
	public int getStudentID(String sName, String password) throws SQLException
	{
		String sql = null;
		ResultSet rs = null;
		int sID = 0;

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ApplyToCollege", "root", "");
			sql = "SELECT sID from student where sName = ? and password = ?";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, sName);
			preparedStatement.setString(2, password);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				sID = rs.getInt("sID");
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(conn != null)
			{
				try {conn.close();} catch (SQLException e) {e.printStackTrace();}
			}
			if(preparedStatement != null)
			{
				try {preparedStatement.close();} catch (SQLException e) {e.printStackTrace();}
			}
		}

		return sID;
		
	}
	/**
	 * Retrieves the student's name from their sID
	 * @param sID the student's ID
	 * @return the student's name
	 * @throws SQLException 
	 */
	public String getStudentName(int inputID) 
	{
		String sql = null;
		ResultSet rs = null;
		String sName = "";
		try{
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ApplyToCollege", "root", "");

			sql = "SELECT sName from student where sID = ?";
			preparedStatement= conn.prepareStatement(sql);
			preparedStatement.setInt(1, inputID);
			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				sName = rs.getString("sName");
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(conn != null)
			{
				try {conn.close();} catch (SQLException e) {e.printStackTrace();}
			}
			if(preparedStatement != null)
			{
				try {preparedStatement.close();} catch (SQLException e) {e.printStackTrace();}
			}
		}
		return sName;
	}
	/**
	 * Retrieves the GPA requirement for a major from the database
	 * @param college the college to query
	 * @param major the major
	 * @return the GPA requirement
	 */
	public String getMinGPA(String college, String major) 
	{
		String sql = null;
		ResultSet rs = null;
		String gpa = "";
		try{
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ApplyToCollege", "root", "");
			sql = "SELECT GPAREQ from major where cName = ? and major = ?";
			preparedStatement= conn.prepareStatement(sql);
			preparedStatement.setString(1, college);
			preparedStatement.setString(2, major);
			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				gpa = rs.getString("GPAREQ");
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(conn != null)
			{
				try {conn.close();} catch (SQLException e) {e.printStackTrace();}
			}
			if(preparedStatement != null)
			{
				try {preparedStatement.close();} catch (SQLException e) {e.printStackTrace();}
			}
		}

		return gpa;
	}
	/**
	 * Retrieves all the professors at a college for that major
	 * @param college
	 * @param major
	 * @return
	 */
	public Vector<String> getProfessors(String college, String major){
		Vector<String> professors = new Vector<>();
		Statement myStmt = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ApplyToCollege", "root", "");
			myStmt = conn.createStatement();
			String sql = "SELECT pName FROM professor WHERE cName = '" +  college + "' AND major = '" + major + "'";
			ResultSet s = myStmt.executeQuery(sql);
			while (s.next()) {
				professors.add(s.getString(1));
			}
		} 
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(conn != null)
			{
				try {conn.close();} catch (SQLException e) {e.printStackTrace();}
			}
			if(myStmt != null)
			{
				try {myStmt.close();} catch (SQLException e) {e.printStackTrace();}
			}
		}
		return professors;
	}

	/**
	 * Runs all the methods that prepare the database to create the database, tables, and to populate the tables with data
	 * @throws SQLException
	 */
	public static void setUpData() throws SQLException
	{
		createDatabase();
		createTables();
		loadDataIntoTables();
	}


	/**
	 * Checks to see if the username entered is unique
	 * @param userName
	 * @return
	 * @throws SQLException
	 */
	public boolean isUsernameUnique(String username) {
		String sql = null;
		ResultSet rs = null;
		boolean flag = false;
		try{
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ApplyToCollege", "root", "");
			sql = "SELECT sName from student";
			preparedStatement = conn.prepareStatement(sql);
			rs = preparedStatement.executeQuery();
			ArrayList<String> a = new ArrayList<String>();
			while (rs.next()) {
				a.add(rs.getString(1));
			}
			if (a.contains(username)) flag = true;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(conn != null)
			{
				try {conn.close();} catch (SQLException e) {e.printStackTrace();}
			}
			if(preparedStatement != null)
			{
				try {preparedStatement.close();} catch (SQLException e) {e.printStackTrace();}
			}
		}
		return flag;
	}

	/**
	 * Enters the username and password into the database on a successful creation
	 * @param username
	 * @param password
	 * @throws SQLException
	 */
	public void createNewAccount(String username, String password, String gpa) throws SQLException{
		String sql = null;
		ResultSet rs = null;
		try{
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ApplyToCollege", "root", "");
			sql = "INSERT INTO student(sname, password, GPA) VALUES (?,?,?)";
			preparedStatement= conn.prepareStatement(sql);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			preparedStatement.setFloat(3, Float.parseFloat(gpa));
			preparedStatement.executeUpdate();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(conn != null)
			{
				try {conn.close();} catch (SQLException e) {e.printStackTrace();}
			}
			if(preparedStatement != null)
			{
				try {preparedStatement.close();} catch (SQLException e) {e.printStackTrace();}
			}
		}
		
	}
	
	/**
	 * Retrieves all states from the database
	 * @return an array list containing all of the states
	 */
		public ArrayList<String> getAllStates(){
			//Create query that gets all states
			ArrayList<String> states = new ArrayList<String>();
			Statement myStmt = null;

			try{
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ApplyToCollege", "root", "");
				myStmt = conn.createStatement();

				String sql = "SELECT state " +
		                   	 "FROM College "+
		                   	 "GROUP BY state"; 
				ResultSet s = myStmt.executeQuery(sql);
				while (s.next()) {
				    states.add(s.getString(1));
				}   
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			finally
			{
				if(conn != null)
				{
					try {conn.close();} catch (SQLException e) {e.printStackTrace();}
				}
				if(myStmt != null)
				{
					try {myStmt.close();} catch (SQLException e) {e.printStackTrace();}
				}
			}
			return states;
		}
		
		/**
		 * gets all colleges from a specific state
		 * @param state the state
		 * @return array list of the colleges
		 */
		public ArrayList<String> getAllColleges(String state){
			//Create query that gets all colleges from state
			ArrayList<String> colleges = new ArrayList<String>();
			Statement myStmt = null;
			try {
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ApplyToCollege", "root", "");
				myStmt = conn.createStatement();
				String sql = "SELECT cName " +
	                  	 "FROM College c "+
	                  	 "WHERE c.state = '" + state + "'"; 
				ResultSet s = myStmt.executeQuery(sql);
				while (s.next()) {
				    colleges.add(s.getString(1));
				}   
			} 
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			finally
			{
				if(conn != null)
				{
					try {conn.close();} catch (SQLException e) {e.printStackTrace();}
				}
				if(myStmt != null)
				{
					try {myStmt.close();} catch (SQLException e) {e.printStackTrace();}
				}
			}
			return colleges;
		}
		
		/**
		 * Gets all majors from a specific college
		 * @param college
		 * @return
		 */
		public ArrayList<String> getAllMajors(String college){
			
			//Create query that gets all colleges from state
			ArrayList<String> majors = new ArrayList<String>();
			Statement myStmt = null;
			try {
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ApplyToCollege", "root", "");
				myStmt = conn.createStatement();
				String sql = "SELECT major " +
	                  	 "FROM Major m "+
	                  	 "WHERE m.cName = '" +  college + "'"; 
				ResultSet s = myStmt.executeQuery(sql);
				while (s.next()) {
				    majors.add(s.getString(1));
				}   
			} catch(SQLException e)
			{
				e.printStackTrace();
			}
			finally
			{
				if(conn != null)
				{
					try {conn.close();} catch (SQLException e) {e.printStackTrace();}
				}
				if(myStmt != null)
				{
					try {myStmt.close();} catch (SQLException e) {e.printStackTrace();}
				}
			}
			return majors;
		}
		
		public void deleteCollegeApplication(int sID, String cName, String major){
			//Create a query that inserts into Apply
			Statement myStmt = null;
			try {
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ApplyToCollege", "root", "");
				myStmt = conn.createStatement();
				
				//CHANGE TO SQL
				String sql = "DELETE FROM Apply WHERE sID = "+ sID + " AND cName = '"+ cName + "' AND major = '"+ major + "';";			
				 myStmt.executeUpdate(sql);
			} catch(SQLException e)
			{
				e.printStackTrace();
			}
			finally
			{
				if(conn != null)
				{
					try {conn.close();} catch (SQLException e) {e.printStackTrace();}
				}
				if(myStmt != null)
				{
					try {myStmt.close();} catch (SQLException e) {e.printStackTrace();}
				}
			}
		}
		/**
		 * gets all the college applications for a student
		 * @param sID
		 * @return
		 */
		public ArrayList<String> getCollegeApplications(int sID){
			//Create a query that inserts into Apply
			ArrayList<String> collegeApplications = new ArrayList<String>();
			Statement myStmt = null;
			try {
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ApplyToCollege", "root", "");
				myStmt = conn.createStatement();
				String sql = "SELECT cName, major, accept " +
	                  	 "FROM Apply a "+
	                  	 "WHERE a.sID = '" +  sID + "'"; 
				ResultSet s = myStmt.executeQuery(sql);
				while (s.next()) {
					String accepted = "Accepted";
					if(s.getString(3).equals("0"))
						accepted = "Not Accepted";
				    collegeApplications.add(s.getString(1) + "		" + s.getString(2) + "		" + accepted);
				}   
			} 
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			finally
			{
				if(conn != null)
				{
					try {conn.close();} catch (SQLException e) {e.printStackTrace();}
				}
				if(myStmt != null)
				{
					try {myStmt.close();} catch (SQLException e) {e.printStackTrace();}
				}
			}
			
			return collegeApplications;
		}
		
		//Create a method that applies for a specific major in a college
		public int apply(int sID, String college, String major){
			//Create a query that inserts into Apply
			int retVal = 0;
			Statement myStmt = null;
			try {
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ApplyToCollege", "root", "");
				myStmt = conn.createStatement();
				String sql = "SELECT cName, major, accept " +
	                  	 "FROM Apply a "+
	                  	 "WHERE a.sID = '" +  sID + "' AND a.cName = '"  +  college + "' AND a.major = '"  + major + "'" ; 
				ResultSet s = myStmt.executeQuery(sql);
				if(!s.next()){
					//CHANGE TO SQL
					sql = "INSERT INTO APPLY(sID, cName, major) " +
		                  	 "VALUES (" + sID +", '" + college +"', '" + major + "');"; 
					
					retVal = myStmt.executeUpdate(sql);
				}
				
			} catch(SQLException e)
			{
				e.printStackTrace();
			}
			finally
			{
				if(conn != null)
				{
					try {conn.close();} catch (SQLException e) {e.printStackTrace();}
				}
				if(myStmt != null)
				{
					try {myStmt.close();} catch (SQLException e) {e.printStackTrace();}
				}
			}
			
			return retVal;
		}

	public String getMinimumGPA(String college, String major) throws SQLException
	{
		String sql = null;
		ResultSet rs = null;
		String gpa = "";
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ApplyToCollege", "root", "");
			sql = "SELECT GPAREQ from major where cName = ? and major = ?";
			preparedStatement= conn.prepareStatement(sql);
			preparedStatement.setString(1, college);
			preparedStatement.setString(2, major);
			rs = preparedStatement.executeQuery();
			
			if (rs.next()) {
				gpa = rs.getString("GPAREQ");
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(conn != null)
			{
				try {conn.close();} catch (SQLException e) {e.printStackTrace();}
			}
			if(preparedStatement != null)
			{
				try {preparedStatement.close();} catch (SQLException e) {e.printStackTrace();}
			}
		}
		return gpa;
	}
		
}
