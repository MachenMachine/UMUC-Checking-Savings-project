
/* 
 * Date: 30 April 2017
 * Author: Ken Machen
 * CMSC-495 Checking and Savings Program
 * 
 * 						Revision History
 * 
 * REVISION #	DATE			DESCRIPTION						NAME
 * 1			18 April 2017	Initial coding				   	Ken
 * 2			20 April 2017	Added Functionality to buttons	Ken
 * 3			21 April 2017	Chanked layout					Ken
 * 4			25 April 2017	Changed connection to interest	Conor
 * 5			26 April 2017   Code clead up					Ken
*/


import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.sql.SQLException;

public class MenuGUI extends JFrame{
  static final long serialVersionUID = 25;
  private JButton balanceButton = new JButton ("BALANCES");
  private JButton transferButton = new JButton ("Transfer");
  private JButton checkingAccountHistoryButton = new JButton (" CHECKING ACCOUNT HISTORY");
  private JButton savingsAccountHistoryButton = new JButton ("SAVINGS ACCOUNT HISTORY");
  private JButton interestButton = new JButton("INTEREST EARNINGS");
  private JTextArea jTextArea = new JTextArea ();
  
  //Constructor with GUI & title input
  public MenuGUI (String title, String loginName, String loginPassword) throws NumberFormatException, SQLException {
    setTitle (title + "'s Accounts");
    setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setSize (600, 400);
    setVisible (true);

    // put scroll bars around the text area
    JScrollPane scrollPane = new JScrollPane (jTextArea);
    add (scrollPane, BorderLayout.CENTER); 
    jTextArea.setCaretPosition(jTextArea.getDocument().getLength());
	setBackground(Color.lightGray);
	JPanel inputOutputPanel = new JPanel();
	inputOutputPanel.setLayout(new GridLayout(6,0,0,5));
    
    // buttons on the top
    JPanel panel = new JPanel (); // FlowLayout
    panel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    gbc.fill = GridBagConstraints.HORIZONTAL;

    //add components to the panel
    panel.add(balanceButton, gbc);
    panel.add (transferButton, gbc);
    panel.add (checkingAccountHistoryButton, gbc);
    panel.add (savingsAccountHistoryButton, gbc);
    panel.add (interestButton, gbc);
    add (panel, BorderLayout.NORTH);
    validate();//ensure all buttons are visible
    
    SessionManager mySession = new SessionManager();
    interestCalculator myInterestChecking = new interestCalculator(loginName, "checking");
    interestCalculator myInterestSaving = new interestCalculator(loginName, "saving");
    //apply ActionListeners to the buttons
    balanceButton.addActionListener(e-> {jTextArea.setText("");
    		
    	try {
			jTextArea.append("Checking Balance: " + mySession.getBalance(loginName, "checking"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	try {
			jTextArea.append("\nSavings Balance: " + mySession.getBalance(loginName, "saving"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    });
    transferButton.addActionListener (e-> {jTextArea.setText("");
    	jTextArea.append("transfer Button Pressed");
    	try {
			new TransferGUI("FUNDS TRANSFER", loginName);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    });//end transferButton actionListener
    checkingAccountHistoryButton.addActionListener(e-> { jTextArea.setText("");
    	try {
			jTextArea.append("Checking History: \n" + mySession.getTransactionHistory(loginName, "checking"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
    	
    });//end checkingAccountHistoryButton actionListener
    savingsAccountHistoryButton.addActionListener(e-> {jTextArea.setText("");
    try {
		jTextArea.append("Savings History: \n" + mySession.getTransactionHistory(loginName, "saving"));
	} catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}	
    });//end savingsAccountHistoryButton listener
    	
    interestButton.addActionListener(e-> {jTextArea.setText("");//end displyButton listener 
    	jTextArea.append("Interest Earnings Checking: " + myInterestChecking.calculateInterest());
    	jTextArea.append("\nInterest Earnings Savings: " + myInterestSaving.calculateInterest());
    });//end interestButton listener
   
  } // end constructor
  
}//end Class MenuGUI