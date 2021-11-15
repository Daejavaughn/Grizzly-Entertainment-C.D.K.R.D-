package main;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


import models.Login;


public class ClientDriver implements ActionListener, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JLabel id;
	private JLabel password;
	private JButton submit;

	String entID;
	String entPassword;
	JLabel choose = new JLabel("Select user type");
	JTextField idField = new JTextField(16);
	JTextField passwordField = new JTextField(16);
	String userTypes[] = {"Customer", "Employee"};
	JComboBox<String> userType = new JComboBox<>(userTypes);
	
	models.Login chk = new models.Login();
	Client client = new Client(); //creates a new client object allowing the user to send requests to the server
	public static void main(String[] args) {
		
		new ClientDriver(); //invokes the ClientDriver constructor
		
		/*Equipment equip = new Equipment("Wrench", "Hand Tool", "yes", 3000);
		client.sendAction("Add Equipment");
		System.out.println("Sending message to server");
		client.sendEquipment(equip);
		System.out.println("Record sent to server");
		client.receiveResponse();
		System.out.println("Response received from server")
		
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
	public ClientDriver() //login UI for users
	{
		frame = new JFrame("User Login");
		frame.setSize(300, 250);
		
		id = new JLabel("User ID");
		password = new JLabel("Password");
		submit = new JButton("login");
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		panel.add(id);
		panel.add(password);
		panel.add(choose);
		panel.add(userType);
		panel.add(idField);
		panel.add(passwordField);
		panel.add(submit);
		
		//positioning the elements in the frame
		id.setBounds(20, 20, 80, 30);
		password.setBounds(20, 50, 80, 30);
		idField.setBounds(80, 25, 160, 25);
		passwordField.setBounds(80, 55, 160, 25);
		submit.setBounds(110, 150, 80, 30);
		choose.setBounds(95, 75, 100, 25);
		userType.setBounds(100, 100, 100, 25);
		
		frame.add(panel);
		frame.setVisible(true);//makes the frame visible
		submit.addActionListener(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==submit)//when the login button is pressed, the actions below are carried out
		{
		//	System.out.println("clicked");
			entID = idField.getText();//gets the user input from the gui and places it in this variable
			entPassword = passwordField.getText();//gets the user input from the gui and places it in this variable
			String utype = String.valueOf(userType.getSelectedItem());//gets the user choice from the gui and places it in this variable
			//chk.verifyLogin(utype, entID, entPassword);
			Login chk = new Login(utype, entID, entPassword); //creates a new login object which contains the user's credentials
			client.sendAction("Verify Login");//tells the server to verfiy the login credentials it will be receiving shortly
			client.sendLogin(chk);//sends the login credentials to the server
			
			if (utype == "Employee")//if the user is an employee
			{
				new EmployeeGui();
				frame.setVisible(false);//hides the login ui
				
			} else if (utype == "Customer")//if the user is a customer
			{
				frame.setVisible(false);//hides the login ui
				new CustomerGui();
			}
		}
		
	}

}

