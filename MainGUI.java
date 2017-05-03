/* 
 *
 * Date: 		5 May 2017
 * Authors: 	Matthew Nielsen, Conor Maginnis, Lennon Brixey, Ken Machen
 * CMSC-495 	Checking and Savings Program
 * Purpose:   	To provide the User interface with the session manager and other
 * 				GUIs and program classes	
 * 
 * 						Revision History
 * 
 * REVISION #	DATE			DESCRIPTION								NAME
 * 1			18 April 2017	Initial coding				   			Ken
 * 2			20 April 2017	Added Functionality to buttons			Ken
 * 3			21 April 2017	Chanked layout							Ken
 * 4			25 April 2017	Changed connection to interest			Conor
 * 5			26 April 2017   Code clead up							Ken
 * 6			28 April 2017	Changed call to interest Calculator		Ken
 * 7			28 April 2017	Fixed interest call to match changes	Lennon
 * 8			3  May	 2017	Cleaned up warnings						Ken
 * 
*/


import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.SQLException;

public class MainGUI extends JFrame{
  static final long serialVersionUID = 25;
  private JButton balanceButton = new JButton ("BALANCES");
  private JButton transferButton = new JButton ("Transfer");
  private JButton checkingAccountHistoryButton = new JButton (" CHECKING ACCOUNT HISTORY");
  private JButton savingsAccountHistoryButton = new JButton ("SAVINGS ACCOUNT HISTORY");
  private JButton interestButton = new JButton("INTEREST EARNINGS");
  private JButton logoutButton = new JButton("LOG OUT");
  private JTextArea jTextArea = new JTextArea ();
  
  //Constructor with GUI & title input
  public MainGUI (String title, String loginName, String loginPassword) throws NumberFormatException, SQLException {
    setTitle (title + "'s Accounts");
    Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("piggy.png"));
   	ImageIcon icon = new ImageIcon(image);
   	setIconImage(icon.getImage());
    setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setSize (400, 400);
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
    panel.add(logoutButton);
    add (panel, BorderLayout.NORTH);
    validate();//ensure all buttons are visible
    
    SessionManager mySession = new SessionManager();
    InterestCalculator myInterest = new InterestCalculator(loginName);
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

    });//end balanceButton action listener

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
    	jTextArea.append(myInterest.toString());
    	
    });//end interestButton listener
    
    logoutButton.addActionListener(e-> {jTextArea.setText("");//end displyButton listener+
    
	    int dialogButton =JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?",null, JOptionPane.YES_NO_OPTION);
		if(dialogButton == JOptionPane.YES_OPTION){
			try {
				TransferGUI myTransfer = new TransferGUI("FUNDS TRANSFER", loginName);
				myTransfer.close();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			int logButton =JOptionPane.showConfirmDialog(null, "You are now logged out. \nWould you like to log in?",null, JOptionPane.YES_NO_OPTION);
				if(logButton == JOptionPane.YES_OPTION){
					JFrame frame = new JFrame();
			    	new LoginGUI(frame, "Banking App");
				}else{
					System.exit(0);
				}

		}
		if(dialogButton == JOptionPane.NO_OPTION){
			return;
		}
    });//end logoutButton listener
   
  } // end constructor

}//end Class MainGUI

