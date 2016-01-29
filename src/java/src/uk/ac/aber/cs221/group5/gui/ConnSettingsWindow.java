package uk.ac.aber.cs221.group5.gui;

import java.awt.Frame;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.AnnotatedArrayType;

import org.junit.experimental.theories.Theories;
import org.junit.internal.MethodSorter;

import com.mysql.jdbc.Wrapper;

import uk.ac.aber.cs221.group5.logic.DbStatus;

/**
 * Provides a wrapper around the Connection Settings Window and has methods to
 * manipulate the Connection Settings Window or initialize it
 * 
 * @author Ben Dudley (bed19)
 * @author David Fairbrother (daf5)
 * @author Jonathan Englund (jee17)
 * @author Josh Doyle (jod32)
 * @version 1.0.0
 * @since 1.0.0
 * @see ConnSettingsWindowGUI
 *
 */
public class ConnSettingsWindow {

   private ConnSettingsWindowGUI childWindow;

   /**
    * Creates a new Connection Window object and if flag set true also creates
    * the GUI object and displays it
    * 
    * @param createNewWindow
    *           True to create and display GUI. False for only object
    */
   public ConnSettingsWindow(boolean createNewWindow) {
      if (createNewWindow) {
         this.createWindow();
      }
   }

   /**
    * Creates the GUI window and displays it
    */
   private void createWindow() {
      childWindow = new ConnSettingsWindowGUI();
      this.setConnStatus(MainWindow.getConnStatus());
      this.setLastSyncLabel(MainWindow.getConnTime());
   }

   /**
    * Saves connection settings into a file at specified path for later reading
    * to load database configuration
    * 
    * @param filename
    *           Path to save the config file at
    * @param dbName
    *           The name of the database
    * @param username
    *           The user name of the database
    * @param password
    *           The password of the database
    * @param dbUrl
    *           The url of the database
    * @param portNo
    *           The port number of the database can be blank or
    *           "Leave Blank for Default" for 3306
    * @throws IOException
    *            Throws an IO exception on an error
    */
   public void saveConnSettings(String filename, String dbName, String username, String password, String dbUrl,
         String portNo) throws IOException {

      FileWriter fileWriter = new FileWriter(filename);
      BufferedWriter writer = new BufferedWriter(fileWriter);

      if (portNo.equals("Leave Blank for Default") || portNo.equals("")) {
         portNo = "3306"; // TODO Give default value
      }

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

   // Checks all frames to see if the GUI for this Class exits. Enables us to
   // create a new ConnSettingsWindow Object
   // without creating a new GUI window.
   private boolean doesGUIExist() {
      for (Frame frame : Frame.getFrames()) {
         if (frame.getTitle().equals("Connection Settings")) {
            return true;
         }
      }
      return false;
   }

   /**
    * Updates the connection status on the connection window
    * 
    * @param status
    *           New status to display
    */
   public void setConnStatus(DbStatus status) {
      this.childWindow.setConnStatus(status);
   }

   /**
    * Calculates the time since CLI was last synced with the Database
    * 
    * @param connTime
    *           The time, in millis, when the Database was last synced
    */
   public void setLastSyncLabel(long connTime) {
      long currentTime = System.currentTimeMillis();
      long timeDifferenceMillis = currentTime - connTime;
      long timeDifferenceSeconds = 0;
      final int conversionFactor = 600;
      // Convert to millis to minutes

      ///// INTEGER DIVISION////
      timeDifferenceSeconds = timeDifferenceMillis / conversionFactor;

      if (connTime == 0) {
         timeDifferenceMillis = 0;
      }

      // Display result
      this.childWindow.setLastSyncedLabel(timeDifferenceSeconds);
   }

}
