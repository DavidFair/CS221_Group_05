package uk.ac.aber.cs221.group5.gui;

import java.awt.Frame;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConnSettingsWindow {
	
	private ConnSettingsWindowGUI childWindow;
	
	public ConnSettingsWindow(){
		//Only spawns a new GUI if one does not already exist.
		if(!this.doesGUIExist()){
			this.createWindow();
		}
	}
	
	public void createWindow(){
		childWindow = new ConnSettingsWindowGUI();
		ConnSettingsWindow window = new ConnSettingsWindow();
		if(!window.doesGUIExist()){
			window.createWindow();
		}
	}
	
	public void saveConnSettings(String filename, String dbName, String username, String password, 
			String dbUrl, String portNo) throws IOException{
		
		FileWriter fileWriter = new FileWriter(filename);
		BufferedWriter writer = new BufferedWriter(fileWriter);
		
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

}
