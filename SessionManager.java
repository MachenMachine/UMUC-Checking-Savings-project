import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class SessionManager {
	Connection connection = null; //keeps an open session until logout.
	SessionManager(){
		connectToDatabase(); //allows the database to only maintain one connection as it can only be reached from the constructor
	}

	private void connectToDatabase() {
		try {
			Class.forName("com.mysql.jdbc.Driver"); //do not change. If you get an error, download the driver from https://www.javatpoint.com/src/jdbc/mysql-connector.jar and add it to the build path
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  
		try {
			connection = DriverManager.getConnection("jdbc:mysql://group3db.ce5lidsgsgti.us-east-1.rds.amazonaws.com:3306/group3db","group3","group3password"); //this is static, do not change this or none of the DB connectivity will work
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  		
}
		public String getBalance(String username, String accountType) throws SQLException{
			String balance;
			Statement statement = connection.createStatement(); //allows the connection to create a new statement
			ResultSet resultSet = statement.executeQuery(
					"select accountBalance from accountInformation WHERE username = '" + username +"' and accountType = '" + accountType +"';"); //passes the query into the statement for execution. the ' are needed for STRING datatypes
			resultSet.next(); //puts it onto row 1
			double balanceInt = resultSet.getDouble(1); //gets the double from the first column in the first row
			balance = "$" + balanceInt; //formats
			return balance; 
		}

}
