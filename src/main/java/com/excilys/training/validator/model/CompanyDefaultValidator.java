package com.excilys.training.validator.model;

import com.excilys.training.model.Company;
import com.excilys.training.validator.Validator;
import com.excilys.training.validator.exception.FailedValidationException;

 public class CompanyDefaultValidator implements Validator<Company>{
	
	public void validate(Company company) throws FailedValidationException{
		Boolean valid = (company!=null)	&& (company.getId()>=0)	;
		if(!valid)
			throw new FailedValidationException();
	}
}
