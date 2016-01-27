package uk.ac.aber.cs221.group5.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Frame;

import javax.swing.JTextField;

import uk.ac.aber.cs221.group5.logic.MemberList;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Font;

public class LoginWindowGUI {

	private JFrame frmLogIn;
	private JTextField txtEmailField;
	private MemberList memberList;
	
	/**
	 * Create the application.
	 */
	public LoginWindowGUI() {
		initialize();
		memberList = new MemberList();
	}
	
	/**
	 * Creates a new thread for the login window and
	 * sets that to visible through the event queue
	 * @throws Exception
	 */
	public void launchWindow() throws Exception {
		
		//Create inner class which implements runnable
		class SetVisible implements Runnable {
			private LoginWindowGUI toSet;
			
			//Pass in previously created login window
			public SetVisible(LoginWindowGUI callingWindow) {
				this.toSet = callingWindow;
			}
			
			//Run window
			public void run() {
				toSet.frmLogIn.setVisible(true);
			}
		}
		EventQueue.invokeLater(new SetVisible(this));
	}
	
	public void passMemberList(MemberList recievingList){
		for(int memberCount = 0; memberCount < recievingList.getLength(); memberCount++){
			memberList.addMember(recievingList.getMember(memberCount));
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {		
		frmLogIn = new JFrame();
		frmLogIn.setTitle("Log In");
		frmLogIn.setResizable(false);
		frmLogIn.setBounds(100, 100, 296, 233);
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
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frmLogIn.getContentPane().setLayout(gridBagLayout);
		
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
			public void actionPerformed(ActionEvent arg0){
				String enteredEmail = txtEmailField.getText();
				
				if (enteredEmail == ""){
					JOptionPane.showMessageDialog(null, "Error: Login Failed - Please Enter an email address");
					return;
				}
				
				String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(enteredEmail);
				if(matcher.matches()==true) {
					if(memberList.memberExists(enteredEmail)){
						for(Frame frame : JFrame.getFrames()){
							if(frame.getTitle().equals("Main Window")){
								frame.setVisible(true);
							}
						}
						frmLogIn.dispose();
					}else {
						JOptionPane.showMessageDialog(null, "Error: Login Failed - Check your email was entered correctly", "InfoBox: Login Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "Error: Login Failed - Invalid Email Entered", "InfoBox: Login Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		GridBagConstraints gbc_btnLogin = new GridBagConstraints();
		gbc_btnLogin.gridwidth = 5;
		gbc_btnLogin.insets = new Insets(0, 0, 5, 5);
		gbc_btnLogin.gridx = 0;
		gbc_btnLogin.gridy = 3;
		frmLogIn.getContentPane().add(btnLogin, gbc_btnLogin);
		
		JButton btnConnectionSettins = new JButton("Connection Settings");
		btnConnectionSettins.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnConnectionSettins.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				ConnSettingsWindow connSettings = new ConnSettingsWindow(true);
			}
		});
		GridBagConstraints gbc_btnConnectionSettins = new GridBagConstraints();
		gbc_btnConnectionSettins.gridwidth = 5;
		gbc_btnConnectionSettins.insets = new Insets(0, 0, 5, 5);
		gbc_btnConnectionSettins.gridx = 0;
		gbc_btnConnectionSettins.gridy = 4;
		frmLogIn.getContentPane().add(btnConnectionSettins, gbc_btnConnectionSettins);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.gridwidth = 5;
		gbc_btnCancel.gridx = 0;
		gbc_btnCancel.gridy = 5;
		frmLogIn.getContentPane().add(btnCancel, gbc_btnCancel);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0){
				System.exit(0);	//Terminates the program
			}
		});
		
		
	}
	
}
