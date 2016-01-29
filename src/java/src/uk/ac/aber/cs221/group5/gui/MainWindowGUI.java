package uk.ac.aber.cs221.group5.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import uk.ac.aber.cs221.group5.logic.DbStatus;
import uk.ac.aber.cs221.group5.logic.Task;
import uk.ac.aber.cs221.group5.logic.TaskList;
import uk.ac.aber.cs221.group5.logic.TaskStatuses;

/**
 * This Class handles all of the GUI aspects of the Main Window. It dislpays all Tasks and gives the
 * user control to open other windows, such as the Edit Window and View Elements Window.
 * 
 * @author Ben Dudley (bed19)
 * @author David Fairbrother (daf5)
 * @author Jonathan Englund (jee17)
 * @author Josh Doyle (jod32)
 * 
 * @version 1.0.0
 * @since 1.0.0
 * 
 * @see MainWindow.java
 * 
 */

public class MainWindowGUI {

   private JFrame frmMainWindow;
   private JTextField txtTaskName;
   private JTextField txtStatus;
   private JTextField txtAssigned;
   private JTextField txtStartDate;
   private JTextField txtEndDate;
   private JTable table;
   private JLabel lblConnStatus;

   private MainWindow main;

   /**
    * This method starts up the Main Window GUI. The Main Window GUI starts out
    * not being visible because it is called before the user has logged in and
    * setting the GUI to visible would give anyone access to the Task data.
    */
   public void launchWindow() {

      // Create inner class which implements runnable
      class SetVisible implements Runnable {
         private MainWindowGUI toSet;

         // Pass in previously created login window
         public SetVisible(MainWindowGUI callingWindow) {
            this.toSet = callingWindow;
         }

         // Set window visible
         public void run() {
            toSet.frmMainWindow.setVisible(false);
         }
      }
      EventQueue.invokeLater(new SetVisible(this));

   }

   /**
    * Create the application.
    * 
    */
   public MainWindowGUI(MainWindow mainWindow) {
      this.main = mainWindow;
      initialize();
   }

