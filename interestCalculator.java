
//interestCalculator Class
//This class will take transaction history to and give an interest amount for the last 30 days
//Lennon Brixey

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class interestCalculator {
	
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
        
	
	interestCalculator(String username) throws SQLException{

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
			}
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
                
                double[] interest = new double[2];
                
                interest = this.calculateInterest(daysinMonth);
                
                sm.updateBalance(username, "saving", interest[0]);
                sm.updateBalance(username, "checking", interest[1]);
                
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
        

	double[] calculateInterest(int daysinMonth){        

		double[] interest = new double[2];
		double savingavgdlybal = 0, checkingavgdlybal = 0;
		double savingSum = 0, checkingSum = 0;  
                double svgBal = 0;
                double chkBal = 0;
                
                svgBal = svgBal;
                
             
                chkBal = checkingBalance;
                
		for (int i = 0; i < daysinMonth; i++){                       //starts with most recent transactions and loops down to 30 day old transactions
           
			for (Transaction x: savingHistory){

                        
				if (x.age == i){                            //checks for transactions that happened on current "day"
					
                                             
						svgBal = svgBal - x.amount;
						svgBal = Math.round(svgBal*100.0)/100.0;
                                                
				} 
                               
			}
			savingSum = savingSum + svgBal;
		}

		savingavgdlybal = savingSum / daysinMonth;                           //total is then divided by 30 to get avg daily balance
                savingavgdlybal = Math.round(savingavgdlybal*100.0)/100.0;
                
		interest[0] = (savingavgdlybal * savingRate)/12;               
		interest[0] = Math.round(interest[0]*100.0)/100.0;    //rounds off the final amount

                for (int i = 0; i < daysinMonth; i++){                       //starts with most recent transactions and loops down to 30 day old transactions

			for (Transaction y: checkingHistory){

                        
				if (y.age == i){                            //checks for transactions that happened on current "day"
					
                                           
						chkBal = chkBal - y.amount;
						chkBal = Math.round(chkBal*100.0)/100.0;
                                                
				} 
                               
			}
			checkingSum = checkingSum + chkBal;
		}

		checkingavgdlybal = checkingSum / daysinMonth;                           //total is then divided by 30 to get avg daily balance
                checkingavgdlybal = Math.round(checkingavgdlybal*100.0)/100.0;
                 System.out.println(checkingSum);
                
                
		interest[1] = (checkingavgdlybal * checkingRate)/12;               
		interest[1] = Math.round(interest[1]*100.0)/100.0;    //rounds off the final amount
                
               
		return interest;                                //returns result
	}
        
        @Override
        public String toString(){
            
        int day = generateDate.getDate();
        double[] interest = new double[2];
        interest[0] = 0; interest[1] = 0;
        
       
        
        interest = this.calculateInterest(day);           
            
            
            return "Savings: " + interest[0] + " earned this month." + 
                    "\nChecking: " + interest[1] + " earned this month.";
        }
        
        
}
