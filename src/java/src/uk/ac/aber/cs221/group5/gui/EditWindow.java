package uk.ac.aber.cs221.group5.gui;

import java.io.IOException;

import uk.ac.aber.cs221.group5.logic.TaskStatuses;

public class EditWindow extends WindowCommon{
	
	private EditWindowGUI childWindow;
	private int rowNo;	//The index of the row selected in the Main Window table when this window was spawned
	
	public EditWindow(int row){
		super();
		this.rowNo = row;
	}

	@Override
	public void createWindow() {
		// TODO Auto-generated method stub
		try {
			childWindow = new EditWindowGUI(rowNo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	public void displayError(String errorText, String errorType) {
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
