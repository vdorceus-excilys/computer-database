package com.excilys.training.validator.dto;

import com.excilys.training.dto.DataTransferObject;
import com.excilys.training.model.Company;
import com.excilys.training.validator.Validator;
import com.excilys.training.validator.exception.FailedValidationException;

public class CompanyDTOValidator implements Validator<DataTransferObject<Company>> {

	@Override
	public void validate(DataTransferObject<Company> model) throws FailedValidationException {
		// TODO Auto-generated method stub
		
	}

}
