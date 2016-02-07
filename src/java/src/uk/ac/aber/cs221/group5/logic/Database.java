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

import uk.ac.aber.cs221.group5.gui.MainWindow;
import uk.ac.aber.cs221.group5.logic.Task.Element;

/**
 * @(#)Database.java 0.1 2015-12-02
 * 
 * Copyright (c) 2015 Aberystwyth University
 * All rights reserved
 * 
 */

/**
 * 
 * Implements database connections, synchronizing and updating through JDBC
 * interface
 * 
 * @author Ben Dudley (bed19)
 * @author David Fairbrother (daf5)
 * @author Jonathan Englund (jee17)
 * @author Josh Doyle (jod32)
 * @version 1.0.0
 * @since 1.0.0
 * @see MainWindow.java
 *
 */
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

   private long connTime; // The system time when the Database last connected

   // How often the DB attempts to update program information
   private static final int REFRESH_SEC_DELAY = 60;
   private static final int SYNC_ALRT_DELAY = 1;

   private static final String PENDING_TASK_FILE = "pendingSaveFile.txt";

   /**
    * Creates a new database object and loads in the MYSQL JDBC driver.
    * 
    * @param usersFilePath
    *           The path to save the members list
    * @param parentWindow
    *           The window to display to and update
    */
   public Database(String usersFilePath, MainWindow parentWindow) {
      // Leave blank so default port is used
      this.dbPortNo = "";
      this.connTimer = null;
      this.currentStatus = DbStatus.DISCONNECTED;

      /* First load JDBC connector */
      // Try to load driver else exit with error code
      try {
         // This is recommended method in documentation
         // As it dynamically loads the class into memory at run time

         Class.forName("com.mysql.jdbc.Driver");

      } catch (ClassNotFoundException e) {
         System.err.println("Unable to load MYSQL JDBC implementation class");
         System.exit(1);
      }

      this.usersPath = usersFilePath;
      this.hostWindow = parentWindow;

   }

   /**
    * Reconnects to the database with the currently stored credentials
    */
   public void connect() {
      // Attempt to reconnect with existing db settings
      connect(dbURL, dbPortNo, dbUsername, dbPassword, dbName);
   }

   /**
    * Attempts a new connection to the database in a new thread, if this fails
    * it opens an error dialog window in the main window.
    * 
    * @param hostUrl
    *           URL to the database server
    * @param portNo
    *           Database server port number, can be left blank for default of
    *           3306
    * @param dbUser
    *           User name to login to database
    * @param dbPass
    *           Password to login to database
    * @param dbName
    *           Database name to select
    */
   public void connect(String hostUrl, String portNo, String dbUser, String dbPass, String dbName) {
      this.dbUsername = dbUser;
      this.dbPassword = dbPass;
      this.dbName = dbName;
      this.dbPortNo = portNo;
      this.dbURL = hostUrl;

      // Create appropriate connection string
      final String urlPrepend = "jdbc:mysql://";
      // Append connection parameters - such as automatically reconnecting
      final String urlAppend = "?autoReconnect=true";

      if (portNo == "") {
         this.dbPortNo = "3306";
      }
      String connectionUrl = urlPrepend + hostUrl + ":" + this.dbPortNo + "/" + this.dbName + urlAppend;

      class ConnectThread implements Runnable {

         private String url;
         private String uName;
         private String pWord;
         private Database parentDb;
         private MainWindow parentWindow;

         public ConnectThread(String url, String uName, String pWord, Database parentDB, MainWindow hostWindow) {
            this.url = url;
            this.uName = uName;
            this.pWord = pWord;
            this.parentDb = parentDB;
            this.parentWindow = hostWindow;
         }

         public void run() {
            try {
               dbConnection = DriverManager.getConnection(url, uName, pWord);
               parentDb.currentStatus = DbStatus.CONNECTED;

               // Now sync
               parentDb.getMembers();
               parentDb.getTasks();

            } catch (SQLException e) {
               parentWindow.displayError("Could not connect to database. " + "Please check connection settings"
                     + "\nError: " + e.getCause(), "Connection Error");

               // For use debugging
               // Thread.dumpStack();
               // System.err.println(e.getMessage());
               // System.err.println("Full connection string was: " + url);

               // Reset state
               parentDb.currentStatus = DbStatus.DISCONNECTED;
               hostWindow.setConnStatus(currentStatus);

            }

         }

      }

      this.connTime = System.currentTimeMillis();
      Thread connectDb = new Thread(new ConnectThread(connectionUrl, dbUser, dbPass, this, hostWindow));

      connectDb.start();

   }

   /**
    * Closes the database connection and cancels any timers then updates the
    * connection status to reflect changes
    */
   public void closeDbConn() {

      if (connTimer != null) {
         connTimer.cancel();
         connTimer = null;
      }

      if (latencyTimer != null) {
         latencyTimer.cancel();
         latencyTimer = null;
      }

      if (dbConnection != null) {
         try {
            dbConnection.close();
         } catch (SQLException e) {
            System.err.println("Could not close JDBC connection");
            // For debugging
            // e.printStackTrace();

            // Exit as we cannot close JDBC which means something is wrong
            System.exit(100);

         }
      }

      // Release current connection
      dbConnection = null;

      // Set status
      currentStatus = DbStatus.DISCONNECTED;

   }

   /**
    * Saves a tasks new status and all comments to database
    * 
    * @param updatedTask
    */
   public void updateDbTask(Task updatedTask) {

      class UpdateDb implements Runnable {

         Task taskObj;

         public UpdateDb(Task task) {
            this.taskObj = task;
         }

         public void run() {

            String status = taskObj.getStatus();
            int statusNum;

            // Convert back to num for db
            switch (status) {
            case "Abandoned":
               statusNum = 0;
               break;

            case "Allocated":
               statusNum = 1;
               break;

            case "Completed":
               statusNum = 2;
               break;

            default:
               throw new IndexOutOfBoundsException("Task status not recognised");
            }

            // Update task status on DB
            String query = "UPDATE `tbl_tasks` SET `Status`='" + statusNum + "' WHERE `TaskID`='" + updatedTask.getID()
                  + "';";
            try {
               executeSqlStatement(query);
            } catch (SQLException e) {
               hostWindow.writePendingTask(updatedTask);
            }

            // Next update all elements
            for (Element elementObj : taskObj.getAllElements()) {
               String index = elementObj.getIndex();

               query = "UPDATE `tbl_elements` SET `TaskComments`='" + elementObj.getComment() + "' WHERE `Index`='"
                     + index + "';";

               try {
                  executeSqlStatement(query);
               } catch (SQLException e) {
                  hostWindow.writePendingTask(updatedTask);
               }
            }
         }
      }

      Thread syncElement = new Thread(new UpdateDb(updatedTask));
      syncElement.start();

   }

   /**
    * This method saves a MemberList in a specified local file
    * 
    * @param filePath
    *           The location of the file where you want to write the information
    *           to
    * @param allUsers
    *           A list of users
    */

   public void saveUserName(String filePath, MemberList allUsers) {

      FileWriter fileWriter;

      try {
         fileWriter = new FileWriter(filePath);
         BufferedWriter write = new BufferedWriter(fileWriter);

         int numOfTasks = allUsers.getLength();
         write.write(numOfTasks + "\n");

         for (int loopCount = 0; loopCount < numOfTasks; loopCount++) {
            Members writeTask = allUsers.getMember(loopCount);
            write.write(writeTask.getEmail() + "\n");
            write.write(writeTask.getName() + "\n");
         }
         write.close();
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

   }

   /**
    * Starts Automatic Synchronisation
    * 
    * @see createRefreshTimer
    */
   public void startAutoSync() {
      createRefreshTimer(REFRESH_SEC_DELAY, this);
   }

   /**
    * Stops the Automatic Synchronisation
    */

   public void stopAutoSync() {

      if (connTimer == null) {
         return;
      }

      connTimer.cancel();
      connTimer = null;

   }

   /**
    * Returns the current status of the db connection
    * 
    * @return the current status of the connection
    */

   public DbStatus getConnStatus() {
      if (this.currentStatus.toString().equals("CONNECTED")) {
         // TODO Execute 'pending sync'

      }
      return currentStatus;
   }

   /**
    * Returns which window you are currently looking at
    * 
    * @return the currently displayed window as a MainWindow type
    */
   public MainWindow getHostWindow() {
      return this.hostWindow;
   }

   /**
    * This method calls all the tasks instead just tasks for a specific user
    */
   public void getTasks() {
      getTasks("");
   }

   /**
    * This method get the tasks for the specific user
    * 
    * @param username
    *           the name of the user that you want to get the tasks of
    */

   public void getTasks(String username) {

      System.out.println("Called getTasks");

      String taskQuery;
      if (username != "") {
         taskQuery = "SELECT * FROM `tbl_tasks` WHERE TaskOwner='" + username + "';";
      } else {
         taskQuery = "SELECT * FROM `tbl_tasks`";
      }

      class TaskSync implements Runnable {

         private Database dbObject;
         private String sqlQuery;

         public TaskSync(String query, Database parentDb) {
            this.sqlQuery = query;
            this.dbObject = parentDb;
         }

         public void run() {
            ResultSet tasksSet = executeSqlSelect(sqlQuery);

            if (tasksSet != null) {
               // Convert SQL result to Task List
               TaskList tasksList = resultSetToTaskList(tasksSet);

               // Update the task elements
               updateTaskElements(tasksList);

               // Update the copy held by the main window

               dbObject.getHostWindow().setTaskList(tasksList);

               currentStatus = DbStatus.CONNECTED;

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

      // Set status to sync
      currentStatus = DbStatus.SYNCHRONIZING;

      Thread sqlExec = new Thread(new TaskSync(taskQuery, this));
      sqlExec.start();

      // Setup latency timer to detect lag
      setupLatencyTimer();

      createRefreshTimer(REFRESH_SEC_DELAY, this);

   }

   /**
    * Displays all the users from the Database
    */

   public void getMembers() {

      System.out.println("Called getMembers");

      class MemberSync implements Runnable {

         private Database parentDB;

         public MemberSync(Database parentDatabase) {
            this.parentDB = parentDatabase;
         }

         public void run() {
            ResultSet members = executeSqlSelect("Select * FROM tbl_users");

            if (members != null) {
               MemberList newMemberList = resultSetToMemberList(members);

               parentDB.getHostWindow().setmemberList(newMemberList);

               parentDB.saveUserName(usersPath, newMemberList);

               currentStatus = DbStatus.CONNECTED;

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
      // Create new thread an exec it
      currentStatus = DbStatus.SYNCHRONIZING;
      Thread sqlExec = new Thread(new MemberSync(this));
      sqlExec.start();

      setupLatencyTimer();

      createRefreshTimer(REFRESH_SEC_DELAY, this);

   }

   /**
    * Returns the length of time you have been connected as a long
    * 
    * @return returns how long you have been connected to the db as a long
    */
   public long getConnTime() {
      return this.connTime;
   }

   /**
    * Sets the window you are currently looking as the host window for future
    * reference
    * 
    * @param newWindow
    *           The window that has been opened i.e. what you are currently
    *           looking at
    */

   public void setHostWindow(MainWindow newWindow) {
      this.hostWindow = newWindow;
   }

   /**
    * Takes a statement object and SQL query and executes the query. Returns the
    * query result as a ResultSet object
    * 
    * @param statementToExec
    *           The Statement object to use
    * @param query
    *           The SQL query to execute
    * @return ResultSet containing result. If it fails null.
    */
   private ResultSet executeSqlSelect(Statement statementToExec, String query) {

      ResultSet sqlOutput;

      try {
         sqlOutput = statementToExec.executeQuery(query);
      } catch (SQLException e) {
         System.err.println("Sql query failed. Query used was:");
         System.err.println(query);

         // Reset ResultSet to null
         sqlOutput = null;

         // For debugging
         // System.err.println("Stack trace");
         // Thread.dumpStack();
      }

      return sqlOutput;

   }

   private void executeSqlStatement(String statementString) throws SQLException {

      try {
         Statement statement = dbConnection.createStatement();
         statement.executeUpdate(statementString);
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         hostWindow.displayError("Error updating database, the error was :" + e.getCause(), "Error updating DB");
         hostWindow.displayWarning("Storing changes locally will attempt to upload on next connection");
         // Throw exception up a level
         throw e;
      }

   }

   /**
    * Executes SQL query passed in by the parameter and returns a ResultSet
    * containing the returned data from the database
    * 
    * @param query
    *           SQL query to execute on the database
    * @return ResultSet containing data if query succeeded. Null if it did not
    */
   private ResultSet executeSqlSelect(String query) {
      Statement sqlStatementObject = null;

      if (dbConnection == null) {
         return null;
      }

      try {
         sqlStatementObject = dbConnection.createStatement();
      } catch (SQLException e) {
         System.err.println("Could not create SQL statement");
         // To use when debugging
         // Thread.dumpStack();

      }

      // Pass resultSet straight through to caller
      return executeSqlSelect(sqlStatementObject, query);

   }

   private TaskList resultSetToTaskList(ResultSet toConvert) {
      TaskList result = new TaskList();

      try {
         while (toConvert.next()) {
            String id = toConvert.getString("TaskID");
            String taskName = toConvert.getString("TaskName");
            String startDate = toConvert.getString("StartDate");
            String endDate = toConvert.getString("EndDate");
            String owner = toConvert.getString("TaskOwner");
            int status = toConvert.getInt("Status");

            // Convert status to enum in Java
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
               // XXX Need to handle bad value for enum
               break;
            }

            // Create object and store
            Task newTask = new Task(id, taskName, startDate, endDate, owner, enumStatus);
            result.addTask(newTask);

         } // End of while loop
         return result;

      } catch (SQLException e) {
         // XXX Error handling
         System.err.println(e.getMessage());
         return null;

      }

   }

   private MemberList resultSetToMemberList(ResultSet toConvert) {
      MemberList newList = new MemberList();

      if (toConvert == null) {
         throw new NullPointerException("Passed null result set in resultSetToMemberList");
      }

      try {
         while (toConvert.next()) {
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

   private void updateTaskElements(TaskList allTasks) {

      int numOfTasks = allTasks.getListSize();
      for (int i = 0; i < numOfTasks; i++) {

         Task currentTask = allTasks.getTask(i);

         // Clear current task to avoid duplicates
         currentTask.clearAllElements();

         String sqlQuery = "SELECT * FROM tbl_elements WHERE TaskID = ";
         sqlQuery = sqlQuery + currentTask.getID();

         ResultSet elements = executeSqlSelect(sqlQuery);

         if (elements != null) {

            try {
               while (elements.next()) {
                  // Iterate over all comments
                  int index = elements.getInt("Index");
                  String elementDesc = elements.getString("TaskDesc");
                  String elementComments = elements.getString("TaskComments");
                  String elementId = elements.getString("Index");

                  if (elementComments == null) {
                     elementComments = "";
                  }

                  // Remove pipes and chars from element desc and
                  // comments
                  elementDesc = elementDesc.replace(",", "");
                  elementComments = elementComments.replace("|", "");

                  currentTask.addElement(elementDesc, elementComments, elementId);
               }

               elements.close();
            } catch (SQLException e) {
               System.err.println(e.getMessage());
            }
         } else {
            throw new NullPointerException("Result set was null in getMembers");
         }

      }

      // Finished getting task elements

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

   private void setupLatencyTimer() {

      if (latencyTimer == null) {
         latencyTimer = new Timer();
      }

      class LatencyAlert extends TimerTask {

         private Database dbObj;

         public LatencyAlert(Database parentDb) {
            this.dbObj = parentDb;
         }

         @Override
         public void run() {
            dbObj.getHostWindow().setConnStatus(currentStatus);

            if (currentStatus == DbStatus.SYNCHRONIZING) {
               // Still syncing create timer to check again
               setupLatencyTimer();
            } else if (currentStatus == DbStatus.CONNECTED) {
               // Finished cancel any and all timers
               latencyTimer.cancel();
               latencyTimer = null;
            } else if (currentStatus == DbStatus.DISCONNECTED) {
               // TODO disconnected logic
            }

         }

      }

      latencyTimer.schedule(new LatencyAlert(this), SYNC_ALRT_DELAY * 1000);

   }

   private Task returnPending(Task returnTask) {
      return returnTask;
   }

}
