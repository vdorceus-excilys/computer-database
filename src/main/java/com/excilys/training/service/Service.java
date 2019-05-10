package com.excilys.training.service;

import java.util.Set;

import com.excilys.training.validator.Validator;
import com.excilys.training.validator.exception.FailedValidationException;

public interface Service <T> {
	
	Validator<T> getValidator();
	void validate(T model) throws FailedValidationException;
	
	Set<T> listAll();
	Set<T> listAll(Long offset, Long limit);
	Set<T> orderedListAll(Long offset, Long limit, String att,Boolean asc);
	Set<T> listSearch(String search);
	T findOne(Long id);
	T findByAttribut(String att, String value);
	Boolean create(T model);
	Boolean update(T model);
	Boolean delete(T model);
	
	Long count();
	
	
}
