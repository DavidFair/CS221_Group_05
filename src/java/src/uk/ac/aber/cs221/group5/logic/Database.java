package uk.ac.aber.cs221.group5.logic;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Timer;
import java.util.TimerTask;

import com.mysql.fabric.xmlrpc.base.Data;

import uk.ac.aber.cs221.group5.gui.MainWindow;



/**
 * @(#)Database.java 0.1 2015-12-02
 * 
 * Copyright (c) 2015 Aberystwyth University
 * All rights reserved
 * 
 */


/**
 * Implements a database class for communicating
 * to a TaskerSRV server
 * @author David (daf5)
 *
 */


//This is currently a skeleton class to allow SQL to be created
public class Database {
	
	private String dbURL;
	private String dbPortNo;
	private String dbName;
	private String dbUsername;
	private String dbPassword;
	private Connection dbConnection;
	
	private Timer connTimer;
	private Timer latencyTimer;
	
	private DbStatus currentStatus;
	
	private String usersPath;
	private MainWindow hostWindow;
	
	//How often the DB attempts to update program information
	private static final int REFRESH_SEC_DELAY = 60;
	private static final int SYNC_ALRT_DELAY = 1;
	
	/**
	 * Creates a new database object and loads JDBC into memory
	 * @param usersFilePath The location to save and load users to
	 */
	public Database(String usersFilePath, MainWindow parentWindow){
		//Leave blank so default port is used
		this.dbPortNo = "";
		this.connTimer = null;
		this.currentStatus = DbStatus.DISCONNECTED;
		
		/* First load JDBC connector */
		//Try to load driver else exit with error code
		try 
		{
			//This is recommended method in documentation
			//As it dynamically loads the class into memory at run time
			
			Class.forName("com.mysql.jdbc.Driver");
			
		} catch (ClassNotFoundException e){
			System.err.println("Unable to load MYSQL JDBC implementation class");
			System.exit(1);
		}
		
		this.usersPath = usersFilePath;
		this.hostWindow = parentWindow;
		
	}
	
	/**
	 * Saves a list of all user names to a local file
	 * @param filePath Path of file to be saved
	 * @param allUsers A MemberList containing all users to be saved
	 * @throws IOException 
	 */
	public void saveUserName(String filePath, MemberList allUsers) throws IOException{
		final String saveFileName = "members.txt";
		
		String fullFilePath = filePath + saveFileName;
		
		FileWriter fileWriter = new FileWriter(fullFilePath);
		BufferedWriter write = new BufferedWriter(fileWriter);
		
		int numOfTasks = allUsers.getLength();
		write.write(numOfTasks+"\n");
		
		for(int loopCount = 0; loopCount < numOfTasks; loopCount++){
			Members writeTask = allUsers.getMember(loopCount);
			write.write(writeTask.getEmail()+"\n");
			write.write(writeTask.getName()+"\n");
		}
		 write.close();
	}
	
	
	public void connect(){
		//XXX Remove when config reading is implemented
		//TODO Re purpose to reconnect function
		//Currently hardcoded until a method which reads from config calls connect
		connect("db.dcs.aber.ac.uk", "", "csgpadm_5", "906BnQjD", "csgp_5_15_16");
	}

	public void updateHostWindow(MainWindow newWindow){
		this.hostWindow = newWindow;
	}
	
	
	public void connect(String hostName, String portNo, String dbUser, String dbPass, String dbName) {
		this.dbUsername = dbUser;
		this.dbPassword = dbPass;
		this.dbName = dbName;
		this.dbPortNo = portNo;

		// Create appropriate connection string
		final String urlPrepend = "jdbc:mysql://";
		// Append connection parameters - such as automatically reconnecting
		final String urlAppend = "?autoReconnect=true";

		this.dbURL = urlPrepend + hostName + this.dbPortNo + "/" + this.dbName + urlAppend;

		class ConnectThread implements Runnable {

			private String url;
			private String uName;
			private String pWord;
			private Database parentDb;

			public ConnectThread(String url, String uName, String pWord, Database parentDB) {
				this.url = url;
				this.uName = uName;
				this.pWord = pWord;
				this.parentDb = parentDB;
			}

			public void run() {
				try {
					dbConnection = DriverManager.getConnection(url, uName, pWord);
					parentDb.currentStatus = DbStatus.CONNECTED;
				} catch (SQLException e) {
					System.err.println("Could not establish connection to DB in connect method");
					// For use debugging
					// Thread.dumpStack();
					System.err.println("Full connection string was: " + url);

					// Reset state
					parentDb.currentStatus = DbStatus.DISCONNECTED;
				}

			}

		}

		Thread connectDb = new Thread(new ConnectThread(dbURL, dbUser, dbPass, this));
		connectDb.start();

	}
	
	
	
