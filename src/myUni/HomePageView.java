package myUni;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class HomePageView extends JFrame{
	private int loggedInStudentID; //the sID of the student that has successfully logged in
	public HomePageView(int loggedInStudentID){
		this.loggedInStudentID = loggedInStudentID;
		
		
		HomePageController controller = new HomePageController();
		

		//Create 3 comboboxes for the filters
		ArrayList<String> states = controller.getAllStates();
		String[] statesArray = new String[states.size()];
		statesArray = states.toArray(statesArray);
		ArrayList<String> colleges = controller.getAllColleges(states.get(0));
		String[] collegesArray = new String[colleges.size()];
		collegesArray = colleges.toArray(collegesArray);
		ArrayList<String> majors = controller.getAllMajors(colleges.get(0));
		String[] majorsArray = new String[majors.size()];
		majorsArray = majors.toArray(majorsArray);
		
		//retrieve all states from home controller
		//retrieve all college from a specific state from home controller
		//retrieve all majors from a specific college from home controller
		JComboBox stateComboBox = new JComboBox(statesArray);
		JComboBox collegeComboBox = new JComboBox(collegesArray);
		JComboBox majorComboBox = new JComboBox(majorsArray);
		
		
		stateComboBox.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				 
				  String state = stateComboBox.getSelectedItem().toString();
				  collegeComboBox.removeAllItems();
				  ArrayList<String> colleges = controller.getAllColleges(state);
				  for(String college: colleges){
					  collegeComboBox.addItem(college);
				  }
				  String college = colleges.get(0);
				  majorComboBox.removeAllItems();
				  ArrayList<String> majors = controller.getAllMajors(college);
				  for(String major: majors){
					  majorComboBox.addItem(major);
				  }
				  invalidate();
				  validate();
				  repaint();
				  } 
				});
		
		collegeComboBox.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) {
				  if(collegeComboBox.getSelectedItem() == null){
					  
				  }
				  else{
					  String college = collegeComboBox.getSelectedItem().toString();
					  majorComboBox.removeAllItems();
					  ArrayList<String> majors = controller.getAllMajors(college);
					  for(String major: majors){
						  majorComboBox.addItem(major);
					  }
					  invalidate();
					  validate();
					  repaint();
					  } 
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
