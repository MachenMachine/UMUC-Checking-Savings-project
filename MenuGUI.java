
/* 
 * Date: 23 April 2017
 * Author: Ken Machen
 * CMSC-495 Checking and Savings Program
 * 
 * 						Revision History
 * 
 * REVISION #	DATE			DESCRIPTION						NAME
 * 1			18 April 2017	Initial coding				   	Ken
 * 2			20 April 2017	Added Functionality to buttons	Ken
 * 3			21 April 2017	Chanked layout					Ken
 * 4
*/


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class MenuGUI extends JFrame{
	
	
  static final long serialVersionUID = 25;
  private JButton balanceButton = new JButton ("BALANCES");
  private JButton transferButton = new JButton ("Transfer");
  private JButton checkingAccountHistoryButton = new JButton (" CHECKING ACCOUNT HISTORY");
  private JButton savingsAccountHistoryButton = new JButton ("SAVINGS ACCOUNT HISTORY");
  private JButton interestButton = new JButton("INTEREST EARNINGS");
  private JTextArea jTextArea = new JTextArea ();
  private List history = new LinkedList<>();
  //private JTextField transferField = new JTextField(20);
 // private JLabel emptyLabel = new JLabel("Space");
  
 
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
    //panel.setLayout(new GridLayout(7,2));
    panel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    gbc.fill = GridBagConstraints.HORIZONTAL;

    //add components to the panel
   
    panel.add(balanceButton, gbc);
    panel.add (transferButton, gbc);
    //panel.add(transferField, gbc);
   	panel.add (checkingAccountHistoryButton, gbc);
    panel.add (savingsAccountHistoryButton, gbc);
    panel.add (interestButton, gbc);
    add (panel, BorderLayout.NORTH);
    
    validate();//ensure all buttons are visible
    
    //apply ActionListeners to the buttons
   SessionManager mySession = new SessionManager();
   interestCalculator myInterest = new interestCalculator();
   String cBalance = mySession.getBalance(loginName, "checking").replace("$", "");
   String sBalance = mySession.getBalance(loginName, "saving").replace("$", "");
   double checkingBalance = Double.parseDouble(cBalance);
   double savingsBalance = Double.parseDouble(sBalance);
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
    	new TransferGUI("FUNDS TRANSFER", loginName);
    });//end transferButton actionListener
    checkingAccountHistoryButton.addActionListener(e-> { jTextArea.setText("");
    	try {
			jTextArea.append("Checking History: " + mySession.getTransactionHistory(loginName, "checking"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
    	
    });//end checkingAccountHistoryButton actionListener
    savingsAccountHistoryButton.addActionListener(e-> {jTextArea.setText("");
    try {
		jTextArea.append("Savings History: " + mySession.getTransactionHistory(loginName, "saving"));
	} catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}	
    });//end savingsAccountHistoryButton listener
    	
    interestButton.addActionListener(e-> {jTextArea.setText("");//end displyButton listener 
    	jTextArea.append("Interest Earnings Checking: " + myInterest.calculateInterest(history, checkingBalance));
    	jTextArea.append("\nInterest Earnings Checking: " + myInterest.calculateInterest(history, savingsBalance));
    });//end interestButton listener
   
  } // end constructor
  
}//end Class SeaPortProgram 
