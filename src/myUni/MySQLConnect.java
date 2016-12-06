//package myUni;
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
	static final String DB_URL = "jdbc:mysql://localhost:3306/ApplyToCollege";

	//  Database credentials
	static final String USER = "root";
	static final String PASS = ""; //enter your root password here...
	private static Connection conn = null;
	private static Statement statement = null;
	private static PreparedStatement preparedStatement = null;



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
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try{
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			sql = "SELECT count(*) from student WHERE password = ? GROUP BY sName having sName = ?";
			preparedStatement= conn.prepareStatement(sql);
			preparedStatement.setString(2, inputName);
			preparedStatement.setString(1,inputPassword );
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
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
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
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try{
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

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
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try{
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			sql = "SELECT GPAREQ from major JOIN college ON major.cName = college.cName WHERE college.cName = ? AND major = ?;";
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
	 * Retrieves enrollment number from a college
	 * @param college
	 * @return enrollment
	 */
	public int getEnrollment(String college){
		int enrollment = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			ResultSet rs;
			String sql;

			sql = "SELECT enrollment from College where cName = ?";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, college);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				enrollment = rs.getInt("enrollment");
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
		return enrollment;
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
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
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
	 * Checks to see if the username entered is unique
	 * @param userName
	 * @return
	 * @throws SQLException
	 */
	public boolean isUsernameUnique(String username) {
		String sql = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try{
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
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
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try{
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
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
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try{
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			myStmt = conn.createStatement();

			String sql = "SELECT state " +
					"FROM College "+
					"GROUP BY state HAVING LENGTH(STATE) = 2";
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
	 * Updates the name, password, gpa
	 * @param sID
	 * @param username
	 * @param password
	 * @param GPA
	 * @throws SQLException
	 */
	public void updateAccountInfo(int sID, String username, String password, Float gpa) throws SQLException{
		String sql = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try{
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			sql = "UPDATE student SET sName = '" + username + "' , password = '" + password + "' , GPA = '" + gpa + "' WHERE sID = '" + sID + "';";
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
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
			if(stmt != null)
			{
				try {stmt.close();} catch (SQLException e) {e.printStackTrace();}
			}
		}

	}
	
	/**
	 * gets password of a student
	 * @param studentID
	 * @return password
	 */
	public String getPassword(int sID){
		String password = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			ResultSet rs;
			String sql;

			sql = "SELECT password from student where sID = ?";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, sID);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				password = rs.getString("password");
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
		return password;
	}
	
	
	/**
	 * gets name of a student
	 * @param studentID
	 * @return name
	 */
	public String getName(int sID){
		String name = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			ResultSet rs;
			String sql;

			sql = "SELECT sName from student where sID = ?";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, sID);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				name = rs.getString("sName");
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
		return name;
	}
	
	
	/**
	 * gets GPA of a student
	 * @param studentID
	 * @return GPA
	 */
	public float getGPA(int sID){
		float gpa = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			ResultSet rs;
			String sql;

			sql = "SELECT GPA from student where sID = ?";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, sID);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				gpa = rs.getFloat("GPA");
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
	 * gets all colleges from a specific state
	 * @param state the state
	 * @return array list of the colleges
	 */
	public ArrayList<String> getAllColleges(String state){
		//Create query that gets all colleges from state
		ArrayList<String> colleges = new ArrayList<String>();
		Statement myStmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
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
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			myStmt = conn.createStatement();
			String sql = "SELECT DISTINCT major " +
					"FROM Major m "+
					"WHERE m.cName IN (SELECT cName FROM College WHERE cName = '"
					+  college + "')";
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
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
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
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			myStmt = conn.createStatement();
			String sql = "SELECT college.cName, major, accept FROM apply LEFT OUTER JOIN college ON " +
					"apply.cName = college.cName WHERE apply.sID = '" +  sID + "'";
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

	/**
	 * Applies the current student to the selected major at the selected college
	 * @param sID
	 * @param college
	 * @param major
	 * @return
	 */
	public int apply(int sID, String college, String major){

		//Create a query that inserts into Apply
		int retVal = 0;
		Statement myStmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
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
	
	/**
	 * Gets the GPA requirement for the selected major
	 * @param college
	 * @param major
	 * @return
	 * @throws SQLException
	 */
	public String getMinimumGPA(String college, String major) throws SQLException 
	{
		String sql = null;
		ResultSet rs = null;
		String gpa = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			sql = "SELECT GPAREQ from major JOIN college ON major.cName = college.cName WHERE college.cName = ? AND major = ?;";
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
	 * Archives applications older than the selected date and returns how many applications were archived
	 * @param timestamp
	 */
	public int archiveApplications(Timestamp timestamp)
	{
		String insertStoreProc = "{call archiveApplications(?, ?)}";
		CallableStatement callableStatement = null;
		int numberArchived = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			callableStatement = conn.prepareCall(insertStoreProc);
			callableStatement.setTimestamp(1, timestamp);
			callableStatement.registerOutParameter(2, Types.INTEGER);
		    boolean hadResults = callableStatement.execute();
		    while (hadResults) {
		        ResultSet rs = callableStatement.getResultSet();
		        hadResults = callableStatement.getMoreResults();
		    }
			numberArchived = callableStatement.getInt(2);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			if(conn != null)
			{
				try {conn.close();} catch (SQLException e) {e.printStackTrace();}
			}
			if(callableStatement != null)
			{
				try {callableStatement.close();} catch (SQLException e) {e.printStackTrace();}
			}
		}
		return numberArchived;
		

	}
}
