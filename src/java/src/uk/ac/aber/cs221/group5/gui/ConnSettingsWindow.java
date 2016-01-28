package uk.ac.aber.cs221.group5.gui;

import java.awt.Frame;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import uk.ac.aber.cs221.group5.logic.DbStatus;

public class ConnSettingsWindow {
	
	private ConnSettingsWindowGUI childWindow;
	
	public ConnSettingsWindow(boolean createNewWindow){
		if (createNewWindow){
			this.createWindow();
		}
	}
	
	private void createWindow(){
		childWindow = new ConnSettingsWindowGUI();
		this.setConnStatus(MainWindow.getConnStatus());
		this.setLastSyncLabel(MainWindow.getConnTime());
	}
	
	public void saveConnSettings(String filename, String dbName, String username, String password, 
			String dbUrl, String portNo) throws IOException{
		
		FileWriter fileWriter = new FileWriter(filename);
		BufferedWriter writer = new BufferedWriter(fileWriter);
		
		if(portNo.equals("Leave Blank for Default") || portNo.equals("")){
			portNo = "3306";	//TODO Give default value
		}
		
		writer.write(dbName);
		writer.newLine();
		writer.write(username);
		writer.newLine();
		writer.write(password);
		writer.newLine();
		writer.write(dbUrl);
		writer.newLine();
		writer.write(portNo);
		
		writer.close();
		fileWriter.close();
		
	}
	
	//Checks all frames to see if the GUI for this Class exits. Enables us to create a new ConnSettingsWindow Object
	//	without creating a new GUI window.
	private boolean doesGUIExist(){
		for(Frame frame : Frame.getFrames()){
			if(frame.getTitle().equals("Connection Settings")){
				return true;
			}
		}
		return false;
	}
	
	public void setConnStatus(DbStatus status){
		this.childWindow.setConnStatus(status);
	}
	
	/**
	 * Calculates the time since CLI was last synced with the Database
	 * @param connTime The time, in millis, when the Database was last synced
	 */
	public void setLastSyncLabel(long connTime){
		long currentTime = System.currentTimeMillis();
		long timeDifferenceMillis = currentTime - connTime;
		long timeDifferenceSeconds = 0;
		final int conversionFactor = 600;
		//Convert to millis to minutes
		
		/////INTEGER DIVISION////
		timeDifferenceSeconds = timeDifferenceMillis / conversionFactor;
		
		if (connTime == 0){
			timeDifferenceMillis = 0;
		}
		
		//Display result
		this.childWindow.setLastSyncedLabel(timeDifferenceSeconds);
	}

}
