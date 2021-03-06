//package myUni;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Cassidy Tarng on 11/15/2016.
 */
public class InformationPageView {

    public InformationPageView(String college, String state, String major){
        JFrame frame = new JFrame();

        // Make sure the program exits when the frame closes
        frame.setTitle("Information Page for " + college);
        frame.setSize(350,250);

        // This will center the JFrame in the middle of the screen
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setLayout(null);

        // Header Label
        JLabel header = new JLabel(college + "-" + major);
        header.setFont(new Font("Arial", Font.BOLD, 15));
        header.setBounds(0, 10, 350, 25);
        header.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(header);

        // College Label
        JLabel collegeLabel = new JLabel("College Name: ");
        collegeLabel.setBounds(66, 30, 150, 30);
        panel.add(collegeLabel);

        // College Text
        JLabel collegeText = new JLabel(college);
        collegeText.setBounds(170, 30, 150, 30);
        panel.add(collegeText);

        // State Label
        JLabel stateLabel = new JLabel("State: ");
        stateLabel.setBounds(113, 50, 150, 30);
        panel.add(stateLabel);

        // State Text
        JLabel stateText = new JLabel(state);
        stateText.setBounds(170, 50, 150, 30);
        panel.add(stateText);
        
        //Enrollment Label
        JLabel enrollmentLabel = new JLabel("Enrollment: ");
        enrollmentLabel.setBounds(85, 70, 150, 30);    
        panel.add(enrollmentLabel);

        //Enrollment Text
        int enrollment = new MySQLConnect().getEnrollment(college);
        JLabel enrollmentText = new JLabel(String.valueOf(enrollment));
        enrollmentText.setBounds(170, 70, 150, 30);
        panel.add(enrollmentText);

        // Major Label
        JLabel majorLabel = new JLabel("Major: ");
        majorLabel.setBounds(111, 90, 150, 30);
        panel.add(majorLabel);

        // Major Text
        JLabel majorText = new JLabel(major);
        majorText.setBounds(170, 90, 150, 30);
        panel.add(majorText);

        // Minimum GPA Label
        JLabel minGPALabel = new JLabel("Minimum GPA Required: ");
        minGPALabel.setBounds(10, 110, 150, 30);
        panel.add(minGPALabel);

        // Minimum GPA Text
        try {
            JLabel minGPAText = new JLabel(new MySQLConnect().getMinimumGPA(college, major));
            minGPAText.setBounds(170, 110, 150, 30);
            panel.add(minGPAText);

        }
        catch (SQLException e){
            e.printStackTrace();
        }

        // Professors Label
        JLabel professorLabel = new JLabel("Professors");
        professorLabel.setBounds(0, 125, 350, 30);
        professorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(professorLabel);

        //
        Vector<String> prof = new MySQLConnect().getProfessors(college, major);
        JList list = new JList(prof);
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(65, 150, 200, 60);
        panel.add(scrollPane);

        // Make sure the JFrame is visible
        frame.setVisible(true);
    }
}