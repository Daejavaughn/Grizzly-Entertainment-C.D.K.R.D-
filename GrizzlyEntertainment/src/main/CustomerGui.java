package main;
//worked on alongside kevaughn
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;



public class CustomerGui implements ActionListener {

	public JFrame cusFrame;
	private JButton rentEquipment;
	private JButton viewTransactions;
	private GridBagConstraints gbc;
	

	public CustomerGui() {

		cusFrame = new JFrame("Customer Dashboard");
	    rentEquipment = new JButton("Rent Equipment");
	    viewTransactions = new JButton("View Transactions");
	    
		cusFrame.setSize(400, 400);
		cusFrame.setLayout(new GridBagLayout());
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 6;
		gbc.ipadx = 50;
		gbc.ipady = 10;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 10, 11, 0);
		
		cusFrame.add(rentEquipment, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 10;
		gbc.gridwidth = 6;
		gbc.ipadx = 50;
		gbc.ipady = 10;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 10, 11, 0);
		cusFrame.add(viewTransactions, gbc);
		
		
		cusFrame.setVisible(true);
		cusFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	/*public static void main(String[] args) {

		new EmployeeGui();

	}*/
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==viewTransactions)
		{
			
		}
		
	}
}
