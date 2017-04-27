
// Main.java This is only for creating Objects to test the interestCalc object and will not be included in final
// Lennon Brixey

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;






public class Main {


	public static void main(String[] args) throws SQLException {
		JFrame frame = new JFrame();
		LoginGUI login = new LoginGUI(frame, "Banking App");

		//SessionManager sm = new SessionManager();
		// sm.transferMoney("cmaginnis", "saving", "checking", "11.00");
		//System.out.println( sm.getTransactionHistory("cmaginnis", "saving"));
		//System.out.println(sm.getBalance("cmaginnis", "saving"));
		//	interestCalculator ic = new interestCalculator("cmaginnis", "saving");
		//	System.out.println(ic.calculateInterest());
		//	System.out.println(sm.hashPassword("lbrixeypw"));
	}

}