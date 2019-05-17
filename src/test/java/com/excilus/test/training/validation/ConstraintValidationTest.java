package com.excilus.test.training.validation;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

import com.excilys.training.validator.Constraint;
import com.excilys.training.validator.ConstraintValidator;
import com.excilys.training.validator.Validator;
import com.excilys.training.validator.exception.FailedValidationException;

public class ConstraintValidationTest {
	
	@Test(expected=FailedValidationException.class)
	public void lowerThanFailed() throws FailedValidationException, ParseException {
		ConstraintValidator validator =  new ConstraintValidator();
		Object pojo = new Object() {
			@Constraint(clazz="java.util.Date",lowerThan="target")
			Date before = Validator.SDF.parse("12-12-2012");
			@Constraint(clazz="java.util.Date",ref="target")
			Date after = Validator.SDF.parse("12-12-2009");
		};
		validator.validate(pojo);
	}
	
	@Test
	public void lowerThanPassed() throws FailedValidationException, ParseException {
		ConstraintValidator validator =  new ConstraintValidator();
		Object pojo = new Object() {
			@Constraint(clazz="java.util.Date",ref="basic")
			Date before = Validator.SDF.parse("12-12-2012");
			@Constraint(clazz="java.util.Date",lowerThan="basic")
			Date after = Validator.SDF.parse("12-12-2009");
		};
		validator.validate(pojo);
	}
	
	@Test(expected=FailedValidationException.class)
	public void greaterThanFailed() throws FailedValidationException, ParseException {
		ConstraintValidator validator =  new ConstraintValidator();
		Object pojo = new Object() {
			@Constraint(clazz="java.util.Date",ref="target")
			Date before = Validator.SDF.parse("12-12-2012");
			@Constraint(clazz="java.util.Date",greaterThan="target")
			Date after = Validator.SDF.parse("12-12-2010");
		};
		validator.validate(pojo);
	}
	
	@Test
	public void greaterThanPassed() throws FailedValidationException, ParseException {
		ConstraintValidator validator =  new ConstraintValidator();
		Object pojo = new Object() {
			@Constraint(clazz="java.util.Date",ref="basic")
			Date before = Validator.SDF.parse("12-12-2012");
			@Constraint(clazz="java.util.Date",greaterThan="basic")
			Date after = Validator.SDF.parse("12-12-2019");
		};
		validator.validate(pojo);
	}
	
	@Test(expected=FailedValidationException.class)
	public void matchesFailed() throws FailedValidationException, ParseException {
		ConstraintValidator validator =  new ConstraintValidator();
		Object pojo = new Object() {
			@Constraint(matches="run")
			String blabla = "pretty";
		};
		validator.validate(pojo);
	}
	@Test
	public void matchesPassed() throws FailedValidationException, ParseException {
		ConstraintValidator validator =  new ConstraintValidator();
		Object pojo = new Object() {
			@Constraint(matches="run")
			String blabla = "runnable";
		};
		validator.validate(pojo);
	}
	
	@Test(expected=FailedValidationException.class)
	public void inListFailed() throws FailedValidationException, ParseException {
		ConstraintValidator validator =  new ConstraintValidator();
		Object pojo = new Object() {
			@Constraint(inList="good,bad")
			String blabla = "pretty";
		};
		validator.validate(pojo);
	}
	@Test
	public void inListPassed() throws FailedValidationException, ParseException {
		ConstraintValidator validator =  new ConstraintValidator();
		Object pojo = new Object() {
			@Constraint(inList="good,bad")
			String blabla = "good";
		};
		validator.validate(pojo);
	}
	
	@Test(expected=FailedValidationException.class)
	public void blankFailed() throws FailedValidationException, ParseException {
		ConstraintValidator validator =  new ConstraintValidator();
		Object pojo = new Object() {
			@Constraint(blank=false)
			String blabla = "";
		};
		validator.validate(pojo);
	}
	@Test
	public void blankPassed() throws FailedValidationException, ParseException {
		ConstraintValidator validator =  new ConstraintValidator();
		Object pojo = new Object() {
			@Constraint(blank=false)
			String blabla = "not blank";
		};
		validator.validate(pojo);
	}
	
	@Test(expected=FailedValidationException.class)
	public void nullableFailed() throws FailedValidationException, ParseException {
		ConstraintValidator validator =  new ConstraintValidator();
		Object pojo = new Object() {
			@Constraint(nullable=false)
			String blabla = null;
		};
		validator.validate(pojo);
	}
	@Test
	public void nullablePassed() throws FailedValidationException, ParseException {
		ConstraintValidator validator =  new ConstraintValidator();
		Object pojo = new Object() {
			@Constraint(nullable=false)
			String blabla = "";
		};
		validator.validate(pojo);
	}
	
	@Test(expected=FailedValidationException.class)
	public void maxValueFailed() throws FailedValidationException, ParseException {
		ConstraintValidator validator =  new ConstraintValidator();
		Object pojo = new Object() {
			@Constraint(clazz="java.lang.Long",maxValue=4)
			Long id = 13L;
		};
		validator.validate(pojo);
	}
	@Test
	public void maxValuePassed() throws FailedValidationException, ParseException {
		ConstraintValidator validator =  new ConstraintValidator();
		Object pojo = new Object() {
			@Constraint(clazz="java.lang.Long",maxValue=14)
			Long id = 6L;
		};
		validator.validate(pojo);
	}
	
	@Test(expected=FailedValidationException.class)
	public void minValueFailed() throws FailedValidationException, ParseException {
		ConstraintValidator validator =  new ConstraintValidator();
		Object pojo = new Object() {
			@Constraint(clazz="java.lang.Long",minValue=4)
			Long id = 3L;
		};
		validator.validate(pojo);
	}
	@Test
	public void minValuePassed() throws FailedValidationException, ParseException {
		ConstraintValidator validator =  new ConstraintValidator();
		Object pojo = new Object() {
			@Constraint(clazz="java.lang.Long",minValue=4)
			Long id = 6L;
		};
		validator.validate(pojo);
	}
	
	@Test(expected=FailedValidationException.class)
	public void maxSizeFailed() throws FailedValidationException, ParseException {
		ConstraintValidator validator =  new ConstraintValidator();
		Object pojo = new Object() {
			@Constraint(maxSize=4)
			String name = "runner";
		};
		validator.validate(pojo);
	}
	@Test
	public void maxSizePassed() throws FailedValidationException, ParseException {
		ConstraintValidator validator =  new ConstraintValidator();
		Object pojo = new Object() {
			@Constraint(maxSize=8)
			String name = "runner";
		};
		validator.validate(pojo);
	}

	@Test(expected=FailedValidationException.class)
	public void minSizeFailed() throws FailedValidationException, ParseException {
		ConstraintValidator validator =  new ConstraintValidator();
		Object pojo = new Object() {
			@Constraint(minSize=8)
			String name = "runner";
		};
		validator.validate(pojo);
	}
	@Test
	public void minSizePassed() throws FailedValidationException, ParseException {
		ConstraintValidator validator =  new ConstraintValidator();
		Object pojo = new Object() {
			@Constraint(minSize=4)
			String name = "runner";
		};
		validator.validate(pojo);
	}
	

}

