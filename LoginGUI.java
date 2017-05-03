
/* 
 * Date: 		5 May 2017
 * Authors: 	Matthew Nielsen, Conor Maginnis, Lennon Brixey, Ken Machen
 * CMSC-495 	Checking and Savings Program
 * Purpose:   	interface to prompt user input of name and password to access 
 * 				accounts through the session manager
 * 
 * 					Revision History
 * 
 * REVISION #	DATE			DESCRIPTION						NAME
 * 1			18 April 2017	Initial coding				   	Ken
 * 2			19 April 2017	Change Field length to 20		Conor
 * 3			20 April 2017	Fixed layout					Ken
 * 4			21 April 2017	Added functionality to password	Ken
 * 5			25 April 2017	Use Session Manager for verify	Conor
 * 6			26 April 2017	Cleaned up code remove joption	Ken
 * 
*/

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.util.Arrays;

public class LoginGUI extends JFrame {
	 
  static final long serialVersionUID = 25;
  private static JFrame controllingFrame;
  private JButton loginButton = new JButton ("LOGIN");
  private JButton cancelButton = new JButton ("CANCEL");
  private JLabel usernameLabel = new JLabel ("USERNAME", SwingConstants.CENTER);
  private JTextField usernameField = new JTextField(20);
  private JLabel passwordLabel = new JLabel ("PASSWORD", SwingConstants.CENTER);
  private JLabel spaceLabel = new JLabel ("  ", SwingConstants.CENTER);
  private JPasswordField passwordField = new JPasswordField(20);
  private String loginName;
  private String loginPassword;
  private JTextArea jTextArea = new JTextArea ();
  private JScrollPane agreementPane = new JScrollPane(jTextArea);
  private JCheckBox agreeCheck = new JCheckBox("Accept User Agreement"); 
  private String agreement;
  
  //Constructor with GUI & title input
  public LoginGUI (JFrame frame, String title) {
    controllingFrame = frame;

	setTitle (title);
	Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("piggy.png"));
	ImageIcon icon = new ImageIcon(image);
	setIconImage(icon.getImage());
    
	setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setSize (500, 350);
    setVisible (true);

    // buttons on the top
    JPanel panel = new JPanel (); // FlowLayout
    JPanel panel2 = new JPanel ();
    panel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    Dimension dimension = new Dimension(450, 150);
    agreementPane.setPreferredSize(dimension);
    getAgreement();
    agreeCheck.setSelected(false);
    
    
     //add components to the panel
    panel.add(spaceLabel, gbc);
    panel.add(usernameLabel, gbc);
    panel.add(usernameField, gbc);
    panel.add(passwordLabel, gbc);
    panel.add(passwordField, gbc);
    
    panel.add(loginButton); 
    panel.add (cancelButton);
    add (panel, BorderLayout.NORTH);
    
    panel2.add(agreeCheck);
    //panel2.add(agreeLabel);
    panel2.add(agreementPane);
    add (panel2, BorderLayout.CENTER);
    
    validate();//ensure all buttons are visible
    
    //apply ActionListeners to the buttons
    passwordField.addActionListener(e->{
    	loginName = usernameField.getText();//get user name input
    	char[] input = passwordField.getPassword();//get password from field
        loginPassword = String.valueOf(input);//convert input to string
        if(agreeCheck.isSelected()){
	        if (isPasswordCorrect(input) && agreeCheck.isSelected()) {//verify username and password using Session Manager class
	            try {
					new MainGUI(usernameField.getText(), loginName, loginPassword);//call menu if password correct
				} catch (Exception e1) {
					System.out.println("error in class MenuGUI");
					e1.printStackTrace();
				}
	            this.setVisible(false);
	        } else {//display error message if password is incorrect
	            JOptionPane.showMessageDialog(controllingFrame,
	                "Invalid password. Try again.",
	                "Error Message",
	                JOptionPane.ERROR_MESSAGE);
	        }
        }else{
        	JOptionPane.showMessageDialog(controllingFrame, "Must Accept User Agreement to Continue!",
	                "Error Message",
	                JOptionPane.ERROR_MESSAGE);
        }
        //Zero out the possible password, for security.
        Arrays.fill(input, '0');
        passwordField.selectAll();
        resetFocus();
    });//end passwordField actionListener
   
