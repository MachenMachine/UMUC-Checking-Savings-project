
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
 * 2			21 April 2017	Added Functionality						Lennon
 * 3			3  May 2017		removed unused code						Ken
 * 
 * 
*/

// Main.java This is only for creating Objects to test the interestCalc object and will not be included in final
// Lennon Brixey

import java.sql.SQLException;
import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) throws SQLException {
		JFrame frame = new JFrame();
		new LoginGUI(frame, "Banking App");
	
	}//end main method

}//end class Main

