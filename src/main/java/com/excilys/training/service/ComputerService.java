package com.excilys.training.service;

import java.util.Set;

import com.excilys.training.model.Computer;
import com.excilys.training.model.validator.FailedValidationException;
import com.excilys.training.model.validator.Validator;
import com.excilys.training.persistance.ComputerPersistor;
import com.excilys.training.persistance.Persistor;
import com.excilys.training.persistance.db.Database;
import com.excilys.training.persistance.db.Mysql;
import com.excilys.training.util.ConfigurationProperties;
import com.excilys.training.util.log.SimpleLog;
import com.excilys.training.util.log.SimpleLogEntry;

public class ComputerService implements Service<Computer>{
	
	private  ComputerService() {
		try {
			ConfigurationProperties config = new ConfigurationProperties();
		config.load(ConfigurationProperties.DEFAULT_PERSISTANCE_PATH);
		Database db = new Mysql(config);
		this.persistor = new ComputerPersistor(db);
		}
		catch(Exception exp) {
			//log service exception
			SimpleLogEntry  s = new SimpleLogEntry(exp,"ERROR WHILE TRYING TO SET COMPUTER CONFIGURATION WITH PROPERTIES");
			log.log(s);
		}
		
	}
	
	private  static  ComputerService self=null;
	private Validator<Computer> computerValidator;
	private Persistor<Computer> persistor;
	protected SimpleLog log = SimpleLog.getInstance();
	
	public static ComputerService getInstance() {
		return (self!=null)? self : (self=new ComputerService());
	}
	@Override
	public void setValidator(Validator<Computer> computerValidator) {
		this.computerValidator = computerValidator;
	}
	@Override
	public void validate (Computer computer) throws FailedValidationException{
		this.computerValidator.validate(computer);
	}
	@Override
	public Set<Computer> listAll(){
		Set<Computer> computers =null;
		try {
			computers = persistor.findAllQuery();
		}catch(Exception exp) {
			//log exception
			SimpleLogEntry  s = new SimpleLogEntry(exp,"ERROR WHILE TRYING TO FETCH COMPUTER(findAllQuery) FROM PERSISTOR");
			log.log(s);
		}
		return computers;		 
	}
	@Override
	public Set<Computer> listAll(Long offset,Long limit){
		Set<Computer> computers =null;
		try {
			computers = persistor.findAllQuery(offset,limit);
		}catch(Exception exp) {
			//log exception
			SimpleLogEntry  s = new SimpleLogEntry(exp,"ERROR WHILE TRYING TO FETCH COMPUTER(findAllQuery) FROM PERSISTOR");
			log.log(s);
		}
		return computers;		 
	}
	@Override
	public Computer findOne(Long id) {
		Computer computer = null;
		try {
			computer = persistor.findOneQuery(id);
		}catch(Exception  exp) {
			//log exception
			SimpleLogEntry  s = new SimpleLogEntry(exp,"ERROR WHILE TRYING TO FETCH COMPUTER (findOneQuery) FROM PERSISTOR");
			log.log(s);
		}
		return  computer;
	}
	@Override 
	public Boolean create(Computer computer) {
		Boolean state =  true;
		try {
			this.validate(computer);
			persistor.createQuery(computer);
		}
		catch(FailedValidationException exp) {
			SimpleLogEntry  s = new SimpleLogEntry(exp,"FAILED TO VALIDATE MODEL COMPUTER");
			log.log(s);
		}
		catch(Exception exp) {
			SimpleLogEntry  s = new SimpleLogEntry(exp,"ERROR WHILE TRYING TO CREATE A NEW COMPUTER WITH PERSISTOR");
			log.log(s);
			state=false;
		}
		return state;
	}
	@Override 
	public Boolean update(Computer computer) {
		Boolean state = true;
		try {
			this.validate(computer);
			persistor.updateQuery(computer);
		}
		catch(FailedValidationException exp) {
			SimpleLogEntry  s = new SimpleLogEntry(exp,"FAILED TO VALIDATE MODEL COMPUTER");
			log.log(s);
		}
		catch(Exception exp) {
			//log exception
			SimpleLogEntry  s = new SimpleLogEntry(exp,"ERROR WHILE TRYING TO UPDATE COMPUTER WITH PERSISTOR");
			log.log(s);
			state = false;
		}
		return state;
	}
	@Override
	public Boolean delete(Computer computer) {
		Boolean state = true;
		try {
			persistor.deleteQuery(computer);
		}catch(Exception exp) {
			//log exception
			SimpleLogEntry  s = new SimpleLogEntry(exp,"ERROR WHILE TRYING TO DELETE COMPUTER WITH PERSISTOR");
			log.log(s);
			state = false;
		}
		return state;
	}
	@Override
	public Computer findByAttribut(String att, String value) {
		return null;
	}
	
	@Override 
	public Long count() {
		Long count ;
		count = persistor.countAll();		
		return count;		
	}
	
	

}
