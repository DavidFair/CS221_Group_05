/**
 * 
 */
package uk.ac.aber.cs221.group5.gui;

/**
 * @author David (daf5)
 * Provides a wrapper for common window functions
 * such as creating and destroying the main window
 * 
 */
public class MainWindow extends WindowCommon {
	
	private MainWindowGUI childWindow;
	
	public MainWindow(){
		//Setup common window features
		super();
	}
	
	@Override
	public void createWindow() {
		//Get a new child window for super class
		childWindow = new MainWindowGUI();
		
		//Ask parent to setup window for us and pass
		//this class's methods for it to work on
		setupWindowLaunch(this);
		}
	
	@Override
	public void callWindowLaunch() throws Exception {
		childWindow.launchWindow();
		
	}

	@Override
	public void destroyWindow() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see uk.ac.aber.cs221.group5.logic.WindowInterface#setTitleText(java.lang.String)
	 */
	@Override
	public void setTitleText(String newTitleText) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see uk.ac.aber.cs221.group5.logic.WindowInterface#displayError(java.lang.String)
	 */
	@Override
	public void displayError(String errorText) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see uk.ac.aber.cs221.group5.logic.WindowInterface#displayWarning(java.lang.String)
	 */
	@Override
	public void displayWarning(String warnText) {
		// TODO Auto-generated method stub

	}

}
