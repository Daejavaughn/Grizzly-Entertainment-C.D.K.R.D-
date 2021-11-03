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

public class Transaction implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Connection connection = null;
	private Statement stmt = null;
	private ResultSet result = null;
	private int numOfRowsAffected = 0;
	
	private int id;
	private String CustID;
	private String EquipType;
	private Date RentalDate;
	private String Availability;
	private double Cost;
	
	private static final Logger Logger=LogManager.getLogger(Transaction.class);
	
	public Transaction()
	{
		connection = DBConnector.getDatabaseConnection();
	}
	
	public void create(String TransactionID, String CustID, String EquipType, Date RentalDate, String Availability, double Cost )
	{
		String insertSql = "INSERT INTO grizzly.customer (TransactionID, CustidEquipType,RentalDate,Availability,Cost) VALUES"
				+ "('" + TransactionID + "', '"+CustID+"','"+EquipType+"',  '"+RentalDate+"' , '"+Availability+"','"+Cost+"')";
		
		try 
		{
			stmt = connection.createStatement();
			numOfRowsAffected = stmt.executeUpdate(insertSql);
			if (numOfRowsAffected ==1)
			{
				JOptionPane.showMessageDialog(null, "Transaction record created","Transaction Creation",
						JOptionPane.INFORMATION_MESSAGE);
				Logger.info("New transaction made \n");
			}
			else 
			{
				Logger.warn("Failed to create new transaction");
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
		String selectSql = "SELECT * FROM grizzly.transaction WHERE 1=1";
		try
		{
			stmt = connection.createStatement();
			
			result = stmt.executeQuery(selectSql);
			while(result.next())
			{
				String TransactionID = result.getString("TransactionID");
				String CustID = result.getString("CustID");
				String EquipType = result.getString("EquipType");
				Date RentalDate = result.getDate("RentalDate");
				String Availability = result.getString("Availability");
				double Quotation = result.getDouble("Quotation");
				
				
				System.out.println("TransactionID:" +TransactionID + "CustomerID:" + CustID + "\tEquipmentType" + EquipType
						+ "\tRentalDate is:" + RentalDate + "\tAvailability:" + Availability
						+ "The estimated cost is:" + Quotation);
				Logger.info("Transaction Table Accessed \n");
			}
		}
		
		catch (SQLException e)
		{
			System.out.println("SQL Exception thrown" + e.getMessage());
			Logger.error(" Error SQL Exception thrown" + e.getMessage());
		}
	}
	
	public void update(String TransactionID, String CustID, String EquipType, Date RentalDate, String Availability, double Quotation)
	{
		String updateSql = "UPDATE grizzly.transaction SET CustID = '" + CustID + "' WHERE TransactionID = " + TransactionID;
		try
		{
			stmt = connection.createStatement();
			
			numOfRowsAffected = stmt.executeUpdate(updateSql);
			if (numOfRowsAffected ==1)
			{
				JOptionPane.showMessageDialog(null, "Transaction record updated","Transaction record update",
						JOptionPane.INFORMATION_MESSAGE);
				Logger.info("Transaction Table Updated \n");
			}
			else 
			{
				Logger.warn("Failed to Update Record \n");
			}
		}
		
		catch(SQLException e)
		{
			System.out.println("SQL Exception thrown" + e.getMessage());
			Logger.error(" Error SQL Exception thrown" + e.getMessage());
		}
	}
	
	public void delete(String TransactionID)
	{
		String deleteSql = "DELETE grizzly.transaction=  WHERE TransactionID = " + TransactionID;
		try
		{
			stmt = connection.createStatement();
			
			numOfRowsAffected = stmt.executeUpdate(deleteSql);
			if (numOfRowsAffected ==1)
			{
				JOptionPane.showMessageDialog(null, "Transaction record updated","Transaction record update",
						JOptionPane.INFORMATION_MESSAGE);
				Logger.info("Transaction Record Deleted \n");
			}
		}
		
		catch(SQLException e)
		{
			System.out.println("SQL Exception thrown" + e.getMessage());
			Logger.error(" Error SQL Exception thrown" + e.getMessage());
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

	public double getCost() {
		return Cost;
	}

	public void setCost(double cost) {
		Cost = cost;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



	@Override
	public String toString() {
		return "Transaction [id=" + id + ", CustID=" + CustID + ", EquipType=" + EquipType + ", RentalDate="
				+ RentalDate + ", Availability=" + Availability + ", Cost=" + Cost + "]";
	}

	public Transaction(String custID, String equipType, java.sql.Date rentalDate, String availability, double cost) {
		super();
		CustID = custID;
		EquipType = equipType;
		RentalDate = rentalDate;
		Availability = availability;
		Cost = cost;
	}
	
}

