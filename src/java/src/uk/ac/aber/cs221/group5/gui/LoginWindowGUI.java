package uk.ac.aber.cs221.group5.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

public class LoginWindowGUI {

	private JFrame frame;
	private JTextField emailField;


	public void launchWindow() throws Exception {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
					LoginWindowGUI window = new LoginWindowGUI();
					window.frame.setVisible(true);
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginWindowGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 300, 150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel emailFieldPanel = new JPanel();
		emailFieldPanel.setBounds(0, 0, 284, 50);
		frame.getContentPane().add(emailFieldPanel);
		emailFieldPanel.setLayout(new MigLayout("", "[][][grow]", "[][][]"));
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 13));
		emailFieldPanel.add(lblEmail, "cell 0 1");
		
		emailField = new JTextField();
		emailFieldPanel.add(emailField, "cell 2 1,growx");
		emailField.setColumns(10);
	
		
		JPanel loginButtonsPanel = new JPanel();
		loginButtonsPanel.setBounds(0, 44, 284, 33);
		frame.getContentPane().add(loginButtonsPanel);
		loginButtonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnLogin = new JButton("Login");
		
		
		loginButtonsPanel.add(btnLogin);
		
		JPanel connButtonPanel = new JPanel();
		connButtonPanel.setBounds(0, 77, 284, 33);
		frame.getContentPane().add(connButtonPanel);
		connButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnConnSet = new JButton("Connection Settings");
		
		btnConnSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		connButtonPanel.add(btnConnSet);
		
		//Set preferred size so btnLogin is same size as btnConnSet
		btnLogin.setPreferredSize(btnConnSet.getPreferredSize());
		
		
	}
}
