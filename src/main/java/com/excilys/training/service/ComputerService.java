package com.excilys.training.service;

import com.excilys.training.model.Computer;
import com.excilys.training.persistance.ComputerPersistor;
import com.excilys.training.persistance.db.Database;
import com.excilys.training.persistance.db.Mysql;
import com.excilys.training.util.ConfigurationProperties;

public class ComputerService extends GenericService<Computer>{
	
	private  ComputerService() {
		super();
		try {
			ConfigurationProperties config = new ConfigurationProperties();
		config.load(ConfigurationProperties.DEFAULT_PERSISTANCE_PATH);
		Database db = new Mysql(config);
		this.persistor = new ComputerPersistor(db);
		}
		catch(Exception exp) {
			//log service exception
			logger.error("ERROR WHILE TRYING TO SET COMPUTER CONFIGURATION WITH PROPERTIES",exp);
		}
		
	}
	
	private  static  ComputerService self=null;
	
	public static ComputerService getInstance() {
		return (self!=null)? self : (self=new ComputerService());
	}
	
	@Override
	public Computer findByAttribut(String att, String value) {
		return null;
	}
	
	
	

}
