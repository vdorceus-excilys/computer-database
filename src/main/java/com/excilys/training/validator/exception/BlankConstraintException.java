package com.excilys.training.validator.exception;


public class BlankConstraintException extends FailedValidationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BlankConstraintException(String msg) {
		super(msg);
	}
	public BlankConstraintException() {
		super();
	}

}
