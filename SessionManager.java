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
import java.util.Date;


public class SessionManager {
    
    // Fields
    Connection connection = null;
    Statement statement;
    ResultSet resultSet;
    String hashPassword;
	//private String recievingaccountType;
    
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
        	String hashedPassword = hashPassword(loginPassword);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(
                "select username from loginInformation WHERE username = '" + loginName +"' and password = '" + hashedPassword +"';");
            if(resultSet.next()){
            return true;
            }
        } catch (SQLException ex1){
            return false;
        }
		return false;
    }
    
     String hashPassword(String loginPassword) {
		double salt1 = 87421;  //random large prime number I found
		double salt2 = 103687;
		double salt3 = 179424673;
		
		char[] arr = new char[18];
		for(int counter =0; counter < loginPassword.length(); counter++){
			arr[counter] = loginPassword.charAt(counter); 
		}
		
		double loginPw =0;
		
		for(int counter = 0; counter < arr.length; counter++){
			loginPw+= arr[counter];
		}
		
		loginPw = (loginPw*salt3*salt1)/salt2; 
		
		return String.valueOf(loginPw);
	}

	public String getBalance(String loginName, String accountType) throws SQLException{
        String balance;
        statement = connection.createStatement();
        resultSet = statement.executeQuery(
                "select accountBalance from accountInformation WHERE username = '" + loginName +"' and accountType = '" + accountType +"';");
        resultSet.next();
        double balanceInt = resultSet.getDouble("AccountBalance");
        balance = "$" + balanceInt;
        return balance; 
    }
    
    public double getBalanceInterestCalculator(String loginName, String accountType) throws SQLException{
        statement = connection.createStatement();
        resultSet = statement.executeQuery(
                "select accountBalance from accountInformation WHERE username = '" + loginName +"' and accountType = '" + accountType +"';");
        resultSet.next();
        return resultSet.getDouble("AccountBalance");
         
    }
    
    public String getTransactionHistory(String loginName, String accountType) throws SQLException{
        String history ="";
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "select * from transactionHistory WHERE username = '" + loginName +"' and accountType = '" + accountType +"' ORDER BY transactionDate;");
        
        while(resultSet.next()){ //if empty, returns false. Will not get an error as it will never be a null row
        	history += "Balance change of $" + resultSet.getDouble("balanceChange") + " on date " + resultSet.getDate("transactionDate") +"\n";
        }
        
        
        return history; 
    }
    
    public ResultSet getTransactionHistoryForInterestCalculator(String loginName, String accountType) throws SQLException {
        
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "select * from transactionHistory WHERE username = '" + loginName +"' and accountType = '" + accountType +"' ORDER BY transactionDate;");
        
        return resultSet;
    }
    
    public Boolean transferMoney(String loginName, String transferringAccountType, String receivingAccountType, String transferAmount) throws SQLException{
        Boolean transferStatus;
        Date date= new Date();
        String todayDate = date.getYear()+1900+"-"+Integer.toString(date.getMonth()+1)+"-"+date.getDate();
        double transferAmountDouble = Double.parseDouble(transferAmount);
        double receivingBalance = 0;
        double transferringBalance=0;
        statement = connection.createStatement();
        
        resultSet = statement.executeQuery("select accountBalance from accountInformation WHERE username = '" + loginName +"' and accountType = '" + receivingAccountType +"';");
        resultSet.next();
        receivingBalance = resultSet.getDouble("accountBalance");
        
        resultSet = statement.executeQuery("select accountBalance from accountInformation WHERE username = '" + loginName +"' and accountType = '" + transferringAccountType +"';");
        resultSet.next();
        transferringBalance = resultSet.getDouble("accountBalance");
        
        double balanceInt = resultSet.getDouble("AccountBalance");
        statement.execute("UPDATE accountInformation SET accountBalance = '" + (transferAmountDouble+receivingBalance) +"', lastUpdate = '" + todayDate +"' WHERE username = '"+ loginName+"' AND accountType = '"+ receivingAccountType+ "';");
        statement.execute("UPDATE accountInformation SET accountBalance = '" + (transferringBalance-transferAmountDouble)+"', lastUpdate = '" + todayDate +"' WHERE username = '"+ loginName+"' AND accountType = '"+ transferringAccountType+ "';");
        statement.execute("INSERT INTO transactionHistory values ('"+loginName+"', '"+ receivingAccountType+"', "+ transferAmountDouble +", '"+todayDate+"');");
        statement.execute("INSERT INTO transactionHistory values ('"+loginName+"', '"+ transferringAccountType+"', "+ -transferAmountDouble +", '"+todayDate+"');");
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