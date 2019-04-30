package com.excilys.training.service;

import com.excilys.training.model.Computer;
import com.excilys.training.persistance.ComputerPersistor;
import com.excilys.training.validator.Validator;

public class ComputerService extends GenericService<Computer>{
	
	private  ComputerService() {
		super();
	}
	
	private  static  ComputerService self=null;
	
	public static ComputerService getInstance(ComputerPersistor persistor,Validator<Computer> validator) {
		self = (self!=null)? self : (self=new ComputerService());
		self.persistor = persistor;
		self.validator = validator;		
		return self;
	}
	
	@Override
	public Computer findByAttribut(String att, String value) {
		return null;
	}
	
	
	

}
