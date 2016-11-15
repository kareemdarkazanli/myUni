package myUni;


import com.mysql.jdbc.StringUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Arrays;

public class CreateAccountPageView {



    public CreateAccountPageView(MySQLConnect theModel)
    {

        JFrame frame = new JFrame();

        // Make sure the program exits when the frame closes
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Create an Account");
        frame.setSize(350,250);

        // This will center the JFrame in the middle of the screen
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setLayout(null);

        // Username Label
        JLabel username = new JLabel("Username*");
        username.setBounds(50, 10, 80, 25);
        panel.add(username);

        // Username Text Field
        JTextField usernameText = new JTextField(20);
        usernameText.setBounds(150, 10, 160, 25);
        panel.add(usernameText);

        // Password Label
        JLabel password = new JLabel("Password*");
        password.setBounds(50, 40, 80, 25);
        panel.add(password);

        // Password Text Field
        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(150, 40, 160, 25);
        panel.add(passwordText);

        // Confirm Password Label
        JLabel confirmPassword = new JLabel("Confirm Password*");
        confirmPassword.setBounds(20, 70, 150, 25);
        panel.add(confirmPassword);

        // Confirm Password Text Field
        JPasswordField confirmPasswordText = new JPasswordField(20);
        confirmPasswordText.setBounds(150, 70, 160, 25);
        panel.add(confirmPasswordText);

        // GPA Label
        JLabel gpa = new JLabel("Enter GPA*");
        gpa.setBounds(50, 100, 150, 25);
        panel.add(gpa);

        // GPA Text Field
        JTextField gpaField = new JTextField(20);
        gpaField.setBounds(150, 100, 50, 25);
        panel.add(gpaField);

        // Create Button
        JButton createButton = new JButton("Create");
        createButton.setBounds(70, 130, 80, 30);
        panel.add(createButton);

        // Cancel Button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(190, 130, 80, 30);
        panel.add(cancelButton);

        // Response text
        JLabel response = new JLabel("");
        response.setBounds(0, 150, 350, 50);
        response.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(response);

        // Creates the data once clicking create
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (theModel.isUsernameUnique(usernameText.getText())) {
                        response.setText("Error: Username already exists");
                    } else if (!Arrays.equals(passwordText.getPassword(), confirmPasswordText.getPassword())) {
                        response.setText("Error: Passwords do not match");
                    } else if (StringUtils.isNullOrEmpty(gpaField.getText())){
                        response.setText("Error: GPA is not valid");
                    } else if (Arrays.equals(passwordText.getPassword(), confirmPasswordText.getPassword())) {
                        theModel.createNewAccount(usernameText.getText(), String.valueOf(passwordText.getPassword()), gpaField.getText());
                        response.setText("Success!");
                        int loggedInStudentID = theModel.getStudentID(usernameText.getText(), String.copyValueOf(passwordText.getPassword()));
    					String loggedInStudentName = theModel.getStudentName(loggedInStudentID);
    					JOptionPane.showMessageDialog(frame,
    							"Success! Created account for " + loggedInStudentName + ". Your ID number is: " + loggedInStudentID);
                        frame.dispose();
                        new LoginPageView(theModel);
                    } else response.setText("Error!");
                }
                catch (SQLException e1){
                    e1.printStackTrace();
                }
                catch (NumberFormatException e2){
                    response.setText("Error: GPA is not valid");
                }
            }
        });

        // Exits out of the dialog when user clicks Cancel
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new LoginPageView(theModel);
            }
        });

        // Make sure the JFrame is visible
        frame.setVisible(true);
    }

}