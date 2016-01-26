package uk.ac.aber.cs221.group5.gui;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.JTextField;

import uk.ac.aber.cs221.group5.logic.DbStatus;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Window.Type;

public class ConnSettingsWindowGUI {

	private JFrame     frmConnectionSettings;
	private JTextField txtDbURL;
	private JTextField txtPortNo;
	private JTextField txtDbName;
	private JTextField txtUsername;
	private JTextField txtPassword;
	private JLabel     lblConnStatus;
	private JLabel     lblLastSynced;
	
	private static final String DB_CONFIG_PATH = "connSaveFile.txt";


	/**
	 * Create the application.
	 */
	public ConnSettingsWindowGUI() {
		initialize();
		frmConnectionSettings.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmConnectionSettings = new JFrame();
		frmConnectionSettings.setTitle("Connection Settings");
		frmConnectionSettings.setBounds(100, 100, 450, 460);
		frmConnectionSettings.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frmConnectionSettings.getContentPane().setLayout(gridBagLayout);
		
		lblConnStatus = new JLabel("connStatus");
		GridBagConstraints gbc_lblConnStatus = new GridBagConstraints();
		gbc_lblConnStatus.insets = new Insets(0, 0, 5, 5);
		gbc_lblConnStatus.gridwidth = 6;
		gbc_lblConnStatus.gridx = 0;
		gbc_lblConnStatus.gridy = 1;
		frmConnectionSettings.getContentPane().add(lblConnStatus, gbc_lblConnStatus);
		
		lblLastSynced = new JLabel("Last Synced mins Minutes Ago");
		GridBagConstraints gbc_lblLastSynced = new GridBagConstraints();
		gbc_lblLastSynced.insets = new Insets(0, 0, 5, 5);
		gbc_lblLastSynced.gridwidth = 6;
		gbc_lblLastSynced.gridx = 0;
		gbc_lblLastSynced.gridy = 2;
		frmConnectionSettings.getContentPane().add(lblLastSynced, gbc_lblLastSynced);
		
		JLabel lblDbURL = new JLabel("Database URL");
		GridBagConstraints gbc_lblDbURL = new GridBagConstraints();
		gbc_lblDbURL.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblDbURL.insets = new Insets(0, 0, 5, 5);
		gbc_lblDbURL.gridx = 2;
		gbc_lblDbURL.gridy = 4;
		frmConnectionSettings.getContentPane().add(lblDbURL, gbc_lblDbURL);
		
		txtDbURL = new JTextField();
		GridBagConstraints gbc_txtDbURL = new GridBagConstraints();
		gbc_txtDbURL.gridwidth = 2;
		gbc_txtDbURL.insets = new Insets(0, 0, 5, 5);
		gbc_txtDbURL.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDbURL.gridx = 4;
		gbc_txtDbURL.gridy = 4;
		frmConnectionSettings.getContentPane().add(txtDbURL, gbc_txtDbURL);
		txtDbURL.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.anchor = GridBagConstraints.EAST;
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.gridx = 2;
		gbc_lblUsername.gridy = 5;
		frmConnectionSettings.getContentPane().add(lblUsername, gbc_lblUsername);
		
		txtUsername = new JTextField();
		GridBagConstraints gbc_txtUsername = new GridBagConstraints();
		gbc_txtUsername.gridwidth = 2;
		gbc_txtUsername.insets = new Insets(0, 0, 5, 5);
		gbc_txtUsername.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUsername.gridx = 4;
		gbc_txtUsername.gridy = 5;
		frmConnectionSettings.getContentPane().add(txtUsername, gbc_txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.EAST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 2;
		gbc_lblPassword.gridy = 6;
		frmConnectionSettings.getContentPane().add(lblPassword, gbc_lblPassword);
		
		txtPassword = new JTextField();
		GridBagConstraints gbc_txtPassword = new GridBagConstraints();
		gbc_txtPassword.gridwidth = 2;
		gbc_txtPassword.insets = new Insets(0, 0, 5, 5);
		gbc_txtPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPassword.gridx = 4;
		gbc_txtPassword.gridy = 6;
		frmConnectionSettings.getContentPane().add(txtPassword, gbc_txtPassword);
		txtPassword.setColumns(10);
		
		JLabel lblDbName = new JLabel("Database Name");
		GridBagConstraints gbc_lblDbName = new GridBagConstraints();
		gbc_lblDbName.anchor = GridBagConstraints.EAST;
		gbc_lblDbName.insets = new Insets(0, 0, 5, 5);
		gbc_lblDbName.gridx = 2;
		gbc_lblDbName.gridy = 9;
		frmConnectionSettings.getContentPane().add(lblDbName, gbc_lblDbName);
		
		txtDbName = new JTextField();
		GridBagConstraints gbc_txtDbName = new GridBagConstraints();
		gbc_txtDbName.gridwidth = 2;
		gbc_txtDbName.insets = new Insets(0, 0, 5, 5);
		gbc_txtDbName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDbName.gridx = 4;
		gbc_txtDbName.gridy = 9;
		frmConnectionSettings.getContentPane().add(txtDbName, gbc_txtDbName);
		txtDbName.setColumns(10);
		
		JLabel lblPortNo = new JLabel("Port Number");
		GridBagConstraints gbc_lblPortNo = new GridBagConstraints();
		gbc_lblPortNo.anchor = GridBagConstraints.SOUTHEAST;
		gbc_lblPortNo.insets = new Insets(0, 0, 5, 5);
		gbc_lblPortNo.gridx = 2;
		gbc_lblPortNo.gridy = 10;
		frmConnectionSettings.getContentPane().add(lblPortNo, gbc_lblPortNo);
		
		txtPortNo = new JTextField();
		txtPortNo.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				txtPortNo.setText("");
			}
		});
		txtPortNo.setText("Leave Blank for Default");
		GridBagConstraints gbc_txtPortNo = new GridBagConstraints();
		gbc_txtPortNo.gridwidth = 2;
		gbc_txtPortNo.insets = new Insets(0, 0, 5, 5);
		gbc_txtPortNo.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPortNo.gridx = 4;
		gbc_txtPortNo.gridy = 10;
		frmConnectionSettings.getContentPane().add(txtPortNo, gbc_txtPortNo);
		txtPortNo.setColumns(10);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				frmConnectionSettings.dispose();	//Just close the window without saving any settings
			}
		});
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCancel.anchor = GridBagConstraints.SOUTH;
		gbc_btnCancel.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancel.gridx = 2;
		gbc_btnCancel.gridy = 12;
		frmConnectionSettings.getContentPane().add(btnCancel, gbc_btnCancel);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//A new ConnSettingsWindow Object that is used just for saving the connection settings. This is
				// created without spawning a new GUI because of the doesGUIExit method within the ConnSettingsWindow
				// Class.
				ConnSettingsWindow saveWindow = new ConnSettingsWindow();
				String dbName = txtDbName.getText();
				String username = txtUsername.getText();
				String password = txtPassword.getText();
				String dbURL = txtDbURL.getText();
				String portNo = txtPortNo.getText();
				if(dbName.equals("") || username.equals("") || password.equals("") || dbURL.equals("")){
					JOptionPane.showMessageDialog(null, "Make sure all fields are filled in.", "Connection Error", JOptionPane.ERROR_MESSAGE);
				}
				else{
				try {
					saveWindow.saveConnSettings(DB_CONFIG_PATH, dbName, username, password, dbURL, portNo);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					frmConnectionSettings.dispose();
					frmConnectionSettings = null;
					
				}
			}
		});
		GridBagConstraints gbc_btnConnect = new GridBagConstraints();
		gbc_btnConnect.insets = new Insets(0, 0, 0, 5);
		gbc_btnConnect.gridx = 5;
		gbc_btnConnect.gridy = 12;
		frmConnectionSettings.getContentPane().add(btnConnect, gbc_btnConnect);
	}
	
	public void setConnStatus(DbStatus status){
		MainWindow main = new MainWindow();
		lblConnStatus.setText(main.getConnStatus().toString());
		main.destroyWindow();
	}
	
	public void setLastSyncedLabel(int minutes){
		Integer integerMinutes = (Integer)(minutes);
		lblLastSynced.setText("Last Synced " + integerMinutes.toString() + "Minutes Ago");
	}
	
}
