package com.excilys.training.service;

import java.util.Set;

import com.excilys.training.model.validator.FailedValidationException;
import com.excilys.training.model.validator.Validator;
import com.excilys.training.persistance.Persistor;
import com.excilys.training.util.log.SimpleLog;
import com.excilys.training.util.log.SimpleLogEntry;

public abstract class GenericService<T> implements Service<T> {
	protected Validator<T> validator;
	protected Persistor<T> persistor;
	protected SimpleLog log = SimpleLog.getInstance();
	
	// Singleton Pattern to be implemented in child classes	
	
	@Override
	public void setValidator(Validator<T> validator) {
		this.validator = validator;
	}
	@Override
	public void validate (T model) throws FailedValidationException{
		this.validator.validate(model);
	}
	@Override
	public Set<T> listAll(){
		Set<T> models =null;
		try {
			models = persistor.findAllQuery();
		}catch(Exception exp) {
			//log exception
			SimpleLogEntry  s = new SimpleLogEntry(exp,"ERROR WHILE TRYING TO FETCH(findAllQuery) FROM PERSISTOR");
			log.log(s);
		}
		return models;
	}
	@Override
	public Set<T> listAll(Long offset, Long limit){
		Set<T> models =null;
		try {
			models = persistor.findAllQuery(offset, limit);
		}catch(Exception exp) {
			//log exception
			SimpleLogEntry  s = new SimpleLogEntry(exp,"ERROR WHILE TRYING TO FETCH(findAllQuery) FROM PERSISTOR");
			log.log(s);
		}
		return models;
	}
	@Override
	public T findOne(Long id) {
		T model = null;
		try {
			model = persistor.findOneQuery(id);
		}catch(Exception  exp) {
			//log exception
			SimpleLogEntry  s = new SimpleLogEntry(exp,"ERROR WHILE TRYING TO FETCH(findOneQuery) FROM PERSISTOR");
			log.log(s);
		}
		return model;
	}
	@Override 
	public Boolean create(T model) {
		Boolean state =  true;
		try {
			persistor.createQuery(model);
		}
		catch(Exception exp) {
			SimpleLogEntry  s = new SimpleLogEntry(exp,"ERROR WHILE TRYING TO CREATE A NEW ENTITY WITH PERSISTOR");
			log.log(s);
			state=false;
		}
		return state;		
	}
	@Override 
	public Boolean update(T model) {
		Boolean state = true;
		try {
			persistor.updateQuery(model);
		}catch(Exception exp) {
			//log exception
			SimpleLogEntry  s = new SimpleLogEntry(exp,"ERROR WHILE TRYING TO UPDATE EXISTING ENTITY WITH PERSISTOR");
			log.log(s);
			state = false;
		}
		return state;		
	}
	@Override
	public Boolean delete(T model) {
		Boolean state = true;
		try {
			persistor.deleteQuery(model);
		}catch(Exception exp) {
			//log exception
			SimpleLogEntry  s = new SimpleLogEntry(exp,"ERROR WHILE TRYING TO DELETE EXISTING ENTITY PERSISTOR");
			log.log(s);
			state = false;
		}
		return state;
	}
	
	@Override 
	abstract public T findByAttribut(String att, String value); 
	
	@Override 
	public Long count() {
		Long count ;
		count = persistor.countAll();		
		return count;		
	}
}
