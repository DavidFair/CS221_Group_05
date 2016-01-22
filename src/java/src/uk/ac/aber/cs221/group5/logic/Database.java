package uk.ac.aber.cs221.group5.logic;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Timer;
import java.util.TimerTask;



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
	
	private String URL;
	private String portNo;
	private String dbName;
	private String dbUsername;
	private String dbPassword;
	private Connection connection;
	
	private Timer connTimer;
	private Timer latencyTimer;
	
	private DbStatus currentStatus;
	
	private String usersPath;
	
	//How often the DB attempts to update program information
	private static final int REFRESH_SEC_DELAY = 60;
	
	/**
	 * Creates a new database object and loads JDBC into memory
	 * @param usersFilePath The location to save and load users to
	 */
	public Database(String usersFilePath){
		//Leave blank so default port is used
		portNo = "";
		connTimer = null;
		currentStatus = DbStatus.DISCONNECTED;
		
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
		
		usersPath = usersFilePath;
		
	}
	
	/**
	 * Saves a list of all user names to a local file
	 * @param filePath Path of file to be saved
	 * @param allUsers A MemberList containing all users to be saved
	 */
	public void saveUserName(String filePath, MemberList allUsers){
		//TODO implement save user name in DB
	}
	
	
	
	
	public boolean connect(){
		//XXX Remove when config reading is implemented
		//Currently hardcoded until a method which reads from config calls connect
		return connect("db.dcs.aber.ac.uk", "", "csgpadm_5", "906BnQjD", "csgp_5_15_16");
	}

	
	
	
	public boolean connect(String hostName, String portNo, String dbUser, String dbPass, String dbName){
		dbUsername = dbUser;
		dbPassword = dbPass;
		
		//Create appropriate connection string
		final String urlPrepend = "jdbc:mysql://";
		//Append connection parameters - such as automatically reconnecting
		final String urlAppend = "?autoReconnect=true";
		
		this.URL = urlPrepend + hostName + portNo + "/" + dbName + urlAppend;
		
		try {
			connection = DriverManager.getConnection(URL, dbUsername, dbPassword);
		} catch (SQLException e) {
			System.err.println("Could not establish connection to DB in connect method");
			//For use debugging
			//Thread.dumpStack();
			System.err.println("Full connection string was: " + this.URL);
			
			//Reset state
			currentStatus = DbStatus.DISCONNECTED;
			return false;
		}
		
		currentStatus = DbStatus.CONNECTED;
		return true;
	}
	
	
	
	public void closeDbConn(){
		
		if (connTimer != null){
			connTimer.cancel();
			connTimer = null;
		}

		
		if (connection != null){
			try {
				connection.close();
			} catch (SQLException e) {
				System.err.println("Could not close JDBC connection");
				//For debugging
				//e.printStackTrace();
				
				//Exit as we cannot close JDBC which means something is wrong
				System.exit(100);
				
			}
		}
		
		//Release current connection
		connection = null;
		
		//Set status
		currentStatus = DbStatus.DISCONNECTED;
		
	}
	
	
	
	public DbStatus getConnStatus(){
		return currentStatus;
	}
	
	
	
	
	public Task[] getTasks(String username){
		String templateQuery;
		if (username != ""){
			templateQuery = "SELECT * FROM `tbl_tasks` WHERE TaskOwner='" + username + "';";
		} else {
			templateQuery = "SELECT * FROM `tbl_tasks`";
		}
		
		
		Thread sqlExec = new Thread(new Runnable() {
			
			public void run() {
				ResultSet tasksSet = executeSqlStatement(templateQuery);
				
				if (tasksSet != null){
					Task[] tasksArray = resultSetToTaskArray(tasksSet);
					
					//TODO need to pass forward instead of back
				} else {
					throw new NullPointerException("Result set was null in getTasks");
				}
			}
		});

		//Create timer to refresh on success
		createRefreshTimer(REFRESH_SEC_DELAY, this );

		return null;
		
	}

	

	public MemberList getMembers(){
		
		ResultSet members;
		members = executeSqlStatement("Select * FROM tbl_users");
		
		if (members != null){
			MemberList createdList = resultSetToMemberList(members);
			saveUserName(usersPath, createdList);
			createRefreshTimer(REFRESH_SEC_DELAY, this);
			return createdList;
		} else {
			throw new NullPointerException("No resultset returned in getMembers");
		}
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
			statementToExec.close();
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
		try{
			sqlStatementObject = connection.createStatement();
		} catch (SQLException e) {
			System.err.println("Could not create SQL statement");
			//To use when debugging
			//Thread.dumpStack();
			
		}
		
		//Pass resultSet straight through to caller
		return executeSqlStatement(sqlStatementObject, query);
		
	}
	
	private Task[] resultSetToTaskArray(ResultSet toConvert){
		try {
			//Get last index for complete length
			toConvert.last();
			int numOfTasks = toConvert.getRow();
		
			Task[] taskArray = new Task[numOfTasks];
		
			toConvert.beforeFirst(); //Move back to beginning 
			int index = 0;
			
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
				taskArray[index] = newTask;
				index++;
			} //End of while loop
			return taskArray;
			
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
	
	private void createRefreshTimer(int seconds, Database database) {

		class RefreshTask extends TimerTask {

			@Override
			public void run() {
				// This will be run when timer fires
				// First check if we are connected and not busy

				if (database.getConnStatus() != DbStatus.CONNECTED) {
					// Give up
					return;
				}
				MemberList newUserList = database.getMembers();
				// Get all tasks
				Task[] newTasksList = database.getTasks("");
				System.out.println("Fired refresh");
				// TODO Call external method to update held state
			}

		}
		
		connTimer = new Timer();
		connTimer.schedule(new RefreshTask(), seconds * 1000);

	}
		
	
}
