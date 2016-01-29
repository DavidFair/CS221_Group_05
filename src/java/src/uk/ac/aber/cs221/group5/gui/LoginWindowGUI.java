package uk.ac.aber.cs221.group5.gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import uk.ac.aber.cs221.group5.logic.DbStatus;
import uk.ac.aber.cs221.group5.logic.MemberList;

/**
 * This Class handles all of the GUI aspects of logging in to TaskerCLI. This includes displaying the
 * Login Window GUI and updating all Components on that table.
 * 
 * @author Ben Dudley (bed19)
 * @author David Fairbrother (daf5)
 * @author Jonathan Englund (jee17)
 * @author Josh Doyle (jod32)
 * 
 * @version 1.0.0
 * @since 1.0.0
 * @see MainWindow.java
 */

public class LoginWindowGUI {

   private JFrame frmLogIn;
   private JTextField txtEmailField;
   private JLabel lblConnStatus;
   private MemberList memberList;

   /**
    * Constructor for the LoginWindowGUI Object. This calls the initialize
    * method, which displays the GUI for the Login Window, and sets up a
    * TaskList for this Object.
    * 
    * @see initialize
    */
   public LoginWindowGUI() {
      initialize();
      memberList = new MemberList();
   }

   /**
    * Creates a new thread for the login window and sets that to visible through
    * the event queue
    * 
    */
   public void launchWindow() {

      // Create inner class which implements runnable
      class SetVisible implements Runnable {
         private LoginWindowGUI toSet;

         // Pass in previously created login window
         public SetVisible(LoginWindowGUI callingWindow) {
            this.toSet = callingWindow;
         }

         // Run window
         public void run() {
            toSet.frmLogIn.setVisible(true);
         }
      }
      EventQueue.invokeLater(new SetVisible(this));
   }

   /**
    * Updates which members are allowed to login in based on the MemberList receivingList
    * @param receivingList
    * 			the updated list of users who are allowed to Login
    */
public void passMemberList(MemberList receivingList) {
      for (int memberCount = 0; memberCount < receivingList.getLength(); memberCount++) {
         memberList.addMember(receivingList.getMember(memberCount));
      }
   }

