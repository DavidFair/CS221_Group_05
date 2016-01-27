package uk.ac.aber.cs221.group5.gui;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JScrollPane;
import java.awt.GridBagConstraints;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import uk.ac.aber.cs221.group5.logic.Task;
import uk.ac.aber.cs221.group5.logic.Task.Element;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Font;

public class ViewElementsWindowGUI {

	private JFrame frmViewTaskElements;
	private JTable table;
	private int selectedRow;

	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public ViewElementsWindowGUI(int tableRow) throws IOException {
		this.selectedRow = tableRow;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		frmViewTaskElements = new JFrame();
		frmViewTaskElements.setResizable(false);
		frmViewTaskElements.setTitle("View Task Elements");
		frmViewTaskElements.setBounds(100, 100, 540, 647);
		frmViewTaskElements.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frmViewTaskElements.getContentPane().setLayout(gridBagLayout);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 9;
		gbc_scrollPane.gridheight = 18;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		frmViewTaskElements.getContentPane().add(scrollPane, gbc_scrollPane);
		
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 18));
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
					"Element Name", "Element Comment"
			}
		) {
			Class[] columnTypes = new Class[]{
				String.class, String.class
			};
			public Class getColumnClass(int columnIndex){
				return columnTypes[columnIndex];
			}
		});
		scrollPane.setViewportView(table);
		
		JButton btnClose = new JButton("Close");
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frmViewTaskElements.dispose();
			}
		});
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		GridBagConstraints gbc_btnClose = new GridBagConstraints();
		gbc_btnClose.insets = new Insets(0, 0, 0, 5);
		gbc_btnClose.gridx = 1;
		gbc_btnClose.gridy = 18;
		frmViewTaskElements.getContentPane().add(btnClose, gbc_btnClose);
		this.populateTable(selectedRow);
		frmViewTaskElements.setVisible(true);
	}
	
	private void populateTable(int tableIndex) throws IOException{
		MainWindow main = new MainWindow();	//Used for loading elements and will not spawn a GUI
		main.loadTasks("taskSaveFile.txt");
		Task displayTask = main.getTaskList().getTask(selectedRow);
		for(int tableRow = 0; tableRow < displayTask.getNumElements(); tableRow++){
			Element displayElement = displayTask.getElement(tableRow);
			DefaultTableModel model = (DefaultTableModel)(table.getModel());
			model.addRow(new Object[]{displayElement.getName(), displayElement.getComment()});
		}
		table.repaint();
	}
}
