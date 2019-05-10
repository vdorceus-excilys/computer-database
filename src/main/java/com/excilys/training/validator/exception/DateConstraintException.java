package com.excilys.training.validator.exception;


public class DateConstraintException extends FailedValidationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DateConstraintException(String msg) {
		super(msg);
	}
	public DateConstraintException() {
		super();
	}

}
