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
	
	
	//Implementing the Default Constructor
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
		connection = DBConnector.getDatabaseConnection(); // DatabaseConnection is stored in variable connection
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
//Implementing the Getters and Setters
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

