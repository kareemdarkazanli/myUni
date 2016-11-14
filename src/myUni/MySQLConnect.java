package myUni;
import java.sql.*;
import java.util.ArrayList;

/**
 * Sets up the SQL connection and supplies various methods to fetch data from the database
 * code template modified from JDBCCallableStatement class example
 *
 */
public class MySQLConnect {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/";

	//  Database credentials
	static final String USER = "root";
	static final String PASS = ""; //enter your root password here...
	private static Connection conn = null;
	private static Statement statement = null;
	private static PreparedStatement preparedStatement = null;
	private static void createDatabase() throws SQLException
	{

		// Open a connection
		System.out.println("Connecting to database...");
		conn = DriverManager.getConnection(DB_URL, USER, PASS);

		String queryDrop = "DROP SCHEMA IF EXISTS ApplyToCollege";
		Statement stmtDrop = conn.createStatement();
		stmtDrop.execute(queryDrop);



		// Create the database
		System.out.println("Creating database...");
		statement = conn.createStatement();

		String sql = "CREATE SCHEMA ApplyToCollege";
		statement.executeUpdate(sql);
		System.out.println("Database created successfully...");
	}
	/**
	 * Populates the database with college, major, professor, and student tables
	 * @throws SQLException
	 */
	private static void createTables() throws SQLException
	{
		// Open a connection and select the database 

		System.out.println("Connecting to database...");
		conn = DriverManager.getConnection(DB_URL+"ApplyToCollege", USER, PASS);
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
				+ "`cName` VARCHAR(30) NOT NULL,"
				+ "`pName` VARCHAR(30),"
				+ "`major` VARCHAR(30),"
				+ "`pID` INT NOT NULL AUTO_INCREMENT,"
				+ "PRIMARY KEY (`pID`),"
				+ "FOREIGN KEY major_foreign_key(cName, major) REFERENCES `ApplyToCollege`.`Major` (cName, major) ON DELETE CASCADE ON UPDATE CASCADE)";
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
		//	         String dropTrigger = "DROP TRIGGER IF EXISTS acceptance";
		//	         stmtDrop.execute(dropTrigger);
		//	         String acceptTrigger = "CREATE TRIGGER acceptance "
		//	         		+ "AFTER INSERT ON Apply "
		//	         		+ "FOR EACH ROW "
		//	         		+ "BEGIN "
		//	         		+ "IF ( (select gpa from student where student.sID = NEW.sID) "
		//	         		+ "    >= (select GPAREQ from major "
		//	         		+ "where major.cName = NEW.cName "
		//	         		+ "AND major.major = NEW.major"
		//	         		+ "        )"
		//	         		+ "    ) "
		//	         		+ "THEN UPDATE Apply "
		//	         		+ "set accept = TRUE "
		//	         		+ "where Apply.cName = NEW.cName "
		//	         		+ "AND Apply.major = NEW.major;"
		//	         		+ "ELSE UPDATE Apply "
		//	         		+ "set accept = FALSE "
		//	         		+ "where Apply.cName = NEW.cName "
		//	         		+ "AND Apply.major = NEW.major;"
		//	         		+ "END;";
		//	         PreparedStatement pState = conn.prepareStatement(acceptTrigger);
		//	         pState.executeUpdate();
		//	         System.out.println("Accept trigger created successfully");


	}
	/**
	 * Loads the data from the text files into the database
	 * @throws SQLException
	 */
	private static void loadDataIntoTables() throws SQLException
	{
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

	/**
	 * Checks if the input username and password are correct
	 * @param inputPassword
	 * @param inputName
	 * @return
	 * @throws SQLException 
	 */
	public boolean areEmailAndPasswordCorrect(String inputName, String inputPassword) throws SQLException {
		String sql = null;
		ResultSet rs = null;

		sql = "SELECT count(*) from student where sName = ? and password = ?";
		preparedStatement= conn.prepareStatement(sql);
		preparedStatement.setString(1, inputName);
		preparedStatement.setString(2,inputPassword );
		rs = preparedStatement.executeQuery();
		int count = 0;
		if (rs.next()) {
			count = rs.getInt("count(*)");
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

		sql = "SELECT sID from student where sName = ? and password = ?";
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setString(1, sName);
		preparedStatement.setString(2, password);
		rs = preparedStatement.executeQuery();
		int sID = 0;
		if (rs.next()) {
			sID = rs.getInt("sID");
		}
		return sID;
	}
	/**
	 * Retrieves the student's name from their sID
	 * @param sID the student's ID
	 * @return the student's name
	 * @throws SQLException 
	 */
	public String getStudentName(int inputID) throws SQLException
	{
		String sql = null;
		ResultSet rs = null;

		sql = "SELECT sName from student where sID = ?";
		preparedStatement= conn.prepareStatement(sql);
		preparedStatement.setInt(1, inputID);
		rs = preparedStatement.executeQuery();
		String sName = "";
		if (rs.next()) {
			sName = rs.getString("sName");
		}
		return sName;
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
	public boolean isUsernameUnique(String username) throws SQLException{
		String sql = null;
		ResultSet rs = null;

		sql = "SELECT sName from student";
		preparedStatement= conn.prepareStatement(sql);
		rs = preparedStatement.executeQuery();
		ArrayList<String> a = new ArrayList<String>();
		while (rs.next()) {
			a.add(rs.getString(1));
		}
		if (a.contains(username)) return true;
		else return false;
	}

	/**
	 * Enters the username and password into the database on a successful creation
	 * @param username
	 * @param password
	 * @throws SQLException
	 */
	//TODO Add in GPA?
	public void createNewAccount(String username, String password) throws SQLException{
		String sql = null;
		ResultSet rs = null;

		sql = "INSERT INTO student(sname, password) VALUES (?,?)";
		preparedStatement= conn.prepareStatement(sql);
		preparedStatement.setString(1, username);
		preparedStatement.setString(2, password);
		preparedStatement.executeUpdate();
	}
}
