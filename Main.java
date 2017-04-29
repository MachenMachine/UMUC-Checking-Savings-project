
// Main.java This is only for creating Objects to test the interestCalc object and will not be included in final
// Lennon Brixey

import java.sql.SQLException;
import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) throws SQLException {
		JFrame frame = new JFrame();
		new LoginGUI(frame, "Banking App");
	
	}

}