    loginButton.addActionListener(e-> {
    	loginName = usernameField.getText();//get username input
    	char[] input = passwordField.getPassword();// get password from field
        loginPassword = String.valueOf(input);//convert input to string
        if(agreeCheck.isSelected()){
	        if (isPasswordCorrect(input) && agreeCheck.isSelected()) {//verify username and password using Session Manager class
	            try {
					new MainGUI(usernameField.getText(), loginName, loginPassword);//call menu if password correct
				} catch (Exception e1) {
					System.out.println("error in class MenuGUI");
					e1.printStackTrace();
				}
	            this.setVisible(false);
	        } else {//display error message if password is incorrect
	            JOptionPane.showMessageDialog(controllingFrame,
	                "Invalid password. Try again.",
	                "Error Message",
	                JOptionPane.ERROR_MESSAGE);
	        }
        }else{
        	JOptionPane.showMessageDialog(controllingFrame, "Must Accept User Agreement to Continue!",
	                "Error Message",
	                JOptionPane.ERROR_MESSAGE);
        }
        //Zero out the possible password, for security.
        Arrays.fill(input, '0');
        passwordField.selectAll();
        resetFocus();
    });//end loginButton ActionListener
    
    cancelButton.addActionListener (e-> {
    	int dialogButton =JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?",null, JOptionPane.YES_NO_OPTION);
		if(dialogButton == JOptionPane.YES_OPTION){System.exit(0);}
		if(dialogButton == JOptionPane.NO_OPTION){
			return;
		}
    });//end cancelButton actionListener
  } // end constructor
  
  //method to verify login information
  private boolean isPasswordCorrect(char[] input) {
      SessionManager sm = new SessionManager();//call method in SessionManager class
  return sm.verifyLogin(loginName, loginPassword);
  }
  
