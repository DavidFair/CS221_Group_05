/**
 * @author David (daf5)
 * This interface specifies the minimum
 * methods each window class and the
 * GUI element it controls must support
 */
package uk.ac.aber.cs221.group5.logic;

public interface WindowInterface {
	
	/**
	 * Allows the window manager to create and
	 * display the specified window
	 */
	public void createWindow();
	
	/**
	 * Allows the window manager to gracefully
	 * close the window
	 */
	public void destroyWindow();
	
	/**
	 * Allows the window manager to change the
	 * text within the window title
	 * @param newTitleText The text to be displayed on window
	 */ 
	public void setTitleText(String newTitleText);
	
	/**
	 * Displays a dialog box with an error message
	 * @param errorText
	 */
	public void displayError(String errorText);
	
	/**
	 * Displays a dialog box with a warning message
	 * @param warnText The warning the dialog box should display
	 */
	public void displayWarning(String warnText);
	
	/**
	 * Returns if the current window exists and is currently
	 * visible
	 * @return True is visible and exists otherwise false
	 */
	public boolean doesWindowExist();
	
}
