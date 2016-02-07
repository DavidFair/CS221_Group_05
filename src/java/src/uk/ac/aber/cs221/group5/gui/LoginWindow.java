/**
 * This Class holds methods used for logging in to TaskerCLI. It is also responsible for creating the GUI
 * for logging in.
 * 
 * @author Ben Dudley (bed19)
 * @author David Fairbrother (daf5)
 * @author Jonathan Englund (jee17)
 * @author Josh Doyle (jod32)
 * 
 * @version 1.0
 * @since 1.0
 * 
 * @see MainWindow
 * @see LoginWindowGUI
 */

package uk.ac.aber.cs221.group5.gui;

import javax.swing.JOptionPane;

import uk.ac.aber.cs221.group5.logic.DbStatus;
import uk.ac.aber.cs221.group5.logic.MemberList;

/**
 * Initialize the login window and provides swing implementation
 * 
 * @author Ben Dudley (bed19)
 * @author David Fairbrother (daf5)
 * @author Jonathan Englund (jee17)
 * @author Josh Doyle (jod32)
 * @version 1.0.0
 * @since 1.0.0
 * @see LoginWindowGUI.java
 * 
 */

public class LoginWindow {

   private LoginWindowGUI childWindow;
   private MemberList memberList = new MemberList();

   /**
    * The constructor for a LoginWindow Object
    */
   public LoginWindow() {
      // Initiate common window functions
      super();
   }

   /**
    * This method is called by MainWindow and is used to pass the MemberList
    * held by the MainWindow Object to the LoginWindowGUI to compare the email
    * address that gets entered by the user in the LoginWindowGUI with the email
    * addresses in the MemberList
    * 
    * @param recievingList
    *           The MemberList being passed on to the LoginWindowGUI
    */
   public void passMemberList(MemberList recievingList) {
      for (int memberCount = 0; memberCount < recievingList.getLength(); memberCount++) {
         memberList.addMember(recievingList.getMember(memberCount));
      }
   }

   /**
    * This method creates a LoginWindowGUI Object, which spawns a Login Window
    * GUI on the screen.
    * 
    * @see LoginWindowGUI
    */

   public void createWindow() {
      // Create new childWindow for super to work on
      childWindow = new LoginWindowGUI();
      // Ask parent to setup window for us and pass
      // this class's methods for it to work on
      childWindow.passMemberList(memberList);

      callWindowLaunch();

   }

   /**
    * This method calls the method in the LoginWindowGUI Object that displays
    * the GUI Window on the screen
    */
   public void callWindowLaunch() {
      childWindow.launchWindow();
   }

   /**
    * Displays a standardised warning dialogue pop-up.
    * 
    * @param warnText
    *           The warning text to display.
    */
   public void displayWarning(String warnText) {
      JOptionPane.showMessageDialog(null, warnText, "Warning", JOptionPane.WARNING_MESSAGE);

   }

   /**
    * This method is used to pass the Connection Status from the Main Window
    * Object, which interfaces with the Database, to the LoginWindowGUI Object
    * which displays the Connection Status.
    * 
    * @param newStatus
    *           The last-received Connection Status from the Database
    */
   public void setLabelGUI(DbStatus newStatus) {
      this.childWindow.setConnStatusLabel(newStatus);
   }

}
