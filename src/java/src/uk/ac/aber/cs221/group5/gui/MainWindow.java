/**
 * 
 */
package uk.ac.aber.cs221.group5.gui;

import java.awt.Frame;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import uk.ac.aber.cs221.group5.logic.MemberList;
import uk.ac.aber.cs221.group5.logic.Task;
import uk.ac.aber.cs221.group5.logic.TaskList;
import uk.ac.aber.cs221.group5.logic.TaskStatuses;
import uk.ac.aber.cs221.group5.logic.Database;
import uk.ac.aber.cs221.group5.logic.DbStatus;


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
	
	private static Database databaseObj;
	
	private static final String DB_CONFIG_PATH = "connSaveFile.txt";
	private static final String MEMBERS_SAVE_PATH = "memberSaveFile.txt";
	private static final String TASK_SAVE_PATH = "taskSaveFile.txt";
	
	private long connTime;	//The time when CLI last synced with the Database
	

	public TaskList getTaskList(){
		return this.taskList;
	}
	

	public void setTaskList (TaskList list) {
		
		this.taskList = list;
		
		try {
			saveChange(TASK_SAVE_PATH);
			for(Frame frame : Frame.getFrames()){
				if(frame.getTitle().equals("Main Window")){
					frame.dispose();
				}
			}
			this.createWindow();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
		
	public MemberList getMemberList(){
		return this.memberList;
	}

	public void setmemberList (MemberList list){
			this.memberList = list;
			try {
				saveChange(TASK_SAVE_PATH);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
	}

	public static void main(String args[]) throws InterruptedException, NumberFormatException, IOException{
		TaskList taskList = new TaskList();
		MemberList memberList = new MemberList();

		memberList.loadMembers(MEMBERS_SAVE_PATH);
	
		MainWindow mainWindow = new MainWindow();
		if(!mainWindow.doesGUIExist()){
			mainWindow.createWindow();
		}
		

		readConfigToDb(DB_CONFIG_PATH);
		
		LoginWindow loginWindow = new LoginWindow();
		loginWindow.passMemberList(memberList);
		loginWindow.createWindow();	

	}



	
	private static void readConfigToDb(String dbFile) throws IOException{
		FileReader fileReader;
		try {
			fileReader = new FileReader(dbFile);
			BufferedReader read = new BufferedReader(fileReader);

			String dbName;
			String dbUsername;
			String dbPassword;
			String url;
			String dbPort; 
		
		    dbName = read.readLine();
			dbUsername = read.readLine();
			dbPassword = read.readLine();
			url = read.readLine();
			dbPort = read.readLine();
			
			read.close();
			
			callConnectOnDb(url, dbPort, dbUsername, dbPassword, dbName);
			
		
		} catch (Exception e) {
			//Display Error
		} 
	}
		
	
	public static void callConnectOnDb(String url, String dbPort, String dbUsername, String dbPassword, String dbName){
		databaseObj.connect(url, dbPort, dbUsername, dbPassword, dbName);
	}
		

	private boolean doesGUIExist(){
		for(Frame frame : Frame.getFrames()){
			if(frame.getTitle().equals("Main Window")){
				return true;
			}
		}
		return false;
	}
		
	public void setConnStatus(DbStatus connStatus){
		this.childWindow.setConnStatusLabel(connStatus);
	}
	
	public DbStatus getConnStatus(){
		return databaseObj.getConnStatus();
	}
	
	public long getConnTime(){
		return this.connTime;
	}
	
	
	public MainWindow(){
		//Setup common window features
		super();
		//Update DB to interface with new main window
		if (databaseObj != null){
			databaseObj.updateHostWindow(this);
		} else {
			databaseObj = new Database(MEMBERS_SAVE_PATH, this);
		}
	}
	
	public void createWindow(){
		//Get a new child window for super class
		childWindow = new MainWindowGUI();
		
		// Update local files with Task files from TaskerSRV if we are connected
		try {
			if (databaseObj.getConnStatus() == DbStatus.CONNECTED) {
				saveChange(TASK_SAVE_PATH);
				loadTasks(TASK_SAVE_PATH);
				this.childWindow.populateTable(this.taskList);
			} else {
				// If disconnected load then save
				loadTasks(TASK_SAVE_PATH);
			}
		} catch (FileNotFoundException e){
			this.displayWarning("Tasks not found locally, you need to connect to database for tasks");
			blankFile(TASK_SAVE_PATH);
		} catch (Exception e1) {
			this.displayError("Task file corrupted - Please connect to database for tasks", 
					"Task File Corruption");
			blankFile(TASK_SAVE_PATH);
		}
		
		//Load the members into the Member List
		try {
			memberList.loadMembers(MEMBERS_SAVE_PATH);
		} catch (NumberFormatException | IOException e) {
			this.displayError("Error loading member list, you need to connect to the database",
					"Error Loading members");
			blankFile(MEMBERS_SAVE_PATH);
		}
			
		//Ask parent to setup window for us and pass
		//this class's methods for it to work on
		setupWindowLaunch(this);
		
		childWindow.setConnStatusLabel(databaseObj.getConnStatus());
	}
	

	public void callWindowLaunch(){
		childWindow.launchWindow();		
	}

	@Override
	public void destroyWindow() {
		databaseObj.closeDbConn();

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
	public void displayError(String errorText, String errorType) {
		JOptionPane.showMessageDialog(null, errorText, errorType, JOptionPane.ERROR_MESSAGE);

	}

	/* (non-Javadoc)
	 * @see uk.ac.aber.cs221.group5.logic.WindowInterface#displayWarning(java.lang.String)
	 */
	@Override
	public void displayWarning(String warnText) {
		JOptionPane.showMessageDialog(null, warnText, "Warning", JOptionPane.WARNING_MESSAGE);

	}
	
/*	public TaskList getTaskList(){
		return this.taskList;
		//TODO check if table is displaying
	}
	
	public void setTaskList (TaskList list) {
		this.taskList = list;
	}
		
	public MemberList getMemberList(){
		return this.memberList;
	}
	public void setMemberList (MemberList list){
		this.memberList = list;
		
	}*/
	
	public void loadTasks(String filename) throws IOException{
		FileReader fileReader = new FileReader(filename);
		BufferedReader read = new BufferedReader(fileReader);
		//New Task List to prevent loading the same Tasks multiple times.
		TaskList newList = new TaskList();
		int numOfTasks = 0;
		String taskID           = null;
		String elements         = null;
		String taskName         = null;
		TaskStatuses taskStatus = null;
		String assigned         = null;
		String startDate        = null;
		String endDate          = null;
		
		try{
			//First read in the number of tasks
			numOfTasks = Integer.parseInt(read.readLine());
			//Load data and create Task objects
			for(int loopCount = 0; loopCount < numOfTasks; loopCount++){
				taskID     = read.readLine();
				elements   = read.readLine();
				taskName   = read.readLine();
				taskStatus = TaskStatuses.valueOf(TaskStatuses.class, read.readLine());
				assigned   = read.readLine();
				startDate  = read.readLine();
				endDate    = read.readLine();
				Task task  = new Task(taskID, taskName, startDate, endDate, assigned, taskStatus);
				if(elements == ",|"){
					String elementPair[] = {"", ""};
					elementPair[0] = elements.substring(0, elements.indexOf(","));
					elementPair[1] = elements.substring(elements.indexOf(",")+1, elements.indexOf("|"));
					Integer elementIndex = 0;
					while(elementPair != null){
						task.addElement(elementPair[0], elementPair[1], elementIndex.toString());
						removePair(elements);
						elementPair[0] = elements.substring(0, elements.indexOf(","));
						elementPair[1] = elements.substring(elements.indexOf(",")+1, elements.indexOf("|"));
						elementIndex++;
					}
				}
				else{
					newList.addTask(task);
			}
			
			}
			read.close();
			this.taskList = newList;
		} catch (Exception e) {
			this.displayError("Error loading Task file.", "Loading Error");
			e.printStackTrace();
			read.close();
		}
	}
	
	public void saveChange(String filename) throws IOException{
		ArrayList<String[]> elements;
		Task writeTask;
		FileWriter fileWriter = new FileWriter(filename);
		BufferedWriter write = new BufferedWriter(fileWriter);
		int numOfTasks = this.taskList.getListSize();
		int numOfElements = 0;	//The number of Elements in a single Task
		write.write(numOfTasks+"\n");
		for(int loopCount = 0; loopCount < numOfTasks; loopCount++){
			writeTask = this.taskList.getTask(loopCount);
			elements = writeTask.getAllElementPairs();
			write.write(writeTask.getID()+"\n");
			//Elements
			numOfElements = writeTask.getNumElements();
			if(numOfElements == 0){
				write.write(",|");
			}
			else{
				for(int i = 0; i < writeTask.getNumElements(); i++){
					String[] elementPair = {"", ""};
					elementPair = elements.get(i);
					write.write(elementPair[0]+",");
					write.write(elementPair[1]+"|");
				}
			}
			write.write("\n");
			write.write(writeTask.getName()+"\n");
			write.write(writeTask.getStatus()+"\n");
			write.write(writeTask.getMembers()+"\n");
			write.write(writeTask.getStart()+"\n");
			write.write(writeTask.getEnd()+"\n");
		}
		write.close();
		fileWriter.close();
	}
	
	/**
	 * Writes updated Task Element Comments to the local Task save file
	 * @param filename The name of the local Task save file
	 * @param newElements An ArrayList of the updated Task Elements
	 * @throws IOException
	 */
	public void updateElements(String filename, ArrayList<String[]> newElements) throws IOException{
		FileWriter fileWriter = new FileWriter(filename);
		BufferedWriter write  = new BufferedWriter(fileWriter);
		int numOfTasks = newElements.size();
		write.write(numOfTasks+"\n");
		for(int taskCount = 0; taskCount < numOfTasks; taskCount++){
			Task writeTask = this.taskList.getTask(taskCount);
			write.write(writeTask.getID()+"\n");
			write.write(newElements.get(taskCount)[0]+","+newElements.get(taskCount)[1]+"|"+"\n");
			write.write(writeTask.getName()+"\n");
			write.write(writeTask.getStatus()+"\n");
			write.write(writeTask.getMembers()+"\n");
			write.write(writeTask.getStart()+"\n");
			write.write(writeTask.getEnd()+"\n");
		}
		write.close();
		fileWriter.close();
	}
	
	public void updateGUITable(int rowNo, String newStatus) throws IOException{
		for(Frame frame : Frame.getFrames()){
			if(frame.getTitle().equals("Main Window")){
				frame.dispose();
			}
		}
		this.childWindow = new MainWindowGUI();
		this.loadTasks(TASK_SAVE_PATH);
		Task editedTask = this.taskList.getTask(rowNo);
		
		TaskStatuses enumStatus = TaskStatuses.valueOf(newStatus);
		editedTask.setStatus(enumStatus);
		this.taskList.changeTask(rowNo, editedTask);  //Updates the Task List with the new Status
		this.saveChange(TASK_SAVE_PATH);
		this.childWindow.updateTable(rowNo, newStatus);
		this.childWindow.setVisible(true);
		for(Frame frame : Frame.getFrames()){
			if(frame.getTitle().equals("Connection Settings")){
				frame.dispose();
			}
		}
	}
	
	public int getNumTask(){
		return this.taskList.getListSize();
	}
	
	public void updateLocalFiles(String taskFile){
		databaseObj.getTasks("");
	}
	
	
////Methods to deal with loading Task Elements
	
	public ArrayList<String[]> getElements(int tableIndex){
		ArrayList<String[]> elementPairs;
		
		Task selectedTask = taskList.getTask(tableIndex);
		
		elementPairs = selectedTask.getAllElementPairs();
		if (elementPairs.size() == 0){
			final String[] blankComment = {"No Elements", "No Comments"};
			elementPairs.add(blankComment);
		}
		
		return elementPairs;
	}
	
	private void blankFile(String filePath){
		
		try {
			FileWriter blankFile = new FileWriter(filePath, false);
			blankFile.append("");
		} catch (IOException e) {
			displayError("Cannot blank file " + filePath, "Error blanking file");
			System.exit(200);
		}
		
		
		
	}
	
	public ArrayList<String[]> getElementsLocal(String filename, int tableIndex) throws IOException{
		ArrayList<String[]> elements = new ArrayList<String[]>();
		int elementLine = (7 * tableIndex) + 1;	//Finds the line in the file where the element(s) for the selected task
												// are located
		FileReader fileReader = new FileReader(filename);
		BufferedReader reader = new BufferedReader(fileReader);
		String taskElements = new String();
		String[] elementPair = {"", ""};	//A single element name and comment pair
		
		//Skip over the lines in the file that we don't need
		for(int lineCount = 0; lineCount <= elementLine; lineCount++){
			reader.readLine();
		}
		taskElements = reader.readLine();
		reader.close();
		fileReader.close();
		
		elementPair = seperateElement(taskElements);
		if(elementPair != null){
			while(elementPair != null){
				elements.add(elementPair);
				taskElements = removePair(taskElements);
				elementPair = seperateElement(taskElements);
			}
			
		}
		else{
			String[] noElements = {"No Elements", "No Comments"};
			elements.add(noElements);
			return elements;
		}
				
		return elements;
	}
	
	private String[] seperateElement(String fileLine){
		String elementName    = new String();
		String elementComment = new String();
		String[] elementPair = {"", ""};
		int split;	//The index of the character that seperates the element name and the element comment
		int elementEnd;	//The index of the character that indicates the end of the element in the file
		
		//True if there are no elements for the Task
		if(fileLine.indexOf(',') == 0){
			return null;
		}
		
		split = fileLine.indexOf(',');
		elementEnd = fileLine.indexOf('|');
		elementName = fileLine.substring(0, split);
		elementPair[0] = elementName;
		elementPair[1] = fileLine.substring(split+1, elementEnd);	//split+1 so the seperator is not included in the comment
		
		return elementPair;
	
	}
	
	//Once a pair is loaded from the file, it is removed from the line so the next to be loaded always starts 
	// at position 0
	private String removePair(String fileLine){
		char[] fileLineChar;
		fileLineChar = fileLine.toCharArray();
		
		//This evaluates True if there is only one element left in the line
		if(fileLine.indexOf('|') == fileLine.length()-1){
			//Signifies there are no more elements. Stops the seperateElement method from trying to seperate an element
			// that does not exist
			fileLine = ",|";
		}
		else{
			for(int charCount = 0; charCount <= fileLine.indexOf('|'); charCount++){
				fileLineChar[charCount] = ' ';
			}
			fileLine = fileLineChar.toString();
			fileLine.trim();
		}
		
		return fileLine;
	}
	
	//Returns a single Task's elements without any editing
	private ArrayList<String> getUneditedElements(String filename) throws NumberFormatException, IOException{
		ArrayList<String> elements = new ArrayList<String>();
		FileReader fileReader = new FileReader(filename);
		BufferedReader read = new BufferedReader(fileReader);
		int numTasks = Integer.parseInt(read.readLine());
		//Skip to the first Tasks's element(s)
		read.readLine();
		//read.readLine();
		for(int elementCount = 0; elementCount < numTasks; elementCount++){
			elements.add(read.readLine());
			//Skip over the rest of the Task data - we don't care about that here
			for(int i = 0; i < 6; i++){
				read.readLine();
			}
		}
		read.close();
		fileReader.close();		
		return elements;
	}
	
	//Gets the System time from the Database of when it was last connected
	private void setConnTime(){
		databaseObj.getConnTime();
	}
}
