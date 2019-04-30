package com.excilys.training.service;

import java.text.ParseException;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.training.persistance.Persistor;
import com.excilys.training.validator.FailedValidationException;
import com.excilys.training.validator.Validator;

public abstract class GenericService<T> implements Service<T> {
	protected Validator<T> validator;
	protected Persistor<T> persistor;
	
	protected static Logger logger = LogManager.getLogger(GenericService.class);
	
	// Singleton Pattern to be implemented in child classes	
	
	@Override
	public Validator<T> getValidator() {
		return this.validator;
	}
	@Override
	public void validate (T model) throws FailedValidationException{
		try {
			this.validator.validate(model);
		}
		catch(ParseException exp) {
			logger.error("EXCEPTION WHILE PARSING MODEL WITH VALIDATOR",exp);
			throw new FailedValidationException();
		}
		
	}
	@Override
	public Set<T> listAll(){
		Set<T> models =null;
		try {
			models = persistor.findAllQuery();
		}catch(Exception exp) {
			logger.error("ERROR WHILE TRYING TO FETCH(findAllQuery) FROM PERSISTOR",exp);
		}
		return models;
	}
	@Override
	public Set<T> listAll(Long offset, Long limit){
		Set<T> models =null;
		try {
			models = persistor.findAllQuery(offset, limit);
		}catch(Exception exp) {
			logger.error("ERROR WHILE TRYING TO FETCH(findAllQuery) FROM PERSISTOR",exp);
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
			logger.error("ERROR WHILE TRYING TO FETCH(findOneQuery) FROM PERSISTOR",exp);
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
			logger.error("ERROR WHILE TRYING TO CREATE A NEW ENTITY WITH PERSISTOR",exp);
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
			logger.error("ERROR WHILE TRYING TO UPDATE EXISTING ENTITY WITH PERSISTOR",exp);
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
			logger.error("ERROR WHILE TRYING TO DELETE EXISTING ENTITY PERSISTOR",exp);
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
