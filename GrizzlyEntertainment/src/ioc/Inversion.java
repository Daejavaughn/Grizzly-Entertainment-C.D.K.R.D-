
package ioc;

public class Inversion 
{
	private String message;
	
	public void setMessage(String message)
	{
		this.message = message;
	}
	
	public void getMessage(String message)
	{
		System.out.println("Your message :" + message);
	}
}
