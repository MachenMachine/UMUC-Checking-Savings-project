
// File: SeaPortProgram.java
// Date: 5 March 2017
// Author: Ken Machen
// Purpose: demonstrate the development of a project - 
//    in this case, SeaPort project



import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

public class MenuGUI extends JFrame{
	
  static final long serialVersionUID = 25;
  private JButton balanceButton = new JButton ("BALANCE");
  private JButton transferButton = new JButton ("Transfer");
  private JButton checkingAccountHistoryButton = new JButton (" CHECKING ACCOUNT HISTORY");
  private JButton savingsAccountHistoryButton = new JButton ("SAVINGS ACCOUNT HISTORY");
  private JTextArea jTextArea = new JTextArea ();  
  private JButton interestButton = new JButton("INTEREST EARNINGS");
 
  //Constructor with GUI & title input
  public MenuGUI (String title) {
    setTitle (title + "'s Accounts");
    setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
    setLocation(400, 200);
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
    panel.setLayout(new GridLayout(4,2));
    //add components to the panel
   
    panel.add(balanceButton); 
    panel.add (transferButton);
    panel.add (checkingAccountHistoryButton);
    panel.add (savingsAccountHistoryButton);
    panel.add (interestButton);
    add (panel, BorderLayout.NORTH);
    
    validate();//ensure all buttons are visible
    
    //apply ActionListeners to the buttons
   
    balanceButton.addActionListener(e-> {
    		
    		jTextArea.append("Balance Button Pressed");
    });
    transferButton.addActionListener (e-> {
    		
    });//end transferButton actionListener
    checkingAccountHistoryButton.addActionListener(e-> { 
    		
    	});//end checkingAccountHistoryButton actionListener
    savingsAccountHistoryButton.addActionListener(e-> {jTextArea.setText("");}   );//end savingsAccountHistoryButton listener
    
    interestButton.addActionListener(e-> {     jTextArea.setText("");//end displyButton listener 
    		});//end interestButton listener
   
  } // end constructor
  
}//end Class SeaPortProgram 