	public void closeDbConn(){
		
		if (connTimer != null){
			connTimer.cancel();
			connTimer = null;
		}

		
		if (dbConnection != null){
			try {
				dbConnection.close();
			} catch (SQLException e) {
				System.err.println("Could not close JDBC connection");
				//For debugging
				//e.printStackTrace();
				
				//Exit as we cannot close JDBC which means something is wrong
				System.exit(100);
				
			}
		}
		
		//Release current connection
		dbConnection = null;
		
		//Set status
		currentStatus = DbStatus.DISCONNECTED;
		
	}
	
	
	
	public DbStatus getConnStatus(){
		return currentStatus;
	}
	
	
	public void getTasks(){
		getTasks("");
	}
	
	public void getTasks(String username){
		
		String taskQuery;
		if (username != ""){
			taskQuery = "SELECT * FROM `tbl_tasks` WHERE TaskOwner='" + username + "';";
		} else {
			taskQuery = "SELECT * FROM `tbl_tasks`";
		}
		

		class TaskSync implements Runnable {
			
			String sqlQuery;
			
			public TaskSync(String query){
				this.sqlQuery = query;
			}
			
			public void run() {
				ResultSet tasksSet = executeSqlStatement(sqlQuery);
				
				if (tasksSet != null){
					//Convert SQL result to Task List
					TaskList tasksList = resultSetToTaskList(tasksSet);
					
					//Update the task elements
					updateTaskElements(tasksList);
					
					//Update the copy held by the main window

					try {
						hostWindow.settaskList(tasksList);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					

					try {
						tasksSet.close();
					} catch (SQLException e) {
						System.err.println(e.getMessage());
					}
				} else {
					System.err.println("No results returned in TaskSync- Attempting to reconnect the DB");
				}
			}
		}
		
		//Set status to sync
		currentStatus = DbStatus.SYNCHRONIZING;
		
		Thread sqlExec = new Thread(new TaskSync(taskQuery));
		sqlExec.start();
		

		createRefreshTimer(REFRESH_SEC_DELAY, this );
		
	}

	

	public void getMembers(){
		
		
		
		class MemberSync implements Runnable {
			
			private Database parentDB;
			
			public MemberSync(Database parentDatabase){
				this.parentDB = parentDatabase;
			}
			
			public void run() {
				ResultSet members = executeSqlStatement("Select * FROM tbl_users");
				
				if (members != null){
					MemberList newMemberList = resultSetToMemberList(members);
					
					try {
						hostWindow.setmemberList(newMemberList);
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

					
					try {
						parentDB.saveUserName(usersPath, newMemberList);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					try {
						members.close();
					} catch (SQLException e) {
						System.err.println(e.getMessage());
					}
				} else {
					System.err.println("No results returned in getMembers - Attempting to reconnect the DB");
				}
			}
		}
		//Create new thread an exec it
		Thread sqlExec = new Thread(new MemberSync(this));
		sqlExec.start();

		createRefreshTimer(REFRESH_SEC_DELAY, this);

	}
	
	public void startAutoSync(){
		createRefreshTimer(REFRESH_SEC_DELAY, this);
	}
	
	public void stopAutoSync(){
		
		if (connTimer == null){
			return;
		}
		
		connTimer.cancel();
		connTimer = null;
		
	}
	
	
	/**
	 * Takes a statement object and SQL query and executes the query. 
	 * Returns the query result as a ResultSet object
	 * @param statementToExec The Statement object to use 
	 * @param query The SQL query to execute
	 * @return ResultSet containing result. If it fails null.
	 */
	private ResultSet executeSqlStatement(Statement statementToExec, String query){
		
		ResultSet sqlOutput;
		
		try{
			sqlOutput = statementToExec.executeQuery(query);
		} catch (SQLException e){
			System.err.println("Sql query failed. Query used was:");
			System.err.println(query);
			
			//Reset ResultSet to null
			sqlOutput = null;
			
			//For debugging
			//System.err.println("Stack trace");
			//Thread.dumpStack();
		}
		
		return sqlOutput;

	}
	
	/**
	 * Executes SQL query passed in by the parameter and returns a ResultSet
	 * containing the returned data from the database 
	 * @param query SQL query to execute on the database
	 * @return ResultSet containing data if query succeeded. Null if it did not
	 */
	private ResultSet executeSqlStatement(String query){
		Statement sqlStatementObject = null;
		
		if (dbConnection == null){
			return null;
		}
		
		try{
			sqlStatementObject = dbConnection.createStatement();
		} catch (SQLException e) {
			System.err.println("Could not create SQL statement");
			//To use when debugging
			//Thread.dumpStack();
			
		}
		
		//Pass resultSet straight through to caller
		return executeSqlStatement(sqlStatementObject, query);
		
	}
	
	private TaskList resultSetToTaskList(ResultSet toConvert){
		TaskList result = new TaskList();
		
		try {
			while (toConvert.next()){
				String id = toConvert.getString("TaskID");
				String taskName = toConvert.getString("TaskName");
				String startDate = toConvert.getString("StartDate");
				String endDate = toConvert.getString("EndDate");
				String owner = toConvert.getString("TaskOwner");
				int status = toConvert.getInt("Status");
				
				//Convert status to enum in Java
				TaskStatuses enumStatus = null;
				switch (status) {
				case 0:
					enumStatus = TaskStatuses.Abandoned;
					break;
					
				case 1:
					enumStatus = TaskStatuses.Allocated;
					break;
				case 2:
					enumStatus = TaskStatuses.Completed;
					break;
				default:
					//XXX Need to handle bad value for enum
					break;
				}
				
				//Create object and store
				Task newTask = new Task(id, taskName, startDate, endDate, owner, enumStatus);
				result.addTask(newTask);
				
			} //End of while loop
			return result;
			
		} catch (SQLException e){
			//XXX Error handling
			System.err.println(e.getMessage());
			return null;
			
		}
		

	}
	
	private MemberList resultSetToMemberList(ResultSet toConvert){
		MemberList newList = new MemberList();
		
		if (toConvert == null){
			throw new NullPointerException("Passed null result set in resultSetToMemberList");
		}
		
		try {
			while (toConvert.next()){
				String name = toConvert.getString("FirstName") + " " + toConvert.getString("LastName");
				String email = toConvert.getString("Email");
				Members newMember = new Members(name, email);
				newList.addMember(newMember);
			}
			
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
		return newList;
	}
	
	private void updateTaskElements(TaskList allTasks){

		// This class runs in seperate threads pulling all task elements
		class ElementSync implements Runnable {
			private TaskList listOfTasks;

			public ElementSync(TaskList allTasks) {
				this.listOfTasks = allTasks;
			}

			public void run() {
				// Get the number of tasks we need to collect elements for

				int numOfTasks = listOfTasks.getListSize();
				for (int i = 0; i < numOfTasks; i++) {

					Task currentTask = listOfTasks.getTask(i);

					// Clear current task to avoid duplicates
					currentTask.clearAllElements();

					String sqlQuery = "SELECT * FROM tbl_elements WHERE TaskID = ";
					sqlQuery = sqlQuery + currentTask.getID();

					ResultSet elements = executeSqlStatement(sqlQuery);

					if (elements != null) {

						try {
							while (elements.next()) {
								// Iterate over all comments
								int index = elements.getInt("Index");
								String elementDesc = elements.getString("TaskDesc");
								String elementComments = elements.getString("TaskComments");
								
								//Remove pipes and chars from element desc and comments
								elementDesc = elementDesc.replace(",", "");
								elementComments = elementComments.replace("|", "");
								

								currentTask.addElement(elementDesc, elementComments);
							}

							elements.close();
						} catch (SQLException e) {
							System.err.println(e.getMessage());
						}
					} else {
						throw new NullPointerException("Result set was null in getMembers");
					}

				}

			}

		} //End of anon inner class
		

		Thread execSql = new Thread(new ElementSync(allTasks));
		execSql.start();

	}
	
	private void createRefreshTimer(int seconds, Database database) {

		class RefreshTask extends TimerTask {

			Database parentDB;

			public RefreshTask(Database db) {
				this.parentDB = db;
			}

			@Override
			public void run() {
				// This will be run when timer fires
				// First check if we are connected and not busy

				if (parentDB.getConnStatus() == DbStatus.CONNECTED) {
					parentDB.getMembers();
					// Get all tasks
					parentDB.getTasks("");
					System.out.println("Fired refresh");
					// TODO Call external method to update held state

				} else if (parentDB.getConnStatus() == DbStatus.DISCONNECTED) {
					// Attempt to reconnect
					parentDB.connect();
				}

			}
		}

		if (connTimer != null) {
			connTimer.cancel();
		}

		connTimer = new Timer();
		connTimer.schedule(new RefreshTask(database), seconds * 1000);

	}
		
	private void setupLatencyTimer(){
		latencyTimer = new Timer();
		
		class LatencyAlert extends TimerTask{

			@Override
			public void run() {
				//hostWindow.whatever(currentStatus)
				
				if (currentStatus == DbStatus.SYNCHRONIZING){
					//Still syncing create timer to check again
					setupLatencyTimer();
				} else if (currentStatus == DbStatus.CONNECTED){
					//Finished cancel any and all timers
					latencyTimer.cancel();
				} else if (currentStatus == DbStatus.DISCONNECTED){
					//TODO disconnected logic
				}
				
			}
			
		}
		
		
	}
	
}
