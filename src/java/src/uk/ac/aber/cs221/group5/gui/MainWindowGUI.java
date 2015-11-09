/*
 * MainWindow.java 1.0 2015-11-09
 * 
 * Copyright (c) Aberystwyth University
 * All Rights Reserved
 */

package uk.ac.aber.cs221.group5.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.JTable;

public class MainWindowGUI {

	private JFrame frame;
	private JTextField txtTaskName;
	private JTextField txtStatus;
	private JTextField txtAssigned;
	private JTextField txtStartDate;
	private JTextField txtEndDate;
	private JTable table;

	/**
	 * Launch the application.
	 * 
	 * @author Joshua Doyle
	 * @version 1.0
	 * @since 1.0 Initial Development
	 */
	public void launchWindow() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
					MainWindowGUI window = new MainWindowGUI();
					window.frame.setVisible(true);
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @author Joshua Doyle
	 * @version 1.0
	 * @since 1.0 Initial Development
	 * @see #initialize()
	 */
	public MainWindowGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame. Draws the components of the 'Main' Window.
	 * 
	 * @author Joshua Doyle
	 * @version 1.0
	 * @since 1.0 Initial Development
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 926, 686);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JPanel quickViewPanel = new JPanel();
		quickViewPanel.setBorder(new TitledBorder(null, "Quick View", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_quickViewPanel = new GridBagConstraints();
		gbc_quickViewPanel.insets = new Insets(0, 0, 5, 5);
		gbc_quickViewPanel.fill = GridBagConstraints.BOTH;
		gbc_quickViewPanel.gridx = 0;
		gbc_quickViewPanel.gridy = 0;
		frame.getContentPane().add(quickViewPanel, gbc_quickViewPanel);
		GridBagLayout gbl_quickViewPanel = new GridBagLayout();
		gbl_quickViewPanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_quickViewPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_quickViewPanel.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_quickViewPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		quickViewPanel.setLayout(gbl_quickViewPanel);
		
		JLabel lblTaskName = new JLabel("Task Status:");
		GridBagConstraints gbc_lblTaskName = new GridBagConstraints();
		gbc_lblTaskName.anchor = GridBagConstraints.WEST;
		gbc_lblTaskName.insets = new Insets(0, 0, 5, 5);
		gbc_lblTaskName.gridx = 0;
		gbc_lblTaskName.gridy = 0;
		quickViewPanel.add(lblTaskName, gbc_lblTaskName);
		
		txtTaskName = new JTextField();
		txtTaskName.setEditable(false);
		GridBagConstraints gbc_txtTaskName = new GridBagConstraints();
		gbc_txtTaskName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTaskName.insets = new Insets(0, 0, 5, 0);
		gbc_txtTaskName.gridx = 2;
		gbc_txtTaskName.gridy = 0;
		quickViewPanel.add(txtTaskName, gbc_txtTaskName);
		txtTaskName.setColumns(10);
		
		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblStatus = new GridBagConstraints();
		gbc_lblStatus.anchor = GridBagConstraints.WEST;
		gbc_lblStatus.insets = new Insets(0, 0, 5, 5);
		gbc_lblStatus.gridx = 0;
		gbc_lblStatus.gridy = 1;
		quickViewPanel.add(lblStatus, gbc_lblStatus);
		
		txtStatus = new JTextField();
		txtStatus.setEditable(false);
		GridBagConstraints gbc_txtStatus = new GridBagConstraints();
		gbc_txtStatus.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtStatus.insets = new Insets(0, 0, 5, 0);
		gbc_txtStatus.gridx = 2;
		gbc_txtStatus.gridy = 1;
		quickViewPanel.add(txtStatus, gbc_txtStatus);
		txtStatus.setColumns(10);
		
		JLabel lblAssignedTaskMembers = new JLabel("Assigned Task Member(s):");
		GridBagConstraints gbc_lblAssignedTaskMembers = new GridBagConstraints();
		gbc_lblAssignedTaskMembers.anchor = GridBagConstraints.WEST;
		gbc_lblAssignedTaskMembers.insets = new Insets(0, 0, 5, 5);
		gbc_lblAssignedTaskMembers.gridx = 0;
		gbc_lblAssignedTaskMembers.gridy = 2;
		quickViewPanel.add(lblAssignedTaskMembers, gbc_lblAssignedTaskMembers);
		
		txtAssigned = new JTextField();
		txtAssigned.setEditable(false);
		GridBagConstraints gbc_txtAssigned = new GridBagConstraints();
		gbc_txtAssigned.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAssigned.insets = new Insets(0, 0, 5, 0);
		gbc_txtAssigned.gridx = 2;
		gbc_txtAssigned.gridy = 2;
		quickViewPanel.add(txtAssigned, gbc_txtAssigned);
		txtAssigned.setColumns(10);
		
		JLabel lblStartDate = new JLabel("Start Date:");
		GridBagConstraints gbc_lblStartDate = new GridBagConstraints();
		gbc_lblStartDate.anchor = GridBagConstraints.WEST;
		gbc_lblStartDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblStartDate.gridx = 0;
		gbc_lblStartDate.gridy = 3;
		quickViewPanel.add(lblStartDate, gbc_lblStartDate);
		
		txtStartDate = new JTextField();
		txtStartDate.setEditable(false);
		GridBagConstraints gbc_txtStartDate = new GridBagConstraints();
		gbc_txtStartDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtStartDate.insets = new Insets(0, 0, 5, 0);
		gbc_txtStartDate.gridx = 2;
		gbc_txtStartDate.gridy = 3;
		quickViewPanel.add(txtStartDate, gbc_txtStartDate);
		txtStartDate.setColumns(10);
		
		JLabel lblExpectedEndDate = new JLabel("Expected End Date:");
		GridBagConstraints gbc_lblExpectedEndDate = new GridBagConstraints();
		gbc_lblExpectedEndDate.anchor = GridBagConstraints.WEST;
		gbc_lblExpectedEndDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblExpectedEndDate.gridx = 0;
		gbc_lblExpectedEndDate.gridy = 4;
		quickViewPanel.add(lblExpectedEndDate, gbc_lblExpectedEndDate);
		
		txtEndDate = new JTextField();
		txtEndDate.setEditable(false);
		GridBagConstraints gbc_txtEndDate = new GridBagConstraints();
		gbc_txtEndDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEndDate.insets = new Insets(0, 0, 5, 0);
		gbc_txtEndDate.gridx = 2;
		gbc_txtEndDate.gridy = 4;
		quickViewPanel.add(txtEndDate, gbc_txtEndDate);
		txtEndDate.setColumns(10);
		
		JLabel lblTaskElements = new JLabel("Task Elements:");
		lblTaskElements.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblTaskElements = new GridBagConstraints();
		gbc_lblTaskElements.anchor = GridBagConstraints.WEST;
		gbc_lblTaskElements.insets = new Insets(0, 0, 5, 5);
		gbc_lblTaskElements.gridx = 0;
		gbc_lblTaskElements.gridy = 5;
		quickViewPanel.add(lblTaskElements, gbc_lblTaskElements);
		
		JTextPane txtElements = new JTextPane();
		txtElements.setEditable(false);
		GridBagConstraints gbc_txtElements = new GridBagConstraints();
		gbc_txtElements.gridheight = 2;
		gbc_txtElements.insets = new Insets(0, 0, 5, 0);
		gbc_txtElements.fill = GridBagConstraints.BOTH;
		gbc_txtElements.gridx = 2;
		gbc_txtElements.gridy = 5;
		quickViewPanel.add(txtElements, gbc_txtElements);
		
		JButton btnNewButton = new JButton("<");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.EAST;
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 7;
		quickViewPanel.add(btnNewButton, gbc_btnNewButton);
		
		JButton btnEdit = new JButton("Edit");
		GridBagConstraints gbc_btnEdit = new GridBagConstraints();
		gbc_btnEdit.insets = new Insets(0, 0, 0, 5);
		gbc_btnEdit.gridx = 1;
		gbc_btnEdit.gridy = 7;
		quickViewPanel.add(btnEdit, gbc_btnEdit);
		
		JButton button = new JButton(">");
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.anchor = GridBagConstraints.WEST;
		gbc_button.gridx = 2;
		gbc_button.gridy = 7;
		quickViewPanel.add(button, gbc_button);
		
		JPanel connSettingsPanel = new JPanel();
		connSettingsPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Connection Status", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_connSettingsPanel = new GridBagConstraints();
		gbc_connSettingsPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_connSettingsPanel.insets = new Insets(0, 0, 5, 0);
		gbc_connSettingsPanel.anchor = GridBagConstraints.NORTH;
		gbc_connSettingsPanel.gridx = 1;
		gbc_connSettingsPanel.gridy = 0;
		frame.getContentPane().add(connSettingsPanel, gbc_connSettingsPanel);
		GridBagLayout gbl_connSettingsPanel = new GridBagLayout();
		gbl_connSettingsPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_connSettingsPanel.rowHeights = new int[]{0, 0, 0};
		gbl_connSettingsPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_connSettingsPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		connSettingsPanel.setLayout(gbl_connSettingsPanel);
		
		JLabel lblNewLabel = new JLabel("Status:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		connSettingsPanel.add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel lblConnstatus = new JLabel("connStatus");
		GridBagConstraints gbc_lblConnstatus = new GridBagConstraints();
		gbc_lblConnstatus.insets = new Insets(0, 0, 5, 5);
		gbc_lblConnstatus.gridx = 2;
		gbc_lblConnstatus.gridy = 0;
		connSettingsPanel.add(lblConnstatus, gbc_lblConnstatus);
		
		JButton btnConnectionSettings = new JButton("Connection Settings");
		GridBagConstraints gbc_btnConnectionSettings = new GridBagConstraints();
		gbc_btnConnectionSettings.insets = new Insets(0, 0, 0, 5);
		gbc_btnConnectionSettings.gridx = 2;
		gbc_btnConnectionSettings.gridy = 1;
		connSettingsPanel.add(btnConnectionSettings, gbc_btnConnectionSettings);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Task Table", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 2;
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		frame.getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		table = new JTable();
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 0;
		gbc_table.gridy = 0;
		panel.add(table, gbc_table);
	}

}
