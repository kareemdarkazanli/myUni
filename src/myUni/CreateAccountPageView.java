package myUni;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class CreateAccountPageView {



    public CreateAccountPageView()
    {
        //TODO DELETE THIS DUMMY DATA
        String dummyUsernames[] = {"admin", "Bob", "Frank"};

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

        // Create Button
        JButton createButton = new JButton("Create");
        createButton.setBounds(70, 100, 80, 30);
        panel.add(createButton);

        // Cancel Button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(190, 100, 80, 30);
        panel.add(cancelButton);

        // Response text
        JLabel response = new JLabel("");
        response.setBounds(0, 120, 350, 50);
        response.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(response);

        // Creates the data once clicking create
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Arrays.asList(dummyUsernames).contains(usernameText.getText())){
                    response.setText("Error: Username already exists");
                }
                else if (!Arrays.equals(passwordText.getPassword(), confirmPasswordText.getPassword())) {
                    response.setText("Error: Passwords do not match");
                }
                else if (Arrays.equals(passwordText.getPassword(), confirmPasswordText.getPassword())){
                    response.setText("Success!");
                }
                else response.setText("Error!");
            }
        });

        // Exits out of the dialog when user clicks Cancel
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new LoginPageView(new MySQLConnect());
            }
        });

        // Make sure the JFrame is visible
        frame.setVisible(true);
    }

}