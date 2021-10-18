package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.swing.JOptionPane;

import factories.DBConnector;

public class Login {

	private static Connection connection = null;
	private Statement stmt = null;
	private ResultSet result = null;
	private int numOfRowsAffected = 0;
	
	
	public Login()
	{
		connection = DBConnector.getDatabaseConnection();
	}
	
	public void create(String UserType, String ID, Date Password )
	{
		String insertSql = "INSERT INTO grizzly.login (UserType,ID,Password) VALUES"
				+ "('" + UserType + "', '"+ID+"',  '"+Password+"')";
		try 
		{
			stmt = connection.createStatement();
			numOfRowsAffected = stmt.executeUpdate(insertSql);
			if (numOfRowsAffected ==1)
			{
				JOptionPane.showMessageDialog(null, "record created","Login Creation",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
		catch (SQLException e)
		{
			System.out.println("SQL Exception thrown" + e.getMessage());
		}
	}
	
	public void readAll()
	{
		String selectSql = "SELECT * FROM grizzly.login WHERE 1=1";
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
			}
		}
		
		catch (SQLException e)
		{
			System.out.println("SQL Exception thrown" + e.getMessage());
		}
	}
	
	public void update(String ID, String newPassword)
	{
		String updateSql = "UPDATE grizzly.login SET Password = '" + newPassword + "' WHERE ID = " + ID;
		try
		{
			stmt = connection.createStatement();
			
			numOfRowsAffected = stmt.executeUpdate(updateSql);
			if (numOfRowsAffected ==1)
			{
				JOptionPane.showMessageDialog(null, "Login record updated","Login record update",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
		catch(SQLException e)
		{
			System.out.println("SQL Exception thrown" + e.getMessage());
		}
	}
	
	public void delete(String ID)
	{
		String deleteSql = "DELETE grizzly.login WHERE ID = " + ID;
		try
		{
			stmt = connection.createStatement();
			
			numOfRowsAffected = stmt.executeUpdate(deleteSql);
			if (numOfRowsAffected ==1)
			{
				JOptionPane.showMessageDialog(null, "Login record updated","Login record update",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
		catch(SQLException e)
		{
			System.out.println("SQL Exception thrown" + e.getMessage());
		}
	}
}
