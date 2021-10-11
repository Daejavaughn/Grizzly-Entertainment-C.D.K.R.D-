package factories;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;


public class DBConnector
{
	private static Connection connection = null;
	public static Connection getDatabaseConnection ()
	{
		if(connection == null)
		{
			String url = "jdbc:mysql://localhost:3306/dblab";
			
			try 
			{
				connection = DriverManager.getConnection(url, "root", "");
				
				if(connection != null)
				{
					JOptionPane.showMessageDialog(null, "Connected to the local Server",
							"JDBC Connection Status",JOptionPane.INFORMATION_MESSAGE);
				}
			}
			
			catch(Exception e)
			{
				System.err.println("Exception" + e.getMessage());
			}
		}
		
		return connection;
	}
}

