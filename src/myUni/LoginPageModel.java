package myUni;

import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class LoginPageModel {

	private ArrayList<ChangeListener> listeners;
	public LoginPageModel() {
		listeners = new ArrayList<ChangeListener>();
	}
	
	/**
	 *Checks the password and email against the database
	 */
	public static boolean areEmailAndPasswordCorrect(String inputPassword, String inputEmail) {
		boolean isCorrect = false;
		String correctPassword = ""; //query the database for the password
		String correctEmail = ""; //query the database for the email
		//select password, email from students where email = inputEmail and password = inputPassword
		if(inputPassword.equals(correctPassword) && inputEmail.equals(correctEmail))
			isCorrect = true;
		return isCorrect;
	}
	/**
	 * updates the views attached to this model. called whenever a controller mutates part of the model.
	 */
	public void updateListeners()
	{
		for (ChangeListener l : listeners)
		{
			l.stateChanged(new ChangeEvent(this));
		}
	}
	
	/**
	 * Attach a listener to the Model
	 * @param c the listener
	 */
	public void attach(ChangeListener c)
	{
		listeners.add(c);
	}
}