//Must be called from the event dispatch thread.
  protected void resetFocus() {
      passwordField.requestFocusInWindow();
  }
 
  public String getAgreement(){ 
	  
	  	agreement = "ACCOUNT AGREEMENT (NOT A REAL CONTRACT!!)" 
		  + "\nYOUR CONTRACT WITH US" 
	  	  + "\nThis document, and any future changes to it, "
		  + "\nis your contract with us. We will refer to "
		  + "\nthis document as your 'Agreement' or"
		  + "\n'Account Agreement'; these terms also include "
  		  + "\nany changes we may make to this document from time to time."
		  + "\nWe reserve the right to amend this Agreement at any time, by "
		  + "\nadding, deleting, or changing provisions of this Agreement."
		  + "\nAll amendments will comply with the applicable notice "
		  + "\nrequirements of federal and North Carolina law that are in "
		  + "\neffect at any time."
		  + "\nIf an amendment gives you the opportunity to reject the change, "
		  + "\nand if you reject the change in the manner provided in such "
		  + "\namendment, we may terminate your right to receive credit and "
		  + "\nmay ask you to return all credit devices as a condition of your"
		  + "\nrejection. We may replace your card with another card at any time."
		  + "\nThe reasons we may amend this Agreement include the following:"
		  + "\n-Changes in regulation or legislation, or a change in the "
		  + "\n interpretation of a regulation or legislation."
		  + "\n-Changes related to your individual credit history, such as: "
		  + "\n your risk profile, your payment or transaction patterns, "
		  + "\n balance patterns, the utilization levels of this and other"
		  + "\n accounts, credit bureau information including the age, "
		  + "\n history and type of other naccounts, and the measure of "
		  + "\n risk associated with each."
		  + "\n-Changes to overall economic and market trends, product design,"
		  + "\n and business needs."
		  + "\n'We', 'us', 'our' means Bank of America, N.A., also known as "
		  + "FIA Card"
		  + "\nServices and referred to in future communications as FIA Card "
		  + "Services."
		  + "\n'You' and 'your' mean each and all of the persons who are "
		  + "\ngranted, accept or use the account and any person who has guaranteed"
		  + "\npayment "
		  + "\nof the account."
		  + "\nvYou may use your account for personal, family, or household "
		  + "\npurposes."
		  + "\nYou may not use your account for business or commercial purposes."
		  + "\nOur failure or delay in exercising any of our rights under this "
		  + "\nAgreement does not mean that we are unable to exercise those"
		  + "\nRights later."
		  + "\n"
		  + "\nTYPES OF TRANSACTIONS"
		  + "\nYou may obtain credit in the form of Purchases, Balance Transfers,"
		  + "\nand Cash Advances, by using cards, access checks, an account number, "
		  + "\nor other credit devices. Cards are all the Accounts we issue to you "
		  + "\nand to any other person with authorization for use on this account "
		  + "\npursuant to this Agreement. An access check is a check we provide to"
		  + "\n you to obtain credit on this account. All access checks include an "
		  + "\nexpiration date printed at the top. We will honor access checks "
		  + "\nreceived for payment before the expiration date printed on the check,"
		  + "\nprovided your account is open and in good standing, with available "
		  + "\ncredit. Access checks without a printed expiration date will not be "
		  + "\nhonored. Sign your card before using it."
		  + "\nPurchase means the use of your card or account number (including "
		  + "\nthrough the use of an enabled mobile device) to:"
		  + "\n1. buy or lease goods or services;"
		  + "\n2. buy wire transfers from a non-financial institution (Wire "
		  + "\nTransfer Purchase);"
		  + "\n3. Make a transaction that is not otherwise a Cash Advance."
		  + "\n"
		  + "\nPurchases include Account Fees, as well as Transaction Fees and "
		  + "\nadjustments associated with any Purchase. Balance Transfer means"
		  + "\na transfer of funds to another creditor initiated by us at your"
		  + "\nrequest. A Balance Transfer does not include a transaction that is"
		  + "\notherwise a Cash Advance, except that any Direct Deposit completed"
		  + "\nat the time of your application for this account will be treated as"
		  + "\na Balance Transfer. Balance Transfers include Transaction Fees and"
		  + "\nadjustments associated with any Balance Transfer."
		  + "\n\nA Cash Advance means the use of your account for a loan in the "
		  + "\nfollowing ways:"
		  + "\n1. Direct Deposit: by a transfer of funds via an ACH"
		  + "\n   Automated Clearing House) transaction to a deposit"
		  + "\n   account initiated by us at your request. A Direct"
		  + "\n   Deposit does not include an Overdraft Protection"
		  + "\n   Cash Advance or a Same-Day Online"
		  + "\n2. Cash Advance."
		  + "\n3. Check Cash Advance: by an access check you sign as drawer."
		  + "\n4. Bank Cash Advance: by loans accessed in the following manner:"
		  + "\n  a.ATM Cash Advance: at an automated teller machine;"
		  + "\n	 b.Over the Counter ('OTC') Cash Advance: at any financial "
  		  + "\n    institution (e.g., to obtain cash, money orders, wire transfers,"
		  + "\n	   or travelers checks);"
  		  + "\n  c.Same-Day Online Cash Advance: by a same day online funds "
  		  + "\n	   transfer to a deposit account;"
  		  + "\n  d.Overdraft Protection Cash Advance: by a transfer of funds"
  		  + "\n    to a deposit account pursuant to an overdraft protection "
  		  + "\n    program (see the section titled Overdraft Protection below);"
  		  + "\n	 e.Cash Equivalents: by the purchase of foreign currency, money "
  		  + "\n    orders, travelers checks, or to obtain cash, each from a "
  		  + "\n    non-financial institution, or person-to-person money transfers,"
  		  + "\n	   bets, lottery tickets purchased outside the United States, "
  		  + "\n	   casino gaming chips, or bail bonds, with your card or account "
  		  + "\n    number (including through the use of an enabled mobile device)."
  		  + "\nCash Advances include Transaction Fees and adjustments associated with "
  		  + "\nany Cash Advance."
  		  + "\nAll Bank Cash Advances are subject to the Cash Credit Line. For more "
  		  + "\ninformation on credit lines, please refer to the section titled Your "
  		  + "\nRevolving Lines within this Agreement.";
  		jTextArea.setText(agreement);  
	  	jTextArea.setEditable(false);
	  	return agreement;
  }  //end method betAgreement

}//end Class LoginGUI 

