package com.excilys.training.model.validator;

import java.text.SimpleDateFormat;

public interface Validator <T> {
	void validate(T model) throws FailedValidationException;

	public static  SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy");
	
}
