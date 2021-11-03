package main;

import java.sql.Date;

import models.Customer;
import models.Equipment;
import models.Login;
import models.Transaction;

public class ClientDriver {
	
	public static void main(String[] args) {
		
		Client client = new Client();
		
		/*Equipment equip = new Equipment("Wrench", "Hand Tool", "yes", 3000);
		client.sendAction("Add Equipment");
		System.out.println("Sending message to server");
		client.sendEquipment(equip);
		System.out.println("Record sent to server");
		client.receiveResponse();
		System.out.println("Response received from server");*/
		
		Login login = new Login("Customer", "170", "password");
		
		client.sendAction("Add Login");
		System.out.println("Sending message to server");
		client.sendLogin(login);
		System.out.println("Record sent to server");
		client.receiveResponse();
		System.out.println("Response received from server");
		
		/*@SuppressWarnings("deprecation")
		Customer customer = new Customer("156", "Hand Tool", new Date(9,10,2021), "yes", 3000);
		
		client.sendAction("Add Customer");
		System.out.println("Sending message to server");
		client.sendCustomer(customer);
		System.out.println("Record sent to server");
		client.receiveResponse();
		System.out.println("Response received from server");*/
		
		/*@SuppressWarnings("deprecation")
		Transaction transaction = new Transaction("156","Hand Tool", new Date(9, 10, 2021), "yes", 3000);
		
		client.sendAction("Add Transaction");
		System.out.println("Sending message to server");
		client.sendTransaction(transaction);
		System.out.println("Record sent to server");
		client.receiveResponse();
		System.out.println("Response received from server");*/
		
		/*client.sendAction("Find Transaction");
		client.sendTransactionId(1);
		client.receiveResponse();(/
		client.closeConnection();*/
	}

}

