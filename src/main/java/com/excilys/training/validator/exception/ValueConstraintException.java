package com.excilys.training.validator.exception;

public class ValueConstraintException extends FailedValidationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ValueConstraintException(String msg){
		super(msg);
	}
	public ValueConstraintException() {
		super();
	}

}