   /**
    * Initialize the contents of the frame. Draws the components of the 'Main'
    * Window.
    */
   private void initialize() {
      frmMainWindow = new JFrame();
      frmMainWindow.setFont(new Font("Dialog", Font.PLAIN, 14));
      frmMainWindow.addWindowFocusListener(new WindowFocusListener() {
         public void windowGainedFocus(WindowEvent arg0) {
            setConnStatusLabel(main.getConnStatus());
         }

         public void windowLostFocus(WindowEvent e) {
         }
      });
      frmMainWindow.setTitle("Main Window");
      frmMainWindow.setResizable(false);
      frmMainWindow.setBounds(100, 100, 926, 720);
      frmMainWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      frmMainWindow.addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent event) {
            main.destroyWindow();
            System.exit(0);
         }
      });
      GridBagLayout gridBagLayout = new GridBagLayout();
      gridBagLayout.columnWidths = new int[] { 559, 356, 0 };
      gridBagLayout.rowHeights = new int[] { 291, 0, 384, 0 };
      gridBagLayout.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
      gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
      frmMainWindow.getContentPane().setLayout(gridBagLayout);

      JPanel quickViewPanel = new JPanel();
      quickViewPanel
            .setBorder(new TitledBorder(null, "Quick View", TitledBorder.LEADING, TitledBorder.TOP, null, null));
      GridBagConstraints gbc_quickViewPanel = new GridBagConstraints();
      gbc_quickViewPanel.fill = GridBagConstraints.BOTH;
      gbc_quickViewPanel.insets = new Insets(0, 0, 5, 5);
      gbc_quickViewPanel.gridx = 0;
      gbc_quickViewPanel.gridy = 0;
      frmMainWindow.getContentPane().add(quickViewPanel, gbc_quickViewPanel);
      GridBagLayout gbl_quickViewPanel = new GridBagLayout();
      gbl_quickViewPanel.columnWidths = new int[] { 0, 0, 0, 0 };
      gbl_quickViewPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
      gbl_quickViewPanel.columnWeights = new double[] { 1.0, 0.0, 1.0, Double.MIN_VALUE };
      gbl_quickViewPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
      quickViewPanel.setLayout(gbl_quickViewPanel);

      JLabel lblTaskName = new JLabel("Task Name:");
      lblTaskName.setFont(new Font("Tahoma", Font.PLAIN, 18));
      GridBagConstraints gbc_lblTaskName = new GridBagConstraints();
      gbc_lblTaskName.anchor = GridBagConstraints.WEST;
      gbc_lblTaskName.insets = new Insets(0, 0, 5, 5);
      gbc_lblTaskName.gridx = 0;
      gbc_lblTaskName.gridy = 0;
      quickViewPanel.add(lblTaskName, gbc_lblTaskName);

      txtTaskName = new JTextField();
      txtTaskName.setFont(new Font("Tahoma", Font.PLAIN, 18));
      txtTaskName.setEditable(false);
      GridBagConstraints gbc_txtTaskName = new GridBagConstraints();
      gbc_txtTaskName.fill = GridBagConstraints.BOTH;
      gbc_txtTaskName.insets = new Insets(0, 0, 5, 0);
      gbc_txtTaskName.gridx = 2;
      gbc_txtTaskName.gridy = 0;
      quickViewPanel.add(txtTaskName, gbc_txtTaskName);
      txtTaskName.setColumns(10);

      JLabel lblStatus = new JLabel("Status:");
      lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 18));
      lblStatus.setHorizontalAlignment(SwingConstants.LEFT);
      GridBagConstraints gbc_lblStatus = new GridBagConstraints();
      gbc_lblStatus.anchor = GridBagConstraints.WEST;
      gbc_lblStatus.insets = new Insets(0, 0, 5, 5);
      gbc_lblStatus.gridx = 0;
      gbc_lblStatus.gridy = 1;
      quickViewPanel.add(lblStatus, gbc_lblStatus);

      txtStatus = new JTextField();
      txtStatus.setFont(new Font("Tahoma", Font.PLAIN, 18));
      txtStatus.setEditable(false);
      GridBagConstraints gbc_txtStatus = new GridBagConstraints();
      gbc_txtStatus.fill = GridBagConstraints.BOTH;
      gbc_txtStatus.insets = new Insets(0, 0, 5, 0);
      gbc_txtStatus.gridx = 2;
      gbc_txtStatus.gridy = 1;
      quickViewPanel.add(txtStatus, gbc_txtStatus);
      txtStatus.setColumns(10);

      JLabel lblAssignedTaskMembers = new JLabel("Assigned Task Member(s):");
      lblAssignedTaskMembers.setFont(new Font("Tahoma", Font.PLAIN, 18));
      GridBagConstraints gbc_lblAssignedTaskMembers = new GridBagConstraints();
      gbc_lblAssignedTaskMembers.anchor = GridBagConstraints.WEST;
      gbc_lblAssignedTaskMembers.insets = new Insets(0, 0, 5, 5);
      gbc_lblAssignedTaskMembers.gridx = 0;
      gbc_lblAssignedTaskMembers.gridy = 2;
      quickViewPanel.add(lblAssignedTaskMembers, gbc_lblAssignedTaskMembers);

      txtAssigned = new JTextField();
      txtAssigned.setFont(new Font("Tahoma", Font.PLAIN, 18));
      txtAssigned.setEditable(false);
      GridBagConstraints gbc_txtAssigned = new GridBagConstraints();
      gbc_txtAssigned.fill = GridBagConstraints.BOTH;
      gbc_txtAssigned.insets = new Insets(0, 0, 5, 0);
      gbc_txtAssigned.gridx = 2;
      gbc_txtAssigned.gridy = 2;
      quickViewPanel.add(txtAssigned, gbc_txtAssigned);
      txtAssigned.setColumns(10);

      JLabel lblStartDate = new JLabel("Start Date:");
      lblStartDate.setFont(new Font("Tahoma", Font.PLAIN, 18));
      GridBagConstraints gbc_lblStartDate = new GridBagConstraints();
      gbc_lblStartDate.anchor = GridBagConstraints.WEST;
      gbc_lblStartDate.insets = new Insets(0, 0, 5, 5);
      gbc_lblStartDate.gridx = 0;
      gbc_lblStartDate.gridy = 3;
      quickViewPanel.add(lblStartDate, gbc_lblStartDate);

      txtStartDate = new JTextField();
      txtStartDate.setFont(new Font("Tahoma", Font.PLAIN, 18));
      txtStartDate.setEditable(false);
      GridBagConstraints gbc_txtStartDate = new GridBagConstraints();
      gbc_txtStartDate.fill = GridBagConstraints.BOTH;
      gbc_txtStartDate.insets = new Insets(0, 0, 5, 0);
      gbc_txtStartDate.gridx = 2;
      gbc_txtStartDate.gridy = 3;
      quickViewPanel.add(txtStartDate, gbc_txtStartDate);
      txtStartDate.setColumns(10);

      JLabel lblExpectedEndDate = new JLabel("Expected End Date:");
      lblExpectedEndDate.setFont(new Font("Tahoma", Font.PLAIN, 18));
      GridBagConstraints gbc_lblExpectedEndDate = new GridBagConstraints();
      gbc_lblExpectedEndDate.anchor = GridBagConstraints.WEST;
      gbc_lblExpectedEndDate.insets = new Insets(0, 0, 5, 5);
      gbc_lblExpectedEndDate.gridx = 0;
      gbc_lblExpectedEndDate.gridy = 4;
      quickViewPanel.add(lblExpectedEndDate, gbc_lblExpectedEndDate);

      txtEndDate = new JTextField();
      txtEndDate.setFont(new Font("Tahoma", Font.PLAIN, 18));
      txtEndDate.setEditable(false);
      GridBagConstraints gbc_txtEndDate = new GridBagConstraints();
      gbc_txtEndDate.fill = GridBagConstraints.BOTH;
      gbc_txtEndDate.insets = new Insets(0, 0, 5, 0);
      gbc_txtEndDate.gridx = 2;
      gbc_txtEndDate.gridy = 4;
      quickViewPanel.add(txtEndDate, gbc_txtEndDate);
      txtEndDate.setColumns(10);

      JLabel lblTaskElements = new JLabel("Task Elements:");
      lblTaskElements.setFont(new Font("Tahoma", Font.PLAIN, 18));
      lblTaskElements.setHorizontalAlignment(SwingConstants.RIGHT);
      GridBagConstraints gbc_lblTaskElements = new GridBagConstraints();
      gbc_lblTaskElements.anchor = GridBagConstraints.WEST;
      gbc_lblTaskElements.insets = new Insets(0, 0, 5, 5);
      gbc_lblTaskElements.gridx = 0;
      gbc_lblTaskElements.gridy = 5;
      quickViewPanel.add(lblTaskElements, gbc_lblTaskElements);

      JButton btnViewElements = new JButton("View");
      btnViewElements.setFont(new Font("Tahoma", Font.PLAIN, 18));
      GridBagConstraints gbc_btnViewElements = new GridBagConstraints();
      gbc_btnViewElements.insets = new Insets(0, 0, 5, 0);
      gbc_btnViewElements.gridx = 2;
      gbc_btnViewElements.gridy = 5;
      quickViewPanel.add(btnViewElements, gbc_btnViewElements);
      btnViewElements.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow > -1) {
               try {
                  // Opens up the View Elements Window with the Element data
                  // from the Task that has been selected in the Table in Main
                  // Window GUI.
                  ViewElementsWindow viewElements = new ViewElementsWindow(table.getSelectedRow());
               } catch (IOException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
               }
            } else {
               // Error message if the user tries to view Elements without first
               // selecting a Task
               JOptionPane.showMessageDialog(null, "Select a Task to View Task Elements", "Selection Error",
                     JOptionPane.ERROR_MESSAGE);
            }
         }
      });

      JButton btnEdit = new JButton("Edit");
      btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 18));
      GridBagConstraints gbc_btnEdit = new GridBagConstraints();
      gbc_btnEdit.insets = new Insets(0, 0, 0, 5);
      gbc_btnEdit.gridx = 1;
      gbc_btnEdit.gridy = 7;
      quickViewPanel.add(btnEdit, gbc_btnEdit);
      btnEdit.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            int row = table.getSelectedRow();
            if (row > -1) {
               EditWindowGUI editWindow;

               try {
                  // Open the Edit Window with the data of the selected task
                  // from the Table in Main Window
                  editWindow = new EditWindowGUI(table.getSelectedRow(), main);
                  editWindow.setFields(txtTaskName.getText(), TaskStatuses.valueOf(txtStatus.getText()),
                        txtAssigned.getText(), txtStartDate.getText(), txtEndDate.getText());
               } catch (IOException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
               }

            } else {
               // Display an error if the user tries to open the Edit Window
               // without selecting a Task
               JOptionPane.showMessageDialog(null, "Select a Task to Edit", "Selection Error",
                     JOptionPane.ERROR_MESSAGE);
            }
         }
      });

      JPanel connSettingsPanel = new JPanel();
      connSettingsPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Connection Status",
            TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
      GridBagConstraints gbc_connSettingsPanel = new GridBagConstraints();
      gbc_connSettingsPanel.anchor = GridBagConstraints.NORTH;
      gbc_connSettingsPanel.fill = GridBagConstraints.HORIZONTAL;
      gbc_connSettingsPanel.insets = new Insets(0, 0, 5, 0);
      gbc_connSettingsPanel.gridx = 1;
      gbc_connSettingsPanel.gridy = 0;
      frmMainWindow.getContentPane().add(connSettingsPanel, gbc_connSettingsPanel);
      GridBagLayout gbl_connSettingsPanel = new GridBagLayout();
      gbl_connSettingsPanel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0 };
      gbl_connSettingsPanel.rowHeights = new int[] { 0, 0, 0 };
      gbl_connSettingsPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
      gbl_connSettingsPanel.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
      connSettingsPanel.setLayout(gbl_connSettingsPanel);

      JLabel lblNewLabel = new JLabel("Status:");
      lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
      GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
      gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
      gbc_lblNewLabel.gridx = 0;
      gbc_lblNewLabel.gridy = 0;
      connSettingsPanel.add(lblNewLabel, gbc_lblNewLabel);

      lblConnStatus = new JLabel("connStatus");
      lblConnStatus.setFont(new Font("Tahoma", Font.PLAIN, 18));
      GridBagConstraints gbc_lblConnStatus = new GridBagConstraints();
      gbc_lblConnStatus.insets = new Insets(0, 0, 5, 5);
      gbc_lblConnStatus.gridx = 2;
      gbc_lblConnStatus.gridy = 0;
      connSettingsPanel.add(lblConnStatus, gbc_lblConnStatus);

      JButton btnConnectionSettings = new JButton("Connection Settings");
      btnConnectionSettings.setFont(new Font("Tahoma", Font.PLAIN, 18));
      btnConnectionSettings.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            // Open the Connection Settings Window
            ConnSettingsWindow connSettings = new ConnSettingsWindow(true);
         }
      });
      GridBagConstraints gbc_btnConnectionSettings = new GridBagConstraints();
      gbc_btnConnectionSettings.insets = new Insets(0, 0, 0, 5);
      gbc_btnConnectionSettings.gridx = 2;
      gbc_btnConnectionSettings.gridy = 1;
      connSettingsPanel.add(btnConnectionSettings, gbc_btnConnectionSettings);

      JButton btnLogOut = new JButton("Log Out");
      btnLogOut.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent arg0) {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            // Confirm the user wants to exit
            int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Confirmation",
                  dialogButton);
            if (dialogResult == JOptionPane.YES_OPTION) {
               // Make sure we close connections to the Database before we exit
               main.destroyWindow();
               System.exit(0);
            }
         }
      });
      GridBagConstraints gbc_btnLogOut = new GridBagConstraints();
      gbc_btnLogOut.gridwidth = 2;
      gbc_btnLogOut.insets = new Insets(0, 0, 5, 0);
      gbc_btnLogOut.gridx = 0;
      gbc_btnLogOut.gridy = 1;
      frmMainWindow.getContentPane().add(btnLogOut, gbc_btnLogOut);

      JPanel panel = new JPanel();
      panel.setBorder(new TitledBorder(null, "Task Table", TitledBorder.LEADING, TitledBorder.TOP, null, null));
      GridBagConstraints gbc_panel = new GridBagConstraints();
      gbc_panel.fill = GridBagConstraints.BOTH;
      gbc_panel.gridwidth = 2;
      gbc_panel.gridx = 0;
      gbc_panel.gridy = 2;
      frmMainWindow.getContentPane().add(panel, gbc_panel);
      GridBagLayout gbl_panel = new GridBagLayout();
      gbl_panel.columnWidths = new int[] { 0, 0 };
      gbl_panel.rowHeights = new int[] { 0, 0 };
      gbl_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
      gbl_panel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
      panel.setLayout(gbl_panel);

      JScrollPane scrollPane = new JScrollPane();
      GridBagConstraints gbc_scrollPane = new GridBagConstraints();
      gbc_scrollPane.fill = GridBagConstraints.BOTH;
      gbc_scrollPane.gridx = 0;
      gbc_scrollPane.gridy = 0;
      panel.add(scrollPane, gbc_scrollPane);

      table = new JTable();
      table.setFont(new Font("Tahoma", Font.PLAIN, 18));
      table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      table.setModel(new DefaultTableModel(new Object[][] {},
            new String[] { "Task ID", "Task Name", "Status", "Assigned Members", "Start Date", "End Date" }) {
         Class[] columnTypes = new Class[] { String.class, String.class, String.class, String.class, String.class,
               String.class };

         public Class getColumnClass(int columnIndex) {
            return columnTypes[columnIndex];
         }
      });
      table.getColumnModel().getColumn(1).setPreferredWidth(324);
      table.getColumnModel().getColumn(3).setPreferredWidth(340);
      table.getColumnModel().getColumn(4).setPreferredWidth(97);
      table.getColumnModel().getColumn(5).setPreferredWidth(129);
      scrollPane.setViewportView(table);

      // Populate Quick View fields when a row from the table is selected
      table.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            int row = table.getSelectedRow();
            if (row > -1) {
               txtTaskName.setText((String) table.getModel().getValueAt(row, 1));
               txtStatus.setText((String) table.getModel().getValueAt(row, 2));
               txtAssigned.setText((String) table.getModel().getValueAt(row, 3));
               txtStartDate.setText((String) table.getModel().getValueAt(row, 4));
               txtEndDate.setText((String) table.getModel().getValueAt(row, 5));
            }
         }
      });
   }

   /**
    * Updates the label that shows the status of the Connection to the Database
    * 
    * @param status
    *           The last received status from the Database
    */
   public void setConnStatusLabel(DbStatus status) {
      lblConnStatus.setText(status.toString());
      table.repaint();
   }

   /**
    * This method adds all the Task Data to the Table in the Main Window GUI.
    * 
    * @param taskList
    *           The list of Tasks in memory to load into the Table
    * @throws NumberFormatException
    *            if the number of Tasks in the local Tasks save file cannot be
    *            read.
    * @throws IOException
    *            if the local Tasks save file cannot be read.
    */
   public void populateTable(TaskList taskList) throws NumberFormatException, IOException {

      DefaultTableModel model = (DefaultTableModel) (table.getModel());
      model.setRowCount(0);

      for (int loopCount = 0; loopCount < taskList.getListSize(); loopCount++) {
         Task task = taskList.getTask(loopCount);
         model.addRow(new Object[] { task.getID(), task.getName(), task.getStatus(), task.getMembers(), task.getStart(),
               task.getEnd() });
      }
      // Updates to show the contents of the table
      frmMainWindow.revalidate();
      frmMainWindow.repaint();
   }

   /**
    * Applies saved changes to the table of Tasks after a Task has been edited
    * 
    * @param rowNo
    *           The row of the Task that has been edited in the table
    * @param newStatus
    *           The enum value that the Task's status has been changed to
    */
   public void updateTable(int rowNo, String newStatus) {
      DefaultTableModel model = (DefaultTableModel) (table.getModel());
      model.setValueAt(newStatus, rowNo, 2);
   }

   /**
    * Sets the visibility of this Main Window GUI.
    * 
    * @param b
    *           The visibility state of the Window.
    */
   public void setVisible(boolean b) {
      this.frmMainWindow.setVisible(b);
   }

}