//Created by Courteney. Uploaded by Daejavaughn due to issues on my end

package models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JInternalFrame;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

import factories.DBConnector;

public class Customer implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Connection connection = null;
	private Statement stmt = null;
	private ResultSet result = null;
	private int numOfRowsAffected = 0;
	
	private String CustID;
	private String EquipType;
	private Date RentalDate;
	private String Availability;
	private double Quotation;
	private JLabel customerid;
	private JLabel equipment;
	private JLabel rental_date;
	private JLabel available;
	private JLabel quota;
	private JFrame frame;
	private JButton submit;
	
	
	
	public Customer(String custID, String equipType, java.sql.Date rentalDate, String availability, double quotation) {
		super();
		CustID = custID;
		EquipType = equipType;
		RentalDate = rentalDate;
		Availability = availability;
		Quotation = quotation;
	}

	private static final Logger Logger=LogManager.getLogger(Customer.class);

	public Customer()
	{
		connection = DBConnector.getDatabaseConnection();
	}
	public void createGUI()
	{
		frame = new JFrame("Customer Rental Form");
		frame.setSize(500, 500);
		customerid = new JLabel("Cutomer ID ");
		JTextField custID = new JTextField(20);
		equipment = new JLabel("Equipment Type ");
		JTextField equipType = new JTextField(20);
		rental_date = new JLabel("Rental Date ");
		JTextField rentalDate = new JTextField(20);
		available = new JLabel("Avalibility ");
		JTextField availability = new JTextField(20);
		quota = new JLabel("Quotation ");
		JTextField quotation = new JTextField(20);
		submit = new JButton("submit");
		
		customerid.setBounds(15, 20, 90, 30);
		custID.setBounds(70, 25, 150, 25);
		equipment.setBounds(15, 60, 90, 30);
		equipType.setBounds(70, 65, 150, 25);
		rental_date.setBounds(15, 100, 90, 30);
		rentalDate.setBounds(70, 105, 150, 25);
		available.setBounds(15, 140, 90, 30);
		availability.setBounds(70, 145, 150, 25);
		quota.setBounds(15, 180, 90, 30);
		quotation.setBounds(70, 185, 150, 25);
		submit.setBounds(110, 220, 80, 30);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.add(customerid);
		panel.add(custID);
		panel.add(equipment);
		panel.add(equipType);
		panel.add(rental_date);
		panel.add(rentalDate);
		panel.add(available);
		panel.add(availability);
		panel.add(quota);
		panel.add(quotation);
		panel.add(submit);
		
		frame.add(panel);
		frame.setVisible(true);
		
		custID = custID.getText();
		equipType = equipType.getText();
		rentalDate = rentalDate.getText();
		availability = availability.getText();
		quotation = quotation.getText();
		submit.addActionListener(this);
	}
	

	public void create(String CustID, String EquipType, Date RentalDate, String Availability, double Quotation )
	{
		String insertSql = "INSERT INTO grizzly.customer (CustID,EquipType,RentalDate,Availability,Quotation) VALUES"
				+ "('" + CustID + "', '"+EquipType+"',  '"+RentalDate+"' , '"+Availability+"','"+Quotation+"')";
		
		try 
		{
			stmt = connection.createStatement();
			numOfRowsAffected = stmt.executeUpdate(insertSql);
			if (numOfRowsAffected ==1)
			{
				JOptionPane.showMessageDialog(null, "Customer record created","Customer Creation",
						JOptionPane.INFORMATION_MESSAGE);
				Logger.info("New customer added to the system \n");
			}
			else if(numOfRowsAffected != 1)
			{
				Logger.warn("Customer was not added sucessfully try again \n");
			}
		}
		
		catch (SQLException e)
		{
			System.out.println("SQL Exception thrown" + e.getMessage());
			Logger.error(" Error SQL Exception thrown" + e.getMessage());
		}
	}
	
	public void readAll()
	{
		String selectSql = "SELECT * FROM grizzly.customer WHERE 1=1";
		try
		{
			stmt = connection.createStatement();
			
			result = stmt.executeQuery(selectSql);
			while(result.next())
			{
				String CustID = result.getString("CustID");
				String EquipType = result.getString("EquipType");
				Date RentalDate = result.getDate("RentalDate");
				String Availability = result.getString("Availability");
				double Quotation = result.getDouble("Quotation");
				
				
				System.out.println("CustomerID:" + CustID + "\tEquipmentType" + EquipType
						+ "\tRentalDate is:" + RentalDate + "\tAvailability:" + Availability
						+ "The estimated cost is:" + Quotation);
				Logger.info("Customer Database Accessed \n");
			}
		}
		
		catch (SQLException e)
		{
			System.out.println("SQL Exception thrown" + e.getMessage());
		}
	}
	
	public void update(String CustID, String EquipType, Date RentalDate, String Availability, double Quotation)
	{
		String updateSql = "UPDATE grizzly.customer SET Availability = '" + Availability + "' WHERE CustomerID = " + CustID;
		try
		{
			stmt = connection.createStatement();
			
			numOfRowsAffected = stmt.executeUpdate(updateSql);
			if (numOfRowsAffected ==1)
			{
				JOptionPane.showMessageDialog(null, "Customer record updated","Customer record update",
						JOptionPane.INFORMATION_MESSAGE);
				Logger.info("Customer Database changed, record updated \n");
			}
			else {
				Logger.warn("Customer File not updated \n");
			}
		}
		
		catch(SQLException e)
		{
			System.out.println("SQL Exception thrown" + e.getMessage());
		}
	}
	
	public void delete(String CustID)
	{
		String deleteSql = "DELETE grizzly.customer=  WHERE CustID = " + CustID;
		try
		{
			stmt = connection.createStatement();
			
			numOfRowsAffected = stmt.executeUpdate(deleteSql);
			if (numOfRowsAffected ==1)
			{
				JOptionPane.showMessageDialog(null, "Customer record updated","Customer record update",
						JOptionPane.INFORMATION_MESSAGE);
				Logger.info(" Customer File deleted \n");
			}
		}
		
		catch(SQLException e)
		{
			System.out.println("SQL Exception thrown" + e.getMessage());
		}
	}

	public String getCustID() {
		return CustID;
	}

	public void setCustID(String custID) {
		CustID = custID;
	}

	public String getEquipType() {
		return EquipType;
	}

	public void setEquipType(String equipType) {
		EquipType = equipType;
	}

	public Date getRentalDate() {
		return RentalDate;
	}

	public void setRentalDate(Date rentalDate) {
		RentalDate = rentalDate;
	}

	public String getAvailability() {
		return Availability;
	}

	public void setAvailability(String availability) {
		Availability = availability;
	}

	public double getQuotation() {
		return Quotation;
	}

	public void setQuotation(double quotation) {
		Quotation = quotation;
	}

	@Override
	public String toString() {
		return "Customer [CustID=" + CustID + ", EquipType=" + EquipType + ", RentalDate=" + RentalDate
				+ ", Availability=" + Availability + ", Quotation=" + Quotation + "]";
	}
	
	
	
}


