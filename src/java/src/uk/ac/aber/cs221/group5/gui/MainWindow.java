/**
 * 
 */
package uk.ac.aber.cs221.group5.gui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import uk.ac.aber.cs221.group5.logic.MemberList;
import uk.ac.aber.cs221.group5.logic.Task;
import uk.ac.aber.cs221.group5.logic.TaskList;
import uk.ac.aber.cs221.group5.logic.TaskStatuses;

/**
 * @author David (daf5)
 * Provides a wrapper for common window functions
 * such as creating and destroying the main window
 * 
 */
public class MainWindow extends WindowCommon {
	
	private MainWindowGUI childWindow;
	
	private TaskList taskList = new TaskList();
	private MemberList memberList = new MemberList();
	
	public static void main(String args[]) throws InterruptedException, NumberFormatException, IOException{
		TaskList taskList = new TaskList();
		MemberList memberList = new MemberList();
		
		memberList.loadMembers("memberSaveFile.txt");
		
		MainWindow mainWindow = new MainWindow();
		mainWindow.createWindow();
		LoginWindow loginWindow = new LoginWindow();
		loginWindow.passMemberList(memberList);
		loginWindow.createWindow();
		mainWindow.childWindow.makeFrameVisible();
		
	}
	
	public MainWindow(){
		//Setup common window features
		super();
	}
	
	public void createWindow(){
		//Get a new child window for super class
		childWindow = new MainWindowGUI();
		
		//Load the tasks into the Task List
		try {
			loadTasks("taskSaveFile.txt");
		} catch (IOException e1) {
			System.out.println("Failed to load Task File");
			//At this point, need to re-configure task file
			e1.printStackTrace();
		}
		
		//Load the members into the Member List
		try {
			memberList.loadMembers("memberSaveFile.txt");
		} catch (NumberFormatException | IOException e) {
			System.out.println("Failed to load member save file");
			e.printStackTrace();
		}
			
		//Ask parent to setup window for us and pass
		//this class's methods for it to work on
		setupWindowLaunch(this);
	}
	

	public void callWindowLaunch(){
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
	
	public void loadTasks(String filename) throws IOException{
		FileReader fileReader = new FileReader(filename);
		BufferedReader read = new BufferedReader(fileReader);
		int numOfTasks = 0;
		String taskID           = null;
		String taskName         = null;
		TaskStatuses taskStatus = null;
		String assigned         = null;
		String startDate        = null;
		String endDate          = null;
		
		//First read in the number of tasks
		numOfTasks = Integer.parseInt(read.readLine());
		//Load data and create Task objects
		for(int loopCount = 0; loopCount < numOfTasks; loopCount++){
			taskID     = read.readLine();
			//Skip over task elements for the demo
			read.readLine();
			taskName   = read.readLine();
			taskStatus = TaskStatuses.valueOf(TaskStatuses.class, read.readLine());
			assigned   = read.readLine();
			startDate  = read.readLine();
			endDate    = read.readLine();
			Task task = new Task(taskID, taskName, startDate, endDate, assigned, taskStatus);
			taskList.addTask(task);
		}
		childWindow.populateTable(taskList);
	}
}
