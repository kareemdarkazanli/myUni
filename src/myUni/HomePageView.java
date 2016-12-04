package myUni;

import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;

public class HomePageView extends JFrame{
	private int loggedInStudentID; //the sID of the student that has successfully logged in
	public HomePageView(int loggedInStudentID, MySQLConnect theModel){
		this.loggedInStudentID = loggedInStudentID;
		
		
		HomePageController controller = new HomePageController(theModel);
		

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
		JLabel statesLabel = new JLabel("State:    ");
		JLabel collegesLabel = new JLabel("College: ");
		JLabel majorsLabel = new JLabel("Major:    ");
		
		
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

		//spinner for the archiving function
		SpinnerModel model1 = new SpinnerDateModel();
		JSpinner spinner1 = new JSpinner(model1);
		JButton archiveButton = new JButton("Archive all applications before selected date");
		JFrame containingFrame = this;
		archiveButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				int numberOfApplicationsArchived = theModel.archiveApplications(new java.sql.Date(((Date) model1.getValue()).getTime()));
				  JOptionPane.showMessageDialog(containingFrame, numberOfApplicationsArchived + " old applications successfully archived");
			}

		});
		
		
		JButton collegeApplicationsButton = new JButton("College Applications");
		collegeApplicationsButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				new CollegeApplicationsView(loggedInStudentID);
			}
			
		});
		
		JButton accountInfoButton = new JButton("Account Info");
		accountInfoButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				new AccountInfoView(loggedInStudentID);
			}
			
		});
		
		JButton logoutButton = new JButton("Logout");
		logoutButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
                new LoginPageView(theModel);
			}
			
		});
		
		//Button panel with apply and view buttons
		JButton applyButton = new JButton("Apply");
		applyButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  String state = stateComboBox.getSelectedItem().toString();
				  String college = collegeComboBox.getSelectedItem().toString();
				  String major = majorComboBox.getSelectedItem().toString();
				  int retVal = controller.apply(loggedInStudentID, college, major);
				  JFrame frame = new JFrame();
				  String response;
				  if(retVal == 1){
					  response = "You have successfully submitted and application for " + major + " at " +college + ".";
				  }
				  else{
					  response = "You have already submitted an application for " + major + " at " +college + ".";
				  }
				  JOptionPane.showMessageDialog(frame,
						    String.valueOf(response));
				  }
			  });
		
		JButton viewButton = new JButton("View");
		viewButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  new InformationPageView(collegeComboBox.getSelectedItem().toString(), stateComboBox.getSelectedItem().toString(), majorComboBox.getSelectedItem().toString());
			  } 
			});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(applyButton);
		buttonPanel.add(viewButton);
		buttonPanel.add(Box.createHorizontalGlue());


		//Drop down filters with apply and view buttons
		//state filters and label
		JPanel statesPanel = new JPanel();
		statesPanel.setLayout(new BoxLayout(statesPanel, BoxLayout.X_AXIS));
		statesPanel.add(statesLabel);
		statesPanel.add(stateComboBox);
		//college filters and label
		JPanel collegesPanel = new JPanel();
		collegesPanel.setLayout(new BoxLayout(collegesPanel, BoxLayout.X_AXIS));
		collegesPanel.add(collegesLabel);
		collegesPanel.add(collegeComboBox);
		//major filters and label
		JPanel majorsPanel = new JPanel();
		majorsPanel.setLayout(new BoxLayout(majorsPanel, BoxLayout.X_AXIS));
		majorsPanel.add(majorsLabel);
		majorsPanel.add(majorComboBox);
		//the surrounding layout
		JPanel filterPanel = new JPanel();
		filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));
		filterPanel.add(statesPanel);
		filterPanel.add(collegesPanel);
		filterPanel.add(majorsPanel);
		filterPanel.add(buttonPanel);	
		filterPanel.add(Box.createVerticalGlue());	
		
		
		JPanel optionsPanel = new JPanel();
		optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
		optionsPanel.add(logoutButton);
		optionsPanel.add(collegeApplicationsButton);
		optionsPanel.add(accountInfoButton);
		optionsPanel.add(Box.createVerticalGlue());	
		
		JPanel archivePanel = new JPanel();
		archivePanel.add(spinner1);
		archivePanel.add(archiveButton);

	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.add(filterPanel, BorderLayout.EAST);
	    this.add(optionsPanel, BorderLayout.WEST);
	    this.add(archivePanel, BorderLayout.SOUTH);
	    this.setSize(500, 300); 
		this.setLocationRelativeTo(null);
	    this.setVisible(true);
		
	}
	
	 
}
