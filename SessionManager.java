/*  File:       SessionManager.java
 *  Authors:    Matthew Nielsen, Conor Maginnis
 *  Date:       4-18-2017
 *  Purpose:    To provide the connection to the sql database and facilitate the
 *              interface between it and the application.
 */


// Imports
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class SessionManager {
    
    // Fields
    Connection connection = null;
    Statement statement;
    ResultSet resultSet;
    String hashPassword;
    
    // Constructor
    SessionManager(){
        connectToDatabase();
    }

    // Methods
    private void connectToDatabase() {
        try {
            Class.forName("com.mysql.jdbc.Driver"); //do not change. If you get an error, download the driver from https://www.javatpoint.com/src/jdbc/mysql-connector.jar and add it to the build path
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }  
        try {
            connection = DriverManager.getConnection("jdbc:mysql://group3db.ce5lidsgsgti.us-east-1.rds.amazonaws.com:3306/group3db","group3","group3password");
        } catch (SQLException e) {
            e.printStackTrace();
        }  		
    }
    
    public Boolean verifyLogin(String loginName, String loginPassword) {
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(
                "select username from loginInformation WHERE username = '" + loginName +"' and password = '" + loginPassword +"';");
            return true;
        } catch (SQLException ex1){
            return false;
        }
    }
    
    public String getBalance(String loginName, String accountType) throws SQLException{
        String balance;
        statement = connection.createStatement();
        resultSet = statement.executeQuery(
                "select accountBalance from accountInformation WHERE username = '" + loginName +"' and accountType = '" + accountType +"';");
        resultSet.next();
        double balanceInt = resultSet.getDouble(1);
        balance = "$" + balanceInt;
        return balance; 
    }
    
    public String getTransactionHistory(String loginName, String accountType) throws SQLException{
        String history;
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "select balanceChange from transactionHistory WHERE username = '" + loginName +"' and accountType = '" + accountType +"';");
        resultSet.next(); 
        double balanceInt = resultSet.getDouble(1);
        history = "$" + balanceInt;
        return history; 
    }
    
    public Boolean transferMoney(String loginName, String transferringaccountType, String receivingAccountType, String transferAmount) throws SQLException{
        Boolean transferStatus;
        transferStatus = true;
        return transferStatus; 
    }
    
    public String calculateInterest(String loginName, String accountType) throws SQLException{
        String interest = "0";
        statement = connection.createStatement();
        resultSet = statement.executeQuery(
                "select accountBalance from accountInformation WHERE username = '" + loginName +"' and accountType = '" + accountType +"';");
        resultSet.next();
        double balanceInt = resultSet.getDouble(1);
        if (accountType == "checking") interest = "$" + (balanceInt*0.0004);
        if (accountType == "savings") interest = "$" + (balanceInt*0.0006);
        return interest; 
    }

}// End of class SessionManager
