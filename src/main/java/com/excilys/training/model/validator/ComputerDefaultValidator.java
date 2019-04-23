package com.excilys.training.model.validator;

import java.text.ParseException;
import java.util.Date;

import com.excilys.training.model.Computer;
import com.excilys.training.util.log.SimpleLog;
import com.excilys.training.util.log.SimpleLogEntry;


public class ComputerDefaultValidator implements Validator<Computer> {
	private static Date MINIMUM_DATE_LIMIT=null;
	private static Date MAXIMUM_DATE_LIMIT=null;
	private static SimpleLog log = SimpleLog.getInstance();
	
	static {
		try{
			MINIMUM_DATE_LIMIT = SDF.parse("01-01-1970");
		}catch(ParseException exp) {
			//log the bloddy thing
			SimpleLogEntry e = new SimpleLogEntry(exp, "FAILED TO PARSE THE MINIMUM DATE FOR COMPUTER DEFAULT VALIDATOR");
			log.log(e);
		}finally {
			MAXIMUM_DATE_LIMIT = new Date();
		}
	}
	
	public void validate(Computer computer) throws FailedValidationException {		
		Boolean valid = false;
		try {
			valid = (computer!=null)
				&& (computer.getId()>0)
				&& (computer.getName().length()>2)
				&& (computer.getIntroduced()==null || (
						computer.getIntroduced().compareTo(MINIMUM_DATE_LIMIT)<0 &&
						computer.getIntroduced().compareTo(MAXIMUM_DATE_LIMIT)>=0)
					)
				&& (computer.getDiscontinued()==null || computer.getIntroduced()!=null &&(
						computer.getDiscontinued().compareTo(computer.getIntroduced())<0 &&
						computer.getDiscontinued().compareTo(MAXIMUM_DATE_LIMIT)>=0)
					)
				;
		}catch(Exception exp) {
			SimpleLogEntry  s = new SimpleLogEntry(exp,"ERROR WHILE TRYING RUNNING VALIDATOR FOR COMPUTER MODEL");
			log.log(s);
		}
		if(!valid)
			throw new FailedValidationException();
	}
}
