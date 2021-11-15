package models;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import factories.DBConnector;

public class Login implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Connection connection = null;
	private Statement stmt = null;
	private ResultSet result = null;
	private int numOfRowsAffected = 0;
	private PreparedStatement pst = null;
	private String userType; 
	private String Id;
	private String password;
	
	private static final Logger Logger=LogManager.getLogger(Login.class);
	
	public Login()
	{
		connection = DBConnector.getDatabaseConnection();
	}
	
	public void create(String UserType, String ID, String Password )
	{
		String insertSql = "INSERT INTO grizzly.login (UserType,ID,Password) VALUES"//inserts the login information of users into the database
				+ "('" + UserType + "', '"+ID+"',  '"+Password+"')";
		try 
		{
			stmt = connection.createStatement();
			numOfRowsAffected = stmt.executeUpdate(insertSql);//runs the sql statement seen above that performs said action
			if (numOfRowsAffected ==1)
			{
				JOptionPane.showMessageDialog(null, "record created","Login Creation",
						JOptionPane.INFORMATION_MESSAGE);
				Logger.info("Login Successful \n");
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
		String selectSql = "SELECT * FROM grizzly.login WHERE 1=1";//used to display all the information from the login table 
		try
		{
			stmt = connection.createStatement();
			
			result = stmt.executeQuery(selectSql);
			while(result.next())
			{
				String UserType = result.getString("UserType");
				String ID = result.getString("ID");
				String Password = result.getString("Password");
				
				
				System.out.println("UserType:" + UserType + "\tID: " + ID
						+ "\tPassword:" + Password);
				Logger.info("Records Accessed \n");
			}
		}
		
		catch (SQLException e)
		{
			System.out.println("SQL Exception thrown" + e.getMessage());
			Logger.error(" Error SQL Exception thrown" + e.getMessage());
		}
	}
	
	public void update(String ID, String newPassword)
	{
		String updateSql = "UPDATE grizzly.login SET Password = '" + newPassword + "' WHERE ID = " + ID; //updates information in the login table using
		try																								 // the user's id to locate their data
		{
			stmt = connection.createStatement();
			
			numOfRowsAffected = stmt.executeUpdate(updateSql);
			if (numOfRowsAffected ==1)
			{
				JOptionPane.showMessageDialog(null, "Login record updated","Login record update",
						JOptionPane.INFORMATION_MESSAGE);
				Logger.info("Password Changed \n");
			}
		}
		
		catch(SQLException e)
		{
			System.out.println("SQL Exception thrown" + e.getMessage());
			Logger.error(" Error SQL Exception thrown" + e.getMessage());
		}
	}
	
	public void delete(String ID)
	{
		String deleteSql = "DELETE grizzly.login WHERE ID = " + ID; //deletes user information from the system using their ID number as a search term
		try
		{
			stmt = connection.createStatement();
			
			numOfRowsAffected = stmt.executeUpdate(deleteSql);
			if (numOfRowsAffected ==1)
			{
				JOptionPane.showMessageDialog(null, "Login record updated","Login record update",
						JOptionPane.INFORMATION_MESSAGE);
				Logger.info("User Deleted \n");
			}
		}
		
		catch(SQLException e)
		{
			System.out.println("SQL Exception thrown" + e.getMessage());
			Logger.error(" Error SQL Exception thrown" + e.getMessage());
		}
	}
	public void verifyLogin(String id, String psw, String uType)//accepts the user's id, password and usertype and checks the database for a match
	{
		String sql = "SELECT * FROM grizzlyserver.login WHERE UserType=? and ID=? and Password=?";
		try {
			pst = connection.prepareStatement(sql);
			pst.setString(1, uType);
			pst.setString(2, id);
			pst.setString(3,  psw);
			
			result = pst.executeQuery();
			if(result.next()) { //if a match is found
				JOptionPane.showMessageDialog(null, "Success, you're logged in as "+result.getString("UserType"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//Implementing Getters and Setters
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

	@Override
	public String toString() {
		return "Login [userType=" + userType + ", Id=" + Id + ", password=" + password + "]";
	}

	public Login(String userType, String id, String password) {
		super();
		this.userType = userType;
		Id = id;
		this.password = password;
	}
	
}
