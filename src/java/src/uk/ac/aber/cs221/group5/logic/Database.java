package uk.ac.aber.cs221.group5.logic;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


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
	
	private DelayTimer connTimer;
	private DelayTimer latencyTimer;
	
	private DbStatus currentStatus;
	
	public Database(){
		//TODO implement DB constructor
		//Leave blank so default port is used
		portNo = "";
		
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
		
		
	}
	
	public void saveUserName(String filePath, MemberList allUsers){
		//TODO implement save user name in DB
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
		if (connection != null){
			try {
				connection.close();
			} catch (SQLException e) {
				System.err.println("Could not close JDBC connection");
				//For debugging
				//e.printStackTrace();
				
				//XXX should we call terminate if a JDBC connection cannot be closed
				
			}
		}
		
		//Release current connection
		connection = null;
		
		//Set status
		currentStatus = DbStatus.DISCONNECTED;
		
		//TODO reset any held timers
	}
	
	
	public DbStatus getConnStatus(){
		return currentStatus;
	}
	
	public Task[] getTasks(String username){
		//TODO method getTasks logic

		executeSqlStatement("//TODO QUERY WHICH GETS TASKS FROM DB WHERE USERNAME");

		//TODO process data from getTasks SQL query
		
		return null;

	}
	

	public Members[] getMembers(){
		//TODO method getMembers logic
		
		executeSqlStatement("//TODO SQL QUERY WHICH GETS ALL MEMBERS");
		
		//TODO process data from getMembers SQL query
		return null;
	}
	
	/**
	 * Takes a statement object and SQL query and executes the query. 
	 * Returns the query result as a ResultSet object
	 * @param statementToExec The Statement object to use 
	 * @param query The SQL query to execute
	 * @return ResultSet containing result. If it fails null.
	 */
	private ResultSet executeSQLStatement(Statement statementToExec, String query){
		
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
		try{
			sqlStatementObject = connection.createStatement();
		} catch (SQLException e) {
			System.err.println("Could not create SQL statement in ");
			//To use when debugging
			//Thread.dumpStack();
			
		}
		
		//Pass resultSet straight through to caller
		return executeSQLStatement(sqlStatementObject, query);
		
	}
	
	
}
