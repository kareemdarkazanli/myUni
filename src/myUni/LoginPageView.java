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
public class LoginPageView extends JFrame implements ActionListener, ChangeListener{

	private JTextField emailField;
	private JPasswordField passwordField;
	private LoginPageModel theModel;
	private JFrame controllingFrame;
	
	/**
	 * LoginPageView has an instance of the model and a containing JFrame
	 */
	public LoginPageView(LoginPageModel model){
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theModel = model;
		emailField = new JTextField("", 10); 
		passwordField = new JPasswordField("", 10); 
		passwordField.setActionCommand("Login");
		passwordField.addActionListener(this);

		JLabel emailLabel = new JLabel("Enter your student email: ");
		JLabel passwordLabel = new JLabel("Enter the password: ");
		passwordLabel.setLabelFor(passwordField);

		JComponent buttonPane = createButtonPanel();

		//Lay out everything.
		JPanel textPane = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		textPane.add(emailLabel);
		textPane.add(emailField);
		textPane.add(passwordLabel);
		textPane.add(passwordField);
		JPanel surrounding = new JPanel();
		surrounding.add(textPane);
		surrounding.add(buttonPane);
		add(surrounding);
		pack();
		setVisible(true);

	}

	/**
	 * Gets the user input email
	 */
	public String getEmail()
	{
		return emailField.getText();
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
 * Authenticates the user based on input actions, or redirects to the sign in page if we decide to implement it
 */
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();

		if ("Login".equals(cmd)) { //Process the password.
			String inputPassword = getPassword();
			String inputEmail = getEmail();
			if (theModel.areEmailAndPasswordCorrect(inputPassword, inputEmail)) {
				JOptionPane.showMessageDialog(controllingFrame,
						"Success! the program will redirect to the home page");
				new HomePageView().setVisible(true); //new instance of a home page view here

		        this.dispose(); //to close the current frame
			} else {
				JOptionPane.showMessageDialog(controllingFrame,
						"Invalid password or email. Try again.",
						
						"Error Message",
						JOptionPane.ERROR_MESSAGE);
			}

			passwordField.selectAll();
		} else { //The user wants to sign up.
			new CreateAccountPageView();
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

