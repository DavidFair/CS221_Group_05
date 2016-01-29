/**
 * 
 * This is the main class of the TaskerCLI system. The system starts and exits within this Class. Creating
 * an Object of this Class for the first time will create a LoginWindow Object, forcing the user to log in
 * before they are given access to the system.
 * 
 * @author Ben Dudley (bed19)
 * @author David Fairbrother (daf5)
 * @author Jonathan Englund (jee17)
 * @author Josh Doyle (jod32)
 * @version 1.0.0
 * @since 1.0.0
 * @see MainWindowGUI
 * @see LoginWindow
 * @see ConnSettingsWindow
 * @see ViewElementsWindow
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
import javax.swing.text.StyledEditorKit.ForegroundAction;

import org.junit.experimental.theories.Theories;

import uk.ac.aber.cs221.group5.logic.MemberList;
import uk.ac.aber.cs221.group5.logic.Task;
import uk.ac.aber.cs221.group5.logic.Task.Element;
import uk.ac.aber.cs221.group5.logic.TaskList;
import uk.ac.aber.cs221.group5.logic.TaskStatuses;
import uk.ac.aber.cs221.group5.logic.Database;
import uk.ac.aber.cs221.group5.logic.DbStatus;

public class MainWindow {


	private static MainWindowGUI childWindow;
	private static LoginWindow loginWindow;


   private TaskList taskList     = new TaskList();
   //TaskList for holding Tasks that cannot be sent to the database
   private TaskList pendingList  = new TaskList();
   private MemberList memberList = new MemberList();

   private static Database databaseObj;

   private static final String DB_CONFIG_PATH = "connSaveFile.txt";
   private static final String MEMBERS_SAVE_PATH = "memberSaveFile.txt";
   private static final String TASK_SAVE_PATH = "taskSaveFile.txt";
   private static final String PENDING_SAVE_PATH = "pendingSaveFile.txt";
	
   private static long connTime; // The time when CLI last synced with the Database

   public TaskList getTaskList() {
      return this.taskList;
	}

   /**
    * This method replaces the TaskList held by the MainWindow object with a new TaskList.
    * @param list A new TaskList to replace the TaskList held by the MainWindow Object.
    */
   public void setTaskList(TaskList list) {

      this.taskList = list;
		
      try {
         saveChange(TASK_SAVE_PATH);
         childWindow.populateTable(taskList);
			

      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

   }
	
   /**
    * This method replaces the MemberList held by the MainWindow object with a new MemberList.
    * @param list A new MemberList to replace the MemberList held by the MainWindow Object 
    */
   public void setmemberList(MemberList list) {
      connTime = System.currentTimeMillis();
		
      this.memberList = list;
      try {
         saveChange(TASK_SAVE_PATH);
         } catch (IOException e) {
			// TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
	
   /**
    * This is the entry point to the System. This method created a new LoginWindow Object and spawns the Login
    * Window GUI.
    * 
    * @throws InterruptedException if the thread handling establishing a connection to the Database was somehow interrupted.
    * @throws NumberFormatException if the number of Members stored in the Members Save File cannot be read. This is usually because the file was somehow corrupted. 
    * @throws IOException if the Members Save File cannot be found.
    */
   public static void main(String args[]) throws InterruptedException, NumberFormatException, IOException {
      TaskList taskList = new TaskList();
      MemberList memberList = new MemberList();

      memberList.loadMembers(MEMBERS_SAVE_PATH);

      MainWindow mainWindow = new MainWindow();
      mainWindow.attachMainWindowToDb(mainWindow);
      readConfigToDb(DB_CONFIG_PATH);
      if (!mainWindow.doesGUIExist()) {
         mainWindow.createWindow();
      }
		
      childWindow.setVisible(false);
		
      loginWindow = new LoginWindow();
      loginWindow.passMemberList(memberList);
      loginWindow.createWindow();
      loginWindow.setLabelGUI(databaseObj.getConnStatus());		
   }


   /**
    * Reads the saved connection settings from the Database Configuration file and attempts to connect to the Database using those settings.
    * @param dbFile The filepath of the Database Configuration file.
    * 
    * @throws IOException if the Database Configuration file cannot be found.
    * 
    * @see callConnectOnDb
    */
   private static void readConfigToDb(String dbFile) throws IOException {
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
         e.printStackTrace();
      }
   }
	
    /**
     * Calls the connect method in the Database Object. 
     * @param url The URL of the database that TaskerCLI is attempting to connect to.
     * @param dbPort The Port Number being used to connect to the Database.
     * @param dbUsername The Username for the database that TaskerCLI is attempting to connect to.
     * @param dbPassword The Password for the database that TaskerCLI is attempting to connect to.
     * @param dbName The name of the Database that TaskerCLI is attempting to connect to.
     * 
     * @see readConfigToDb
     */
   public static void callConnectOnDb(String url, String dbPort, String dbUsername, String dbPassword, String dbName) {
      databaseObj.connect(url, dbPort, dbUsername, dbPassword, dbName);
   }
	
	
   /**
    * Checks if the GUI Window for the MainWindow exists. This enables creation of multiple MainWindow Objects without spawning multiple GUI Windows.
    * @return Whether or not the Main Window GUI has been created.
    */
   private boolean doesGUIExist() {
      for (Frame frame : Frame.getFrames()) {
         if (frame.getTitle().equals("Main Window")) {
            return true;   
         }
      }
      return false;
   }
	
   /**
    * Sets the value of the Connection Status indicator label in the Main Window GUI
    * @param connStatus The current connection status of the Database.
    */
   public void setConnStatus(DbStatus connStatus) {
      if (childWindow!= null){
         MainWindow.childWindow.setConnStatusLabel(connStatus);
      }
      
      if (loginWindow != null){
    	  loginWindow.setLabelGUI(connStatus);
      }
      
      if (connStatus == DbStatus.CONNECTED){
    	  readPendingTasks();
      }
      
      
      
    }
	
   /**
    * Used to enable or disable the auto-sync timer.
    * @param timerState The Boolean state of the auto-syn timer.
    */
   public void setAutoTimer(boolean timerState){
      if(timerState){
         databaseObj.startAutoSync();
      }
      else{
         databaseObj.stopAutoSync();
      }
		
   }

   /**
    * Gets the connection status from the Database Object.
    * @return The connection status of the Database.
    */
   public static DbStatus getConnStatus() {
      return databaseObj.getConnStatus();
   }

   /**
    * Gets the System Time of when TaskerCLI last synced with the Database.
    * @return The System Time of when TaskerCLI last synced with the Database/
    */
   public static long getConnTime() {
      return MainWindow.connTime;
   }

   /**
    * Constructor for the MainWindow Class.
    */
   public MainWindow() {
      // Setup common window features
      super();

   }
	
   /**
    * Links the Database Object with this MainWindow Object
    * @param main The MainWindow Object to link with the Database Object.
    */
   public void attachMainWindowToDb(MainWindow main){
      if (databaseObj != null) {
         databaseObj.setHostWindow(main);
      } else {
         databaseObj = new Database(MEMBERS_SAVE_PATH, this);
      }
		
   }

   /**
    * Creates a Main Window GUI Window. This method also downloads Tasks from the Database or, failing that, loads a previous version of the TaskList from the local save file.
    * 
    * @see saveChange
    * @see loadTasks
    * @see displayWarning
    * @see displayError
    * @see blankFile
    */
   public void createWindow() {
      // Get a new child window for super class
      childWindow = new MainWindowGUI(this);

      // Update local files with Task files from TaskerSRV if we are connected
      try {
          if (databaseObj.getConnStatus() == DbStatus.CONNECTED) {
            saveChange(TASK_SAVE_PATH);
            loadTasks(TASK_SAVE_PATH);
               MainWindow.childWindow.populateTable(this.taskList);
          } else {
               // If disconnected load then save
               loadTasks(TASK_SAVE_PATH);
               MainWindow.childWindow.populateTable(this.taskList);
          }
      } catch (FileNotFoundException e) {
         this.displayWarning("Tasks not found locally, you need to connect to database for tasks");
         blankFile(TASK_SAVE_PATH);
      } catch (Exception e1) {
         this.displayError("Task file empty - Please connect to database for tasks", "Task File empty");
         blankFile(TASK_SAVE_PATH);
      }

      // Load the members into the Member List
      try {
         memberList.loadMembers(MEMBERS_SAVE_PATH);
      } catch (NumberFormatException | IOException e) {
         this.displayError("Error loading member list, you need to connect to the database",
               "Error Loading members");
         blankFile(MEMBERS_SAVE_PATH);
		}

		// Ask parent to setup window for us and pass
		// this class's methods for it to work on
		//setupWindowLaunch(this);

		childWindow.setConnStatusLabel(databaseObj.getConnStatus());
	}

	/**
	 * Ensures the connection to the Database is closed. Called at each exit point of TaskerCLI to avoid dangling connections on the server side.
	 */
	public void destroyWindow() {
		databaseObj.closeDbConn();

	}
	
	/**
	 * Updates a Task in the Database with a new Task.
	 * @param updatedTask The Task to update on the Database.
	 */
	public void updateTask(Task updatedTask){
		databaseObj.updateDbTask(updatedTask);
	}
	
	/**
	 * Displays a standardised error dialogue pop-up.
	 * @param errorText The error text to display.
	 * @param errorType The title of the error dialogue pop-up.
	 */
	public void displayError(String errorText, String errorType) {
		JOptionPane.showMessageDialog(null, errorText, errorType, JOptionPane.ERROR_MESSAGE);

	}

	/**
	 * Displays a standardised errir dialogue pop-up.
	 * @param warnText The warning text to display.
	 */
	public void displayWarning(String warnText) {
		JOptionPane.showMessageDialog(null, warnText, "Warning", JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * Loads the Tasks from the local save file into the TaskList held by this MainWindow Object.
	 * @param filename The filename of the Tasks Save File.
	 * 
	 * @throws Exception if the Tasks Save File cannot be found or is corrupted.
	 */
	public void loadTasks(String filename) throws Exception {
		FileReader fileReader = new FileReader(filename);
		BufferedReader read = new BufferedReader(fileReader);
		ArrayList<String> extractedElementIds = new ArrayList<String>();
		// New Task List to prevent loading the same Tasks multiple times.
		TaskList newList = new TaskList();
		int numOfTasks = 0;
		String taskID = null;
		String elementID = null;
		String elements = null;
		String taskName = null;
		TaskStatuses taskStatus = null;
		String assigned = null;
		String startDate = null;
		String endDate = null;

		try {
			// First read in the number of tasks
			numOfTasks = Integer.parseInt(read.readLine());
			// Load data and create Task objects
			for (int loopCount = 0; loopCount < numOfTasks; loopCount++) {
				int elementNum = 0;
				taskID = read.readLine();
				elementID = read.readLine();
				extractedElementIds = getElementIndexes(elementID);
				elements = read.readLine();
				taskName = read.readLine();
				taskStatus = TaskStatuses.valueOf(TaskStatuses.class, read.readLine());
				assigned = read.readLine();
				startDate = read.readLine();
				endDate = read.readLine();
				Task task = new Task(taskID, taskName, startDate, endDate, assigned, taskStatus);
				ArrayList<String[]> elementCommentPairs = seperateElementComments(elements);
				for (String[] processed : elementCommentPairs){
					task.addElement(processed[0], processed[1], extractedElementIds.get(elementNum));
				}
				newList.addTask(task);
				
			}
			read.close();
			this.taskList = newList;
		} catch (Exception e) {
			read.close();
			//e.printStackTrace();
			throw e;
		}
	}

	/**
	 * Saves all Tasks from the TaskList held by this MainWindow Object to the local Tasks save file.
	 * @param filename The filename of the local Tasks save file.
	 * 
	 * @throws IOException if the local Tasks save file cannot be found.
	 */
	public void saveChange(String filename) throws IOException {
		ArrayList<uk.ac.aber.cs221.group5.logic.Task.Element> elements;
		Task writeTask;
		FileWriter fileWriter = new FileWriter(filename);
		BufferedWriter write = new BufferedWriter(fileWriter);
		int numOfTasks = this.taskList.getListSize();
		int numOfElements = 0; // The number of Elements in a single Task
		write.write(numOfTasks + "\n");
		for (int loopCount = 0; loopCount < numOfTasks; loopCount++) {
			writeTask = taskList.getTask(loopCount);
			elements = writeTask.getAllElements();
			write.write(writeTask.getID() + "\n");
			// Elements
			numOfElements = writeTask.getNumElements();
			elements = writeTask.getAllElements();
			if (numOfElements == 0) {
				write.write("0\n");
				write.write(",|");
			} else {
				for (int idCount = 0; idCount < writeTask.getNumElements(); idCount++){
					write.write(writeTask.getElement(idCount).getIndex()+",");
				}
				write.write("\n");
				for (int i = 0; i < writeTask.getNumElements(); i++) {
					uk.ac.aber.cs221.group5.logic.Task.Element writeElement = elements.get(i);
					write.write(writeElement.getName()+","+writeElement.getComment()+"|");
				}
			}
			write.write("\n");
			write.write(writeTask.getName() + "\n");
			write.write(writeTask.getStatus() + "\n");
			write.write(writeTask.getMembers() + "\n");
			write.write(writeTask.getStart() + "\n");
			write.write(writeTask.getEnd() + "\n");
		}
		write.close();
		fileWriter.close();
	}
	


	public void readPendingTasks(){
		try {
			FileReader fileReader = new FileReader(PENDING_SAVE_PATH);
			BufferedReader read = new BufferedReader(fileReader);
			
			int numOfTasks = Integer.parseInt(read.readLine());
			for (int i=0; i < numOfTasks; i++){
			//Read the indexs into an array of elementIndexes
			ArrayList<String> elementIndexes = getElementIndexes(read.readLine());
			ArrayList<String[]> elementComments = seperateElementComments(read.readLine());
			String taskName = read.readLine();
			String taskStatus = read.readLine();
			String taskMemeber = read.readLine();
			String startDate = read.readLine();
			String endDate = read.readLine();
			String taskId = read.readLine();
			
			TaskStatuses status = TaskStatuses.valueOf(taskStatus);
			
			Task toPush = new Task(taskId, taskName, startDate, endDate, taskMemeber, status);
			
			int elementIndex = 0;
			for (String[] elementPair: elementComments){
				toPush.addElement(elementPair[0], elementPair[1], elementIndexes.get(elementIndex));
				elementIndex++;
			}
			
			databaseObj.updateDbTask(toPush);
			blankFile(PENDING_SAVE_PATH);
			
			} //Loop back over all task objects in save file
			
					
			
		} catch (IOException e){
		
		}
		
		blankFile(PENDING_SAVE_PATH);
	}
	
	/**
	 * Writes tasks that cannot be sent to the Database to a 'Pending Tasks' file for them to be sent to the Database at a later time.
	 * @param pendingTask The Task that could not be sent to the Database.
	 * 
	 * @throws IOException if the 'Pending Tasks' file cannot be found.
	 */
	public void writePendingTask(Task pendingTask){
		try {
		FileWriter fileWriter = new FileWriter(PENDING_SAVE_PATH, true);
		BufferedWriter write = new BufferedWriter(fileWriter);
		write.write(pendingTask.getID() + "\n");
		ArrayList<uk.ac.aber.cs221.group5.logic.Task.Element> elements = pendingTask.getAllElements();
		
		int numOfElements = pendingTask.getNumElements();
		if (numOfElements == 0) {
			write.write("0\n");
			write.write(",|");
		} else {
			for (int idCount = 0; idCount < pendingTask.getNumElements(); idCount++){
				write.write(pendingTask.getElement(idCount).getIndex()+",");
			}
			write.write("\n");
			for (int i = 0; i < pendingTask.getNumElements(); i++) {
				uk.ac.aber.cs221.group5.logic.Task.Element writeElement = elements.get(i);
				write.write(writeElement.getName()+","+writeElement.getComment()+"|");
			}
		}
		write.write("\n");
		write.write(pendingTask.getName() + "\n");
		write.write(pendingTask.getStatus() + "\n");
		write.write(pendingTask.getMembers() + "\n");
		write.write(pendingTask.getStart() + "\n");
		write.write(pendingTask.getEnd() + "\n");
		write.write(pendingTask.getID() + "\n");
		write.close();
		} catch (IOException e) {
			displayError("Error - Could not write pending tasks to local file", 
					"Error storing pending tasks");
		}
	}

	/**
	 * Updates the local save files with data downloaded from the Database
	 * @param taskFile
	 */
	public void updateLocalFiles(String taskFile) {
		databaseObj.getTasks("");
	}

	//// Methods to deal with loading Task Elements

	/**
	 * Returns Description-Comment pairs for the Elements associated with a single Task.
	 * @param tableIndex The position of the Task in the Table in the MainWindow GUI. This is also the index of Task in the TaskList of this MainWindow Object.
	 * 
	 * @return Returns an ArrayList of Description-Comment Pairs for all of the Elements associated with a single Task.
	 */
	public ArrayList<String[]> getElements(int tableIndex) {
		ArrayList<String[]> elementPairs;

		Task selectedTask = taskList.getTask(tableIndex);

		elementPairs = selectedTask.getAllElementPairs();
		if (elementPairs.size() == 0) {
			final String[] blankComment = { "No Elements", "No Comments" };
			elementPairs.add(blankComment);
		}

		return elementPairs;
	}

	/**
	 * Erases the content of a file.
	 * @param filePath The path of the file to be erased.
	 */
	private void blankFile(String filePath) {

		try {
			FileWriter blankFile = new FileWriter(filePath, false);
			blankFile.append("");
		} catch (IOException e) {
			displayError("Cannot blank file " + filePath, "Error blanking file");
			System.exit(200);
		}

	}

	/**
	 * Returns Description-Comment pairs for the Elements associated with a single Task, loaded from the local Tasks save file.
	 * @param filename The filename of the local Tasks save file.
	 * @param tableIndex The number of the Task in the Main Window GUI Table. This is also the ordering of the selected Task in the local Tasks save file.
	 * 
	 * @return An ArrayList of Description-Comment pairs for the Elements associated with a single Task, loaded from the local Tasks save file.
	 * 
	 * @throws IOException if the local Tasks save file is not found.
	 */
	public ArrayList<String[]> getElementsLocal(String filename, int tableIndex) throws IOException {
		ArrayList<String[]> elements = new ArrayList<String[]>();
		int elementLine = (7 * tableIndex) + 1; // Finds the line in the file
												// where the element(s) for the
												// selected task
												// are located
		FileReader fileReader = new FileReader(filename);
		BufferedReader reader = new BufferedReader(fileReader);
		String taskElements = new String();
		String[] elementPair = { "", "" }; // A single element name and comment
											// pair

		// Skip over the lines in the file that we don't need
		for (int lineCount = 0; lineCount <= elementLine; lineCount++) {
			reader.readLine();
		}
		taskElements = reader.readLine();
		reader.close();
		fileReader.close();

		elementPair = seperateElement(taskElements);
		if (elementPair != null) {
			while (elementPair != null) {
				elements.add(elementPair);
				taskElements = removePair(taskElements);
				elementPair = seperateElement(taskElements);
			}

		} else {
			String[] noElements = { "No Elements", "No Comments" };
			elements.add(noElements);
			return elements;
		}

		return elements;
	}
	
	private ArrayList<String[]> seperateElementComments(String input) {

		ArrayList<String[]> processedStrings = new ArrayList<String[]>();
		
		if (input.equals(",|")) {
			// No elements or comments
			// Leave it as is
			return processedStrings; //Return blank ArrayList
		}

		while (input.length() > 2) {
			String[] elementPair = { "", "" };
				
			if (input.charAt(0) == '|') {
					// Remove the element seperator character from the begining
					// of the elements
					// Substring copies the string from index to the end
					input = input.substring(1);
				}
			
				// Input does not have pipe as first char, so copy string from 0
				// to terminator (',')
				elementPair[0] = input.substring(0, input.indexOf(","));
				elementPair[1] = input.substring(input.indexOf(",") + 1, input.indexOf("|"));
				input = removePair(input);
			
				processedStrings.add(elementPair);
		}

		// Input length is < 2
		return processedStrings;
	}


	

	/**
	 * Seperates a single Description-Comment pair so they can be added to the Task as an Element.
	 * @param fileLine The line from the local save file containing the Description and the Comment.
	 * 
	 * @return Returns the seperated Description-Comment pair.
	 */
	private String[] seperateElement(String fileLine) {
		String elementName = new String();
		String elementComment = new String();
		String[] elementPair = { "", "" };
		int split; // The index of the character that seperates the element name
					// and the element comment
		int elementEnd; // The index of the character that indicates the end of
						// the element in the file

		// True if there are no elements for the Task
		if (fileLine.indexOf(',') == 0) {
			return null;
		}

		split = fileLine.indexOf(',');
		elementEnd = fileLine.indexOf('|');
		elementName = fileLine.substring(0, split);
		elementPair[0] = elementName;
		elementPair[1] = fileLine.substring(split + 1, elementEnd); // split+1
																	// so the
																	// seperator
																	// is not
																	// included
																	// in the
																	// comment

		return elementPair;

	}
	
	/**
	 * Once a Description-Comment pair for an Element is loaded from the file, it is removed from the line
	 * so the next pair to be loaded will always start at position 0.
	 * @param fileLine The line from the local save file containing the unseperated Description-Comment pair(s) for an Element.
	 * 
	 * @return The line from the local save file with the first Description-Comment pair removed.
	 */
	private String removePair(String fileLine) {
		char[] fileLineChar;
		fileLineChar = fileLine.toCharArray();
		String trimmed;

		// This evaluates True if there is only one element left in the line
		if (fileLine.charAt(0) == ',') {
			// Signifies there are no more elements. Stops the seperateElement
			// method from trying to seperate an element
			// that does not exist
			fileLine = ",|";
			trimmed = fileLine;
		} else {
			for (int charCount = 0; charCount < fileLine.indexOf('|'); charCount++) {
				fileLineChar[charCount] = ' ';
			}
			fileLine = String.copyValueOf(fileLineChar);
			trimmed = fileLine.trim();
		}

		return trimmed;
	}

	/**
	 * Gets the unedited Element data for a single Task from the local Tasks save file. 
	 * @param filename The filename of the local Tasks save file.
	 * 
	 * @return The Element Data for a single task straight from the local Tasks save file.
	 * 
	 * @throws NumberFormatException if the local Tasks save file has been corrupted. 
	 * @throws IOException if the local Tasks save file cannot be located.
	 */
	private ArrayList<String> getUneditedElements(String filename) throws NumberFormatException, IOException {
		ArrayList<String> elements = new ArrayList<String>();
		FileReader fileReader = new FileReader(filename);
		BufferedReader read = new BufferedReader(fileReader);
		int numTasks = Integer.parseInt(read.readLine());
		// Skip to the first Tasks's element(s)
		read.readLine();
		// read.readLine();
		for (int elementCount = 0; elementCount < numTasks; elementCount++) {
			elements.add(read.readLine());
			// Skip over the rest of the Task data - we don't care about that
			// here
			for (int i = 0; i < 6; i++) {
				read.readLine();
			}
		}
		read.close();
		fileReader.close();
		return elements;
	}


	
////Methods for dealing with Task Element IDs
	/**
	 * Extracts the unique identifiers for each Element in a Task from the local Tasks save file.
	 * @param indexes The line from the local save file that contains the unique Element identifiers.
	 * 
	 * @return An ArrayList of all the unique identifiers for the Elements of the Task, with all file formatting removed.
	 */
	private ArrayList<String> getElementIndexes(String indexes){
		ArrayList<String> extractedIndexes = new ArrayList<String>();
		
		for(int i = 0; i < indexes.length(); i=i+2){
			//This line takes the i'th Element ID from the line, effectively removing the seperators
			extractedIndexes.add(indexes.substring(i, i+1));
		}
		
		return extractedIndexes;
	}
}
