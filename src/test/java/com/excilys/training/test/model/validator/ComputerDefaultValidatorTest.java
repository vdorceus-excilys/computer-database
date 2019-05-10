package com.excilys.training.test.model.validator;

import java.text.SimpleDateFormat;

import org.junit.Before;
import org.junit.Test;

import com.excilys.training.model.Computer;
import com.excilys.training.validator.exception.FailedValidationException;
import com.excilys.training.validator.model.ComputerDefaultValidator;

public class ComputerDefaultValidatorTest {
	
	Computer computerOkay,computerNoName, computerNoId, computerIncoherentDate, computerNegativeId;
	SimpleDateFormat sdf;
	ComputerDefaultValidator validator;

	@Before
	public void setUp() throws Exception {
		validator = new ComputerDefaultValidator();
		sdf = new SimpleDateFormat("dd-MM-yyyy");
		computerOkay = new Computer();
		computerOkay.setId(1L);
		computerOkay.setName("Macbook");
		computerOkay.setIntroduced(sdf.parse("24-04-2000"));
		computerOkay.setDiscontinued(sdf.parse("24-04-2019"));
		
		computerNoName= new Computer();
		computerNoName.setId(2L);
		
		computerNoId= new Computer();
		
		computerIncoherentDate = new Computer();
		computerIncoherentDate.setId(1L);
		computerIncoherentDate.setName("blabla");
		computerIncoherentDate.setIntroduced(sdf.parse("24-04-2020"));
		computerIncoherentDate.setDiscontinued(sdf.parse("24-04-2019"));
		
		computerNegativeId = new Computer();
		computerNegativeId.setId(-1L);
		computerNegativeId.setName("Macbook");
		computerNegativeId.setIntroduced(sdf.parse("24-04-2000"));
		computerNegativeId.setDiscontinued(sdf.parse("24-04-2019"));
				
	}

	@Test
	public void validTest() {
		validator.validate(computerOkay);
	}
	@Test(expected=FailedValidationException.class)
	public void noNameTest() {
		validator.validate(computerNoName);
	}
	@Test(expected=FailedValidationException.class)
	public void noIdTest() {
		validator.validate(computerNoId);
	}
	@Test(expected=FailedValidationException.class)
	public void incoherentDateTest() {
		validator.validate(computerIncoherentDate);
	}
	@Test(expected=FailedValidationException.class)
	public void negativeIdTest() {
		validator.validate(computerNegativeId);
	}

}
