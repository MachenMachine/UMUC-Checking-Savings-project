
/* 
 * Date: 23 April 2017
 * Author: Ken Machen
 * CMSC-495 Checking and Savings Program
 * 
 * 						Revision History
 * 
 * REVISION #	DATE			DESCRIPTION							NAME
 * 1			18 April 2017	Initial coding				   		Ken
 * 2			19 April 2917	Fixed radio buttons/groups			Ken
 * 2			20 April 2017	Fixed layout/ added Error catching	Ken
 * 3			21 April 2017	changed what to do on exit			Ken
 * 4	
*/
 

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

public class TransferGUI extends JFrame implements PropertyChangeListener{
	
  static final long serialVersionUID = 25;
  private JButton transferButton = new JButton ("Transfer");
  private JLabel fromAccount = new JLabel("Transfer From:");
  private JLabel toAccount = new JLabel("Transfer To:");
  private JRadioButton checkingRadio = new JRadioButton("Checking");
  private JRadioButton savingsRadio = new JRadioButton("Saving");
  private JRadioButton checkingRadio2 = new JRadioButton("Checking");
  private JRadioButton savingsRadio2 = new JRadioButton("Savings");
  private JLabel transferLabel = new JLabel ("Enter Transfer Amount. ", SwingConstants.CENTER);
  private JTextArea jTextArea = new JTextArea ();
  private JFormattedTextField transferField;
  private NumberFormat amountFormat; 
  private double amount = 0.00;
  
 
  //Constructor with GUI & title input
  public TransferGUI (String title, String loginName) {
    setTitle (title );
   // setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null);
    setSize (600, 400);
    setVisible (true);

    //set format of textField
    amountFormat = NumberFormat.getNumberInstance();
    transferField = new JFormattedTextField(amountFormat);
    transferField.setValue(new Double(amount));
    transferField.setColumns(20);
    transferField.addPropertyChangeListener("value", this);
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

    checkingRadio.setActionCommand("from Checking");
    savingsRadio.setActionCommand("from Savings");
    checkingRadio2.setActionCommand("to Checking");
    savingsRadio2.setActionCommand("to Savings");
    
    ButtonGroup fromGroup = new ButtonGroup();
    fromGroup.add(checkingRadio);
    fromGroup.add(savingsRadio);
    
    ButtonGroup toGroup = new ButtonGroup();
    toGroup.add(checkingRadio2);
    toGroup.add(savingsRadio2);
    
    //add components to the panel
    panel.add(fromAccount, gbc);
    panel.add(checkingRadio, gbc);
    panel.add(savingsRadio, gbc);
    panel.add(toAccount, gbc);
    panel.add(checkingRadio2, gbc);
    panel.add(savingsRadio2, gbc);
    panel.add(transferLabel, gbc);
    panel.add(transferField, gbc);
    panel.add(transferButton, gbc);
    //panel.add(transferField, gbc);
   	add (panel, BorderLayout.NORTH);
    
    validate();//ensure all buttons are visible
    
    SessionManager mySession = new SessionManager();
    //apply ActionListeners to the buttons
    transferButton.addActionListener (e-> {jTextArea.setText("");
    	String stringAmount = String.valueOf(amount);
    	if((checkingRadio.isSelected() || savingsRadio.isSelected()) && (checkingRadio2.isSelected() || savingsRadio2.isSelected()) ){
    		if((amount > 0) ){
		    	int dialogButton =JOptionPane.showConfirmDialog(null, "Are you sure you want to transfer $" + amount + "?", "CONFIRM TRANSACTION", JOptionPane.YES_NO_OPTION);
				if(dialogButton == JOptionPane.YES_OPTION){
					jTextArea.setText("");
					jTextArea.append("Transfering $" + amount + " " + fromGroup.getSelection().getActionCommand() + " " + toGroup.getSelection().getActionCommand());
					try {
						mySession.transferMoney(loginName, fromGroup.getSelection().getActionCommand(), toGroup.getSelection().getActionCommand(), stringAmount);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					jTextArea.append("\n\n$" + amount + " Transferred Successfully!");
					try {
						jTextArea.append("\n\nNew Checking Balance: " + mySession.getBalance(loginName, "checking"));
						jTextArea.append("\nNew Savings Balance: " + mySession.getBalance(loginName, "saving"));
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				if(dialogButton == JOptionPane.NO_OPTION){
					jTextArea.setText("");
					jTextArea.append("Transaction Cancelled!");
					return;
				}
	    	}else{
	    		JOptionPane.showMessageDialog(null, "Please Enter a Positive number.");
	    	}
    	}else {
    		JOptionPane.showMessageDialog(null, "Please Select To and From Accounts.");
    	}
    	transferField.setValue(0.00);
    	amount = 0.00;
    });//end transferButton actionListener
    transferField.addActionListener(e-> {
    	//transferField.setFormatterFactory();
    	    
    });
    
    
    
  } // end constructor


	@Override
	public void propertyChange(PropertyChangeEvent e) {
		Object source = e.getSource();
	    if (source == transferField) {
	        amount = ((Number)transferField.getValue()).doubleValue();
	    } 
	}
	

  
}//end Class SeaPortProgram 
