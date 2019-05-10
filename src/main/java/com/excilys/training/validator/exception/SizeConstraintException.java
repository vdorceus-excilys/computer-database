package com.excilys.training.validator.exception;

public class SizeConstraintException extends FailedValidationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SizeConstraintException(String msg) {
		super(msg);
	}
	public SizeConstraintException() {
		super();
	}

}
