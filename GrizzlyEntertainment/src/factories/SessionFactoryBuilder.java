package factories;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import models.Equipment;

public class SessionFactoryBuilder {
	
	private static SessionFactory factory = null;
	
	public static SessionFactory getSessionFactory()
	{
		if (factory == null)
		{
			factory = new Configuration()
					.configure("hibernate.cfg.xml").addAnnotatedClass(Equipment.class)
					.buildSessionFactory();
		}
		
		return factory;
	}
	
	public static void closeSessionFactory()
	{
		if (factory != null)
		{
			factory.close();
		}
	}

}
