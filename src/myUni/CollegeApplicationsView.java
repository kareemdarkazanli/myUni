package myUni;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.regex.PatternSyntaxException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CollegeApplicationsView extends JFrame{
	
	private int loggedInStudentID; //the sID of the student that has successfully logged in

	
	public CollegeApplicationsView(int loggedInStudentID){
		
		this.loggedInStudentID = loggedInStudentID;
		CollegeApplicationsController controller = new CollegeApplicationsController();

		
		JPanel collegeApplicationsPanel = new JPanel();
		collegeApplicationsPanel.setLayout(new BoxLayout(collegeApplicationsPanel, BoxLayout.Y_AXIS));

		ArrayList<String> collegeApplications = new CollegeApplicationsController().getCollegeApplications(loggedInStudentID);
		
		ArrayList<JCheckBox> collegeApplicationsCheckBox = new ArrayList<JCheckBox>();
		for(int i = 0; i < collegeApplications.size(); i++){
			collegeApplicationsCheckBox.add(new JCheckBox(collegeApplications.get(i)));
		}
		
		for(int i = 0; i < collegeApplicationsCheckBox.size(); i++){
			collegeApplicationsPanel.add(collegeApplicationsCheckBox.get(i));
		}
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i = 0; i < collegeApplicationsCheckBox.size(); i++){
					if(collegeApplicationsCheckBox.get(i).isSelected()){
						try {
						    String[] splitArray = collegeApplications.get(i).split("\\s+");
						    controller.deleteCollegeApplication(loggedInStudentID, splitArray[0], splitArray[1]);
							collegeApplications.remove(i);
						    collegeApplicationsCheckBox.remove(i);
						} catch (PatternSyntaxException ex) {
						    // 
						}
						
						
					}
				}
				
				for(int i = 0; i < collegeApplicationsCheckBox.size(); i++){
					if(collegeApplicationsCheckBox.get(i).isSelected()){
						try {
						    String[] splitArray = collegeApplications.get(i).split("\\s+");
						    controller.deleteCollegeApplication(loggedInStudentID, splitArray[0], splitArray[1]);
							collegeApplications.remove(i);
						    collegeApplicationsCheckBox.remove(i);
						} catch (PatternSyntaxException ex) {
						    // 
						}
						
						
					}
				}
				collegeApplicationsPanel.removeAll();
				for(int i = 0; i < collegeApplicationsCheckBox.size(); i++){
					collegeApplicationsPanel.add(collegeApplicationsCheckBox.get(i));
				}
				invalidate();
				validate();
				repaint();
			}
			
		});
		
		JButton backButton = new JButton("Back to Homepage");
		backButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.add(collegeApplicationsPanel, BorderLayout.CENTER);
		buttonPanel.add(deleteButton, BorderLayout.NORTH);
		buttonPanel.add(backButton, BorderLayout.SOUTH);
		this.add(buttonPanel, BorderLayout.SOUTH);
	    this.setSize(500, 300);
		this.setLocationRelativeTo(null);
	    this.setVisible(true);
	}

}
