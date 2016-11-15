package myUni;

import javax.swing.*;
import java.awt.*;

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
        collegeLabel.setBounds(10, 30, 150, 30);
        panel.add(collegeLabel);

        // College Text
        JLabel collegeText = new JLabel(college);
        collegeText.setBounds(100, 30, 150, 30);
        panel.add(collegeText);

        // State Label
        JLabel stateLabel = new JLabel("State: ");
        stateLabel.setBounds(56, 50, 150, 30);
        panel.add(stateLabel);

        // State Text
        JLabel stateText = new JLabel(state);
        stateText.setBounds(100, 50, 150, 30);
        panel.add(stateText);

        // Major Label
        JLabel majorLabel = new JLabel("Major: ");
        majorLabel.setBounds(55, 70, 150, 30);
        panel.add(majorLabel);

        // Major Text
        JLabel majorText = new JLabel(major);
        majorText.setBounds(100, 70, 150, 30);
        panel.add(majorText);

        // Make sure the JFrame is visible
        frame.setVisible(true);
    }
}
