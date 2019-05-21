package com.excilys.training.validator.spring;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.excilys.training.dto.DefaultComputerSkin;

public class ComputerValidator implements ConstraintValidator<ComputerConstraint,DefaultComputerSkin>{

	@Override
	public void initialize(ComputerConstraint computer) {		
	}
	
	@Override
	public boolean isValid(DefaultComputerSkin computer, ConstraintValidatorContext context) {
		return computer!=null;
	}

}
