package com.excilys.training.validator.dto;

import com.excilys.training.dto.DataTransferObject;
import com.excilys.training.model.Company;
import com.excilys.training.validator.FailedValidationException;
import com.excilys.training.validator.Validator;

public class CompanyDTOValidator implements Validator<DataTransferObject<Company>> {

	@Override
	public void validate(DataTransferObject<Company> model) throws FailedValidationException {
		// TODO Auto-generated method stub
		
	}

}
