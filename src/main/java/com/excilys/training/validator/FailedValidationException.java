package com.excilys.training.validator;

public class FailedValidationException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public FailedValidationException(String exp) {
		super(exp);
	}
	public FailedValidationException() {
		super();
	}

}
