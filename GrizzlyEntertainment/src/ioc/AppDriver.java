package ioc;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppDriver 
{
	public static void main(String[] args) 
	{
		//load the configuration file
		ClassPathXmlApplicationContext context;
			new ClassPathXmlApplicationContext("Beans.xml");
				//retrieve a bean"
		Inversion in0bj = (Inversion) context.getBean("Inversion");
           //use the bean object to call method(s)
			in0bj.getMessage() ;
				//close the context
				context.close ();
	}
}
