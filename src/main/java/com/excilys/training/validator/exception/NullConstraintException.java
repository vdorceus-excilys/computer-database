package com.excilys.training.validator.exception;

public class NullConstraintException extends FailedValidationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NullConstraintException(String msg) {
		super(msg);
	}
	public NullConstraintException() {
		super();
	}

}
