package uk.ac.aber.cs221.group5.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.html.parser.Element;

import uk.ac.aber.cs221.group5.logic.Task;
import uk.ac.aber.cs221.group5.logic.TaskList;
import uk.ac.aber.cs221.group5.logic.TaskStatuses;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.Window.Type;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Font;

public class EditWindowGUI {

	private JFrame frmEditTask;
	private JTextField txtTaskName;
	private JComboBox cmbTaskStatus;
	private JTextField txtAssignedTaskMembers;
	private JTextField txtStartDate;
	private JTextField txtExpectedEndDate;
	private JTable table;
	private int rowNo;	//The index of the row selected in the Main Window table when this window was spawned
	
	private MainWindow main;
	private static final String TASK_SAVE_PATH = "taskSaveFile.txt";


	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public EditWindowGUI(int row, MainWindow mainWindow) throws IOException {
		this.rowNo = row;

		//Stop the auto-sync from firing while a Task is being Edited
		this.main = mainWindow;
		main.setAutoTimer(false);
		initialize();
		this.populateTable(rowNo);

	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmEditTask = new JFrame();
		frmEditTask.setTitle("Edit Task");
		frmEditTask.setType(Type.UTILITY);
		frmEditTask.setAlwaysOnTop(true);
		frmEditTask.setResizable(false);
		frmEditTask.setBounds(100, 100, 520, 581);
		frmEditTask.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frmEditTask.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				main.setAutoTimer(true);
				frmEditTask.dispose();
			}
		});
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		frmEditTask.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblTaskName = new JLabel("Task Name:");
		lblTaskName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTaskName.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblTaskName = new GridBagConstraints();
		gbc_lblTaskName.insets = new Insets(0, 0, 5, 5);
		gbc_lblTaskName.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblTaskName.gridx = 1;
		gbc_lblTaskName.gridy = 1;
		frmEditTask.getContentPane().add(lblTaskName, gbc_lblTaskName);
		
		txtTaskName = new JTextField();
		txtTaskName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtTaskName.setEditable(false);
		GridBagConstraints gbc_txtTaskName = new GridBagConstraints();
		gbc_txtTaskName.gridwidth = 2;
		gbc_txtTaskName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTaskName.insets = new Insets(0, 0, 5, 5);
		gbc_txtTaskName.gridx = 2;
		gbc_txtTaskName.gridy = 1;
		frmEditTask.getContentPane().add(txtTaskName, gbc_txtTaskName);
		txtTaskName.setColumns(10);
		
		JLabel lblTaskStatus = new JLabel("Task Status:");
		lblTaskStatus.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTaskStatus.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblTaskStatus = new GridBagConstraints();
		gbc_lblTaskStatus.anchor = GridBagConstraints.WEST;
		gbc_lblTaskStatus.insets = new Insets(0, 0, 5, 5);
		gbc_lblTaskStatus.gridx = 1;
		gbc_lblTaskStatus.gridy = 2;
		frmEditTask.getContentPane().add(lblTaskStatus, gbc_lblTaskStatus);
		
		cmbTaskStatus = new JComboBox();
		cmbTaskStatus.setFont(new Font("Tahoma", Font.PLAIN, 18));
		cmbTaskStatus.setModel(new DefaultComboBoxModel(new String[] {"Allocated", "Abandoned", "Completed"}));
		GridBagConstraints gbc_cmbTaskStatus = new GridBagConstraints();
		gbc_cmbTaskStatus.gridwidth = 2;
		gbc_cmbTaskStatus.insets = new Insets(0, 0, 5, 5);
		gbc_cmbTaskStatus.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbTaskStatus.gridx = 2;
		gbc_cmbTaskStatus.gridy = 2;
		frmEditTask.getContentPane().add(cmbTaskStatus, gbc_cmbTaskStatus);
		
		JLabel lblAssignedTaskMembers = new JLabel("Assigned Task Member(s):");
		lblAssignedTaskMembers.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblAssignedTaskMembers.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblAssignedTaskMembers = new GridBagConstraints();
		gbc_lblAssignedTaskMembers.anchor = GridBagConstraints.EAST;
		gbc_lblAssignedTaskMembers.insets = new Insets(0, 0, 5, 5);
		gbc_lblAssignedTaskMembers.gridx = 1;
		gbc_lblAssignedTaskMembers.gridy = 3;
		frmEditTask.getContentPane().add(lblAssignedTaskMembers, gbc_lblAssignedTaskMembers);
		
		txtAssignedTaskMembers = new JTextField();
		txtAssignedTaskMembers.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtAssignedTaskMembers.setEditable(false);
		GridBagConstraints gbc_txtAssignedTaskMembers = new GridBagConstraints();
		gbc_txtAssignedTaskMembers.gridwidth = 2;
		gbc_txtAssignedTaskMembers.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAssignedTaskMembers.insets = new Insets(0, 0, 5, 5);
		gbc_txtAssignedTaskMembers.gridx = 2;
		gbc_txtAssignedTaskMembers.gridy = 3;
		frmEditTask.getContentPane().add(txtAssignedTaskMembers, gbc_txtAssignedTaskMembers);
		txtAssignedTaskMembers.setColumns(10);
		
		JLabel lblStartDate = new JLabel("Start Date:");
		lblStartDate.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_lblStartDate = new GridBagConstraints();
		gbc_lblStartDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblStartDate.anchor = GridBagConstraints.WEST;
		gbc_lblStartDate.gridx = 1;
		gbc_lblStartDate.gridy = 4;
		frmEditTask.getContentPane().add(lblStartDate, gbc_lblStartDate);
		
		txtStartDate = new JTextField();
		txtStartDate.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtStartDate.setEditable(false);
		GridBagConstraints gbc_txtStartDate = new GridBagConstraints();
		gbc_txtStartDate.gridwidth = 2;
		gbc_txtStartDate.insets = new Insets(0, 0, 5, 5);
		gbc_txtStartDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtStartDate.gridx = 2;
		gbc_txtStartDate.gridy = 4;
		frmEditTask.getContentPane().add(txtStartDate, gbc_txtStartDate);
		txtStartDate.setColumns(10);
		
		JLabel lblExpectedEndDate = new JLabel("Expected End Date:");
		lblExpectedEndDate.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_lblExpectedEndDate = new GridBagConstraints();
		gbc_lblExpectedEndDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblExpectedEndDate.anchor = GridBagConstraints.WEST;
		gbc_lblExpectedEndDate.gridx = 1;
		gbc_lblExpectedEndDate.gridy = 5;
		frmEditTask.getContentPane().add(lblExpectedEndDate, gbc_lblExpectedEndDate);
		
		txtExpectedEndDate = new JTextField();
		txtExpectedEndDate.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtExpectedEndDate.setEditable(false);
		GridBagConstraints gbc_txtExpectedEndDate = new GridBagConstraints();
		gbc_txtExpectedEndDate.gridwidth = 2;
		gbc_txtExpectedEndDate.insets = new Insets(0, 0, 5, 5);
		gbc_txtExpectedEndDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtExpectedEndDate.gridx = 2;
		gbc_txtExpectedEndDate.gridy = 5;
		frmEditTask.getContentPane().add(txtExpectedEndDate, gbc_txtExpectedEndDate);
		txtExpectedEndDate.setColumns(10);
		
		JLabel lblTaskElements = new JLabel("Task Elements:");
		lblTaskElements.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_lblTaskElements = new GridBagConstraints();
		gbc_lblTaskElements.insets = new Insets(0, 0, 5, 5);
		gbc_lblTaskElements.anchor = GridBagConstraints.WEST;
		gbc_lblTaskElements.gridx = 1;
		gbc_lblTaskElements.gridy = 7;
		frmEditTask.getContentPane().add(lblTaskElements, gbc_lblTaskElements);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 7;
		frmEditTask.getContentPane().add(scrollPane, gbc_scrollPane);
		
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 18));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(table.getSelectedRow() > -1){
					
				}
				table.getModel().isCellEditable(table.getSelectedRow(), table.getSelectedColumn());
			}
		});
		table.setModel(new EditTableModel(new Object[][] {
		},
		new String[] {
			"Element Name", "Element Comment"
		}));
		scrollPane.setViewportView(table);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//MainWindow used to sync with database - want to avoid syncing with database during editing
				// so we avoid editing tasks that have been deleted and encountering concurrency issues.
				try{
					main.updateLocalFiles(TASK_SAVE_PATH);
				}catch(Exception e){
					main.displayError("Could not downlad Task data.", "Connection Error");
				}
				//Resume auto-sync
				main.setAutoTimer(true);
				frmEditTask.dispose();
			}
		});
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancel.gridx = 1;
		gbc_btnCancel.gridy = 8;
		frmEditTask.getContentPane().add(btnCancel, gbc_btnCancel);
		
		JButton btnSave = new JButton("Save");
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				try{
					main.updateLocalFiles(TASK_SAVE_PATH);
				}catch(Exception e){
					main.displayError("Could not download Task data.", "Connection Error");
				}
				//Send updated Element Comments to Database
				if(table.getValueAt(0, 0) == "No Elements"){
					
				}
				else{
					try {
						main.loadTasks(TASK_SAVE_PATH);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						main.displayError("Could not load task data", "Loading Error");
					}
					TaskList updatedTaskList = main.getTaskList();
					Task updatedTask = updatedTaskList.getTask(rowNo);
					for(int tableRow = 0; tableRow < table.getRowCount(); tableRow++){
						String updateComment = (String) table.getValueAt(tableRow, 1);
						updatedTask.getElement(tableRow).setComment(updateComment);
					}
					main.updateTask(updatedTask);
				}
				//Resume auto-sync
				main.setAutoTimer(true);
				frmEditTask.dispose();
			}
		});
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.insets = new Insets(0, 0, 0, 5);
		gbc_btnSave.gridx = 3;
		gbc_btnSave.gridy = 8;
		frmEditTask.getContentPane().add(btnSave, gbc_btnSave);
	}
	
	public void setFields(String name, TaskStatuses status, String assigned, String start, String end){
		txtTaskName.setText(name);
		txtAssignedTaskMembers.setText(assigned);
		cmbTaskStatus.setSelectedIndex(status.ordinal());
		txtAssignedTaskMembers.setText(assigned);
		txtStartDate.setText(start);
		txtExpectedEndDate.setText(end);
		frmEditTask.setVisible(true);
	}
	
	private void populateTable(int tableIndex) throws IOException{
		int selectionIndex;	//The index in the table that was selected in main window
		ArrayList<String[]> elements = new ArrayList<String[]>();
		
		try {
			main.loadTasks(TASK_SAVE_PATH);
		} catch (Exception e) {
			main.updateLocalFiles(TASK_SAVE_PATH);
			try {
				main.loadTasks(TASK_SAVE_PATH);
			} catch (Exception e1) {

				main.displayError("Could not show local tasks", "Error Loading");
			}
		}
		
		
		elements = main.getElements(tableIndex);
		
		for(String[] pair : elements){
			EditTableModel model = (EditTableModel) table.getModel();
			model.addRow(new Object[]{pair[0], pair[1]});
		}
		this.frmEditTask.repaint();
		
	}
	
	private void updateLocalFiles(){
		main.updateLocalFiles(TASK_SAVE_PATH);
	}
	
////INNER CLASSSES
	private class EditTableModel extends AbstractTableModel{
		private String[] headers = {"", ""};
		
		public EditTableModel(Object[][] objects, String[] strings) {
			this.headers = strings;
		}
		
		//This DefaultTableModel is used for general Table Model methods (getColumnCount etc.)
		DefaultTableModel model = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Element Name", "Element Comment"
				});
			
		@Override
		public int getColumnCount() {
			return model.getColumnCount();
		}

		public void addRow(Object[] objects) {
			model.addRow(objects);
			
		}

		@Override
		public int getRowCount() {
			return model.getRowCount();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return model.getValueAt(rowIndex, columnIndex);
		}
		
		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex){
			if(columnIndex == 1){
				return true;
			}
			return false;
		}
		
		@Override
		public String getColumnName(int column){
			return headers[column];
		}
		
	}
}
