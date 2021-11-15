package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import models.Customer;
import models.Equipment;
import models.Login;

public class ClientHandler implements Runnable{

	private Socket client;
	ObjectOutputStream objOs;
	ObjectInputStream objIs;
	private static Connection dBConn = null;
	public boolean loggedIn;
	private Statement stmt; 
	private ResultSet result = null;
	private static final Logger Logger=LogManager.getLogger(ClientHandler.class);
	public ClientHandler(Socket client)
	{
		this.client = client;
	}
	
	private static Connection getDatabaseConnection()
	{
		if (dBConn == null)//if there is connection to the database presently
		{
			try {
				String url = "jdbc:mysql://localhost:3306/grizzlyserver";
				dBConn = DriverManager.getConnection(url, "root", "");
				
				JOptionPane.showMessageDialog(null, "DB Connection Established",
						"CONNECTION STATUS", JOptionPane.INFORMATION_MESSAGE);
				Logger.info("Database Connection Established");
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(null, "Could not connect to database\n" + ex,
						"Connection Failure", JOptionPane.ERROR_MESSAGE);
				Logger.info("Database Connection Failure");
			}
			
			
		}
		return dBConn;//returns the state of the database connection
		
	}	
	
	private void addEquipment(Equipment equipment)
	{
		String sql = "INSERT INTO grizzlyserver.equipment (Name, Category, Available, Cost) VALUES ('"+ equipment.getName() + "', '" + equipment.getCategory()
					+ "', '" + equipment.getAvailable() +"', '" + equipment.getCost() + "')";
		
		try {
			stmt = dBConn.createStatement();
			
			if ((stmt.executeUpdate(sql) == 1))
			{
				objOs.writeObject(true);//Returns true to the client if successful
			} else {
				objOs.writeObject(false);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			Logger.error("IOExcepton " + ioe.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			Logger.error("IOExcepton " + e.getMessage());
		}
		
	}
	
	private void addLogin(Login login)
	{
		String sql = "INSERT INTO grizzlyserver.login (UserType, ID, Password) VALUES ('"+ login.getUserType()
				 + "', '"+ login.getId() +"',  '"+login.getPassword()+"')";
		
		try {
			stmt = dBConn.createStatement();
			
			if ((stmt.executeUpdate(sql) == 1))
			{
				objOs.writeObject(true);//Returns true to the client if successful
			} else {
				objOs.writeObject(false);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			Logger.error("IOExcepton " + ioe.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			Logger.error("IOExcepton " + e.getMessage());
		}
		
	}
	
	
	private void addCustomer(Customer customer)
	{
		String sql = "INSERT INTO grizzlyserver.customer (CUSTID, EquipType, RentalDate, Availability, Quotation) VALUES ('"+ customer.getCustID() + "', '" + customer.getEquipType()
					+ "', '" + customer.getRentalDate() +"', '" + customer.getAvailability() +"', '" + customer.getQuotation() + "')";
		
		try {
			stmt = dBConn.createStatement();
			
			if ((stmt.executeUpdate(sql) == 1))
			{
				objOs.writeObject(true);//Returns true to the client if successful
			} else {
				objOs.writeObject(false);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			Logger.error("IOExcepton " + ioe.getMessage());
		} catch (SQLException e) {
			Logger.error("IOExcepton " + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	private void addTransaction(models.Transaction transaction)
	{
		String sql = "INSERT INTO grizzlyserver.transaction (CUSTID, EquipType, RentalDate, Availability, Cost) VALUES ('"+ transaction.getCustID() + "', '" + transaction.getEquipType()
		+ "', '" + transaction.getRentalDate() +"', '" + transaction.getAvailability() +"', '" + transaction.getCost() + "')"; 
		
		try {
			stmt = dBConn.createStatement();
			
			if ((stmt.executeUpdate(sql) == 1))
			{
				objOs.writeObject(true);//Returns true to the client if successful
			} else {
				objOs.writeObject(false);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			Logger.error("IOExcepton " + ioe.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			Logger.error("IOExcepton " + e.getMessage());
		}
		
	}
	
	private models.Transaction findTransaction (int transactionId) {

		models.Transaction transactionObj = new models.Transaction();
		String query= "SELECT * FROM grizzlyserver.transaction WHERE TransactionID = " + transactionId;
		try {
			stmt = dBConn.createStatement ();
			result = stmt.executeQuery (query);
		if (result.next()) {
			transactionObj.setId(result.getInt(1));
			transactionObj.setEquipType(result.getString(3)); 
			transactionObj.setRentalDate(result.getDate(4));
			transactionObj.setCost(result.getDouble(6));
			transactionObj.setCustID(result.getString(2));
			}
		} catch (SQLException e) { 
			e.printStackTrace();
			Logger.error("IOExcepton " + e.getMessage());
		}
			
		return transactionObj;
	}
	public void verifyLogin(Login login)
	{
		PreparedStatement pst = null;
		String sql = "SELECT * FROM grizzlyserver.login WHERE UserType=? and ID=? and Password=?";
		try {
			pst = dBConn.prepareStatement(sql);
			pst.setString(1, login.getUserType());
			pst.setString(2, login.getId());
			pst.setString(3,  login.getPassword());
			result = pst.executeQuery();
			if(result.next()) {
				JOptionPane.showMessageDialog(null, "Success, you're logged in as "+result.getString("UserType"));
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void viewEquipment(String eid, String name, String cat, String avail, double cost)
	{
		PreparedStatement pst = null;
		String sql = "SELECT * FROM grizzlyserver.equipment";
		try {
			pst = dBConn.prepareStatement(sql);
			result = pst.executeQuery();;
			if(result.next()) {

				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean loginStatus()
	{
		return loggedIn;
	}
	private void closeConnection()
	{
		try {
			objOs.close();
			objIs.close();
			client.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			Logger.error("IOExcepton " + ex.getMessage());
		}
	}
	
	@Override
	public void run() {
		String action = "";
		getDatabaseConnection();
		Equipment equipObj = null;
		Login loginObj = null;
		Customer customerObj = null;
		models.Transaction transactionObj = null;
		try {
			objOs = new ObjectOutputStream(client.getOutputStream());
			objIs = new ObjectInputStream(client.getInputStream());
			try {
				action = (String) objIs.readObject();
				
				if (action.equals("Add Equipment"))
				{
					equipObj = (Equipment) objIs.readObject();
					addEquipment(equipObj);
					objOs.writeObject(true);
				} 
				if (action.equals("Add Login"))
				{
					loginObj = (Login) objIs.readObject();
					addLogin(loginObj);
					objOs.writeObject(true);
				} 
				if (action.equals("Add Customer"))
				{
					customerObj = (Customer) objIs.readObject();
					addCustomer(customerObj);
					objOs.writeObject(true);
				} 
				if (action.equals("Add Transaction"))
				{
					transactionObj = (models.Transaction) objIs.readObject();
					addTransaction(transactionObj);
					objOs.writeObject(true);
				}
				if (action.equals("Find Transaction")) {
					int transactionId = (int) objIs.readObject();
					transactionObj = findTransaction(transactionId);
					objOs.writeObject(transactionObj);
				}
				if (action.equals("Verify Login")) {
					loginObj = (Login) objIs.readObject();
					verifyLogin(loginObj);
				}
				
			} catch (ClassNotFoundException ex) {
				ex.printStackTrace();
				Logger.error("IOExcepton " + ex.getMessage());
			} catch (ClassCastException ex) {
				ex.printStackTrace();
				Logger.error("IOExcepton " + ex.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				Logger.error("IOExcepton " + e.getMessage());
			}
			this.closeConnection();
			}
		catch (IOException ex) {
			ex.printStackTrace();
		}
}
}
