package uk.ac.aber.cs221.group5.gui;

import java.awt.Frame;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConnSettingsWindow {
	
	private ConnSettingsWindowGUI childWindow;
	
	public static void main (String args[]){
		ConnSettingsWindow window = new ConnSettingsWindow();
		if(!window.doesGUIExist()){
			window.createWindow();
		}
		
	}
	
	public void createWindow(){
		childWindow = new ConnSettingsWindowGUI();
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
	
	private boolean doesGUIExist(){
		for(Frame frame : Frame.getFrames()){
			if(frame.getTitle().equals("Connection Settings")){
				return true;
			}
		}
		return false;
	}

}
