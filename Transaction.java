/* 
 *
 * Date: 		5 May 2017
 * Authors: 	Matthew Nielsen, Conor Maginnis, Lennon Brixey, Ken Machen
 * CMSC-495 	Checking and Savings Program
 * Purpose:   	Works with the InterextCalculator class to pass date and amount.	
 * 
 * 						Revision History
 * 
 * REVISION #	DATE			DESCRIPTION								NAME
 * 1			18 April 2017	Initial coding				   			Lennon
 * 2			20 April 2017	Added Functionality						Lennon
 * 3			3  May 2017		removed unused code						Ken
 * 
 * 
*/

//Transaction class for building the transaction history for interest Calculator

public class Transaction {
            
    int age;  
    double amount;
    
    Transaction(int date, double amt){

        this.age = date;
        this.amount = amt;
    }
    
}