   /**
    * Initialize the contents of the Frame. This positions all components in the
    * Frame and displays the window.
    */
   private void initialize() {
      frmLogIn = new JFrame();
      frmLogIn.setTitle("Log In");
      frmLogIn.setBounds(100, 100, 298, 360);
      frmLogIn.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      frmLogIn.addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent event) {
            MainWindow main = new MainWindow();
            main.destroyWindow();
            System.exit(0);
         }
      });
      GridBagLayout gridBagLayout = new GridBagLayout();
      gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0, 0 };
      gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
      gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
      gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
      frmLogIn.getContentPane().setLayout(gridBagLayout);

      JLabel lblSpacer2 = new JLabel(" ");
      GridBagConstraints gbc_lblSpacer2 = new GridBagConstraints();
      gbc_lblSpacer2.insets = new Insets(0, 0, 5, 5);
      gbc_lblSpacer2.gridx = 3;
      gbc_lblSpacer2.gridy = 0;
      frmLogIn.getContentPane().add(lblSpacer2, gbc_lblSpacer2);

      JLabel lblSpacer3 = new JLabel(" ");
      GridBagConstraints gbc_lblSpacer3 = new GridBagConstraints();
      gbc_lblSpacer3.insets = new Insets(0, 0, 5, 5);
      gbc_lblSpacer3.gridx = 0;
      gbc_lblSpacer3.gridy = 1;
      frmLogIn.getContentPane().add(lblSpacer3, gbc_lblSpacer3);

      JLabel lblEmail = new JLabel("Email:");
      lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 18));
      GridBagConstraints gbc_lblEmail = new GridBagConstraints();
      gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
      gbc_lblEmail.gridx = 1;
      gbc_lblEmail.gridy = 1;
      frmLogIn.getContentPane().add(lblEmail, gbc_lblEmail);

      txtEmailField = new JTextField();
      txtEmailField.setFont(new Font("Tahoma", Font.PLAIN, 18));
      GridBagConstraints gbc_txtEmailField = new GridBagConstraints();
      gbc_txtEmailField.gridwidth = 2;
      gbc_txtEmailField.insets = new Insets(0, 0, 5, 5);
      gbc_txtEmailField.anchor = GridBagConstraints.NORTH;
      gbc_txtEmailField.fill = GridBagConstraints.HORIZONTAL;
      gbc_txtEmailField.gridx = 2;
      gbc_txtEmailField.gridy = 1;
      frmLogIn.getContentPane().add(txtEmailField, gbc_txtEmailField);
      txtEmailField.setColumns(10);

      JButton btnLogin = new JButton("Log In");
      btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 18));
      btnLogin.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            String enteredEmail = txtEmailField.getText();

            if (enteredEmail == "") {
               // This evaluates True if the user did not enter anything into
               // the email field
               JOptionPane.showMessageDialog(null, "Error: Login Failed - Please Enter an email address");
               return;
            }

            String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(enteredEmail);
            if (matcher.matches() == true) {
               if (memberList.memberExists(enteredEmail)) {
                  // This for loop searches through all the Frames and shows the
                  // Main Window when the user has been logged in.
                  for (Frame frame : JFrame.getFrames()) {
                     if (frame.getTitle().equals("Main Window")) {
                        frame.setVisible(true);
                     }
                  }
                  frmLogIn.dispose();
               } else {
                  JOptionPane.showMessageDialog(null, "Error: Login Failed - Check your email was entered correctly",
                        "InfoBox: Login Error", JOptionPane.ERROR_MESSAGE);
               }
            } else {
               JOptionPane.showMessageDialog(null, "Error: Login Failed - Invalid Email Entered",
                     "InfoBox: Login Error", JOptionPane.ERROR_MESSAGE);
            }
         }
      });
      GridBagConstraints gbc_btnLogin = new GridBagConstraints();
      gbc_btnLogin.gridwidth = 5;
      gbc_btnLogin.insets = new Insets(0, 0, 5, 0);
      gbc_btnLogin.gridx = 0;
      gbc_btnLogin.gridy = 3;
      frmLogIn.getContentPane().add(btnLogin, gbc_btnLogin);

      JButton btnConnectionSettins = new JButton("Connection Settings");
      btnConnectionSettins.setFont(new Font("Tahoma", Font.PLAIN, 18));
      btnConnectionSettins.addMouseListener(new MouseAdapter() {
         @Override
         // This event is triggered when the user clicks the 'Connection
         // Settings' window
         public void mouseClicked(MouseEvent arg0) {
            ConnSettingsWindow connSettings = new ConnSettingsWindow(true);
         }
      });

      JLabel lblSpacer = new JLabel(" ");
      GridBagConstraints gbc_lblSpacer = new GridBagConstraints();
      gbc_lblSpacer.gridwidth = 15;
      gbc_lblSpacer.insets = new Insets(0, 0, 5, 5);
      gbc_lblSpacer.gridx = 0;
      gbc_lblSpacer.gridy = 4;
      frmLogIn.getContentPane().add(lblSpacer, gbc_lblSpacer);

      lblConnStatus = new JLabel("connStatus");
      lblConnStatus.setFont(new Font("Tahoma", Font.PLAIN, 18));
      GridBagConstraints gbc_lblConnStatus = new GridBagConstraints();
      gbc_lblConnStatus.insets = new Insets(0, 0, 5, 0);
      gbc_lblConnStatus.gridwidth = 15;
      gbc_lblConnStatus.gridx = 0;
      gbc_lblConnStatus.gridy = 5;
      frmLogIn.getContentPane().add(lblConnStatus, gbc_lblConnStatus);
      GridBagConstraints gbc_btnConnectionSettins = new GridBagConstraints();
      gbc_btnConnectionSettins.gridwidth = 11;
      gbc_btnConnectionSettins.insets = new Insets(0, 0, 5, 0);
      gbc_btnConnectionSettins.gridx = 0;
      gbc_btnConnectionSettins.gridy = 6;
      frmLogIn.getContentPane().add(btnConnectionSettins, gbc_btnConnectionSettins);

      JButton btnCancel = new JButton("Cancel");
      btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 18));
      GridBagConstraints gbc_btnCancel = new GridBagConstraints();
      gbc_btnCancel.insets = new Insets(0, 0, 5, 0);
      gbc_btnCancel.gridwidth = 8;
      gbc_btnCancel.gridx = 0;
      gbc_btnCancel.gridy = 7;
      frmLogIn.getContentPane().add(btnCancel, gbc_btnCancel);
      btnCancel.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            System.exit(0); // Terminates the program when the user clicks
                            // 'Cancel' before logging in
         }
      });
   }

   /**
    * This method updates the Connection Status label on the Login Window with
    * the Status value from the Main Window Object that was passed through the
    * LoginWindow Object.
    * 
    * @param newStatus
    *           The latest status from the Database
    */
   public void setConnStatusLabel(DbStatus newStatus) {
      lblConnStatus.setText(newStatus.toString());
   }

}
