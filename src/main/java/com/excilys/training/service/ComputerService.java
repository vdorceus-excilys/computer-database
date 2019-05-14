package com.excilys.training.service;

import com.excilys.training.model.Computer;
import com.excilys.training.persistance.ComputerPersistor;
import com.excilys.training.validator.Validator;

public class ComputerService extends GenericService<Computer>{
	
	public ComputerService(ComputerPersistor persistor,Validator<Object> validator) {
		super();
		this.persistor = persistor;
		this.validator = validator;
	}
		
	@Override
	public Computer findByAttribut(String att, String value) {
		return null;
	}
	
	
	

}
