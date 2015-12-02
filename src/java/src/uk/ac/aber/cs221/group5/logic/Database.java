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
	
	private String dbUsername;
	private String dbPassword;
	
	private Connection connection;
	
	private DelayTimer connTimer;
	private DelayTimer latencyTimer;
	
	public Database(){
		//TODO implement DB constructor
		
		/* First load JDBC connector */
		//Try to load driver else exit with error code
		try 
		{
			//This is recommended method in documentation
			//As it dynamically loads the class into memory at run time
			Class.forName("com.mysql.jdbc.driver");
			
		} catch (ClassNotFoundException e){
			System.err.println("Unable to load MYSQL JDBC implementation class");
			System.exit(1);
		}
		
		
	}
	
	public void saveUserName(String filePath){
		//TODO implement save user name in DB
	}
	
	public boolean connect(String URL, String portNo){
		
		try {
			connection = DriverManager.getConnection(URL, dbUsername, dbPassword);
		} catch (SQLException e) {
			System.err.println("Could not connect to DB in connect method");
			Thread.dumpStack();
		}
		
		return false;
	}
	
	public boolean closeDbConn(){
		//TODO implement connection closing
		return false;
	}
	
	//TODO change return type to ConnStatus enum
	public void getConnStatus(){
		//TODO implement getConnStatus
	}
	
	public Task[] getTasks(String username){
		//TODO method getTasks logic
		
		Statement sqlStatementObject = getNewSqlStatement();
		
		String query = "SQL STATEMENT TO GET TASKS AND ELEMENTS FROM USERNAME";
		
		executeSQLStatement(sqlStatementObject, query);

		//TODO process data from getTasks SQL query
		
		return null;

	}
	
	public Members[] getMembers(){
		//TODO method getMembers logic
		
		Statement sqlStatementObject = getNewSqlStatement();
		
		String query = "SQL STATEMENT TO GET ALL USERS IN DB";
		
		executeSQLStatement(sqlStatementObject, query);
		
		//TODO process data from getMembers SQL query
		return null;
	}
	
	private void executeSQLStatement(Statement statementToExec, String query){
		try{
			ResultSet sqlOutput = statementToExec.executeQuery(query);
		} catch (SQLException e){
			System.err.println("Sql query failed. Query used was:");
			System.err.println();
			System.err.println(query);
			System.err.println();
			System.err.println("Stack trace");
			Thread.dumpStack();
		}

	}
	
	private Statement getNewSqlStatement(){
		Statement sqlStatementObject = null;
		try{
			sqlStatementObject = connection.createStatement();
		} catch (SQLException e) {
			System.err.println("Could not create SQL statement in ");
			Thread.dumpStack();
		}
		
		return sqlStatementObject;
	}
	
	
}
