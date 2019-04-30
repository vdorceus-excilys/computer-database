package com.excilys.training.service;

import java.util.Set;

import com.excilys.training.validator.FailedValidationException;
import com.excilys.training.validator.Validator;

public interface Service <T> {
	
	Validator<T> getValidator();
	void validate(T model) throws FailedValidationException;
	
	Set<T> listAll();
	Set<T> listAll(Long offset, Long limit);
	T findOne(Long id);
	T findByAttribut(String att, String value);
	Boolean create(T model);
	Boolean update(T model);
	Boolean delete(T model);
	
	Long count();
	
	
}
