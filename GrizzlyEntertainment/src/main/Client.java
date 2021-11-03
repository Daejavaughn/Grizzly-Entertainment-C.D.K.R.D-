package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import models.Customer;
import models.Equipment;
import models.Login;
import models.Transaction;

public class Client implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Socket connectionSocket;
	private ObjectOutputStream objOs;
	private ObjectInputStream objIs;
	private String action = "";
	
	private static final Logger Logger=LogManager.getLogger(Client.class);
	
	public Client() {
		this.createConnection();
		this.configureStreams();
	}
	
	private void createConnection()
	{
		try {
			//create a socket to connect to the server
			connectionSocket = new Socket("127.0.0.1", 8888);
		} catch (IOException ex) {
			ex.printStackTrace();
			Logger.error("IOExcepton " + ex.getMessage());
		}
	}
	
	private void configureStreams()
	{
		try {
			//create an input stream to receive data from the server
			objIs = new ObjectInputStream(connectionSocket.getInputStream());
			//create an output stream to send data to the server
			objOs = new ObjectOutputStream(connectionSocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			Logger.error("IOExcepton " + e.getMessage());
		}
	}
	
	void closeConnection()
	{
		try {
			objOs.close();
			objIs.close();
			connectionSocket.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			Logger.error("IOExcepton " + ex.getMessage());
		}
	}
	
	void sendAction(String action)
	{
		this.action = action;
		try {
			objOs.writeObject(action);
		} catch (IOException e) {
			e.printStackTrace();
			Logger.error("IOExcepton " + e.getMessage());
		}
	}
	
	void sendEquipment(Equipment equipObj)
	{
		try {
			objOs.writeObject(equipObj);
		} catch (IOException ex) {
			ex.printStackTrace();
			Logger.error("IOExcepton " + ex.getMessage());
		}
	}
	void sendLogin(Login loginObj)
	{
		try {
			objOs.writeObject(loginObj);
		} catch (IOException ex) {
			ex.printStackTrace();
			Logger.error("IOExcepton " + ex.getMessage());
		}
	}
	void sendCustomer(Customer customerObj)
	{
		try {
			objOs.writeObject(customerObj);
		} catch (IOException ex) {
			ex.printStackTrace();
			Logger.error("IOExcepton " + ex.getMessage());
		}
	}
	void sendTransaction(Transaction transactionobj)
	{
		try {
			objOs.writeObject(transactionobj);
		} catch (IOException ex) {
			ex.printStackTrace();
			Logger.error("IOExcepton " + ex.getMessage());
		}
	}
	
	void sendTransactionId(int Id)
	{
		try {
			objOs.writeObject(Id);
		} catch (IOException ex) {
			ex.printStackTrace();
			Logger.error("IOExcepton " + ex.getMessage());
		}
	}

	public void receiveResponse()
	{
		try {
			if(action.equalsIgnoreCase("Add Equipment"))
			{
				Boolean flag = (Boolean) objIs.readObject();
				if (flag == true)
				{
					JOptionPane.showMessageDialog(null, "Equipment added successfully",
							"Add Equipment Status", JOptionPane.ERROR_MESSAGE);
					Logger.info("Equipment Added");
				}
			}
			if(action.equalsIgnoreCase("Add Login"))
			{
				Boolean flag = (Boolean) objIs.readObject();
				if (flag == true)
				{
					JOptionPane.showMessageDialog(null, "Login Details added successfully",
							"Add Login Status", JOptionPane.ERROR_MESSAGE);
					Logger.info("Login Details Added");
				}
			}
			if(action.equalsIgnoreCase("Add Customer"))
			{
				Boolean flag = (Boolean) objIs.readObject();
				if (flag == true)
				{
					JOptionPane.showMessageDialog(null, "Customer Details added successfully",
							"Add Customer Status", JOptionPane.ERROR_MESSAGE);
					Logger.info("Customer Details Added");
				}
			}
			if(action.equalsIgnoreCase("Add Transaction"))
			{
				Boolean flag = (Boolean) objIs.readObject();
				if (flag == true)
				{
					JOptionPane.showMessageDialog(null, "Transaction Details added successfully",
							"Add Transaction Status", JOptionPane.ERROR_MESSAGE);
					Logger.info("Transaction Details Added");
				}
			}
			if (action.equalsIgnoreCase("Find Transaction"))
			{
				Transaction transaction = new Transaction();
				transaction = (Transaction) objIs.readObject();
				if (transaction == null)
				{
					JOptionPane.showMessageDialog(null, "Record could not be found",
							"Find Record Status", JOptionPane.ERROR_MESSAGE);
					Logger.info("Transaction Details not found");
					return;
				}
			}
			
		} catch (ClassCastException ex) {
			ex.printStackTrace();
			Logger.error("IOExcepton " + ex.getMessage());
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			Logger.error("IOExcepton " + ex.getMessage());
		} catch (IOException ex)  {
			ex.printStackTrace();
			Logger.error("IOExcepton " + ex.getMessage());
		}
	}
}
