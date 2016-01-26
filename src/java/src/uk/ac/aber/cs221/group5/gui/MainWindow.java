/**
 * 
 */
package uk.ac.aber.cs221.group5.gui;

import java.awt.Frame;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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
	
	private String dbUrl;
	private String dbName;
	private String dbPass;
	private String dbUser;
	private String dbPort;
	

	public TaskList getTaskList(){
		return this.taskList;
	}
	

	public void settaskList (TaskList list) {
		
		this.taskList = list;
		
		try {
			saveChange("taskSaveFile.txt");
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
				saveChange("taskSaveFile.txt");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		
	}

	private static Database databaseObj;

	//Use current directory of java applet
	private final static String FILE_PATH = "./";	
	
	public static void main(String args[]) throws InterruptedException, NumberFormatException, IOException{
		TaskList taskList = new TaskList();
		MemberList memberList = new MemberList();
		
		MainWindow mainWindow = new MainWindow();
		if(!mainWindow.doesGUIExist()){
			mainWindow.createWindow();
		}
		
		databaseObj.connect();
		
		try{
			memberList.loadMembers("memberSaveFile.txt");
		}catch(NumberFormatException e){
			
		}
		
		LoginWindow loginWindow = new LoginWindow();
		loginWindow.passMemberList(memberList);
		loginWindow.createWindow();	

	}



	
	private void readConfigToDb(String dbFile) throws IOException{
		FileReader fileReader;
		try {
			fileReader = new FileReader(dbFile);
			BufferedReader read = new BufferedReader(fileReader);
			int dbTasks = 0;
			String dbName;
			String dbUsername;
			String dbPassword;
			String url;
			String dbPort; 
		
			dbTasks = Integer.parseInt(read.readLine());
		
		    dbName = read.readLine();
			this.dbName = dbName;
			dbUsername = read.readLine();
			this.dbUser = dbUsername;
			dbPassword = read.readLine();
			this.dbPass = dbPassword;
			url = read.readLine();
			this.dbUrl = url;
			dbPort = read.readLine();
			this.dbPort = dbPort;
			read.close();
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
		
		//TODO read config in here
		
		//databaseObj.connect(url, dbPort, dbUsername, dbPassword, dbName);


	private boolean doesGUIExist(){
		for(Frame frame : Frame.getFrames()){
			if(frame.getTitle().equals("Main Window")){
				return true;
			}
		}
		return false;
	}
		
	public void setConnStatus(DbStatus connStatus){
		//TODO implement setConnStatus
	}
	
	public MainWindow(){
		//Setup common window features
		super();
		//Update DB to interface with new main window
		if (databaseObj != null){
			databaseObj.updateHostWindow(this);
		} else {
			databaseObj = new Database(FILE_PATH, this);
		}
	}
	
	public void createWindow(){
		
		//Get a new child window for super class
		childWindow = new MainWindowGUI();
		
		//Load the tasks into the Task List
		try {
			loadTasks("taskSaveFile.txt");
			this.childWindow.populateTable(this.taskList);
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
		int numOfTasks = 0;
		String taskID           = null;
		String elements         = null;
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
			elements   = read.readLine();
			taskName   = read.readLine();
			taskStatus = TaskStatuses.valueOf(TaskStatuses.class, read.readLine());
			assigned   = read.readLine();
			startDate  = read.readLine();
			endDate    = read.readLine();
			Task task  = new Task(taskID, taskName, startDate, endDate, assigned, taskStatus);
			this.taskList.addTask(task);
		}
		
		read.close();
	}
	
	public void saveChange(String filename) throws IOException{
		ArrayList<String> elements = this.getUneditedElements(filename);
		FileWriter fileWriter = new FileWriter(filename);
		BufferedWriter write = new BufferedWriter(fileWriter);
		int numOfTasks = this.taskList.getListSize();
		write.write(numOfTasks+"\n");
		for(int loopCount = 0; loopCount < numOfTasks; loopCount++){
			Task writeTask = this.taskList.getTask(loopCount);
			write.write(writeTask.getID()+"\n");
			write.write(elements.get(loopCount)+"\n");
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
		this.loadTasks("taskSaveFile.txt");
		Task editedTask = this.taskList.getTask(rowNo);
		
		TaskStatuses enumStatus = TaskStatuses.valueOf(newStatus);
		editedTask.setStatus(enumStatus);
		this.taskList.changeTask(rowNo, editedTask);  //Updates the Task List with the new Status
		this.saveChange("taskSaveFile.txt");
		this.childWindow.updateTable(rowNo, newStatus);
		this.childWindow.setVisible(true);
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
}
