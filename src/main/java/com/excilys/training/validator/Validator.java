package com.excilys.training.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.excilys.training.validator.exception.FailedValidationException;

public interface Validator <T> {
	void validate(T model) throws FailedValidationException,ParseException ;

	public static  SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy");
	
}
