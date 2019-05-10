package com.excilys.training.validator.exception;

public class MatchConstraintException extends FailedValidationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MatchConstraintException(String msg) {
		super(msg);
	}
	public MatchConstraintException() {
		super();
	}

}
