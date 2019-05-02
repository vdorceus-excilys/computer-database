package com.excilys.training.validator.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.training.dto.DataTransferObject;
import com.excilys.training.dto.DefaultComputerSkin;
import com.excilys.training.model.Computer;
import com.excilys.training.validator.FailedValidationException;
import com.excilys.training.validator.Validator;

public class ComputerDTOValidator implements Validator<DataTransferObject<Computer>> {
	
	private static Date MINIMUM_DATE_LIMIT=null;
	private static Date MAXIMUM_DATE_LIMIT=null;	
	private static Logger logger = LogManager.getLogger(ComputerDTOValidator.class);
	private static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

	static {
		try{
			MINIMUM_DATE_LIMIT = SDF.parse("01-01-1970");
		}catch(ParseException exp) {
			logger.error("ParseException occured while staticly initializing ComputerDefaultValidator class",exp);
		}finally {
			MAXIMUM_DATE_LIMIT = new Date();
		}
	}
	
	@Override
	public void validate(DataTransferObject<Computer> model) throws FailedValidationException,ParseException {
		// TODO Auto-generated method stub
		DefaultComputerSkin computerDTO = (DefaultComputerSkin) model;
		Boolean valid = (computerDTO!=null) &&
				(computerDTO.getId()==null || Long.valueOf(computerDTO.getId().trim())>1L) &&
				(computerDTO.getName()!=null && computerDTO.getName().trim().length()>2);
		
		if(computerDTO.getIntroduced()!=null || !computerDTO.getIntroduced().trim().isEmpty()) {
			valid = valid && (computerDTO.getIntroduced().trim().length()==10);
			if(valid) {
				Date introduced = SDF.parse(computerDTO.getIntroduced().trim());
				valid &= introduced.compareTo(MINIMUM_DATE_LIMIT)>0;
			}
				
		}
		if(computerDTO.getDiscontinued()!=null || !computerDTO.getDiscontinued().trim().isEmpty()) {
			valid = valid && (computerDTO.getDiscontinued().trim().length()==10);
			if(valid) {
				Date discontinued = SDF.parse(computerDTO.getDiscontinued().trim());
				valid &= discontinued.compareTo(MINIMUM_DATE_LIMIT)>0;
			}
		}
		
		if(!valid)
			throw new FailedValidationException();
	}

}
