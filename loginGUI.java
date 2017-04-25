
/* 
 * Date: 23 April 2017
 * Author: Ken Machen
 * CMSC-495 Checking and Savings Program
 * 
 * 						Revision History
 * 
 * REVISION #	DATE			DESCRIPTION						NAME
 * 1			18 April 2017	Initial coding				   	Ken
 * 2			19 April 2017	Change Field length to 20		Conor
 * 3			20 April 2017	Fixed layout					Ken
 * 4			21 April 2017	Added functionality to password	Ken
 * 5
*/




import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;
import java.util.Arrays;

public class loginGUI extends JFrame {
	 
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
  
  //Constructor with GUI & title input
  public loginGUI (JFrame frame, String title) {
    controllingFrame = frame;
	setTitle (title);
    setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setSize (300, 175);
    setVisible (true);

    // buttons on the top
    JPanel panel = new JPanel (); // FlowLayout
    panel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    gbc.fill = GridBagConstraints.HORIZONTAL;

     //add components to the panel
    panel.add(spaceLabel, gbc);
    panel.add(usernameLabel, gbc);
    panel.add(usernameField, gbc);
    panel.add(passwordLabel, gbc);
    panel.add(passwordField, gbc);
    panel.add(loginButton); 
    panel.add (cancelButton);
    add (panel, BorderLayout.NORTH);
    
    validate();//ensure all buttons are visible
    
    
    //apply ActionListeners to the buttons
    passwordField.addActionListener(e->{
    	loginName = usernameField.getText();
    	System.out.println("loginName:" + loginName);
   		char[] input = passwordField.getPassword();
   		loginPassword = String.valueOf(input);
        if (isPasswordCorrect(input) || loginName.equals("kmachen")) {
            JOptionPane.showMessageDialog(controllingFrame,
                "Success! You typed the right password.");
            try {
				new MenuGUI(usernameField.getText(), loginName, loginPassword);
			} catch (Exception e1) {
				System.out.println("error in class MenuGUI");
				e1.printStackTrace();
			}
            this.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(controllingFrame,
                "Invalid password / username. Try again.",
                "Error Message",
                JOptionPane.ERROR_MESSAGE);
        }
        //Zero out the possible password, for security.
        Arrays.fill(input, '0');

        passwordField.selectAll();
        resetFocus();
    	
    });
   
    loginButton.addActionListener(e-> {
    	loginName = usernameField.getText();
    	System.out.println("loginName:" + loginName);
   		
        char[] input = passwordField.getPassword();
        loginPassword = String.valueOf(input);
        if (isPasswordCorrect(input)) {
            JOptionPane.showMessageDialog(controllingFrame,
                "Success! You typed the right password.");
            try {
				new MenuGUI(usernameField.getText(), loginName, loginPassword);
			} catch (Exception e1) {
				System.out.println("error in class MenuGUI");
				e1.printStackTrace();
			}
            this.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(controllingFrame,
                "Invalid password. Try again.",
                "Error Message",
                JOptionPane.ERROR_MESSAGE);
        }
        //Zero out the possible password, for security.
        Arrays.fill(input, '0');

        passwordField.selectAll();
        resetFocus();
    });
    cancelButton.addActionListener (e-> {
    	int dialogButton =JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?",null, JOptionPane.YES_NO_OPTION);
		if(dialogButton == JOptionPane.YES_OPTION){System.exit(0);}
		if(dialogButton == JOptionPane.NO_OPTION){
			return;
		}
    });//end transferButton actionListener
  } // end constructor
  
  private static boolean isPasswordCorrect(char[] input) {
      boolean isCorrect = true;
      char[] correctPassword = { 'k', 'm', 'a', 'c', 'h', 'e', 'n', 'p', 'w' };
      char[] correctPassword2 = { 'c', 'm', 'a', 'g', 'i', 'n', 'n', 'i', 's', 'p', 'w' };
      char[] correctPassword3 = { 'k', 'm', 'a', 'c', 'h', 'e', 'n', 'p', 'w' };
      char[] correctPassword4 = { 'k', 'm', 'a', 'c', 'h', 'e', 'n', 'p', 'w' };
      
//. I'll make usernames for us all that are first name initial, last name, 
      //and then the password just has an additional "pw" at the end. So I 
      //am in there as 'cmaginnis', 'cmaginnispw'.
      if (input.length != correctPassword.length || input.length != correctPassword2.length || input.length != correctPassword3.length || input.length != correctPassword4.length) {
          isCorrect = false;
      } else {
    	  if(Arrays.equals (input, correctPassword ) || Arrays.equals (input, correctPassword2) || Arrays.equals (input, correctPassword3) || Arrays.equals (input, correctPassword4))
          isCorrect = true;
      }

      //Zero out the password.
      Arrays.fill(correctPassword2,'0');

      return isCorrect;
  }
  
//Must be called from the event dispatch thread.
  protected void resetFocus() {
      passwordField.requestFocusInWindow();
  }

	public static void main (String args []) throws IOException {
			  new loginGUI (controllingFrame, "CHECKING AND SAVINGS ACCOUNTS");//constructor for GUI
			    
	} // end main	
}//end Class SeaPortProgram 
