package com.excilys.training.validator.exception;

public class ExpectedValueConstraintException extends FailedValidationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ExpectedValueConstraintException(String msg) {
		super(msg);
	}
	public ExpectedValueConstraintException() {
		super();
	}

}
