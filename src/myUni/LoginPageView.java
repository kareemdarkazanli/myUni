package myUni;
/* *
 * password field code modified from tutorial at java API
 * https://docs.oracle.com/javase/tutorial/uiswing/components/passwordfield.html
 * */
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
public class LoginPageView extends JFrame implements ActionListener, ChangeListener{

	private JTextField nameField;
	private JPasswordField passwordField;
	private MySQLConnect theModel;
	private JFrame controllingFrame;
	
	/**
	 * LoginPageView has an instance of the model
	 */
	public LoginPageView(MySQLConnect model){
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theModel = model;
		nameField = new JTextField("", 10); 
		passwordField = new JPasswordField("", 10); 
		passwordField.setActionCommand("Login");
		passwordField.addActionListener(this);

		JLabel emailLabel = new JLabel("Username: ");
		JLabel passwordLabel = new JLabel("Password: ");
		passwordLabel.setLabelFor(passwordField);

		JComponent buttonPane = createButtonPanel();

		//Lay out everything.
		JPanel textPane = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		textPane.add(emailLabel);
		textPane.add(nameField);
		textPane.add(passwordLabel);
		textPane.add(passwordField);
		JPanel surrounding = new JPanel();
		surrounding.add(textPane);
		surrounding.add(buttonPane);
		add(surrounding);
		pack();
		this.setLocationRelativeTo(null);
		setVisible(true);

	}

	/**
	 * Gets the user input name
	 */
	public String getUserName()
	{
		return nameField.getText();
	}
	/**
	 * Gets the user input password
]   */
	public String getPassword()
	{
		return String.copyValueOf(passwordField.getPassword());
	}

/**
 * Creates and lays out the button panel
 * @return a panel with the login and sign up buttons
 */
	protected JComponent createButtonPanel() {
		JPanel p = new JPanel(new GridLayout(0,1));
		JButton loginButton = new JButton("Login");
		JButton createStudentButton = new JButton("Sign Up");

		loginButton.setActionCommand("Login");
		createStudentButton.setActionCommand("Sign Up");
		loginButton.addActionListener(this);
		createStudentButton.addActionListener(this);

		p.add(loginButton);
		p.add(createStudentButton);

		return p;
	}
/**
 * Authenticates the user based on input actions, or redirects to the sign in page if the user wants to create a Student Account
 */
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();

		if ("Login".equals(cmd)) { //Process the password.
			String inputPassword = getPassword();
			String inputName = getUserName();
			try {
				if (theModel.areEmailAndPasswordCorrect(inputName, inputPassword)) {
					int loggedInStudentID = theModel.getStudentID(inputName, inputPassword);
					String loggedInStudentName = theModel.getStudentName(loggedInStudentID);
					JOptionPane.showMessageDialog(controllingFrame,
							"Success! Now logged in as " + loggedInStudentName + ". Your ID number is: " + loggedInStudentID);
					new HomePageView(loggedInStudentID, theModel).setVisible(true); //new instance of a home page view here
				    this.dispose(); //to close the current frame
				} else {
					JOptionPane.showMessageDialog(controllingFrame,
							"Invalid password or email. Try again.",
							
							"Error Message",
							JOptionPane.ERROR_MESSAGE);
				}
			} catch (HeadlessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			passwordField.selectAll();
		} else { //The user wants to sign up.
			new CreateAccountPageView(theModel);
			this.dispose();
		}
	}
	/**
	 * repaints this view whenever a change is made to the model
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		repaint();
	}



}

