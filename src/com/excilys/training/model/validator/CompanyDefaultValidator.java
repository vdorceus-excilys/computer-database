package com.excilys.training.model.validator;

import com.excilys.training.model.Company;

public class CompanyDefaultValidator implements Validator<Company>{
	
	public void validate(Company company) throws FailedValidationException{
		Boolean valid = (company!=null)	&& (company.getId()>=0)	;
		if(!valid)
			throw new FailedValidationException();
	}
}
