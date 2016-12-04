package myUni;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.PatternSyntaxException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class AccountInfoView extends JFrame{
	
	private int loggedInStudentID; 

	public AccountInfoView(int loggedInStudentID){
		
		this.loggedInStudentID = loggedInStudentID;
		
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.TRAILING));

		JLabel nameLabel = new JLabel("Name");
		panel.add(nameLabel);
		JTextField nameTextField = new JTextField("", 10);
		nameTextField.setText(new MySQLConnect().getName(loggedInStudentID));
		panel.add(nameTextField);
		
		JLabel gpaLabel = new JLabel("GPA");
		panel.add(gpaLabel);
		JTextField gpaTextField = new JTextField("", 5);
		gpaTextField.setText(String.valueOf(new MySQLConnect().getGPA(loggedInStudentID)));
		panel.add(gpaTextField);
		
		JLabel passwordLabel = new JLabel("Password");
		panel.add(passwordLabel);
		JPasswordField passwordTextField = new JPasswordField("", 10);
		passwordTextField.setText(new MySQLConnect().getPassword(loggedInStudentID));
		panel.add(passwordTextField);
		
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  try {
					new MySQLConnect().updateAccountInfo(loggedInStudentID, nameTextField.getText().toString(), passwordTextField.getText().toString(), Float.parseFloat(gpaTextField.getText().toString()));
					dispose();
				  } catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			  } 
			});
		
	    this.add(panel, BorderLayout.WEST);
	    this.add(saveButton, BorderLayout.SOUTH);
	    this.setSize(500, 300);
		this.setLocationRelativeTo(null);
	    this.setVisible(true);
	}
}
