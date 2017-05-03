
/* 
 * * Date: 		5 May 2017
 * Authors: 	Matthew Nielsen, Conor Maginnis, Lennon Brixey, Ken Machen
 * CMSC-495 	Checking and Savings Program
 * Purpose:   	Works with the InterextCalculator class to pass date and amount.	
 * 
 * 						Revision History
 * 
 * REVISION #	DATE			DESCRIPTION									NAME
 * 1			18 April 2017	Initial coding				   				Lennon
 * 2			20 April 2017	Added Functionality							Lennon
 * 3			25 April 2017	change number of days per month				Lennon
 * 4			25 April 2017	Added constructor to work with Login GUI	Conor
 * 5 			27 April 2017	Code for checking what day it is			Lennon
 * 6			28 April 2017	Fixed communication with GUI with toString	Lennon
 * 7			28 April 2017	updates the database with transaction		Lennon/Conor
 * 8			3  May 2017		removed unused code							Ken
 *  * 
*/


//interestCalculator Class
//This class will take transaction history to and give an interest amount for the last 30 days
//Lennon Brixey

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InterestCalculator {
	

	private List<Transaction> savingHistory = new ArrayList<>();
    private List<Transaction> checkingHistory = new ArrayList<>();
    private double savingBalance = 0;
    private double checkingBalance = 0;
    private double savingRate = 0.06;
    private double checkingRate = 0.04;
    private int daysinMonth;
    private Date generateDate = new Date();
    @SuppressWarnings("deprecation")
	  private int thisMonth = generateDate.getMonth()+1;
    @SuppressWarnings("deprecation")
	  private int thisDay = generateDate.getDate();
      
	  @SuppressWarnings("deprecation")
	  List<Transaction> savingHistory = new ArrayList<>();
        List<Transaction> checkingHistory = new ArrayList<>();
	      double savingBalance = 0;
        double checkingBalance = 0;
        double savingRate = 0.06;
        double checkingRate = 0.04;
        double savingInterest, checkingInterest;
        int daysinMonth;
            	
        Date generateDate = new Date();
        int thisMonth = generateDate.getMonth()+1;
        int thisDay = generateDate.getDate();
        

	InterestCalculator(String username){

		/*  this constructor will need to create the accounts and transaction logs to be sent to the calculate method
            each account will then be sent to the calculate method and those results can be sent back to whatever called this
            class.
		 */
		
		String todayDate = "";
		SessionManager sm = new SessionManager();
		ResultSet rs = null;
		String date = "";
		Date generateDate = new Date();
           
                
		try {
			savingBalance = sm.getBalanceInterestCalculator(username, "saving");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			rs = sm.getTransactionHistoryForInterestCalculator(username, "saving");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
        
            String zero;
            
            if (generateDate.getMonth() < 10){
                
                zero = Integer.toString(0);                            
                
            }
            else {zero = "";}

                    
                    
			while(rs.next()){
				 date = rs.getString("transactionDate");
				 date = removeChars(date);
				 todayDate = generateDate.getYear()+1900+""+zero+Integer.toString(generateDate.getMonth()+1)+""+generateDate.getDate();
				 int transactionDateInt = Integer.parseInt(date);

                 int todayDateInt = Integer.parseInt(todayDate);
                 Transaction t = new Transaction(todayDateInt - transactionDateInt, rs.getDouble("balanceChange")); 
				 savingHistory.add(t);
			}//end while loop
		} catch (SQLException e) {
			e.printStackTrace();
		}
               
		try {

			checkingBalance = sm.getBalanceInterestCalculator(username, "saving");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
                                

        rs = null;        

		try {
			rs = sm.getTransactionHistoryForInterestCalculator(username, "checking");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
                    
            String zero;
            
            if (generateDate.getMonth() < 10){
                
                zero = Integer.toString(0);                            
                
            }
            else {zero = "";}

                    
                    
			while(rs.next()){
				 date = rs.getString("transactionDate");
				 date = removeChars(date);
				 todayDate = generateDate.getYear()+1900+""+zero+Integer.toString(generateDate.getMonth()+1)+""+generateDate.getDate();
				 int transactionDateInt = Integer.parseInt(date);
                                 
                                
                                 
				 int todayDateInt = Integer.parseInt(todayDate);
                                 
                              
				 Transaction t = new Transaction(todayDateInt - transactionDateInt, rs.getDouble("balanceChange")); 
				 checkingHistory.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

               
				// following if statements check to see what month it is and assign appropriate amount of days for that month 

                    
                if (thisMonth == 1 || thisMonth == 3 || thisMonth == 5 || thisMonth == 7 || thisMonth == 8 || thisMonth == 10 || thisMonth == 11){
                    
                    daysinMonth = 31;
                    
                }
                else if (thisMonth == 4 || thisMonth == 6 || thisMonth == 9 || thisMonth == 11){
                    
                    daysinMonth = 30;
                }
                else if (thisMonth == 2){
                    
                    daysinMonth = 28;
                }
                else{daysinMonth = 30;}
                
            if (thisDay == daysinMonth){

            // This is where the calculate interest method will be called to add a transaction to database
                
            }
    }//end constuctor
  

	private String removeChars(String date) {
		String ret ="";
		for(int x = 0; x <date.length(); x++){
			if(date.substring(x, x+1).equals("-") == false){
				ret+=date.substring(x,x+1);
			}
		}
		return ret;

	}//end removeChars method
    

	double[] calculateInterest(int daysinMonth){        

		double[] interest = new double[2];
		double savingavgdlybal = 0, checkingavgdlybal = 0;
		double savingSum = 0, checkingSum = 0;  
                
		for (int i = 0; i < daysinMonth; i++){//starts with most recent transactions and loops down to 30 day old transactions
           
			for (Transaction x: savingHistory){

                if (x.age == i){//checks for transactions that happened on current "day"
					savingBalance = savingBalance - x.amount;
					savingBalance = Math.round(savingBalance*100.0)/100.0;
                } 
            }//end for loop
			savingSum = savingSum + savingBalance;
		}//end for loop

		savingavgdlybal = savingSum / daysinMonth;//total is then divided by 30 to get avg daily balance
        savingavgdlybal = Math.round(savingavgdlybal*100.0)/100.0;
        System.out.println(savingavgdlybal);

                
		interest[0] = (savingavgdlybal * savingRate)/12;               
		interest[0] = Math.round(interest[0]*100.0)/100.0;    //rounds off the final amount


                for (int i = 0; i < daysinMonth; i++){  //starts with most recent transactions and loops down to 30 day old transactions

			for (Transaction y: checkingHistory){
				if (y.age == i){ //checks for transactions that happened on current "day"
					checkingBalance = checkingBalance - y.amount;
					checkingBalance = Math.round(checkingBalance*100.0)/100.0;
                } 

			}
			checkingSum = checkingSum + checkingBalance;
		}


		checkingavgdlybal = checkingSum / daysinMonth; //total is then divided by 30 to get avg daily balance
        checkingavgdlybal = Math.round(checkingavgdlybal*100.0)/100.0;
        System.out.println(checkingavgdlybal);
                
		interest[1] = (checkingavgdlybal * checkingRate)/12;               
		interest[1] = Math.round(interest[1]*100.0)/100.0;    //rounds off the final amount
               
		return interest;                                //returns result
	}//end calculateInterest method
        
        @SuppressWarnings("deprecation")
		@Override
        public String toString(){
            
	        int day = generateDate.getDate();
	        double[] interest = new double[2];
	        interest[0] = 0; interest[1] = 0;
	        interest = this.calculateInterest(day);           
            
            return "Savings: $" + interest[0] + " earned this month." + 
                    "\nChecking: $" + interest[1] + " earned this month.";
        }//end toString method
        
}

