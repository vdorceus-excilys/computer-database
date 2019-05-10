package com.excilys.training.validator.exception;

public class ComparaisonConstraintException extends FailedValidationException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ComparaisonConstraintException(String msg) {
		super(msg);
	}
	
	public ComparaisonConstraintException() {
		super();
	}

}
