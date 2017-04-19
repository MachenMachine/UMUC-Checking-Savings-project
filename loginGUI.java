
// File: SeaPortProgram.java
// Date: 5 March 2017
// Author: Ken Machen
// Purpose: demonstrate the development of a project - 
//    in this case, SeaPort project



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
import java.awt.Window;
import java.io.IOException;
import java.util.Arrays;

public class loginGUI extends JFrame {
	
  static final long serialVersionUID = 25;
  private static JFrame controllingFrame;
  private JButton loginButton = new JButton ("LOGIN");
  private JButton cancelButton = new JButton ("CANCEL");
  private JLabel usernameLabel = new JLabel ("USERNAME", SwingConstants.CENTER);
  private JTextField usernameField = new JTextField(10);
  private JLabel passwordLabel = new JLabel ("PASSWORD", SwingConstants.CENTER);
  private JPasswordField passwordField = new JPasswordField(10);
  
  //Constructor with GUI & title input
  public loginGUI (JFrame frame, String title) {
    controllingFrame = frame;
	setTitle (title);
    setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
    setLocation(400, 200);
    setSize (600, 400);
    setVisible (true);

    // buttons on the top
    JPanel panel = new JPanel (); // FlowLayout
    //panel.setLayout(new GridLayout(7,1));
    panel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    gbc.fill = GridBagConstraints.HORIZONTAL;

     //add components to the panel
    panel.add(usernameLabel, gbc);
    panel.add(usernameField, gbc);
    panel.add(passwordLabel, gbc);
    panel.add(passwordField, gbc);
    panel.add(loginButton); 
    panel.add (cancelButton,gbc);
    add (panel, BorderLayout.NORTH);
    
    validate();//ensure all buttons are visible
    
    //apply ActionListeners to the buttons
    passwordField.addActionListener(e->{
   		char[] input = passwordField.getPassword();
        if (isPasswordCorrect(input)) {
            JOptionPane.showMessageDialog(controllingFrame,
                "Success! You typed the right password.");
            try {
				new MenuGUI(usernameField.getText());
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
   
    loginButton.addActionListener(e-> {
        char[] input = passwordField.getPassword();
        if (isPasswordCorrect(input)) {
            JOptionPane.showMessageDialog(controllingFrame,
                "Success! You typed the right password.");
            try {
				new MenuGUI(usernameField.getText());
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
      char[] correctPassword = { 'b', 'u', 'g', 'a', 'b', 'o', 'o' };

      if (input.length != correctPassword.length) {
          isCorrect = false;
      } else {
          isCorrect = Arrays.equals (input, correctPassword);
      }

      //Zero out the password.
      Arrays.fill(correctPassword,'0');

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
