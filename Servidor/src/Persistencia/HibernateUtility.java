package Persistencia;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtility {
	
	public static SessionFactory factory;
	//to disallow creating objects by other classes.
	 
	    private HibernateUtility() {
	    }
	//maling the Hibernate SessionFactory object as singleton
	 
	    public static synchronized SessionFactory getSessionFactory() {
	 
	        if (factory == null) {
	        	//creating configuration object
	    		Configuration cfg=new Configuration();
	    		cfg.configure("hibernate.cfg.xml");//populates the data of the configuration file	
	    		//creating seession factory object 
	    		factory=cfg.buildSessionFactory();
	        }
	        return factory;
	    }

}
