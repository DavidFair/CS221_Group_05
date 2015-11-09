/**
 * @author David (daf5)
 * This class implements methods required by the login GUI
 */

package uk.ac.aber.cs221.group5.logic;

import uk.ac.aber.cs221.group5.gui.LoginWindowGUI;

/**
 * @author David
 *
 */
public class LoginWindow implements WindowInterface {

	private LoginWindowGUI childWindow;
	private boolean windowExists;
	
	public LoginWindow() {
		windowExists = false;
	}
		
	/**
	 * This method creates a new login window
	 * and sets it to visible
	 */
	@Override
	public void createWindow() {
		//Get a new child window
		childWindow = new LoginWindowGUI();
		
		//Try to launch a new window
		try {
			childWindow.launchWindow();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		//Set window status to visible
		windowExists = true;
	}
	


	@Override
	public void destroyWindow() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTitleText(String newTitleText) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayError(String errorText) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayWarning(String warnText) {
		// TODO Auto-generated method stub
		
	}

	public void logIn(String userName) {
		// TODO Auto-generated method stub
		//This should initiate DB login method
	}

	public void openConnSettings() {
		// TODO Auto-generated method stub
		//This should open ConnSettings Window 
	}
	
	@Override
	public boolean doesWindowExist() {
		return windowExists;
	}

}
