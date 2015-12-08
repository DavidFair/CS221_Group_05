package uk.ac.aber.cs221.group5.gui;

import uk.ac.aber.cs221.group5.logic.TaskStatuses;

public class EditWindow extends WindowCommon{
	
	private EditWindowGUI childWindow;
	
	public EditWindow(){
		super();
	}

	@Override
	public void createWindow() {
		// TODO Auto-generated method stub
		childWindow = new EditWindowGUI();
		setupWindowLaunch(this);
		
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

	@Override
	public void callWindowLaunch() throws Exception {
		// TODO Auto-generated method stub
		childWindow.launchWindow();
	}
	
	public void setFields(String name, TaskStatuses status, String assigned, String start, String end){
		childWindow.setFields(name, status, assigned, start, end);
	}

}
