
//interestCalculator Class
//This class will take transaction history to and give an interest amount for the last 30 days
//Lennon Brixey

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class interestCalculator {
	
	List<Transaction> history = new ArrayList<>();
	double balance = 0;
        double rate;
        
        
        
        
        interestCalculator(String username){
            
                   
                int daysinMonth;
            	
		Date generateDate = new Date();
                
                int thisMonth = generateDate.getMonth()+1;
                int thisDay = generateDate.getDate();
         
               /* following if statements check to see what month it is and assign appropriate amount of days for that month */
                
                
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
                
            interestCalculator saving = new interestCalculator(username, "saving", daysinMonth);                //builds new calculator for each account type
            interestCalculator checking = new interestCalculator(username, "checking", daysinMonth);
                
                
            }
        }
        
        
	
	interestCalculator(String username, String accountType, int daysinMonth){

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
                else if (accountType.equals("saving")){
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
                                 
                                 System.out.println(transactionDateInt);
                                 
				 int todayDateInt = Integer.parseInt(todayDate);
                                 
                                 System.out.println(todayDateInt);
				 Transaction t = new Transaction(todayDateInt - transactionDateInt, rs.getDouble("balanceChange")); 
				 history.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
                
                this.calculateInterest(daysinMonth);                             //this results needs to added as a transaction to the current account
                
                
                
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

	double calculateInterest(int daysinMonth){        

		double interest;
		double avgdlybal;
		double sum = 0;   
		for (int i = 0; i < daysinMonth; i++){                       //starts with most recent transactions and loops down to 30 day old transactions

			for (Transaction x: history){

                        
				if (x.age == i){                            //checks for transactions that happened on current "day"
					
                                                System.out.println("Transaction: " + x.age + " for: " + x.amount);
						balance = balance + x.amount;
						balance = Math.round(balance*100.0)/100.0;
                                                
				} 
                               
			}
			sum = sum + balance;
		}

		avgdlybal = sum / 30;                           //total is then divided by 30 to get avg daily balance
                avgdlybal = Math.round(avgdlybal*100.0)/100.0;
                
                
                System.out.println(avgdlybal);
                
		interest = (avgdlybal * rate)/12;               
		interest = Math.round(interest*100.0)/100.0;    //rounds off the final amount

               
		return interest;                                //returns result
	}
}
