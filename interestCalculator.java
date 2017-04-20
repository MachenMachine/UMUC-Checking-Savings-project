
//interestCalculator Class
//This class will take transaction history to and give an interest amount for the last 30 days
//Lennon Brixey

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class interestCalculator {
	
	List<Transaction> history = new ArrayList<Transaction>();
	double balance = 0;
        double rate;
	
	interestCalculator(String username, String accountType){

		/*  this constructor will need to create the accounts and transaction logs to be sent to the calculate method
            each account will then be sent to the calculate method and those results can be sent back to whatever called this
            class.
		 */
		
		String todayDate = "";
		SessionManager sm = new SessionManager();
		ResultSet rs = null;
		String date = "";
		Date generateDate = new Date();
                
                if (accountType.equals("checking")){
                    rate = 0.04;
                }
                else if (accountType.equals("savings")){
                    rate = 0.06;
                }
                
		try {
			balance = sm.getBalanceInterestCalculator(username, accountType);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			rs = sm.getTransactionHistoryForInterestCalculator(username, accountType);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while(rs.next()){
				 date = rs.getString("transactionDate");
				 date = removeChars(date);
				 todayDate = generateDate.getYear()+1900+""+Integer.toString(generateDate.getMonth()+1)+""+generateDate.getDate();
				 int transactionDateInt = Integer.parseInt(date);
				 int todayDateInt = Integer.parseInt(todayDate);
				 Transaction t = new Transaction(todayDateInt - transactionDateInt, rs.getDouble("balanceChange")); 
				 history.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private String removeChars(String date) {
		String ret ="";
		for(int x = 0; x <date.length(); x++){
			if(date.substring(x, x+1).equals("-") == false){
				ret+=date.substring(x,x+1);
			}
		}
		return ret;
		
	}

	double calculateInterest(){        

		double interest;
		double avgdlybal;
		double sum = 0;   
		for (int i = 0; i < 30; i++){                       //starts with most recent transactions and loops down to 30 day old transactions

			for (Transaction x: history){

				if (x.age == i){                            //checks for transactions that happened on current "day"
					
						balance = balance + x.amount;
						balance = Math.round(balance*100.0)/100.0;
				} 
			}
			sum = sum + balance;
		}

		avgdlybal = sum / 30;                           //total is then divided by 30 to get avg daily balance
                
		interest = (avgdlybal * rate)/12;               
		interest = Math.round(interest*100.0)/100.0;    //rounds off the final amount


		return interest;                                //returns result
	}
}
