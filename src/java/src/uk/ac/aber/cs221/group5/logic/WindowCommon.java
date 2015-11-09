package uk.ac.aber.cs221.group5.logic;

/**
 * Provides common functions to all objects
 * by utilising the interface methods all
 * objects must use
 * @author David
 *
 */

public abstract class WindowCommon implements WindowInterface {

	private boolean doesWindowExist;
	
	public WindowCommon(){
		doesWindowExist = false;
	}
	
	/**
	 * Common code base for creating a new window
	 * and dealing with any exceptions 
	 * @param callingClass The window class which needs
	 *  a new window generating
	 */
	public void setupWindowLaunch(WindowInterface callingClass){
		
		//Calling class should have already got us a new window
		//TODO: Test for if calling class has not got us new window object
		
		//Try to launch a new window
		try {
			callingClass.callWindowLaunch();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		//Set window status to visible
		doesWindowExist = true;
		
	}
	

	/**
	 * Returns if window exists
	 * @return True if window exists else false
	 */
	public boolean doesWindowExist(){
		return doesWindowExist;
	}

}
