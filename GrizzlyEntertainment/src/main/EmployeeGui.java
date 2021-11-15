package main;
//worked on alongside kevaughn
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;



public class EmployeeGui implements ActionListener {

	public JFrame empFrame;
	private JButton viewRequests;
	private JButton viewEquipment;
	private JButton scheduleEquipment;
	private GridBagConstraints gbc;
	

	public EmployeeGui() {

		empFrame = new JFrame("Employee Dashboard");
	    viewRequests = new JButton("View Requests");
	    viewEquipment = new JButton("View Equipment");
	    scheduleEquipment = new JButton("Schedule Equipment");
	    
		empFrame.setSize(400, 400);
		empFrame.setLayout(new GridBagLayout());
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 6;
		gbc.ipadx = 50;
		gbc.ipady = 10;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 10, 11, 0);
		
		empFrame.add(viewRequests, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 10;
		gbc.gridwidth = 6;
		gbc.ipadx = 50;
		gbc.ipady = 10;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 10, 11, 0);
		empFrame.add(viewEquipment, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 16;
		gbc.gridwidth = 6;
		gbc.ipadx = 50;
		gbc.ipady = 10;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 10, 11, 0);
		empFrame.add(scheduleEquipment, gbc);
		
		empFrame.setVisible(true);
		empFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	/*public static void main(String[] args) {

		new EmployeeGui();

	}*/
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==viewEquipment)
		{
			
		}
		
	}
}
