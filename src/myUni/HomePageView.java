package myUni;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HomePageView extends JFrame{
	private int loggedInStudentID; //the sID of the student that has successfully logged in
	public HomePageView(int loggedInStudentID){
		this.loggedInStudentID = loggedInStudentID;
		HomePageController controller = new HomePageController();
		
		String[] petStrings = { "Bird", "Cat", "Dog", "Rabbit", "Pig" };

		//Create 3 comboboxes for the filters
		
		
		//retrieve all states from home controller
		//retrieve all college from a specific state from home controller
		//retrieve all majors from a specific college from home controller
		JComboBox stateComboBox = new JComboBox(petStrings);
		JComboBox collegeComboBox = new JComboBox(petStrings);
		JComboBox majorComboBox = new JComboBox(petStrings);
		
		
		stateComboBox.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  //retrieve all colleges from a specific state from home controller
				  //Select the first college in the list
				  //Repopulate collegeComboBox with first college selected
				  //Retrieve all majors of that first college
				  //Select the first major in the list
				  //Repopulate majors with the first major selected
				  String state = stateComboBox.getSelectedItem().toString();
				  System.out.println(state);
				  collegeComboBox.addItem("StateSelected");
				  } 
				});
		
		collegeComboBox.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) {
				  //Retrieve all majors of the selected college
				  //Select the first major in the list
				  //Repopulate majors with the first major selected
				  String college = collegeComboBox.getSelectedItem().toString();
				  } 
				});
		
		
		//Button panel with apply and view buttons
		JButton applyButton = new JButton("Apply");
		applyButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  String state = stateComboBox.getSelectedItem().toString();
				  String college = collegeComboBox.getSelectedItem().toString();
				  String major = majorComboBox.getSelectedItem().toString();
				  } 
				});
		
		JButton viewButton = new JButton("View");
		viewButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  
			  } 
			});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(applyButton);
		buttonPanel.add(viewButton);
		buttonPanel.add(Box.createHorizontalGlue());


		//Drop down filters with apply and view buttons
		JPanel filterPanel = new JPanel();
		filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));
		filterPanel.add(stateComboBox);
		filterPanel.add(collegeComboBox);
		filterPanel.add(majorComboBox);
		filterPanel.add(buttonPanel);	
		filterPanel.add(Box.createVerticalGlue());	
		
		
		
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.add(filterPanel, BorderLayout.EAST);
	    this.setSize(500, 300); 
	    this.setVisible(true);
		
	}
	
	 
}
