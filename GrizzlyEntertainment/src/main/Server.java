package main;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;


import models.Customer;
import models.Equipment;
import models.Login;

//no logging was done here as this class will not be used
public class Server {
	
	private ServerSocket serverSocket;
	private Socket connectionSocket;
	private ObjectOutputStream objOs;
	private ObjectInputStream objIs;
	private static Connection dBConn = null;
	private Statement stmt;
	private ResultSet result = null;
	
	public Server(){
		
		this.createConnection();
		this.waitForRequests();
	}
	
	private void createConnection()
	{
		try {
			//create new instance of the ServerSocket listening on port 8888
			serverSocket = new ServerSocket(8888);
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	private void configureStreams()
	{
		try {
			//instantiate the output stream, using the getOutputStream method
			//of the Socket object as argument to the constructor
			
			objOs = new ObjectOutputStream(connectionSocket.getOutputStream());
			
			//instantiate the input stream, using the getInputStream method
			//of the Socket object as argument to the constructor
			
			objIs = new ObjectInputStream(connectionSocket.getInputStream());
			
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	private static Connection getDatabaseConnection()
	{
		if (dBConn == null)
		{
			try {
				String url = "jdbc:mysql://localhost:3306/grizzlyserver";
				dBConn = DriverManager.getConnection(url, "root", "");
				
				JOptionPane.showMessageDialog(null, "DB Connection Established",
						"CONNECTION STATUS", JOptionPane.INFORMATION_MESSAGE);
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(null, "Could not connect to database\n" + ex,
						"Connection Failure", JOptionPane.ERROR_MESSAGE);
			}
			
			
		}
		return dBConn;
		
	}	
	private void closeConnection()
	{
		try {
			objOs.close();
			objIs.close();
			connectionSocket.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
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
		} catch (SQLException e) {
			e.printStackTrace();
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
		} catch (SQLException e) {
			e.printStackTrace();
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
		} catch (SQLException e) {
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
		} catch (SQLException e) {
			e.printStackTrace();
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
		}
			
		return transactionObj;
	}
	private void waitForRequests()
	{
		String action = "";
		getDatabaseConnection();
		Equipment equipObj = null;
		Login loginObj = null;
		Customer customerObj = null;
		models.Transaction transactionObj = null;
		try {
			while (true) {
				connectionSocket = serverSocket.accept();
				this.configureStreams();
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
				} catch (ClassNotFoundException ex) {
					ex.printStackTrace();
				} catch (ClassCastException ex) {
					ex.printStackTrace();
				}
				this.closeConnection();
				}
			} catch (EOFException ex) {
				System.out.println("Client has terminated connections with the server");
				ex.printStackTrace();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
	}

}
